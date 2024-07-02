import com.tidal.sdk.plugins.extensions.loadLocalProperties

plugins {
    alias(libs.plugins.tidal.android.application)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.tidal.sdk.catalogue.demo"

    defaultConfig {
        applicationId = "com.tidal.sdk.catalogue.demo"
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
            buildConfigField(
                "String",
                "TIDAL_CLIENT_REDIRECT_URI",
                "${project.loadLocalProperties()["tidal.redirecturi"]}",
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
    implementation(project(":catalogue"))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime)

    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("com.github.tony19:logback-android:3.0.0")

    implementation(libs.bundles.compose)
    implementation(libs.androidx.core.ktx)
}
