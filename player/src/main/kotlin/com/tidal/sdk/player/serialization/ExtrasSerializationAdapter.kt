package com.tidal.sdk.player.serialization

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.tidal.sdk.player.common.model.BaseMediaProduct
import java.lang.reflect.Type

class ExtrasSerializationAdapter :
    JsonSerializer<BaseMediaProduct.Extras>,
    JsonDeserializer<BaseMediaProduct.Extras?> {

    override fun serialize(
        src: BaseMediaProduct.Extras?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?,
    ): JsonElement {
        return when (src) {
            is BaseMediaProduct.Extras -> src.toJsonElement()
            else -> JsonNull.INSTANCE
        }
    }

    private fun BaseMediaProduct.Extras.toJsonElement(): JsonElement {
        return when (this) {
            is BaseMediaProduct.Extras.Primitive -> JsonPrimitive(content)
            is BaseMediaProduct.Extras.Collection -> JsonObject().apply {
                content.forEach { (key, value) ->
                    add(key, value.toJsonElement())
                }
            }
        }
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): BaseMediaProduct.Extras? {
        return when {
            json == null -> null
            else -> json.toExtras()
        }
    }

    private fun JsonElement.toExtras(): BaseMediaProduct.Extras? {
        return when {
            isJsonObject -> {
                val content = mutableMapOf<String, BaseMediaProduct.Extras>()
                asJsonObject.entrySet().forEach { entry ->
                    entry.value.toExtras()?.let { extras ->
                        content[entry.key] = extras
                    }
                }
                BaseMediaProduct.Extras.Collection(content)
            }

            isJsonPrimitive -> {
                BaseMediaProduct.Extras.Primitive(asJsonPrimitive.asString)
            }

            else -> null
        }
    }
}
