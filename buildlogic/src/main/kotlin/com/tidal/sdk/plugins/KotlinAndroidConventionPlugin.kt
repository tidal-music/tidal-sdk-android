package com.tidal.sdk.plugins

import com.tidal.sdk.plugins.constant.PluginId
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class KotlinAndroidConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) =
        target.run {
            pluginManager.apply(PluginId.KOTLIN_ANDROID_PLUGIN_ID)
            ConfiguresKotlinCompiler()(this)
            ConfiguresJUnit5()(this)
        }
}
