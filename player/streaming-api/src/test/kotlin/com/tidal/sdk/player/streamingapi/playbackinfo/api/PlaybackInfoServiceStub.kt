package com.tidal.sdk.player.streamingapi.playbackinfo.api

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.streamingapi.BroadcastPlaybackInfoFactory
import com.tidal.sdk.player.streamingapi.TrackPlaybackInfoFactory
import com.tidal.sdk.player.streamingapi.VideoPlaybackInfoFactory
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackMode
import org.junit.jupiter.api.fail

/**
 * Stub implementation of [PlaybackInfoService] that is useful for testing without any
 * dependencies.
 *
 * This implementation hard codes the result of the functions to a various values useful in tests.
 */
@Suppress("ThrowingExceptionsWithoutMessageOrCause")
internal class PlaybackInfoServiceStub : PlaybackInfoService {

    override suspend fun getTrackPlaybackInfo(
        trackId: Int,
        playbackMode: PlaybackMode,
        assetPresentation: AssetPresentation,
        audioQuality: AudioQuality,
        streamingSessionId: String,
        playlistUuid: String?,
    ) = when (trackId) {
        PLAYBACK_INFO_ID_FOR_UNCAUGHT_EXCEPTION -> throw NullPointerException()
        PLAYBACK_INFO_ID_SUCCESS -> TrackPlaybackInfoFactory.DEFAULT
        else -> fail("Unsupported call")
    }

    override suspend fun getVideoPlaybackInfo(
        videoId: Int,
        playbackMode: PlaybackMode,
        assetPresentation: AssetPresentation,
        videoQuality: VideoQuality,
        streamingSessionId: String,
        playlistUuid: String?,
    ) = when (videoId) {
        PLAYBACK_INFO_ID_FOR_UNCAUGHT_EXCEPTION -> throw NullPointerException()
        PLAYBACK_INFO_ID_SUCCESS -> VideoPlaybackInfoFactory.DEFAULT
        else -> fail("Unsupported call")
    }

    override suspend fun getBroadcastPlaybackInfo(
        djSessionId: String,
        audioQuality: AudioQuality,
    ) = when (djSessionId) {
        PLAYBACK_INFO_ID_FOR_UNCAUGHT_EXCEPTION.toString() -> throw NullPointerException()
        PLAYBACK_INFO_ID_SUCCESS.toString() ->
            BroadcastPlaybackInfoFactory.DEFAULT_MISSING_STREAMING_SESSION_ID

        else -> fail("Unsupported call")
    }

    companion object {
        const val PLAYBACK_INFO_ID_SUCCESS = 0
        const val PLAYBACK_INFO_ID_FOR_UNCAUGHT_EXCEPTION = 1
    }
}
