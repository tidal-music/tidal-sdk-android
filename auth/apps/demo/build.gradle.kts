import com.tidal.sdk.plugins.extensions.loadLocalProperties

plugins {
    alias(libs.plugins.tidal.android.application)
}

android {
    namespace = "com.tidal.sdk.auth.demo"

    defaultConfig {
        applicationId = "com.tidal.sdk.auth.demo"
        versionCode = 1
        versionName = "0.1.0"
    }

    buildTypes {
        all {
            buildConfigField(
                "String",
                "TIDAL_CLIENT_SCOPES",
                "${project.loadLocalProperties()["tidal.clientscopes"]}",
            )
        }
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
    implementation(project(":auth"))

    implementation(libs.bundles.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime)

    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("com.github.tony19:logback-android:3.0.0")
}
