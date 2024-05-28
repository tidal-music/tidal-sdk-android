plugins {
    alias(libs.plugins.tidal.android.library)
    alias(libs.plugins.android.junit5)
}

android {
    namespace = "com.tidal.sdk.common"
}

dependencies {
    api(libs.kotlin.logging)
    api(libs.slf4j.api)
}
