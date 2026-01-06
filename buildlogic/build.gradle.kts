plugins { `kotlin-dsl` }

dependencies {
    compileOnly(gradleApi())
    implementation(libs.plugin.kotlin.android)
    implementation(libs.plugin.dokka)
    implementation(libs.plugin.android.tools.build)
    implementation(libs.plugin.gradle.maven.publish)
}

gradlePlugin {
    plugins {
        register("tidal-sdk-kotlin-jvm") {
            id = libs.plugins.tidal.kotlin.jvm.get().pluginId
            version = libs.plugins.tidal.kotlin.jvm.get().version
            implementationClass = "com.tidal.sdk.plugins.KotlinJvmLibraryConventionPlugin"
        }

        register("tidal-sdk-android-library") {
            id = libs.plugins.tidal.android.library.get().pluginId
            version = libs.plugins.tidal.android.library.get().version
            implementationClass = "com.tidal.sdk.plugins.KotlinAndroidLibraryConventionPlugin"
        }

        register("tidal-sdk-android-application") {
            id = libs.plugins.tidal.android.application.get().pluginId
            version = libs.plugins.tidal.android.application.get().version
            implementationClass = "com.tidal.sdk.plugins.KotlinAndroidApplicationConventionPlugin"
        }

        register("tidal-sdk-jvm-platform") {
            id = libs.plugins.tidal.jvm.platform.get().pluginId
            version = libs.plugins.tidal.jvm.platform.get().version
            implementationClass = "com.tidal.sdk.plugins.JvmPlatformConventionPlugin"
        }

        register("tidal-sdk-affected-test-detection") {
            id = "com.tidal.sdk.affected-test-detection"
            version = "1.0.0"
            implementationClass = "com.tidal.sdk.plugins.AffectedTestDetectionPlugin"
        }
    }
}
