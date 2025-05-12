package com.tidal.sdk.player.commonandroid.jwt

import android.util.Base64
import com.tidal.sdk.player.commonandroid.Base64Codec
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

private const val PAYLOAD_SPLIT_DELIMITER = "."

class Base64JwtDecoder(private val base64Codec: Base64Codec) {

    fun getClaims(token: String) =
        token.split(PAYLOAD_SPLIT_DELIMITER).getOrNull(1)?.getClaimsFromPayload()

    private fun String.getClaimsFromPayload(): JsonObject {
        val decodedPayload =
            base64Codec.decode(toByteArray(CHARSET), Base64.URL_SAFE).toString(CHARSET)
        return Json.parseToJsonElement(decodedPayload) as JsonObject
    }

    companion object {
        val CHARSET = Charsets.UTF_8
    }
}
