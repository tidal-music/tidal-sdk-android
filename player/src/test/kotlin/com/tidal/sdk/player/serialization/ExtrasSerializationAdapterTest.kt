package com.tidal.sdk.player.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.GsonBuilder
import com.google.gson.JsonPrimitive
import com.tidal.sdk.player.common.model.Extra
import com.tidal.sdk.player.common.model.Extra.Bool
import com.tidal.sdk.player.common.model.Extra.CollectionList
import com.tidal.sdk.player.common.model.Extra.CollectionMap
import com.tidal.sdk.player.common.model.Extra.Text
import com.tidal.sdk.player.common.model.Extras
import org.junit.jupiter.api.Test

internal class ExtrasSerializationAdapterTest {

    private val gson = GsonBuilder()
        .registerTypeHierarchyAdapter(Extra::class.java, ExtrasSerializationAdapter())
        .disableHtmlEscaping()
        .create()

    private val sampleExtras = CollectionMap(
        mapOf(
            "boolean" to Bool(true),
            "int" to number(1),
            "float" to number(1.01f),
            "double" to number(1.7976931348623157E308),
            "text" to Text("textValue"),
            "listNumber" to CollectionList(listOf(number(1), number(2), number(3))),
            "listMix" to CollectionList(
                listOf(
                    number(1),
                    Text("a"),
                    Bool(false),
                    CollectionList(listOf(Text("listValue"))),
                    CollectionMap(mapOf("key1" to number(1), "key2" to Text("a"))),
                ),
            ),
            "mapMix" to CollectionMap(
                mapOf(
                    "key2.1" to CollectionMap(
                        mapOf(
                            "key2.1.1" to Bool(true),
                            "key2.1.2" to number(1),
                            "key2.1.3" to Text("a"),
                        ),
                    ),
                ),
            ),
        ),
    )

    private val sampleJson = """
        {
            "boolean":true,
            "int":1,
            "float":1.01,
            "double":1.7976931348623157E308,
            "text":"textValue",
            "listNumber":[1,2,3],
            "listMix":[1,"a",false,["listValue"],{"key1":1,"key2":"a"}],
            "mapMix":{"key2.1":{"key2.1.1":true,"key2.1.2":1,"key2.1.3":"a"}}
        }
    """.lines().joinToString("") {
        it.replace(" ", "")
    }

    /**
     * This is done to fix number's quality check.
     * Gson implements LazilyParsedNumber whose equality only works for the Number with same type.
     * Hence this converts current numbers to "LazilyParsedNumber" and allows for quality check.
     */
    private fun number(number: Number): Extra.Number {
        return Extra.Number(JsonPrimitive(number.toString()).asNumber)
    }

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
        assertThat(gson.fromJson(sampleJson, Extra::class.java)).isEqualTo(sampleExtras)
    }

    @Test
    fun verifySerializationFromExtrasHolderClassToJson() {
        val extrasHolder = ExtrasHolder("otherValue", mapOf("extra1" to sampleExtras))
        val expectedJson = """
            {"someOtherValue":"otherValue","extras":{"extra1":$sampleJson}}
        """.trimIndent()

        assertThat(gson.toJson(extrasHolder)).isEqualTo(expectedJson)
    }

    @Test
    fun verifyDeserializationFromJsonToExtrasHolderClass() {
        val extrasHolder = ExtrasHolder("otherValue", mapOf("extra1" to sampleExtras))
        val actualJson = """
            {"someOtherValue":"otherValue","extras":{"extra1":$sampleJson}}
        """.trimIndent()

        assertThat(gson.fromJson(actualJson, ExtrasHolder::class.java)).isEqualTo(extrasHolder)
    }

    @Test
    fun verifyNullExtrasIsNotSerialized() {
        val extrasHolder = ExtrasHolder("otherValue", null)
        val expectedJson = """
            {"someOtherValue":"otherValue"}
        """.trimIndent()

        assertThat(gson.toJson(extrasHolder)).isEqualTo(expectedJson)
    }

    @Test
    fun verifyNullDeserializationForExtras() {
        val extrasHolder = ExtrasHolder("otherValue", null)
        val actualJson = """
            {"someOtherValue":"otherValue","extras":null}
        """.trimIndent()

        assertThat(gson.fromJson(actualJson, ExtrasHolder::class.java)).isEqualTo(extrasHolder)
    }
}
