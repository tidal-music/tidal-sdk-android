package com.tidal.sdk.player.playbackengine.bts

import androidx.annotation.Keep

@Keep data class BtsManifest(val codecs: String, val urls: List<String>)
