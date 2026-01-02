import com.tidal.sdk.sdkModules
import java.time.LocalDate

plugins {
    alias(libs.plugins.kotlin.dokka)
    alias(libs.plugins.tidal.android.application) apply false
    alias(libs.plugins.tidal.android.library) apply false
    alias(libs.plugins.tidal.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.tidal.jvm.platform) apply false
}

tasks.register("printSdkModules") { doLast { sdkModules.forEach { println(it.name) } } }

sdkModules.forEach {
    rootProject.tasks.register("publish-sdk-module-${it.name}") {
        (it.subprojects + it).forEach {
            it.tasks.findByName("publishToMavenCentral")?.let { dependsOn(it) }
        }
    }
}

tasks.dokkaHtmlMultiModule.configure {
    moduleVersion = "bom-${project(":bom").property("version")}"
    includes.setFrom("README.md")
    pluginsMapConfiguration.set(
        mapOf(
            "org.jetbrains.dokka.base.DokkaBase" to
                """{"footerMessage": "© ${LocalDate.now().year} TIDAL"}"""
        )
    )
}

// Task to detect affected modules based on git changes
tasks.register("detectAffectedModules") {
    group = "verification"
    description = "Detects which modules are affected by git changes and outputs their test tasks"

    doLast {
        val baseRef = project.findProperty("baseRef") as? String ?: "origin/main"
        val runAllTests = project.findProperty("runAllTests") as? String == "true"

        // Get changed files from git
        val changedFiles = if (runAllTests) {
            logger.lifecycle("Force running all tests (runAllTests=true)")
            listOf("build.gradle.kts") // Trigger all tests
        } else {
            val gitCommand = listOf("git", "diff", "--name-only", "$baseRef...HEAD")
            val process = ProcessBuilder(gitCommand)
                .directory(rootDir)
                .start()

            val output = process.inputStream.bufferedReader().use { it.readText() }
            val exitCode = process.waitFor()

            if (exitCode != 0) {
                logger.error("Git command failed with exit code $exitCode")
                logger.error(process.errorStream.bufferedReader().use { it.readText() })
                throw RuntimeException("Failed to get git diff")
            }

            output.lines().filter { it.isNotBlank() }
        }

        logger.lifecycle("Detecting affected modules for changes since: $baseRef")
        logger.lifecycle("Changed files: ${changedFiles.size}")
        changedFiles.forEach { logger.debug("  - $it") }

        // Check for root-level changes that affect all modules
        val rootLevelPatterns = listOf(
            "build.gradle.kts",
            "settings.gradle.kts",
            "gradle.properties",
            "gradlew",
            "gradlew.bat"
        )

        val hasRootLevelChanges = changedFiles.any { file ->
            rootLevelPatterns.any { pattern -> file == pattern } ||
            file.startsWith("gradle/") ||
            file.startsWith("buildlogic/") ||
            file.startsWith(".github/workflows/")
        }

        if (hasRootLevelChanges) {
            logger.lifecycle("Root-level changes detected - will run all tests")
        }

        // Map changed files to their containing projects
        val directlyAffectedProjects = mutableSetOf<Project>()

        if (hasRootLevelChanges) {
            // All projects are affected
            directlyAffectedProjects.addAll(allprojects)
        } else {
            changedFiles.forEach { file ->
                allprojects.forEach { project ->
                    if (project != rootProject) {
                        val projectPath = project.projectDir.relativeTo(rootDir).path
                        if (file.startsWith("$projectPath/")) {
                            directlyAffectedProjects.add(project)
                            logger.debug("File $file affects project ${project.path}")
                        }
                    }
                }
            }
        }

        logger.lifecycle("Directly affected projects: ${directlyAffectedProjects.map { it.path }}")

        // Find all projects that depend on the affected projects
        val allAffectedProjects = mutableSetOf<Project>()
        allAffectedProjects.addAll(directlyAffectedProjects)

        // Iterate through all projects to find those that depend on affected projects
        allprojects.forEach { project ->
            project.configurations.forEach { configuration ->
                configuration.dependencies.forEach { dependency ->
                    if (dependency is org.gradle.api.artifacts.ProjectDependency) {
                        if (dependency.dependencyProject in directlyAffectedProjects) {
                            allAffectedProjects.add(project)
                            logger.debug("Project ${project.path} depends on affected project ${dependency.dependencyProject.path}")
                        }
                    }
                }
            }
        }

        // Handle transitive dependencies (iterate until no new projects are added)
        var foundNew = true
        while (foundNew) {
            val currentSize = allAffectedProjects.size
            val currentAffected = allAffectedProjects.toSet()

            allprojects.forEach { project ->
                project.configurations.forEach { configuration ->
                    configuration.dependencies.forEach { dependency ->
                        if (dependency is org.gradle.api.artifacts.ProjectDependency) {
                            if (dependency.dependencyProject in currentAffected) {
                                allAffectedProjects.add(project)
                            }
                        }
                    }
                }
            }

            foundNew = allAffectedProjects.size > currentSize
        }

        logger.lifecycle("All affected projects (including dependents): ${allAffectedProjects.map { it.path }}")

        // Generate test tasks for affected projects
        val testTasks = mutableListOf<String>()
        val modulesToSkip = setOf("buildlogic", "template", "bom")

        allAffectedProjects.forEach { project ->
            if (project != rootProject && project.name !in modulesToSkip) {
                // Add main test task if it exists
                if (project.tasks.findByName("test") != null ||
                    project.tasks.findByName("testReleaseUnitTest") != null ||
                    project.tasks.findByName("testDebugUnitTest") != null) {

                    // For Android projects, use the test task that aggregates all variants
                    val testTask = when {
                        project.tasks.findByName("test") != null -> "${project.path}:test"
                        else -> "${project.path}:testReleaseUnitTest"
                    }
                    testTasks.add(testTask)
                    logger.debug("Added test task: $testTask")
                }

                // Check for demo app tests
                val demoProject = project.subprojects.find {
                    it.name == "demo" || it.path.endsWith(":apps:demo")
                }

                if (demoProject != null) {
                    val demoTestTask = when {
                        demoProject.tasks.findByName("test") != null -> "${demoProject.path}:test"
                        demoProject.tasks.findByName("testReleaseUnitTest") != null -> "${demoProject.path}:testReleaseUnitTest"
                        else -> null
                    }

                    if (demoTestTask != null) {
                        testTasks.add(demoTestTask)
                        logger.debug("Added demo test task: $demoTestTask")
                    }
                }
            }
        }

        // Output the test tasks
        val output = testTasks.distinct().sorted().joinToString(" ")

        if (output.isBlank()) {
            logger.lifecycle("No test tasks to run")
            println("") // Empty output means no tests
        } else {
            logger.lifecycle("Test tasks to run: $output")
            println(output) // This is captured by the GitHub workflow
        }

        // Also write to a file for easier debugging
        val outputFile = file("build/affected-tests.txt")
        outputFile.parentFile.mkdirs()
        outputFile.writeText(output)
        logger.lifecycle("Test tasks also written to: ${outputFile.absolutePath}")
    }
}
