plugins {
    alias(libs.plugins.tidal.android.library)
    alias(libs.plugins.android.junit5)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.kotlin.plugin.serialization)
}

android { namespace = "com.tidal.sdk.auth" }

dependencies {
    api(libs.tidal.sdk.common)

    implementation(libs.androidx.security.crypto)
    implementation(libs.dagger)
    implementation(libs.kotlinxCoroutinesAndroid)
    implementation(libs.kotlinxCoroutinesCore)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.retrofit.converter)
    implementation(libs.okhttp.loggingInterceptor)
    implementation(libs.retrofit)

    ksp(libs.dagger.compiler)

    testImplementation(libs.test.androidx.junit)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.kotlin.test)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.test.fluidtime)
    testImplementation(libs.test.turbine)

    kspTest(libs.dagger.compiler)

    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
}
