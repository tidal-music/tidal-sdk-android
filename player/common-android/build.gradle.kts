plugins {
    alias(libs.plugins.tidal.android.library)
    alias(libs.plugins.kotlin.plugin.serialization)
}

android { namespace = "com.tidal.sdk.player.commonandroid" }

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.truetime)

    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
    androidTestImplementation(libs.test.assertk)
}
