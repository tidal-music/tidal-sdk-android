package com.tidal.sdk.player.playbackengine.drm

/** The mode for which drm operation we are doing. */
internal sealed class DrmMode {

    /**
     * Drm mode for streaming protected content. This tells us that we need to request the license
     * key.
     */
    object Streaming : DrmMode()

    /** Drm mode for acquiring a persistent license during an offline download. */
    object OfflineAcquisition : DrmMode()

    /** Drm mode for renewing an existing persistent offline license. */
    object OfflineRenewal : DrmMode()

    /** Drm mode for releasing an existing persistent offline license. */
    object OfflineRelease : DrmMode()
}
