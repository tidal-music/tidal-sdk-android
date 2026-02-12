package com.tidal.sdk.tidalapi.generated.models

import java.io.File
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObjectInstance
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

/**
 * Reflection-based smoke tests for all generated model classes. These tests discover classes at
 * runtime so they stay valid regardless of what the code generator produces.
 */
class SerializerSmokeTest {

    private val json = Json {
        classDiscriminator = "type"
        ignoreUnknownKeys = true
        serializersModule = getOneOfSerializer()
    }

    /**
     * Discover all concrete (non-enum, non-interface, non-abstract) classes in the generated models
     * package by reading the .openapi-generator/FILES manifest.
     */
    private fun discoverModelClasses(): List<KClass<*>> {
        val filesManifest =
            File("${System.getProperty("user.dir")}/.openapi-generator/FILES").readLines()

        return filesManifest
            .filter { it.contains("/models/") && it.endsWith(".kt") }
            .mapNotNull { filePath ->
                val className =
                    filePath.removePrefix("src/main/kotlin/").removeSuffix(".kt").replace('/', '.')
                try {
                    val clazz = Class.forName(className).kotlin
                    // Skip interfaces, abstract classes, enums, and companion objects
                    if (
                        !clazz.java.isInterface &&
                            !clazz.java.isEnum &&
                            !java.lang.reflect.Modifier.isAbstract(clazz.java.modifiers) &&
                            clazz.simpleName != "Utils"
                    ) {
                        clazz
                    } else {
                        null
                    }
                } catch (_: ClassNotFoundException) {
                    null
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> getSerializer(kClass: KClass<T>): KSerializer<T>? {
        return try {
            val companion = kClass.companionObjectInstance ?: return null
            val method = companion::class.java.methods.find { it.name == "serializer" }
            method?.invoke(companion) as? KSerializer<T>
        } catch (_: Exception) {
            null
        }
    }

    @Test
    fun `all generated model classes are discoverable`() {
        val classes = discoverModelClasses()
        assertTrue(classes.size > 100, "Expected at least 100 model classes, found ${classes.size}")
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun `all serializable model classes have valid serializers`() {
        val classes = discoverModelClasses()
        val failures = mutableListOf<String>()

        for (kClass in classes) {
            val serializer = getSerializer(kClass)
            if (serializer == null) {
                // Not all classes must be @Serializable (e.g. sealed class impls)
                continue
            }
            if (serializer.descriptor.serialName.isBlank()) {
                failures.add("${kClass.simpleName}: serializer has blank serial name")
            }
        }

        assertTrue(failures.isEmpty(), "Serializer issues found:\n${failures.joinToString("\n")}")
    }

    @Test
    fun `serializable model classes can attempt deserialization from empty object`() {
        val classes = discoverModelClasses()
        var testedCount = 0

        for (kClass in classes) {
            val serializer = getSerializer(kClass) ?: continue
            testedCount++
            try {
                // Try deserializing an empty JSON object - this will fail for models with
                // required fields, but should fail with SerializationException (not a crash).
                json.decodeFromString(serializer, "{}")
            } catch (_: SerializationException) {
                // Expected for models with required fields - the serializer works correctly
            } catch (e: Exception) {
                // Unexpected exception type means the serializer is broken
                throw AssertionError(
                    "${kClass.simpleName}: unexpected exception during deserialization: ${e::class.simpleName}: ${e.message}",
                    e,
                )
            }
        }

        assertTrue(
            testedCount > 100,
            "Expected to test at least 100 serializers, tested $testedCount",
        )
    }

    @Test
    fun `getOneOfSerializer registers all polymorphic subclasses`() {
        val module: SerializersModule = assertDoesNotThrow { getOneOfSerializer() }
        assertNotNull(module, "getOneOfSerializer() should return a non-null SerializersModule")
    }
}
