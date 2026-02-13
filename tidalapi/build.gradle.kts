plugins {
    alias(libs.plugins.tidal.android.library)
    alias(libs.plugins.kotlin.kapt)
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

    testImplementation(libs.test.junit5Api)
    testRuntimeOnly(libs.test.junit5Engine)
    testImplementation(libs.okhttp.mockwebserver)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlin.reflect)

    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
}
