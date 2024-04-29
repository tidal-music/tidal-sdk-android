package com.tidal.sdk.plugins

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType

internal class ConfiguresJUnit5 : (Project) -> Unit {

    override fun invoke(target: Project) {
        target.tasks.withType<Test> {
            useJUnitPlatform()
        }
    }
}
