package com.tidal.sdk.plugins

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project

internal class ConfiguresAndroidLint : (Project) -> Unit {

    override fun invoke(target: Project) {
        target.extensions.findByType(LibraryExtension::class.java)?.lint {
            disable += "CoroutineCreationDuringComposition"
        }
        target.extensions.findByType(ApplicationExtension::class.java)?.lint {
            disable += "CoroutineCreationDuringComposition"
        }
    }
}
