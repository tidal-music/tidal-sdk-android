plugins {
    alias(libs.plugins.tidal.android.library)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.kotlin.plugin.serialization)
}

android {
    namespace = "com.tidal.sdk.player.events"

    sourceSets {
        getByName("androidTest").java.srcDirs("src/testext/kotlin")
        getByName("test").java.srcDirs("src/testext/kotlin")
    }
}

dependencies {
    compileOnly(libs.androidx.annotations)

    implementation(libs.gson)
    implementation(project(":player:common"))
    implementation(project(":player:common-android"))
    implementation(libs.dagger)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.tidal.sdk.auth)
    implementation(libs.tidal.sdk.eventproducer)
    implementation(libs.kotlinxCoroutinesCore)
    implementation(libs.tidal.networktime)

    testImplementation(libs.test.assertk)
    testImplementation(libs.test.junit5Api)
    testImplementation(libs.test.junit5Params)
    testImplementation(libs.test.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testRuntimeOnly(libs.test.junit5Engine)
    testImplementation(project(":player:testutil"))

    androidTestImplementation(project(":player:testutil"))

    ksp(libs.dagger.compiler)
}
