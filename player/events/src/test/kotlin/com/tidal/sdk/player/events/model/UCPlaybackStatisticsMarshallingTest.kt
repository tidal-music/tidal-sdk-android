package com.tidal.sdk.player.events.model

import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.MediaStorage
import java.util.UUID

internal class UCPlaybackStatisticsMarshallingTest :
    PlaybackStatisticsMarshallingTest() {

    override val ts = -1L
    override val uuidString = "123e4567-e89b-12d3-a456-426614174000"
    override val user = User(8L, 54, "sessionId")
    override val client = Client("token", Client.DeviceType.TV, "version")
    override val payload = UCPlaybackStatistics.Payload(
        "streamingSessionId",
        Long.MAX_VALUE,
        -7,
        "actualProductId",
        AudioQuality.LOSSLESS,
        MediaStorage.INTERNET,
        PlaybackStatistics.Payload.Cdm.WIDEVINE,
        "cdmVersion",
        listOf(
            PlaybackStatistics.Payload.Stall(
                PlaybackStatistics.Payload.Stall.Reason.SEEK,
                1.0,
                10L,
                1L,
            ),
        ),
        listOf(PlaybackStatistics.Payload.Adaptation(3.0, 0L, "mimeType", "codecs", 3, 2, 1)),
        Long.MAX_VALUE,
        EndReason.ERROR,
        "errorMessage",
        "errorCode",
    )
    override val playbackStatistics = UCPlaybackStatistics(
        ts,
        UUID.fromString(uuidString),
        user,
        client,
        payload,
        emptyMap(),
    )
}
