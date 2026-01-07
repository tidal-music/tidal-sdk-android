plugins { alias(libs.plugins.tidal.kotlin.jvm) }

dependencies {
    implementation(libs.kotlin.reflect)
    implementation(libs.okhttp.mockwebserver)
}
