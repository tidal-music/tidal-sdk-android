package com.tidal.sdk.player.serialization

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.tidal.sdk.player.common.model.Extra
import java.lang.reflect.Type

class ExtrasSerializationAdapter :
    JsonSerializer<Extra>,
    JsonDeserializer<Extra?> {

    override fun serialize(
        src: Extra?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?,
    ): JsonElement {
        return when (src) {
            is Extra -> src.toJsonElement()
            else -> JsonNull.INSTANCE
        }
    }

    private fun Extra.toJsonElement(): JsonElement {
        return when (this) {
            is Extra.Bool -> JsonPrimitive(value)
            is Extra.CollectionList -> toJsonArrayElement()
            is Extra.CollectionMap -> toJsonObjectElement()
            is Extra.Number -> JsonPrimitive(value)
            is Extra.Text -> JsonPrimitive(value)
        }
    }

    private fun Extra.CollectionMap.toJsonObjectElement(): JsonElement {
        return value?.let {
            JsonObject().apply {
                it.forEach { (key, extra) ->
                    add(key, extra?.toJsonElement())
                }
            }
        } ?: JsonNull.INSTANCE
    }

    private fun Extra.CollectionList.toJsonArrayElement(): JsonElement {
        return value?.let {
            JsonArray(it.size).apply {
                it.forEach { extra ->
                    add(extra.toJsonElement())
                }
            }
        } ?: JsonNull.INSTANCE
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): Extra? {
        return json?.toExtra()
    }

    private fun JsonElement.toExtra(): Extra? {
        return when (this) {
            is JsonObject -> {
                Extra.CollectionMap(
                    entrySet().associate {
                        Pair(it.key, it.value?.toExtra())
                    },
                )
            }

            is JsonArray -> {
                Extra.CollectionList(mapNotNull { it.toExtra() })
            }

            is JsonPrimitive -> {
                when {
                    isBoolean -> Extra.Bool(asBoolean)
                    isNumber -> Extra.Number(asNumber)
                    isString -> Extra.Text(asString)
                    else -> null
                }
            }

            else -> null
        }
    }
}
