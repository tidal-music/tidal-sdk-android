package com.tidal.sdk.player.events.model

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.VideoQuality
import java.util.UUID

internal class VideoDownloadStatisticsMarshallingTest : DownloadStatisticsMarshallingTest() {

    override val ts = -1L
    override val uuidString = "123e4567-e89b-12d3-a456-426614174000"
    override val user = User(8L, 54, "sessionId")
    override val client = Client("token", Client.DeviceType.ANDROID_AUTO, "version")
    override val payload = VideoDownloadStatistics.Payload(
        "streamingSessionId",
        0L,
        "actualProductId",
        AssetPresentation.PREVIEW,
        AudioMode.STEREO,
        VideoQuality.AUDIO_ONLY,
        EndReason.OTHER,
        5L,
        "errorMessage",
        "errorCode",
    )
    override val downloadStatistics = VideoDownloadStatistics(
        ts,
        UUID.fromString(uuidString),
        user,
        client,
        payload,
        emptyMap(),
    )
}
