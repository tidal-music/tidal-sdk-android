package com.tidal.sdk.plugins

import java.io.File
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.logging.Logger
import org.gradle.kotlin.dsl.register

/**
 * Plugin that detects which modules are affected by code changes and determines which tests to run.
 * Uses Git diff and Gradle's dependency graph to accurately identify all affected modules including
 * transitive dependencies.
 */
class AffectedTestDetectionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            require(this == rootProject) {
                "AffectedTestDetectionPlugin can only be applied to the root project"
            }

            tasks.register("detectAffectedModules") {
                group = "verification"
                description =
                    "Detects which modules are affected by git changes and outputs their test tasks"

                doLast {
                    val detector = AffectedModuleDetector(project)
                    detector.detectAndOutputAffectedTestTasks()
                }
            }
        }
    }
}

private class AffectedModuleDetector(private val project: Project) {
    private val logger: Logger = project.logger
    private val rootDir: File = project.rootDir
    private val allProjects = project.allprojects
    private val rootProject = project.rootProject

    private val rootLevelPatterns =
        listOf(
            "build.gradle.kts",
            "settings.gradle.kts",
            "gradle.properties",
            "gradlew",
            "gradlew.bat",
        )

    private val modulesToSkip = setOf("buildlogic", "template", "bom")
    private val sdkGroupId = "com.tidal.sdk"

    fun detectAndOutputAffectedTestTasks() {
        val baseRef = project.findProperty("baseRef") as? String ?: "origin/main"
        val runAllTests = project.findProperty("runAllTests") as? String == "true"

        logger.lifecycle("Detecting affected modules for changes since: $baseRef")

        val changedFiles = getChangedFiles(baseRef, runAllTests)
        logger.lifecycle("Changed files: ${changedFiles.size}")
        changedFiles.forEach { logger.debug("  - $it") }

        val hasRootLevelChanges = detectRootLevelChanges(changedFiles)
        if (hasRootLevelChanges) {
            logger.lifecycle("Root-level changes detected - will run all tests")
        }

        val directlyAffectedProjects = mapFilesToProjects(changedFiles, hasRootLevelChanges)
        logger.lifecycle("Directly affected projects: ${directlyAffectedProjects.map { it.path }}")

        val allAffectedProjects = findAllAffectedProjects(directlyAffectedProjects)
        logger.lifecycle(
            "All affected projects (including dependents): ${allAffectedProjects.map { it.path }}"
        )

        val testTasks = generateTestTasks(allAffectedProjects)
        outputTestTasks(testTasks)
    }

    private fun getChangedFiles(baseRef: String, runAllTests: Boolean): List<String> {
        if (runAllTests) {
            logger.lifecycle("Force running all tests (runAllTests=true)")
            return listOf("build.gradle.kts") // Trigger all tests
        }

        val gitCommand = listOf("git", "diff", "--name-only", "$baseRef...HEAD")
        val process = ProcessBuilder(gitCommand).directory(rootDir).start()

        val output = process.inputStream.bufferedReader().use { it.readText() }
        val exitCode = process.waitFor()

        if (exitCode != 0) {
            logger.error("Git command failed with exit code $exitCode")
            logger.error(process.errorStream.bufferedReader().use { it.readText() })
            @Suppress("TooGenericExceptionThrown") throw RuntimeException("Failed to get git diff")
        }

        return output.lines().filter { it.isNotBlank() }
    }

    private fun detectRootLevelChanges(changedFiles: List<String>): Boolean {
        return changedFiles.any { file ->
            rootLevelPatterns.any { pattern -> file == pattern } ||
                file.startsWith("gradle/") ||
                file.startsWith("buildlogic/") ||
                file.startsWith(".github/workflows/")
        }
    }

    private fun mapFilesToProjects(
        changedFiles: List<String>,
        hasRootLevelChanges: Boolean,
    ): Set<Project> {
        if (hasRootLevelChanges) {
            return allProjects.toSet()
        }

        val directlyAffectedProjects = mutableSetOf<Project>()
        changedFiles.forEach { file ->
            findProjectForFile(file)?.let { project ->
                directlyAffectedProjects.add(project)
                logger.debug("File $file affects project ${project.path}")
            }
        }

        return directlyAffectedProjects
    }

    private fun findProjectForFile(file: String): Project? {
        return allProjects.firstOrNull { project ->
            if (project == rootProject) return@firstOrNull false
            val projectPath = project.projectDir.relativeTo(rootDir).path
            file.startsWith("$projectPath/")
        }
    }

    private fun findAllAffectedProjects(directlyAffectedProjects: Set<Project>): Set<Project> {
        val allAffectedProjects = mutableSetOf<Project>()
        allAffectedProjects.addAll(directlyAffectedProjects)

        // Find projects that depend on the affected projects
        findDependentProjects(directlyAffectedProjects, allAffectedProjects)

        // Handle transitive dependencies
        var foundNew = true
        while (foundNew) {
            val currentSize = allAffectedProjects.size
            val currentAffected = allAffectedProjects.toSet()
            findDependentProjects(currentAffected, allAffectedProjects)
            foundNew = allAffectedProjects.size > currentSize
        }

        return allAffectedProjects
    }

    private fun mapExternalDependencyToLocalProject(
        dependency: ExternalModuleDependency
    ): Project? {
        if (dependency.group == sdkGroupId) {
            return allProjects.find { it.name == dependency.name }
        }
        return null
    }

    private fun findDependentProjects(
        sourceProjects: Set<Project>,
        targetSet: MutableSet<Project>,
    ) {
        allProjects.forEach { project ->
            if (projectDependsOnAny(project, sourceProjects)) {
                targetSet.add(project)
            }
        }
    }

    private fun projectDependsOnAny(project: Project, sourceProjects: Set<Project>): Boolean {
        return project.configurations.any { configuration ->
            configuration.dependencies.any { dependency ->
                isDependencyOnAffectedProject(dependency, sourceProjects, project)
            }
        }
    }

    private fun isDependencyOnAffectedProject(
        dependency: org.gradle.api.artifacts.Dependency,
        sourceProjects: Set<Project>,
        dependentProject: Project,
    ): Boolean {
        return when (dependency) {
            is ProjectDependency -> {
                @Suppress("DEPRECATION")
                val isAffected = dependency.dependencyProject in sourceProjects
                if (isAffected) {
                    @Suppress("DEPRECATION")
                    logger.debug(
                        "Project ${dependentProject.path} depends on affected project ${dependency.dependencyProject.path}"
                    )
                }
                isAffected
            }

            is ExternalModuleDependency -> {
                val localProject = mapExternalDependencyToLocalProject(dependency)
                val isAffected = localProject != null && localProject in sourceProjects
                if (isAffected && localProject != null) {
                    logger.debug(
                        "Project ${dependentProject.path} depends on affected project ${localProject.path} via Maven artifact ${dependency.group}:${dependency.name}"
                    )
                }
                isAffected
            }

            else -> false
        }
    }

    private fun generateTestTasks(affectedProjects: Set<Project>): List<String> {
        val testTasks = mutableListOf<String>()

        affectedProjects.forEach { project ->
            if (project != rootProject && project.name !in modulesToSkip) {
                addProjectTestTasks(project, testTasks)
                addDemoTestTasks(project, testTasks)
            }
        }

        return testTasks.distinct().sorted()
    }

    private fun addProjectTestTasks(project: Project, testTasks: MutableList<String>) {
        if (
            project.tasks.findByName("test") != null ||
                project.tasks.findByName("testReleaseUnitTest") != null ||
                project.tasks.findByName("testDebugUnitTest") != null
        ) {

            val testTask =
                when {
                    project.tasks.findByName("test") != null -> "${project.path}:test"
                    else -> "${project.path}:testReleaseUnitTest"
                }
            testTasks.add(testTask)
            logger.debug("Added test task: $testTask")
        }
    }

    private fun addDemoTestTasks(project: Project, testTasks: MutableList<String>) {
        val demoProject =
            project.subprojects.find { it.name == "demo" || it.path.endsWith(":apps:demo") }

        if (demoProject != null) {
            val demoTestTask =
                when {
                    demoProject.tasks.findByName("test") != null -> "${demoProject.path}:test"
                    demoProject.tasks.findByName("testReleaseUnitTest") != null ->
                        "${demoProject.path}:testReleaseUnitTest"
                    else -> null
                }

            if (demoTestTask != null) {
                testTasks.add(demoTestTask)
                logger.debug("Added demo test task: $demoTestTask")
            }
        }
    }

    private fun outputTestTasks(testTasks: List<String>) {
        val output = testTasks.joinToString(" ")

        if (output.isBlank()) {
            logger.lifecycle("No test tasks to run")
            println("") // Empty output means no tests
        } else {
            logger.lifecycle("Test tasks to run: $output")
            println(output) // This is captured by the GitHub workflow
        }

        // Also write to a file for easier debugging
        val outputFile = project.file("build/affected-tests.txt")
        outputFile.parentFile.mkdirs()
        outputFile.writeText(output)
        logger.lifecycle("Test tasks also written to: ${outputFile.absolutePath}")
    }
}
