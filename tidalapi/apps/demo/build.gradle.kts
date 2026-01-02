plugins {
    alias(libs.plugins.tidal.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.tidal.sdk.tidalapi.demo"

    defaultConfig {
        applicationId = "com.tidal.sdk.tidalapi.demo"
        versionCode = 1
        versionName = "0.1.0"
    }

    buildTypes {
        debug {}
    }


    packaging {
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
