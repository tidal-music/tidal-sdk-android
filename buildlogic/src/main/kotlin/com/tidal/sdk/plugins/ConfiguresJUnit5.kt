package com.tidal.sdk.plugins

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType

internal class ConfiguresJUnit5 : (Project) -> Unit {

    override fun invoke(target: Project) {
        with(target) {
            tasks.withType<Test> { useJUnitPlatform() }

            // Add common JUnit 5 dependencies
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("testImplementation", libs.findLibrary("test-junit5Api").get())
                add("testRuntimeOnly", libs.findLibrary("test-junit5Engine").get())
                add("testRuntimeOnly", "org.junit.platform:junit-platform-launcher")
            }
        }
    }
}
