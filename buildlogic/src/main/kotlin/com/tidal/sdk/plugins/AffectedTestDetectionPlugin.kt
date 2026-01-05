package com.tidal.sdk.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
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
                description = "Detects which modules are affected by git changes and outputs their test tasks"

                doLast {
                    val detector = AffectedModuleDetector(project)
                    detector.detectAndOutputAffectedTestTasks()
                }
            }
        }
    }
}

/**
 * Handles the detection of affected modules and generation of test tasks
 */
private class AffectedModuleDetector(private val project: Project) {
    private val logger = project.logger
    private val rootDir = project.rootDir
    private val allProjects = project.allprojects

    private val rootLevelPatterns = listOf(
        "build.gradle.kts",
        "settings.gradle.kts",
        "gradle.properties",
        "gradlew",
        "gradlew.bat"
    )

    private val modulesToSkip = setOf("buildlogic", "template", "bom")

    fun detectAndOutputAffectedTestTasks() {
        val baseRef = project.findProperty("baseRef") as? String ?: "origin/main"
        val runAllTests = project.findProperty("runAllTests") as? String == "true"

        val changedFiles = getChangedFiles(baseRef, runAllTests)
        logChangedFiles(baseRef, changedFiles)

        val affectedProjects = findAffectedProjects(changedFiles)
        val testTasks = generateTestTasks(affectedProjects)

        outputTestTasks(testTasks)
    }

    private fun getChangedFiles(baseRef: String, runAllTests: Boolean): List<String> {
        if (runAllTests) {
            logger.lifecycle("Force running all tests (runAllTests=true)")
            return listOf("build.gradle.kts") // Trigger all tests
        }

        return runGitDiff(baseRef)
    }

    private fun runGitDiff(baseRef: String): List<String> {
        val gitCommand = listOf("git", "diff", "--name-only", "$baseRef...HEAD")
        val process = ProcessBuilder(gitCommand)
            .directory(rootDir)
            .start()

        val output = process.inputStream.bufferedReader().use { it.readText() }
        val exitCode = process.waitFor()

        if (exitCode != 0) {
            logger.error("Git command failed with exit code $exitCode")
            logger.error(process.errorStream.bufferedReader().use { it.readText() })
            @Suppress("TooGenericExceptionThrown")
            throw RuntimeException("Failed to get git diff")
        }

        return output.lines().filter { it.isNotBlank() }
    }

    private fun logChangedFiles(baseRef: String, changedFiles: List<String>) {
        logger.lifecycle("Detecting affected modules for changes since: $baseRef")
        logger.lifecycle("Changed files: ${changedFiles.size}")
        changedFiles.forEach { logger.debug("  - $it") }
    }

    private fun findAffectedProjects(changedFiles: List<String>): Set<Project> {
        val hasRootLevelChanges = hasRootLevelChanges(changedFiles)

        if (hasRootLevelChanges) {
            logger.debug("Root-level changes detected - will run all tests")
        }

        val directlyAffectedProjects = findDirectlyAffectedProjects(changedFiles, hasRootLevelChanges)
        logger.debug("Directly affected projects: ${directlyAffectedProjects.map { it.path }}")

        val allAffectedProjects = findAllDependentProjects(directlyAffectedProjects)
        logger.debug("All affected projects (including dependents): ${allAffectedProjects.map { it.path }}")

        return allAffectedProjects
    }

    private fun hasRootLevelChanges(changedFiles: List<String>): Boolean {
        return changedFiles.any { file ->
            rootLevelPatterns.any { pattern -> file == pattern } ||
            file.startsWith("gradle/") ||
            file.startsWith("buildlogic/") ||
            file.startsWith(".github/workflows/")
        }
    }

    private fun findDirectlyAffectedProjects(
        changedFiles: List<String>,
        hasRootLevelChanges: Boolean
    ): Set<Project> {
        val directlyAffectedProjects = mutableSetOf<Project>()

        if (hasRootLevelChanges) {
            directlyAffectedProjects.addAll(allProjects)
        } else {
            changedFiles.forEach { file ->
                allProjects.forEach { project ->
                    if (project != project.rootProject) {
                        val projectPath = project.projectDir.relativeTo(rootDir).path
                        if (file.startsWith("$projectPath/")) {
                            directlyAffectedProjects.add(project)
                            logger.debug("File $file affects project ${project.path}")
                        }
                    }
                }
            }
        }

        return directlyAffectedProjects
    }

    private fun findAllDependentProjects(directlyAffectedProjects: Set<Project>): Set<Project> {
        val allAffectedProjects = mutableSetOf<Project>()
        allAffectedProjects.addAll(directlyAffectedProjects)

        // Find initial dependents
        findProjectsDependingOn(directlyAffectedProjects, allAffectedProjects)

        // Handle transitive dependencies
        var foundNew = true
        while (foundNew) {
            val currentSize = allAffectedProjects.size
            val currentAffected = allAffectedProjects.toSet()

            findProjectsDependingOn(currentAffected, allAffectedProjects)

            foundNew = allAffectedProjects.size > currentSize
        }

        return allAffectedProjects
    }

    private fun findProjectsDependingOn(sourceProjects: Set<Project>, targetSet: MutableSet<Project>) {
        allProjects.forEach { project ->
            project.configurations.forEach { configuration ->
                configuration.dependencies.forEach { dependency ->
                    if (dependency is ProjectDependency) {
                        @Suppress("DEPRECATION")
                        if (dependency.dependencyProject in sourceProjects) {
                            targetSet.add(project)
                            @Suppress("DEPRECATION")
                            logger.debug("Project ${project.path} depends on affected project ${dependency.dependencyProject.path}")
                        }
                    }
                }
            }
        }
    }

    private fun generateTestTasks(affectedProjects: Set<Project>): List<String> {
        val testTasks = mutableListOf<String>()

        affectedProjects.forEach { project ->
            if (project != project.rootProject && project.name !in modulesToSkip) {
                addProjectTestTasks(project, testTasks)
                addDemoTestTasks(project, testTasks)
            }
        }

        return testTasks.distinct().sorted()
    }

    private fun addProjectTestTasks(project: Project, testTasks: MutableList<String>) {
        if (hasTestTask(project)) {
            val testTask = when {
                project.tasks.findByName("test") != null -> "${project.path}:test"
                else -> "${project.path}:testReleaseUnitTest"
            }
            testTasks.add(testTask)
            logger.debug("Added test task: $testTask")
        }
    }

    private fun hasTestTask(project: Project): Boolean {
        return project.tasks.findByName("test") != null ||
               project.tasks.findByName("testReleaseUnitTest") != null ||
               project.tasks.findByName("testDebugUnitTest") != null
    }

    private fun addDemoTestTasks(project: Project, testTasks: MutableList<String>) {
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

    private fun outputTestTasks(testTasks: List<String>) {
        val output = testTasks.joinToString(" ")

        if (output.isBlank()) {
            logger.debug("No test tasks to run")
            println("") // Empty output means no tests
        } else {
            logger.debug("Test tasks to run: $output")
            println(output) // This is captured by the GitHub workflow
        }

        writeTestTasksToFile(output)
    }

    private fun writeTestTasksToFile(output: String) {
        val outputFile = project.file("build/affected-tests.txt")
        outputFile.parentFile.mkdirs()
        outputFile.writeText(output)
        logger.debug("Test tasks also written to: ${outputFile.absolutePath}")
    }
}