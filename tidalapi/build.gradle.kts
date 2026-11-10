import java.util.Properties
import java.util.zip.ZipFile

plugins {
    alias(libs.plugins.tidal.android.library)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.android.junit5)
    alias(libs.plugins.kotlin.plugin.serialization)
}

android { namespace = "com.tidal.sdk.tidalapi" }

dependencies {
    api(libs.tidal.sdk.common)
    api(libs.tidal.sdk.auth)

    api(libs.kotlinx.serialization.json)
    api(libs.retrofit)

    implementation(libs.kotlinxCoroutinesCore)
    implementation(libs.okhttp.loggingInterceptor)
    implementation(libs.converter.kotlinx.serialization)
    implementation(libs.converter.scalars)

    testImplementation(libs.test.junit5Api)
    testRuntimeOnly(libs.test.junit5Engine)
    testImplementation(libs.okhttp.mockwebserver)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlin.reflect)

    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
}

// TM-2824: fail when tidalapi changes a JVM signature that the released player calls.
// Baseline: the tidalapi version in the published POM of the current player version. If that
// player version is not on Maven Central, this change ships a new player built against this
// tidalapi, so there is nothing to protect.
// ponytail: hand-kept from `jdeps -verbose:class` on player-streaming-api's classes.jar; add a
// class when the player calls another tidalapi type. Unresolvable baseline (offline) skips.
val playerApiSurface =
    listOf(
            "generated.TidalApiClient",
            "generated.apis.TrackManifests*",
            "generated.apis.VideoManifests*",
            "generated.models.AudioNormalizationData*",
            "generated.models.DrmData*",
            "generated.models.LinkObject*",
            "generated.models.TrackFilesReadById40*",
            "generated.models.TrackManifests*",
            "generated.models.VideoManifests*",
            "networking.*",
        )
        .joinToString(";") { "com.tidal.sdk.tidalapi.$it" }

val japicmp by configurations.creating

dependencies { japicmp(libs.japicmp) }

fun published(notation: String): File? =
    configurations
        .detachedConfiguration(dependencies.create(notation))
        .incoming
        .artifactView { lenient(true) }
        .files
        .singleOrNull()

fun classesJar(aar: File, out: File): String {
    out.parentFile.mkdirs()
    ZipFile(aar).use { zip ->
        zip.getInputStream(zip.getEntry("classes.jar")).use { input ->
            out.outputStream().use { input.copyTo(it) }
        }
    }
    return out.path
}

// Member patterns in -i disable the class patterns, so the TidalApiClient factories the player
// calls get their own pass. The class pass skips the other factories: removing an API the player
// does not use cannot crash it.
val tidalApiClient = "com.tidal.sdk.tidalapi.generated.TidalApiClient"

fun registerPlayerAbiCheck(name: String, vararg filters: String) =
    tasks.register<JavaExec>(name) {
        description = "Fails when tidalapi breaks the binary API the released player calls."
        dependsOn("bundleReleaseAar")
        classpath = japicmp
        mainClass.set("japicmp.JApiCmp")
        isIgnoreExitValue = true
        val playerProperties = rootProject.file("player/gradle.properties")
        val newAar = layout.buildDirectory.file("outputs/aar/tidalapi-release.aar")
        val workDirectory = layout.buildDirectory.dir("playerAbi/$name")
        var message = ""
        doFirst {
            val player = Properties().apply { playerProperties.reader().use(::load) }["version"]
            val pom = published("com.tidal.sdk:player-streaming-api:$player@pom")
            if (pom == null) {
                logger.lifecycle("player $player is unreleased; it ships with this tidalapi.")
                throw StopExecutionException()
            }
            val baseMatch =
                Regex("<artifactId>tidalapi</artifactId>\\s*<version>([^<]+)</version>")
                    .find(pom.readText())
            val base = checkNotNull(baseMatch) { "No tidalapi dependency in $pom" }.groupValues[1]
            val baseAar =
                published("${libs.tidal.sdk.tidalapi.get().module}:$base@aar")
                    ?: throw StopExecutionException()
            val directory = workDirectory.get().asFile
            args(
                "-o",
                classesJar(baseAar, directory.resolve("old.jar")),
                "-n",
                classesJar(newAar.get().asFile, directory.resolve("new.jar")),
                *filters,
                "-b",
                "--include-synthetic",
                "--ignore-missing-classes",
                "--error-on-binary-incompatibility",
            )
            message =
                "tidalapi breaks the binary API that released player $player (built against " +
                    "tidalapi $base) calls, so apps on that player crash. Bump " +
                    "player/gradle.properties and player/CHANGELOG.md in this PR to release " +
                    "the player against this tidalapi."
        }
        doLast { if (executionResult.get().exitValue != 0) throw GradleException(message) }
    }

val checkPlayerAbi =
    registerPlayerAbiCheck(
        "checkPlayerAbi",
        "-i",
        playerApiSurface,
        "-e",
        "$tidalApiClient#create*()",
    )
val checkPlayerAbiFactories =
    registerPlayerAbiCheck(
        "checkPlayerAbiFactories",
        "-i",
        "$tidalApiClient#createTrackManifests();$tidalApiClient#createVideoManifests()",
    )

tasks
    .matching { it.name == "test" }
    .configureEach { dependsOn(checkPlayerAbi, checkPlayerAbiFactories) }
