package com.tidal.sdk.tidalapi.networking

import com.tidal.sdk.tidalapi.generated.models.AppreciationsCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistsCreateSingleResourceDataDocument
import java.lang.reflect.Type
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Converter
import retrofit2.Retrofit

/**
 * Lets dry-run-aware resource-create calls return a generic JSON:API success document.
 *
 * These endpoints normally return a typed `*CreateSingleResourceDataDocument`. A dry run may
 * instead return a generic JSON:API document. This factory maps that document to an absent typed
 * body while leaving ordinary typed create responses strict.
 */
internal class OptionalCreateResponseConverterFactory(
    private val json: Json,
    private val delegate: Converter.Factory,
) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit,
    ): Converter<ResponseBody, *>? {
        if (getRawType(type).simpleName !in DRY_RUN_CREATE_RESPONSE_TYPES) return null

        val typedConverter =
            delegate.responseBodyConverter(type, annotations, retrofit) ?: return null

        return Converter { body ->
            val contentType = body.contentType()
            val payload = body.bytes()
            val text = payload.decodeToString()

            if (text.isGenericJsonApiSuccessDocument()) {
                null
            } else {
                typedConverter.convert(payload.toResponseBody(contentType))
            }
        }
    }

    private fun String.isGenericJsonApiSuccessDocument(): Boolean {
        val document =
            runCatching { json.parseToJsonElement(this) as? JsonObject }.getOrNull() ?: return false

        if ("errors" in document) return false

        return document["data"] == JsonNull || ("data" !in document && "meta" in document)
    }

    private companion object {
        val DRY_RUN_CREATE_RESPONSE_TYPES =
            setOf(
                AppreciationsCreateSingleResourceDataDocument::class.java.simpleName,
                ArtistsCreateSingleResourceDataDocument::class.java.simpleName,
            )
    }
}
