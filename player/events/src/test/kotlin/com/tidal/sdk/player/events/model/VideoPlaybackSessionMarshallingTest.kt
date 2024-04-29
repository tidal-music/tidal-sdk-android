package com.tidal.sdk.player.events.model

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.VideoQuality
import java.util.UUID

internal class VideoPlaybackSessionMarshallingTest : PlaybackSessionMarshallingTest() {

    override val ts = 0L
    override val uuidString = "123e4567-e89b-12d3-a456-426614174000"
    override val user = User(1L, 2, "sessionId")
    override val client = Client("token", Client.DeviceType.TABLET, "version")
    override val payload = VideoPlaybackSession.Payload(
        UUID.fromString(uuidString),
        Long.MAX_VALUE,
        Double.MAX_VALUE,
        "${Int.MIN_VALUE}",
        "-8",
        AssetPresentation.PREVIEW,
        VideoQuality.AUDIO_ONLY,
        "sourceType",
        "sourceId",
        listOf(
            PlaybackSession.Payload.Action(
                0L,
                0.0,
                PlaybackSession.Payload.Action.Type.PLAYBACK_STOP,
            ),
        ),
        Long.MIN_VALUE,
        Double.MIN_VALUE,
    )
    override val playbackSession = VideoPlaybackSession(
        ts,
        UUID.fromString(uuidString),
        user,
        client,
        payload,
    )
}
