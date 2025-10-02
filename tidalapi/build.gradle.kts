plugins {
    alias(libs.plugins.tidal.android.library)
    alias(libs.plugins.android.junit5)
    alias(libs.plugins.kotlin.plugin.serialization)
}

android { namespace = "com.tidal.sdk.tidalapi" }

dependencies {
    api(libs.tidal.sdk.common)
    api(libs.tidal.sdk.auth)

    api(libs.kotlinx.serialization.json)
    api(libs.retrofit)

    implementation(libs.kotlinxCoroutinesCore)
    implementation(libs.okhttp.loggingInterceptor)
    implementation(libs.converter.kotlinx.serialization)
    implementation(libs.converter.scalars)

    testApi(libs.test.androidx.junit)
    testApi(libs.test.junit5Api)
    testApi(libs.test.junit5Engine)

    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
}
