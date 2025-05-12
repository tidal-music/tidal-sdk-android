package com.tidal.sdk.player.playbackengine.emu

import com.google.gson.Gson
import com.tidal.sdk.player.commonandroid.Base64Codec
import java.nio.charset.Charset

/**
 * Create [EmuManifest] from our encoded [String] manifest, decoded with the provided [base64Codec],
 * then parsed with the provided [gson] instance.
 */
internal class EmuManifestFactory(private val gson: Gson, private val base64Codec: Base64Codec) {

    fun create(encodedManifest: String, charset: Charset = Charsets.UTF_8): EmuManifest {
        val decodedManifest = base64Codec.decode(encodedManifest.toByteArray(charset))
        return gson.fromJson(decodedManifest.toString(charset), EmuManifest::class.java)
    }
}
