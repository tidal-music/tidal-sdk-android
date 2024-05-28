package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.common.MediaItem
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.tidal.sdk.player.playbackengine.bts.BtsManifestFactory

internal class PlayerProgressiveMediaSourceFactory(
    private val progressiveMediaSourceFactoryFactory: ProgressiveMediaSourceFactoryFactory,
    private val cacheDataSourceFactory: CacheDataSource.Factory,
    private val btsManifestFactory: BtsManifestFactory,
) {

    fun create(mediaItem: MediaItem, encodedManifest: String): ProgressiveMediaSource {
        val btsManifest = btsManifestFactory.create(encodedManifest)
        val newMediaItem = mediaItem.buildUpon()
            .setUri(btsManifest.urls.firstOrNull())
            .build()
        return progressiveMediaSourceFactoryFactory.create(cacheDataSourceFactory)
            .createMediaSource(newMediaItem)
    }
}
