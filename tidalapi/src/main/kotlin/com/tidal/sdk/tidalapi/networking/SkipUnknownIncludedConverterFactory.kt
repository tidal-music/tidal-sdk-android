package com.tidal.sdk.tidalapi.networking

import com.tidal.sdk.tidalapi.generated.models.IncludedInner
import java.lang.reflect.Type
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.serializer
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit

/**
 * Decodes response bodies like the kotlinx converter, but first drops `included` resources whose
 * `type` this SDK version doesn't know. The API can add resource types at any time; unknown enum
 * values are handled by the generator (`enumUnknownDefaultCase`), not here.
 */
internal class SkipUnknownIncludedConverterFactory(private val json: Json) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): Converter<ResponseBody, *>? {
        val serializer = serializerOrNull(type) ?: return null
        return Converter<ResponseBody, Any> { body ->
            val document = json.parseToJsonElement(body.use { it.string() })
            json.decodeFromJsonElement(serializer, withoutUnknownIncluded(document))
        }
    }

    private fun serializerOrNull(type: Type): KSerializer<Any>? =
        try {
            json.serializersModule.serializer(type)
        } catch (_: IllegalArgumentException) {
            null
        }

    private fun withoutUnknownIncluded(document: JsonElement): JsonElement {
        val included = (document as? JsonObject)?.get("included") as? JsonArray ?: return document
        val known = included.filter(::isKnownType)
        if (known.size == included.size) return document
        return JsonObject(document + ("included" to JsonArray(known)))
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun isKnownType(resource: JsonElement): Boolean {
        val type = ((resource as? JsonObject)?.get("type") as? JsonPrimitive)?.contentOrNull
        return json.serializersModule.getPolymorphic(IncludedInner::class, type) != null
    }
}
