package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.exoplayer.drm.DrmSessionManagerProvider
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import com.tidal.sdk.player.playbackengine.emu.EmuManifestFactory

@Suppress("UnsafeOptInUsageError")
internal class PlayerHlsMediaSourceFactory(
    private val hlsMediaSourceFactory: HlsMediaSource.Factory,
    private val emuManifestFactory: EmuManifestFactory,
) {

    fun create(
        mediaItem: MediaItem,
        encodedManifest: String,
        drmSessionManagerProvider: DrmSessionManagerProvider,
    ): MediaSource {
        val emuManifest = emuManifestFactory.create(encodedManifest)
        val newMediaItem = mediaItem.buildUpon()
            .setUri(emuManifest.urls.firstOrNull())
            .setMimeType(MimeTypes.APPLICATION_M3U8)
            .build()

        return hlsMediaSourceFactory
            .setDrmSessionManagerProvider(drmSessionManagerProvider)
            .createMediaSource(newMediaItem)
    }
}
