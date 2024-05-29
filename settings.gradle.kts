pluginManagement {
    includeBuild("buildlogic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

rootProject.name = "tidal-sdk-android"

buildCache {
    local {
        isEnabled = !System.getenv().containsKey("CI")
    }
}

include("bom")

fun includeFromDefaultHierarchy(sdkModuleName: String) {
    include(sdkModuleName)
    include("$sdkModuleName:apps:demo")
}

includeFromDefaultHierarchy("player")
listOf(
    "common",
    "common-android",
    "events",
    "playback-engine",
    "streaming-api",
    "streaming-privileges",
    "testutil"
).forEach {
    val projectName = "player:$it"
    include(projectName)
    project(":$projectName").projectDir = project(":player").projectDir.resolve(it)
}
includeFromDefaultHierarchy("auth")
includeFromDefaultHierarchy("common")
includeFromDefaultHierarchy("eventproducer")
