package com.tidal.sdk.plugins

import com.tidal.sdk.plugins.constant.Config
import com.tidal.sdk.plugins.constant.PluginId
import com.tidal.sdk.plugins.extensions.androidApplication
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension

internal class KotlinAndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = target.run {
        pluginManager.apply(PluginId.ANDROID_APPLICATION_PLUGIN_ID)
        pluginManager.apply(KotlinAndroidConventionPlugin::class.java)
        val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
        val testAndroidXRunnerLibrary = libs.findLibrary("test-androidx-runner").get()
        dependencies.add("androidTestImplementation", testAndroidXRunnerLibrary)
        val androidTestOrchestratorLibrary = libs.findLibrary("test-androidx-orchestrator").get()
        dependencies.add("androidTestUtil", androidTestOrchestratorLibrary)

        configureApplication()
    }

    private fun Project.configureApplication() {
        androidApplication {
            compileSdk = Config.ANDROID_COMPILE_SDK

            defaultConfig {
                targetSdk = Config.ANDROID_TARGET_SDK
                minSdk = Config.ANDROID_MIN_SDK
                testInstrumentationRunner = Config.ANDROID_TEST_RUNNER_JUNIT
                testInstrumentationRunnerArguments["clearPackageData"] = "true"
            }

            testOptions {
                execution = "ANDROIDX_TEST_ORCHESTRATOR"
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            buildTypes {
                release {
                    isMinifyEnabled = false
                }
            }
        }
    }
}
