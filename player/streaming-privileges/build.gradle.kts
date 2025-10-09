plugins {
    alias(libs.plugins.tidal.android.library)
    alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.tidal.sdk.player.streamingprivileges"

    sourceSets { getByName("test").java.srcDirs("src/testext/kotlin") }
}

dependencies {
    compileOnly(libs.dagger)
    compileOnly(libs.androidx.annotations)

    implementation(libs.okhttp.okhttp)
    implementation(project(":player:common"))
    implementation(project(":player:common-android"))
    implementation(libs.gson)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit)

    testImplementation(project(":player:testutil"))
    testImplementation(libs.test.assertk)
    testImplementation(libs.test.mockito.kotlin)
    testImplementation(libs.test.junit5Params)

    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)

    ksp(libs.dagger.compiler)
}
