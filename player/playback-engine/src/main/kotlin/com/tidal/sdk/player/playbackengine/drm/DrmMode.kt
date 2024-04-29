package com.tidal.sdk.player.playbackengine.drm

/**
 * The mode for which drm operation we are doing.
 */
internal sealed class DrmMode {

    /**
     * Drm mode for streaming protected content. This tells us that we need to request the license
     * key.
     */
    object Streaming : DrmMode()
}
