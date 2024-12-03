plugins {
    alias(libs.plugins.tidal.android.library)
}

android {
    namespace = "com.tidal.sdk.flo.extensions.rxjava3"
}

dependencies {
    api(project(":flo"))
    api(libs.rxjava3)
}
