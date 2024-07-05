plugins {
    alias(libs.plugins.tidal.android.library)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.android.junit5)
}

android {
    namespace = "com.tidal.sdk.eventproducer"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    api(libs.okhttp.okhttp)
    api(libs.tidal.sdk.common)
    api(libs.tidal.sdk.auth)

    implementation(libs.dagger)
    implementation(libs.kotlinxCoroutinesCore)
    implementation(libs.retrofit)
    implementation(libs.room.runtime)
    implementation(libs.moshi)
    implementation(libs.okhttp.loggingInterceptor)
    implementation(libs.tickaroo.annotation)
    implementation(libs.tickaroo.core)
    implementation(libs.tickaroo.retrofitConverter)
    implementation(libs.tidal.networktime.singletons)

    ksp(libs.dagger.compiler)
    kapt(libs.room.compiler)
    kapt(libs.tickaroo.processor)
    kapt(libs.moshi.codegen)

    testImplementation(libs.test.assertk)
    testImplementation(libs.test.mockk)
    testImplementation(libs.okhttp.mockwebserver)
    testImplementation(libs.kotlinx.coroutines.test)

    testApi(libs.test.androidx.junit)
    testApi(libs.test.junit5Api)
    testApi(libs.test.junit5Engine)
}
