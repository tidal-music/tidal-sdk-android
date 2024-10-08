package com.tidal.sdk.player.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.GsonBuilder
import com.tidal.sdk.player.common.model.BaseMediaProduct.Extras
import org.junit.jupiter.api.Test

internal class ExtrasSerializationAdapterTest {

    private val gson = GsonBuilder()
        .registerTypeHierarchyAdapter(Extras::class.java, ExtrasSerializationAdapter())
        .disableHtmlEscaping()
        .create()

    private val sampleExtras = Extras.Collection(
        mapOf(
            "key1" to Extras.Primitive("value1"),
            "key2" to Extras.Collection(
                mapOf(
                    "key2.1" to Extras.Collection(
                        mapOf(
                            "key2.1.1" to Extras.Primitive("value2.1.1"),
                            "key2.1.2" to Extras.Primitive("value2.1.2"),
                            "key2.1.3" to Extras.Primitive("value2.1.3"),
                        ),
                    ),
                ),
            ),
        ),
    )

    private val sampleJson = """
        {"key1":"value1","key2":{"key2.1":{"key2.1.1":"value2.1.1","key2.1.2":"value2.1.2","key2.1.3":"value2.1.3"}}}
    """.trimIndent()

    data class ExtrasHolder(
        val someOtherValue: String,
        val extras: Extras?,
    )

    @Test
    fun verifySerializationFromExtrasToJson() {
        assertThat(gson.toJson(sampleExtras)).isEqualTo(sampleJson)
    }

    @Test
    fun verifyDeserializationFromJsonToExtras() {
        assertThat(gson.fromJson(sampleJson, Extras::class.java)).isEqualTo(sampleExtras)
    }

    @Test
    fun verifySerializationFromExtrasHolderClassToJson() {
        val extrasHolder = ExtrasHolder("otherValue", sampleExtras)
        val expectedJson = """
            {"someOtherValue":"otherValue","extras":$sampleJson}
        """.trimIndent()

        assertThat(gson.toJson(extrasHolder)).isEqualTo(expectedJson)
    }

    @Test
    fun verifyDeserializationFromJsonToExtrasHolderClass() {
        val extrasHolder = ExtrasHolder("otherValue", sampleExtras)
        val expectedJson = """
            {"someOtherValue":"otherValue","extras":$sampleJson}
        """.trimIndent()

        assertThat(gson.fromJson(expectedJson, ExtrasHolder::class.java)).isEqualTo(extrasHolder)
    }

    @Test
    fun verifyNullIsNotSerialized() {
        val extrasHolder = ExtrasHolder("otherValue", null)
        val expectedJson = """
            {"someOtherValue":"otherValue"}
        """.trimIndent()

        assertThat(gson.toJson(extrasHolder)).isEqualTo(expectedJson)
    }

    @Test
    fun verifyNullDeserialization() {
        val extrasHolder = ExtrasHolder("otherValue", null)
        val expectedJson = """
            {"someOtherValue":"otherValue","extras":null}
        """.trimIndent()

        assertThat(gson.fromJson(expectedJson, ExtrasHolder::class.java)).isEqualTo(extrasHolder)
    }

    @Test
    fun verifyWrongTypeIsIgnoredOnDeserialization() {
        val extrasHolder = ExtrasHolder("otherValue", null)
        val expectedJson = """
            {"someOtherValue":"otherValue","extras":["invalid-array-content"]}
        """.trimIndent()
        assertThat(gson.fromJson(expectedJson, ExtrasHolder::class.java)).isEqualTo(extrasHolder)
    }
}
