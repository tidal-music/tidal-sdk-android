plugins {
    alias(libs.plugins.tidal.android.library)
    alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.tidal.sdk.flo"
}

dependencies {
    ksp(libs.moshi.codegen)
    implementation(libs.moshi)
    implementation(libs.moshi.adapters)
    api(libs.okhttp.okhttp)
}
