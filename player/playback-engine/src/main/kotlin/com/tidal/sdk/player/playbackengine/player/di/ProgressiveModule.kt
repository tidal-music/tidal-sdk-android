package com.tidal.sdk.player.playbackengine.player.di

import androidx.media3.datasource.cache.CacheDataSource
import com.tidal.sdk.player.playbackengine.bts.DefaultBtsManifestFactory
import com.tidal.sdk.player.playbackengine.mediasource.PlayerProgressiveMediaSourceFactory
import com.tidal.sdk.player.playbackengine.mediasource.ProgressiveMediaSourceFactoryFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
internal object ProgressiveModule {

    @Provides
    @Reusable
    @Suppress("UnsafeOptInUsageError")
    fun playerProgressiveMediaSourceFactory(
        progressiveMediaSourceFactoryFactory: ProgressiveMediaSourceFactoryFactory,
        cacheDataSourceFactory: CacheDataSource.Factory,
        btsManifestFactory: DefaultBtsManifestFactory,
    ) = PlayerProgressiveMediaSourceFactory(
        progressiveMediaSourceFactoryFactory,
        cacheDataSourceFactory,
        btsManifestFactory,
    )
}
