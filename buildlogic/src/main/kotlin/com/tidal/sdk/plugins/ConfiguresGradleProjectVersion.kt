package com.tidal.sdk.plugins

import org.gradle.api.Project

internal class ConfiguresGradleProjectVersion : (Project) -> Unit {

    override fun invoke(target: Project) {
        var project = target
        while (!project.hasVersion) {
            project = project.parent ?: break
        }
        target.version = project.version
    }

    /**
     * @see [Project.getVersion]
     */
    private val Project.hasVersion
        get() = !(version as String).contentEquals("unspecified")
}
