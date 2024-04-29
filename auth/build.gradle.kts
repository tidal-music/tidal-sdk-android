plugins {
    alias(libs.plugins.tidal.android.library)
    alias(libs.plugins.android.junit5)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.kotlin.plugin.serialization)
}

android {
    namespace = "com.tidal.sdk.auth"
}

dependencies {
    api(libs.tidal.sdk.common)

    implementation(libs.androidx.security.crypto)
    implementation(libs.dagger)
    implementation(libs.kotlinxCoroutinesAndroid)
    implementation(libs.kotlinxCoroutinesCore)
    implementation(libs.kotlinx.coroutines.test)
    api(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.retrofit.converter)
    implementation(libs.okhttp.loggingInterceptor)
    implementation(libs.retrofit)

    ksp(libs.dagger.compiler)

    testImplementation(libs.test.junit5Api)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.kotlin.test)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.test.fluidtime)

    testRuntimeOnly(libs.test.junit5Engine)

    kspTest(libs.dagger.compiler)

    testApi(libs.test.androidx.junit)
    testApi(libs.test.junit5Api)
    testApi(libs.test.junit5Engine)
    testApi(libs.test.turbine)

    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
}
