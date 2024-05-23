package com.tidal.sdk.player.events.model

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.MediaStorage
import com.tidal.sdk.player.common.model.StreamType
import com.tidal.sdk.player.common.model.VideoQuality
import java.util.UUID

internal class VideoPlaybackStatisticsMarshallingTest : PlaybackStatisticsMarshallingTest() {

    override val ts = -1L
    override val uuidString = "123e4567-e89b-12d3-a456-426614174000"
    override val user = User(8L, 54, "sessionId")
    override val client = Client("token", Client.DeviceType.TV, "version")
    override val payload = VideoPlaybackStatistics.Payload(
        "streamingSessionId",
        Long.MIN_VALUE,
        0,
        "actualProductId",
        StreamType.LIVE,
        AssetPresentation.FULL,
        VideoQuality.LOW,
        MediaStorage.INTERNET,
        PlaybackStatistics.Payload.Cdm.WIDEVINE,
        "cdmVersion",
        listOf(
            PlaybackStatistics.Payload.Stall(
                PlaybackStatistics.Payload.Stall.Reason.UNEXPECTED,
                0.0,
                1L,
                -1L,
            ),
        ),
        listOf(PlaybackStatistics.Payload.Adaptation(0.0, 0L, "mimeType", "codecs", 0, 0, 0)),
        Long.MIN_VALUE,
        EndReason.COMPLETE,
        null,
        null,
    )
    override val playbackStatistics = VideoPlaybackStatistics(
        ts,
        UUID.fromString(uuidString),
        user,
        client,
        payload,
    )
}
