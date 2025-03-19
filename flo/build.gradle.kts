plugins {
    alias(libs.plugins.tidal.android.library)
    alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.tidal.sdk.flo"
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group != "com.google.guava") {
            return@eachDependency
        }
        if (requested.name != "guava") {
            return@eachDependency
        }
        // https://nvd.nist.gov/vuln/detail/cve-2023-2976, comes via moshi-kotlin-codegen 1.15.1
        if (requested.version == "30.1.1-jre") {
            useVersion("32.0.1-jre")
        }
    }
}

dependencies {
    ksp(libs.moshi.codegen)
    implementation(libs.moshi)
    implementation(libs.moshi.adapters)
    api(libs.okhttp.okhttp)
}
