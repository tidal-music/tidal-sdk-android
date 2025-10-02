plugins { alias(libs.plugins.tidal.kotlin.jvm) }

dependencies {
    compileOnly(libs.androidx.annotations)

    implementation(libs.gson)
    implementation(libs.okhttp.okhttp)
}
