plugins {
    alias(libs.plugins.tidal.android.application)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.tidal.sdk.eventproducer.demo"

    defaultConfig {
        applicationId = "com.tidal.sdk.eventproducer.demo"
        versionCode = 1
        versionName = "0.1.0"
    }

    buildTypes { debug {} }
    packagingOptions {
        resources.excludes.apply {
            add("META-INF/LICENSE.md")
            add("META-INF/LICENSE-notice.md")
        }
    }
}

dependencies {
    implementation(project(":eventproducer"))

    implementation(libs.bundles.compose)
    implementation(libs.androidx.core.ktx)
}
