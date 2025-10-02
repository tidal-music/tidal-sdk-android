plugins {
    alias(libs.plugins.tidal.android.library)
    alias(libs.plugins.android.junit5)
}

android { namespace = "com.tidal.sdk.template" }

dependencies {
    api(libs.tidal.sdk.common)

    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
}
