package com.tidal.sdk.plugins.extensions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import java.io.File
import java.util.Properties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

/**
 * Extension function that allows configuring Gradle's [ApplicationExtension]
 * Same as calling:
 * android {
 *    ...
 * }
 * block in an application module
 */
internal fun Project.androidApplication(action: ApplicationExtension.() -> Unit) {
    action.invoke(extensions.getByType())
}

/**
 * Extension function that allows configuring Gradle's [LibraryExtension]
 * Same as calling:
 * android {
 *    ...
 * }
 * block in a library module
 */
internal fun Project.androidLibrary(action: LibraryExtension.() -> Unit) {
    action.invoke(extensions.getByType())
}

/**
 * Extension function that loads properties from a 'local.properties' file
 * 'local.properties' is expected to be in the project root.
 */
fun Project.loadLocalProperties(): Properties {
    val properties = Properties()
    with(localPropertiesFile) {
        if (exists()) {
            properties.load(inputStream())
        } else {
            logger.warn("Attempted to read 'local.properties' but file was not found!")
        }
    }
    return properties
}

/**
 * Extension property that returns the 'local.properties' file
 * 'local.properties' is expected to be in the project root.
 */
val Project.localPropertiesFile: File get() = rootProject.file("local.properties")
