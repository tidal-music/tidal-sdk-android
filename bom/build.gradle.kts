import com.tidal.sdk.sdkModules
import org.gradle.api.internal.artifacts.verification.exceptions.DependencyVerificationException

plugins {
    alias(libs.plugins.tidal.jvm.platform)
}

dependencies {
    constraints {
        val dependencies = mutableListOf<Provider<MinimalExternalModuleDependency>>()
        rootProject.sdkModules
            .filterNot { it === project || it.name === "common" }
            .forEach {
                val expectedDependencyName = "tidal-sdk-${it.name}"
                try {
                    dependencies.add(
                        versionCatalogs.named("libs").findLibrary(expectedDependencyName).get(),
                    )
                } catch (ignored: NoSuchElementException) {
                    throw DependencyVerificationException(
                        "Dependency $expectedDependencyName missing in version catalog",
                    )
                }
            }
        dependencies.forEach { api(it) }
    }
}
