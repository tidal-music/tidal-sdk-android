plugins {
    alias(libs.plugins.tidal.android.library)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.android.junit5)
}

android {
    namespace = "com.tidal.sdk.catalogue"
}

dependencies {

    api(libs.tidal.sdk.common)

    implementation(libs.kotlinxCoroutinesCore)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.loggingInterceptor)
    implementation(libs.retrofit)

    testApi(libs.test.androidx.junit)
    testApi(libs.test.junit5Api)
    testApi(libs.test.junit5Engine)

    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
}
