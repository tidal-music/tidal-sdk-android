package com.tidal.sdk.plugins.extensions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
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
