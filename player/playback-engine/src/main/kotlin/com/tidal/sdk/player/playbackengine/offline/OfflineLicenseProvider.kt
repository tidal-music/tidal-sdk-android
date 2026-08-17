package com.tidal.sdk.player.playbackengine.offline

import androidx.media3.common.Format
import com.tidal.sdk.player.common.model.Extras
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo

/** Acquires and manages persistent Widevine licenses for downloaded media. */
interface OfflineLicenseProvider {

    /**
     * Acquires a persistent license for [playbackInfo] and [drmFormat].
     *
     * [drmFormat] must come from the offline manifest selected for download. A Base64-encoded
     * Widevine key-set ID is returned for the caller to persist alongside the downloaded media.
     * DRM-free formats return `null`.
     */
    suspend fun acquireOfflineLicense(
        playbackInfo: PlaybackInfo,
        drmFormat: Format,
        extras: Extras? = null,
    ): String?

    /** Renews [offlineLicense] and returns the replacement Base64-encoded key-set ID. */
    suspend fun renewOfflineLicense(
        playbackInfo: PlaybackInfo,
        offlineLicense: String,
        extras: Extras? = null,
    ): String

    /** Releases [offlineLicense] from the device's Widevine content decryption module. */
    suspend fun releaseOfflineLicense(offlineLicense: String)
}
