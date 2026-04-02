package com.tidal.sdk.plugins

import com.tidal.sdk.plugins.constant.PluginId
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure

internal class KotlinJvmLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) =
        target.run {
            pluginManager.apply(PluginId.KOTLIN_JVM_PLUGIN_ID)

            // Configure Java compatibility
            extensions.configure<JavaPluginExtension> {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
            }

            ConfiguresDokka()(this)
            ConfiguresKotlinCompiler()(this)
            ConfiguresMavenPublish()(this)
            ConfiguresJUnit5()(this)
        }
}
