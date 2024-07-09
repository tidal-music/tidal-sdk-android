plugins {
    alias(libs.plugins.tidal.android.library)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.android.junit5)
    alias(libs.plugins.kotlin.plugin.serialization)
}

android {
    namespace = "com.tidal.sdk.catalogue"
}
dependencies {

    api(libs.tidal.sdk.common)

    implementation(libs.kotlinxCoroutinesCore)
    api(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.loggingInterceptor)
    api(libs.retrofit)
    implementation(libs.converter.kotlinx.serialization)
    implementation(libs.converter.scalars)
    api(libs.tidal.sdk.auth)

    testApi(libs.test.androidx.junit)
    testApi(libs.test.junit5Api)
    testApi(libs.test.junit5Engine)

    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
}
