package com.tidal.sdk.player.streamingapi.playbackinfo.api

import com.tidal.sdk.player.common.model.AudioQuality
import org.junit.jupiter.api.fail

/**
 * Stub implementation of [PlaybackInfoService] that is useful for testing without any dependencies.
 *
 * This implementation hard codes the result of the functions to a various values useful in tests.
 */
internal class PlaybackInfoServiceStub : PlaybackInfoService {

    override suspend fun getBroadcastPlaybackInfo(djSessionId: String, audioQuality: AudioQuality) =
        fail("Unsupported call")
}
