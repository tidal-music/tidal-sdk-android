import java.util.Properties
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.tidal.android.application)
}

val localProperties = Properties()
val localPropertiesFile: File = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

val tidalClientId = "tidal.clientid"
val clientId = localProperties[tidalClientId]

android {
    namespace = "com.tidal.sdk.player"

    defaultConfig {
        applicationId = "com.tidal.sdk.player"
        versionCode = 1
        versionName = "0.1.0"
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
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    kotlinOptions {
        freeCompilerArgs += "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
        freeCompilerArgs += "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi"
    }
}

dependencies {
    implementation(project(":player"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.truetime)
}

val ensureClientIdPresent: Task = tasks.create("ensureClientIdPresent") {
    doLast {
        if (clientId == null && System.getenv("CI").isNullOrBlank()) {
            throw InvalidUserDataException(
                "$tidalClientId missing in ${localPropertiesFile.absolutePath}",
            )
        }
    }
}
tasks.withType(KotlinCompile::class.java).configureEach {
    dependsOn(ensureClientIdPresent)
}
