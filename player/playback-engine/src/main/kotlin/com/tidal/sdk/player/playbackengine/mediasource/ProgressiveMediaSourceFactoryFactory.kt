package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.datasource.DataSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy
import androidx.media3.extractor.ExtractorsFactory

@Suppress("UnsafeOptInUsageError")
internal class ProgressiveMediaSourceFactoryFactory(
    private val extractorsFactory: ExtractorsFactory,
    private val loadErrorHandlingPolicy: LoadErrorHandlingPolicy,
) {

    fun create(dataSourceFactory: DataSource.Factory) =
        ProgressiveMediaSource.Factory(dataSourceFactory, extractorsFactory)
            .setLoadErrorHandlingPolicy(loadErrorHandlingPolicy)
}
