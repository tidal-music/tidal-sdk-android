package com.tidal.sdk.player.streamingapi.offline

import com.tidal.sdk.player.streamingapi.TrackPlaybackInfoFactory
import com.tidal.sdk.player.streamingapi.VideoPlaybackInfoFactory
import com.tidal.sdk.player.streamingapi.playbackinfo.offline.OfflinePlaybackInfoProvider

internal class OfflinePlaybackInfoProviderStub : OfflinePlaybackInfoProvider {
    override suspend fun getOfflineTrackPlaybackInfo(trackId: String, streamingSessionId: String) =
        TrackPlaybackInfoFactory.OFFLINE_PLAY

    override suspend fun getOfflineVideoPlaybackInfo(videoId: String, streamingSessionId: String) =
        VideoPlaybackInfoFactory.OFFLINE_PLAY
}
