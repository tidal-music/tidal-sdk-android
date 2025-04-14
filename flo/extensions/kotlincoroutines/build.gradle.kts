plugins {
    alias(libs.plugins.tidal.android.library)
}

android {
    namespace = "com.tidal.sdk.flo.extensions.kotlincoroutines"
}

dependencies {
    api(project(":flo"))
    api(libs.kotlinxCoroutinesCore)
}
