plugins {
    alias(libs.plugins.tidal.kotlin.jvm)
    alias(libs.plugins.google.devtools.ksp)
}

dependencies {
    ksp(libs.dagger.compiler)

    compileOnly(libs.androidx.annotations)

    implementation(libs.dagger)
    implementation(libs.gson)
    implementation(libs.kotlinxCoroutinesCore)
    implementation(libs.okhttp.okhttp)
    implementation(libs.retrofit.converter.gson)
    api(libs.retrofit)
    implementation(project(":player:common"))

    testImplementation(libs.kotlin.reflect)
    testImplementation(libs.kotlinxCoroutinesCore)
    testImplementation(libs.test.assertk)
    testImplementation(libs.test.junit5Params)
    testImplementation(libs.test.mockito.kotlin)
    testImplementation(libs.okhttp.mockwebserver) {
        exclude(group = "com.squareup.okhttp3", module = "okhttp")
    }
    testImplementation(project(":player:testutil"))

    testRuntimeOnly(libs.test.junit5Engine)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
