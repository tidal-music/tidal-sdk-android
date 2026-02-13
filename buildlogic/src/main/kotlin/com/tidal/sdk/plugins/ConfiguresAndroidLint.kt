package com.tidal.sdk.plugins

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

internal class ConfiguresAndroidLint : (Project) -> Unit {

    override fun invoke(target: Project) =
        with(target) {
            extensions.configure(CommonExtension::class.java) {
                lint { disable += "CoroutineCreationDuringComposition" }
            }
        }
}
