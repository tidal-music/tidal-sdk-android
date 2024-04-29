plugins {
    alias(libs.plugins.tidal.android.library)
    alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.tidal.sdk.player.playbackengine"

    sourceSets {
        getByName("androidTest").java.srcDirs("src/testext/kotlin")
        getByName("test").java.srcDirs("src/testext/kotlin")
    }
}

dependencies {
    ksp(libs.dagger.compiler)
    kspAndroidTest(libs.dagger.compiler)

    compileOnly(libs.androidx.annotations)

    implementation(project(":player:common"))
    implementation(project(":player:common-android"))
    implementation(project(":player:events"))
    api(project(":player:streaming-api"))
    implementation(project(":player:streaming-privileges"))
    implementation(libs.gson)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlinxCoroutinesCore)
    implementation(libs.okhttp.loggingInterceptor)
    implementation(libs.okhttp.okhttp)
    api(libs.tidal.exoPlayer.core)
    implementation(libs.tidal.exoPlayer.dash)
    implementation(libs.tidal.exoPlayer.hls)
    implementation(libs.tidal.exoPlayer.extension.flac)
    implementation(libs.tidal.exoPlayer.extension.okhttp)
    implementation(libs.dagger)

    testImplementation(libs.test.assertk)
    testImplementation(libs.test.junit5Api)
    testImplementation(libs.test.mockito.kotlin)
    testRuntimeOnly(libs.test.junit5Engine)
    testImplementation(libs.test.junit5Params)
    testImplementation(project(":player:testutil"))

    androidTestRuntimeOnly(libs.test.mockito.android)
    androidTestImplementation(libs.test.mockito.kotlin)
    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
    androidTestImplementation(libs.test.assertk)
    androidTestImplementation(project(":player:testutil"))
}
