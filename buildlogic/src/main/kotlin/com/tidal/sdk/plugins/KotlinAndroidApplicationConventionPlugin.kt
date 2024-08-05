package com.tidal.sdk.plugins

import com.tidal.sdk.plugins.constant.Config
import com.tidal.sdk.plugins.constant.PluginId
import com.tidal.sdk.plugins.extensions.androidApplication
import com.tidal.sdk.plugins.extensions.loadLocalProperties
import com.tidal.sdk.plugins.extensions.localPropertiesFile
import org.gradle.api.InvalidUserDataException
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.TaskProvider
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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

    @Suppress("LongMethod")
    private fun Project.configureApplication() {
        val localProperties = loadLocalProperties()

        val tidalClientId = "tidal.clientid"
        val clientId = localProperties[tidalClientId]
        val tidalClientRedirectUri = "tidal.clientredirecturi"
        val clientRedirectUri = localProperties[tidalClientRedirectUri]
        androidApplication {
            compileSdk = Config.ANDROID_COMPILE_SDK

            defaultConfig {
                targetSdk = Config.ANDROID_TARGET_SDK
                minSdk = Config.ANDROID_MIN_SDK
                buildConfigField(
                    "String",
                    "TIDAL_CLIENT_ID",
                    "$clientId",
                )
                buildConfigField(
                    "String",
                    "TIDAL_CLIENT_SECRET",
                    "${localProperties["tidal.clientsecret"]}",
                )
                buildConfigField(
                    "String",
                    "TIDAL_CLIENT_REDIRECT_URI",
                    "$clientRedirectUri",
                )
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

            buildFeatures {
                buildConfig = true
                compose = true
            }
        }
        val ensureClientPropsPresent: TaskProvider<*> = tasks.register("ensureClientPropsPresent") {
            doLast {
                arrayOf(
                    tidalClientId to clientId,
                    tidalClientRedirectUri to clientRedirectUri,
                ).forEach { (key, value) ->
                    if (value == null && System.getenv("CI").isNullOrBlank()) {
                        throw InvalidUserDataException(
                            "$key missing in ${localPropertiesFile.absolutePath}",
                        )
                    }
                }
            }
        }
        tasks.withType(KotlinCompile::class.java).configureEach {
            dependsOn(ensureClientPropsPresent)
        }
    }
}
