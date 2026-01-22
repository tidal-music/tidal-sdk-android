package com.tidal.sdk.player.streamingapi.playbackinfo.api

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.streamingapi.VideoPlaybackInfoFactory
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackMode
import org.junit.jupiter.api.fail

/**
 * Stub implementation of [PlaybackInfoService] that is useful for testing without any dependencies.
 *
 * This implementation hard codes the result of the functions to a various values useful in tests.
 */
@Suppress("ThrowingExceptionsWithoutMessageOrCause")
internal class PlaybackInfoServiceStub : PlaybackInfoService {

    override suspend fun getVideoPlaybackInfo(
        videoId: String,
        playbackMode: PlaybackMode,
        assetPresentation: AssetPresentation,
        videoQuality: VideoQuality,
        streamingSessionId: String,
        playlistUuid: String?,
    ) =
        when (videoId) {
            PLAYBACK_INFO_ID_FOR_UNCAUGHT_EXCEPTION -> throw NullPointerException()
            PLAYBACK_INFO_ID_SUCCESS -> VideoPlaybackInfoFactory.DEFAULT
            else -> fail("Unsupported call")
        }

    override suspend fun getBroadcastPlaybackInfo(djSessionId: String, audioQuality: AudioQuality) =
        fail("Unsupported call")

    companion object {
        const val PLAYBACK_INFO_ID_SUCCESS = "0"
        const val PLAYBACK_INFO_ID_FOR_UNCAUGHT_EXCEPTION = "1"
    }
}
