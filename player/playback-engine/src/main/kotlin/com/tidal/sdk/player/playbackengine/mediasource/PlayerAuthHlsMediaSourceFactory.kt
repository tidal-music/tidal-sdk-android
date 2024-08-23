package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.exoplayer.drm.DrmSessionManagerProvider
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource

internal class PlayerAuthHlsMediaSourceFactory(
    private val hlsMediaSourceFactory: HlsMediaSource.Factory,
) {

    fun create(
        mediaItem: MediaItem,
        url: String,
        drmSessionManagerProvider: DrmSessionManagerProvider,
    ): MediaSource {
        val newMediaItem = mediaItem.buildUpon()
            .setUri(url)
            .setMimeType(MimeTypes.APPLICATION_M3U8)
            .build()

        return hlsMediaSourceFactory
            .setDrmSessionManagerProvider(drmSessionManagerProvider)
            .createMediaSource(newMediaItem)
    }
}
