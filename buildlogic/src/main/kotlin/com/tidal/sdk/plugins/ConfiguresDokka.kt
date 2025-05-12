package com.tidal.sdk.plugins

import com.tidal.sdk.plugins.constant.PluginId
import java.time.LocalDate
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.gradle.DokkaTaskPartial

internal class ConfiguresDokka : (Project) -> Unit {

    override fun invoke(target: Project) =
        with(target) {
            pluginManager.apply(PluginId.KOTLIN_DOKKA_PLUGIN_ID)
            tasks.withType<DokkaTaskPartial>().configureEach {
                dokkaSourceSets.configureEach {
                    val moduleNameBuilder = StringBuilder(project.name)
                    var crawler = project
                    while (crawler.parent !== rootProject) {
                        crawler = crawler.parent!!
                        moduleNameBuilder.insert(0, "${crawler.name}-")
                    }
                    moduleName.set(moduleNameBuilder.toString())
                    includes.setFrom("README.md")
                    pluginsMapConfiguration.set(
                        mapOf(
                            "org.jetbrains.dokka.base.DokkaBase" to
                                """{"footerMessage": "© ${LocalDate.now().year} TIDAL"}"""
                        )
                    )
                }
            }
        }
}
