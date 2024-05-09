plugins {
    alias(libs.plugins.tidal.android.library)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.android.junit5)
}

android {
    namespace = "com.tidal.sdk.template"
}

dependencies {

    api(project(":common"))

    testApi(libs.test.androidx.junit)
    testApi(libs.test.junit5Api)
    testApi(libs.test.junit5Engine)

    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
}
