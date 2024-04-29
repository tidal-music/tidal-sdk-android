package com.tidal.sdk

import org.gradle.api.Project

val Project.sdkModules
    get() = rootProject.subprojects.filter { it.parent === rootProject }
