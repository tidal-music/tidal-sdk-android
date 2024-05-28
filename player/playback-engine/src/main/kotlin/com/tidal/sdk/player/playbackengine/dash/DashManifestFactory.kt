package com.tidal.sdk.player.playbackengine.dash

import android.net.Uri
import androidx.media3.exoplayer.dash.manifest.DashManifest
import androidx.media3.exoplayer.upstream.ParsingLoadable
import com.tidal.sdk.player.commonandroid.Base64Codec
import java.io.ByteArrayInputStream
import java.nio.charset.Charset

/**
 * Create ExoPlayer's [DashManifest] from our encoded [String] manifest, decoded with the provided
 * [base64Codec], then parsed with the provided [dashManifestParser].
 */
internal class DashManifestFactory(
    private val base64Codec: Base64Codec,
    private val dashManifestParser: ParsingLoadable.Parser<DashManifest>,
) {

    fun create(encodedManifest: String, charset: Charset = Charsets.UTF_8): DashManifest {
        val decodedManifest = base64Codec.decode(encodedManifest.toByteArray(charset))
        val byteArrayInputStream = byteArrayInputStreamF(decodedManifest)
        return dashManifestParser.parse(Uri.EMPTY, byteArrayInputStream)
    }

    private var byteArrayInputStreamF: (ByteArray) -> ByteArrayInputStream =
        { ByteArrayInputStream(it) }
}
