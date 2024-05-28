package com.tidal.sdk.player.playbackengine.bts

import java.nio.charset.Charset

interface BtsManifestFactory {
    fun create(encodedManifest: String, charset: Charset = Charsets.UTF_8): BtsManifest
}
