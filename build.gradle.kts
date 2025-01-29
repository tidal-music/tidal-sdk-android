import com.tidal.sdk.sdkModules
import java.time.LocalDate

plugins {
    alias(libs.plugins.kotlin.dokka)
    alias(libs.plugins.tidal.android.application) apply false
    alias(libs.plugins.tidal.android.library) apply false
    alias(libs.plugins.tidal.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.tidal.jvm.platform) apply false
    alias(libs.plugins.compose.compiler) apply false
}

tasks.register("printSdkModules") {
    doLast {
        sdkModules.forEach { println(it.name) }
    }
}

sdkModules.forEach {
    rootProject.tasks.register("publish-sdk-module-${it.name}") {
        (it.subprojects + it).forEach {
            it.tasks.findByName("publishToMavenCentral")?.let {
                dependsOn(it)
            }
        }
    }
}

tasks.dokkaHtmlMultiModule.configure {
    moduleVersion = "bom-${project(":bom").property("version")}"
    includes.setFrom("README.md")
    pluginsMapConfiguration.set(mapOf("org.jetbrains.dokka.base.DokkaBase" to
        """{"footerMessage": "© ${LocalDate.now().year} TIDAL"}"""
    ))
}
