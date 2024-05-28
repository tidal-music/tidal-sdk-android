package com.tidal.sdk.player.streamingapi.playbackinfo.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.tidal.sdk.player.streamingapi.playbackinfo.model.ManifestMimeType.BTS
import com.tidal.sdk.player.streamingapi.playbackinfo.model.ManifestMimeType.DASH
import com.tidal.sdk.player.streamingapi.playbackinfo.model.ManifestMimeType.EMU
import java.lang.reflect.Type

/**
 * The mime type of a given [Manifest].
 *
 * [EMU] The corresponding manifest should be EmuManifest.
 * [BTS] The corresponding manifest should be BtsManifest.
 * [DASH] The corresponding manifest should be DashManifest.
 */
enum class ManifestMimeType(val mimeType: String = "") {
    EMU("application/vnd.tidal.emu"),
    BTS("application/vnd.tidal.bts"),
    DASH("application/dash+xml"),
    ;

    object Converter {
        /**
         * A [JsonDeserializer] that helps deserializing [ManifestMimeType] using gson.
         */
        class Deserializer : JsonDeserializer<ManifestMimeType> {
            override fun deserialize(
                json: JsonElement?,
                typeOfT: Type?,
                context: JsonDeserializationContext?,
            ): ManifestMimeType {
                val src = json?.asString
                return enumValues<ManifestMimeType>().find { it.mimeType.contentEquals(src) }
                    ?: throw JsonParseException(
                        "Unsupported unmarshalling: $src as ${ManifestMimeType::class.simpleName}",
                    )
            }
        }
    }
}
