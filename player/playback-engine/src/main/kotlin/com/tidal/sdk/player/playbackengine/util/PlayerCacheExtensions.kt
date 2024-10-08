package com.tidal.sdk.player.playbackengine.util

import com.tidal.sdk.player.playbackengine.player.PlayerCache

@Suppress("UnsafeOptInUsageError")
internal fun PlayerCache.clear() {
    with(cache) {
        keys.forEach {
            removeResource(it)
        }
    }
}
