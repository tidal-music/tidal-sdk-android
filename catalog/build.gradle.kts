plugins {
    alias(libs.plugins.tidal.android.library)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.android.junit5)
}

android {
    namespace = "com.tidal.sdk.catalog"
}

dependencies {
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapters)
    implementation(libs.okhttp.okhttp)
    testImplementation(libs.kotlintest.runner.junit5)
    api(libs.tidal.sdk.common)

    testApi(libs.test.androidx.junit)
    testApi(libs.test.junit5Api)
    testApi(libs.test.junit5Engine)

    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
}
