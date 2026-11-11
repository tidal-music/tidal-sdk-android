package com.tidal.sdk.tidalapi.networking

import com.tidal.sdk.tidalapi.generated.models.IncludedInner
import java.lang.reflect.Type
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.serializer
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit

/**
 * Decodes response bodies like the kotlinx converter, but when a document fails to decode, drops
 * the `included` resources this SDK version can't decode (a new resource type, or a value it
 * doesn't know yet) and decodes the rest. A failure anywhere else still fails the response.
 */
internal class SkipUnknownIncludedConverterFactory(private val json: Json) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): Converter<ResponseBody, *>? {
        val serializer = serializerOrNull(type) ?: return null
        return Converter<ResponseBody, Any> { body -> decode(serializer, body.use { it.string() }) }
    }

    private fun serializerOrNull(type: Type): KSerializer<Any>? =
        try {
            json.serializersModule.serializer(type)
        } catch (_: IllegalArgumentException) {
            null
        }

    private fun decode(serializer: KSerializer<Any>, body: String): Any =
        try {
            json.decodeFromString(serializer, body)
        } catch (error: IllegalArgumentException) {
            val document = withoutUndecodableIncluded(body) ?: throw error
            try {
                json.decodeFromJsonElement(serializer, document)
            } catch (_: IllegalArgumentException) {
                throw error
            }
        }

    /** Returns null when there is nothing to drop, so the original error stands. */
    private fun withoutUndecodableIncluded(body: String): JsonObject? {
        val document =
            try {
                json.parseToJsonElement(body) as? JsonObject
            } catch (_: IllegalArgumentException) {
                null
            } ?: return null
        val included = document["included"] as? JsonArray ?: return null
        val decodable = included.filter(::isDecodable)
        if (decodable.size == included.size) return null
        return JsonObject(document + ("included" to JsonArray(decodable)))
    }

    private fun isDecodable(resource: JsonElement): Boolean =
        try {
            json.decodeFromJsonElement(IncludedInner.serializer(), resource)
            true
        } catch (_: IllegalArgumentException) {
            false
        }
}
