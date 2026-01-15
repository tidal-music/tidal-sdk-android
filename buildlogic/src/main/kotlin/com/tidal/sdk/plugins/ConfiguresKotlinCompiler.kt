package com.tidal.sdk.plugins

import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal class ConfiguresKotlinCompiler : (Project) -> Unit {

    override fun invoke(target: Project) =
        with(target) {
            tasks.withType<KotlinCompile>().configureEach {
                compilerOptions { freeCompilerArgs.add("-Xjvm-default=all") }
            }
        }
}
