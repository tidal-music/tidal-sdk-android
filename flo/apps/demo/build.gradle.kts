plugins {
    alias(libs.plugins.tidal.android.application)
}

android {
    namespace = "com.tidal.sdk.flo.demo"

    buildFeatures {
        compose = false
    }

    defaultConfig {
        applicationId = "com.tidal.sdk.flo.demo"
        versionCode = 1
        versionName = "0.1.0"
    }

    flavorDimensions += "api"
    productFlavors {
        create("core") {
            dimension = "api"
            applicationIdSuffix = ".core"
        }
        create("kotlincoroutines") {
            dimension = "api"
            applicationIdSuffix = ".kotlincoroutines"
        }
        create("rxjava3") {
            dimension = "api"
            applicationIdSuffix = ".rxjava3"
        }
        create("rxjava2") {
            dimension = "api"
            applicationIdSuffix = ".rxjava2"
        }
        create("rxjava") {
            dimension = "api"
            applicationIdSuffix = ".rxjava"
        }
    }
}

dependencies {
    implementation(project(":flo"))
    "kotlincoroutinesImplementation"(project(":flo:extensions:kotlincoroutines"))
    "rxjava3Implementation"(project(":flo:extensions:rxjava3"))
    "rxjava2Implementation"(project(":flo:extensions:rxjava2"))
    "rxjavaImplementation"(project(":flo:extensions:rxjava"))
}
