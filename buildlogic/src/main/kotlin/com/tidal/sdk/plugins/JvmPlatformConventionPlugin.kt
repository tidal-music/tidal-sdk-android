package com.tidal.sdk.plugins

import com.tidal.sdk.plugins.constant.PluginId
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class JvmPlatformConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = target.run {
        pluginManager.apply(PluginId.JAVA_PLATFORM_PLUGIN_ID)
        ConfiguresMavenPublish()(this)
    }
}
