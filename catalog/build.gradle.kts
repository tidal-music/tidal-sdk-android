plugins {
    alias(libs.plugins.tidal.android.library)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.android.junit5)
}

android {
    namespace = "com.tidal.sdk.catalog"
}

dependencies {

    implementation(libs.kotlinxCoroutinesCore)
    implementation(libs.kotlin.reflect)
    implementation(libs.moshi)
    implementation(libs.moshi.adapters)
    implementation(libs.okhttp.okhttp)
    api(libs.tidal.sdk.common)

    testApi(libs.test.androidx.junit)
    testApi(libs.test.junit5Api)
    testApi(libs.test.junit5Engine)

    testImplementation(libs.test.kotlin.test.runner)
    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
}
