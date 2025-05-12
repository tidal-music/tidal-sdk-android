package com.tidal.sdk.plugins

import com.tidal.sdk.plugins.constant.PluginId
import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPomLicense
import org.gradle.kotlin.dsl.configure

internal class ConfiguresMavenPublish : (Project) -> Unit {

    override fun invoke(target: Project): Unit =
        with(target) {
            pluginManager.apply(PluginId.GRADLE_MAVEN_PUBLISH_PLUGIN_ID)
            ConfiguresGradleProjectVersion()(this)

            configure<MavenPublishBaseExtension> {
                setPublishJavadoc(this@with, false)
                publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)

                if (project.properties.hasGpgData()) {
                    signAllPublications()
                }

                val artifactId = this@with.path.toArtifactId()
                val version = this@with.version as String
                coordinates(SDK_GROUP_ID, artifactId, version)

                pom {
                    name.set(artifactId)
                    inceptionYear.set(SDK_INCEPTION_YEAR)
                    url.set(SDK_GITHUB_URL)
                    licenses { license { apache() } }
                    scm {
                        url.set(SDK_GITHUB_URL)
                        connection.set(SCM_CONNECTION)
                        developerConnection.set(SCM_DEVELOPER_CONNECTION)
                    }
                    developers { developer { name.set("TIDAL") } }
                    project.gradle.projectsEvaluated {
                        this@pom.description.set(
                            project.properties[PROJECT_DESCRIPTION_KEY] as String?
                        )
                    }
                }
            }
        }

    private fun Map<String, Any?>.hasGpgData() =
        (this.containsKey("signingInMemoryKeyId") &&
            this.containsKey("signingInMemoryKeyPassword") &&
            this.containsKey("signingInMemoryKey"))

    private fun MavenPublishBaseExtension.setPublishJavadoc(
        project: Project,
        shouldPublish: Boolean,
    ) {
        project.plugins.withId("com.android.library") {
            configure(AndroidSingleVariantLibrary(publishJavadocJar = shouldPublish))
        }
    }

    private fun String.toArtifactId(): String {
        return substring(1).replace(':', '-')
    }

    private fun MavenPomLicense.apache() {
        name.set("The Apache License, Version 2.0")
        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
        distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
    }

    companion object {

        private const val SDK_GROUP_ID = "com.tidal.sdk"
        private const val SDK_INCEPTION_YEAR = "2024"
        private const val SDK_GITHUB_URL = "https://github.com/tidal-music/tidal-sdk-android/"
        private const val SCM_CONNECTION =
            "scm:git:https://github.com/tidal-music/tidal-sdk-android.git"
        private const val SCM_DEVELOPER_CONNECTION =
            "scm:git:ssh://git@github.com/tidal-music/tidal-sdk-android.git"
        private const val PROJECT_DESCRIPTION_KEY = "projectDescription"
    }
}
