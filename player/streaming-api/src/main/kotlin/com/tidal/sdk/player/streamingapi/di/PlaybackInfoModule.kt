package com.tidal.sdk.player.streamingapi.di

import com.tidal.sdk.player.streamingapi.playbackinfo.api.PlaybackInfoService
import com.tidal.sdk.player.streamingapi.playbackinfo.mapper.ApiErrorMapper
import com.tidal.sdk.player.streamingapi.playbackinfo.offline.OfflinePlaybackInfoProvider
import com.tidal.sdk.player.streamingapi.playbackinfo.repository.PlaybackInfoRepository
import com.tidal.sdk.player.streamingapi.playbackinfo.repository.PlaybackInfoRepositoryDefault
import com.tidal.sdk.tidalapi.generated.apis.TrackManifests
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
internal object PlaybackInfoModule {

    @Provides
    @Reusable
    fun providePlaybackInfoRepository(
        offlinePlaybackInfoProvider: OfflinePlaybackInfoProvider?,
        playbackInfoService: PlaybackInfoService,
        trackManifests: TrackManifests,
        apiErrorMapperLazy: Lazy<ApiErrorMapper>,
    ): PlaybackInfoRepository {
        return PlaybackInfoRepositoryDefault(
            offlinePlaybackInfoProvider,
            playbackInfoService,
            apiErrorMapperLazy,
            trackManifests,
        )
    }
}
