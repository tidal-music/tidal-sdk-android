package com.tidal.sdk.plugins

import com.tidal.sdk.plugins.constant.Config
import com.tidal.sdk.plugins.constant.PluginId
import com.tidal.sdk.plugins.extensions.androidLibrary
import kotlin.collections.set
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension

internal class KotlinAndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = target.run {
        pluginManager.apply(PluginId.ANDROID_LIBRARY_PLUGIN_ID)
        pluginManager.apply(KotlinAndroidConventionPlugin::class.java)
        ConfiguresDokka()(this)
        val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
        val dokkaAndroidLibrary = libs.findLibrary("dokka-android").get().get()
        dependencies.add("dokkaPlugin", dokkaAndroidLibrary)
        val testAndroidXRunnerLibrary = libs.findLibrary("test-androidx-runner").get()
        dependencies.add("androidTestImplementation", testAndroidXRunnerLibrary)
        val testAndroidXOrchestratorLibrary = libs.findLibrary("test-androidx-orchestrator").get()
        dependencies.add("androidTestUtil", testAndroidXOrchestratorLibrary)
        ConfiguresMavenPublish()(this)
        configureLibrary()
    }

    private fun Project.configureLibrary() {
        androidLibrary {
            compileSdk = Config.ANDROID_COMPILE_SDK

            defaultConfig {
                minSdk = Config.ANDROID_MIN_SDK
                testInstrumentationRunner = Config.ANDROID_TEST_RUNNER_JUNIT
                consumerProguardFiles("consumer-rules.pro")
                testInstrumentationRunnerArguments["clearPackageData"] = "true"
            }

            testOptions {
                execution = "ANDROIDX_TEST_ORCHESTRATOR"
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
            }

            buildTypes {
                release {
                    isMinifyEnabled = false
                }
            }
        }
    }
}
