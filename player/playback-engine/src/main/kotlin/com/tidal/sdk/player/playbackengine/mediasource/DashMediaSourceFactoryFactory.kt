package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.datasource.DataSource
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy

@Suppress("UnsafeOptInUsageError")
internal class DashMediaSourceFactoryFactory(
    private val loadErrorHandlingPolicy: LoadErrorHandlingPolicy,
) {

    fun create(dataSourceFactory: DataSource.Factory) = DashMediaSource.Factory(dataSourceFactory)
        .setLoadErrorHandlingPolicy(loadErrorHandlingPolicy)
}
