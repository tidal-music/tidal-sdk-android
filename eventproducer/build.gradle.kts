plugins {
    alias(libs.plugins.tidal.android.library)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.android.junit5)
    alias(libs.plugins.kotlin.plugin.serialization)
}

android {
    namespace = "com.tidal.sdk.eventproducer"

    buildFeatures { buildConfig = true }
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
    implementation(libs.truetime)

    // XML serialization
    implementation(libs.kotlinx.serialization.xml)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.retrofit.converter)

    ksp(libs.dagger.compiler)
    ksp(libs.room.compiler)
    ksp(libs.moshi.codegen)

    testImplementation(libs.test.assertk)
    testImplementation(libs.test.mockk)
    testImplementation(libs.okhttp.mockwebserver)
    testImplementation(libs.kotlinx.coroutines.test)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testApi(libs.test.androidx.junit)
}
