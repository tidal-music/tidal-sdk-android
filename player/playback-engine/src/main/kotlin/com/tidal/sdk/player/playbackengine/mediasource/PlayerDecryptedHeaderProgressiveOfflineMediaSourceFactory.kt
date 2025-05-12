package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.tidal.sdk.player.playbackengine.Encryption
import com.tidal.sdk.player.playbackengine.bts.BtsManifestFactory
import com.tidal.sdk.player.playbackengine.datasource.DecryptedHeaderFileDataSourceFactoryFactory

internal class PlayerDecryptedHeaderProgressiveOfflineMediaSourceFactory(
    private val progressiveMediaSourceFactoryFactory: ProgressiveMediaSourceFactoryFactory,
    @Suppress("MaxLineLength")
    private val decryptedHeaderFileDataSourceFactoryFactory:
        DecryptedHeaderFileDataSourceFactoryFactory, // ktlint-disable max-line-length
    // parameter-wrapping
    private val encryption: Encryption?,
    private val btsManifestFactory: BtsManifestFactory,
) {

    fun create(
        mediaItem: MediaItem,
        encodedManifest: String,
        productId: String,
    ): ProgressiveMediaSource {
        val btsManifest = btsManifestFactory.create(encodedManifest)
        val newMediaItem = mediaItem.buildUpon().setUri(btsManifest.urls.firstOrNull()).build()

        return progressiveMediaSourceFactoryFactory
            .create(
                decryptedHeaderFileDataSourceFactoryFactory.create(
                    encryption!!.getDecryptedHeader(productId)
                )
            )
            .createMediaSource(newMediaItem)
    }
}
