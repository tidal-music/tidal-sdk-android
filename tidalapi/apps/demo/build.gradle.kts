plugins {
    alias(libs.plugins.tidal.android.application)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.tidal.sdk.tidalapi.demo"

    defaultConfig {
        applicationId = "com.tidal.sdk.tidalapi.demo"
        versionCode = 1
        versionName = "0.2.0"
    }

    buildTypes {
        debug {}
        composeOptions {
            kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
        }
    }
    packagingOptions {
        resources.excludes.apply {
            add("META-INF/LICENSE.md")
            add("META-INF/LICENSE-notice.md")
        }
    }
}

dependencies {
    implementation(project(":tidalapi"))

    implementation(libs.bundles.compose)
    implementation(libs.androidx.core.ktx)
}
