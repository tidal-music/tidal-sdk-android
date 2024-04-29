package com.tidal.sdk.player.playbackengine.bts

import com.google.gson.Gson
import com.tidal.sdk.player.commonandroid.Base64Codec
import java.nio.charset.Charset

/**
 * Create [BtsManifest] from our encoded [String] manifest, decoded with the provided
 * [base64Codec], then parsed with the provided [gson] instance.
 */
internal class DefaultBtsManifestFactory(
    private val gson: Gson,
    private val base64Codec: Base64Codec,
) : BtsManifestFactory {

    override fun create(encodedManifest: String, charset: Charset): BtsManifest {
        val decodedManifest = base64Codec.decode(encodedManifest.toByteArray(charset))
        return gson.fromJson(decodedManifest.toString(charset), BtsManifest::class.java)
    }
}
