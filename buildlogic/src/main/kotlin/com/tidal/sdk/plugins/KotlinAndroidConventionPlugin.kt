package com.tidal.sdk.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

internal class KotlinAndroidConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) =
        target.run {
            ConfiguresKotlinCompiler()(this)
            ConfiguresJUnit5()(this)
            ConfiguresAndroidLint()(this)
        }
}
