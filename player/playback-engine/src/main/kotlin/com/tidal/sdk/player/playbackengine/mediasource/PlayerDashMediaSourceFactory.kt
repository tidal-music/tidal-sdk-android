package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.drm.DrmSessionManagerProvider
import com.tidal.sdk.player.playbackengine.dash.DashManifestFactory

@Suppress("UnsafeOptInUsageError")
internal class PlayerDashMediaSourceFactory(
    private val dashMediaSourceFactory: DashMediaSource.Factory,
    private val dashManifestFactory: DashManifestFactory,
) {

    fun create(
        mediaItem: MediaItem,
        encodedManifest: String,
        drmSessionManagerProvider: DrmSessionManagerProvider,
    ): DashMediaSource {
        val dashManifest = dashManifestFactory.create(encodedManifest)

        return dashMediaSourceFactory
            .setDrmSessionManagerProvider(drmSessionManagerProvider)
            .createMediaSource(dashManifest, mediaItem)
    }
}
