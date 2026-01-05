import com.tidal.sdk.plugins.extensions.loadLocalProperties

plugins {
    alias(libs.plugins.tidal.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.tidal.sdk.player"

    defaultConfig {
        applicationId = "com.tidal.sdk.player"
        versionCode = 1
        versionName = "0.1.0"
    }

    buildTypes {
        defaultConfig {
            buildConfigField(
                "String",
                "TIDAL_CLIENT_SCOPES",
                "${loadLocalProperties()["tidal.clientscopes"]}",
            )
        }
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    kotlinOptions {
        freeCompilerArgs += listOf(
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi"
        )
    }
}

dependencies {
    implementation(project(":player"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.truetime)
}
