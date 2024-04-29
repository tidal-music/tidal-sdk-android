plugins {
    alias(libs.plugins.tidal.android.library)
    alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.tidal.sdk.player"
}

dependencies {
    api(project(":player:common"))
    api(project(":player:playback-engine"))
    api(project(":player:events"))
    api(libs.okhttp.okhttp)
    api(libs.tidal.sdk.auth)
    api(libs.tidal.sdk.eventproducer)

    ksp(libs.dagger.compiler)

    implementation(project(":player:common-android"))
    implementation(libs.dagger)
    implementation(libs.kotlinxCoroutinesCore)
    implementation(libs.okhttp.loggingInterceptor)
    implementation(libs.gson)
    implementation(project(":player:streaming-privileges"))

    testImplementation(libs.test.assertk)
    testImplementation(libs.test.junit5Api)
    testRuntimeOnly(libs.test.junit5Engine)
    testImplementation(project(":player:testutil"))
    testImplementation(libs.test.mockito.kotlin)
    testImplementation(libs.okhttp.mockwebserver)

    androidTestImplementation(libs.test.assertk)
    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
    androidTestImplementation(libs.okhttp.mockwebserver)
    androidTestImplementation(project(":player:testutil"))
    androidTestImplementation(libs.test.mockito.kotlin)
    androidTestImplementation(libs.test.mockito.android)
}
