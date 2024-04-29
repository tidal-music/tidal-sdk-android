plugins {
    alias(libs.plugins.tidal.android.library)
    alias(libs.plugins.kotlin.plugin.serialization)
}

android {
    namespace = "com.tidal.sdk.player.commonandroid"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.truetime)

    testImplementation(libs.test.junit5Api)
    testRuntimeOnly(libs.test.junit5Engine)

    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
    androidTestImplementation(libs.test.assertk)
}
