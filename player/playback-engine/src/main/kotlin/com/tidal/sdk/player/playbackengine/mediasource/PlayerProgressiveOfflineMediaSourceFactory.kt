package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.tidal.sdk.player.playbackengine.bts.BtsManifestFactory
import com.tidal.sdk.player.playbackengine.offline.OfflineStorageProvider
import com.tidal.sdk.player.streamingapi.offline.Storage

internal class PlayerProgressiveOfflineMediaSourceFactory(
    private val progressiveMediaSourceFactoryFactory: ProgressiveMediaSourceFactoryFactory,
    private val btsManifestFactory: BtsManifestFactory,
    private val offlineStorageProvider: OfflineStorageProvider?,
) {

    fun create(
        mediaItem: MediaItem,
        encodedManifest: String,
        storage: Storage,
    ): ProgressiveMediaSource {
        val btsManifest = btsManifestFactory.create(encodedManifest)
        val newMediaItem = mediaItem.buildUpon().setUri(btsManifest.urls.firstOrNull()).build()

        return progressiveMediaSourceFactoryFactory
            .create(offlineStorageProvider!!.getDataSourceFactoryForOfflinePlay(storage))
            .createMediaSource(newMediaItem)
    }
}
