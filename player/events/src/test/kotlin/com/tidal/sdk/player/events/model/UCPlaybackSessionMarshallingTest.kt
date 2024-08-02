package com.tidal.sdk.player.events.model

import com.tidal.sdk.player.common.model.AudioQuality
import java.util.UUID

internal class UCPlaybackSessionMarshallingTest : PlaybackSessionMarshallingTest() {

    override val ts = 0L
    override val uuidString = "123e4567-e89b-12d3-a456-426614174000"
    override val user = User(0L, 3, "sessionId")
    override val client = Client("token", Client.DeviceType.TV, "version")
    override val payload = UCPlaybackSession.Payload(
        UUID.fromString(uuidString),
        Long.MIN_VALUE,
        Double.MIN_VALUE,
        "${Int.MAX_VALUE}",
        "7",
        AudioQuality.HIGH,
        "sourceType",
        "sourceId",
        listOf(
            PlaybackSession.Payload.Action(
                1L,
                -1.0,
                PlaybackSession.Payload.Action.Type.PLAYBACK_START,
            ),
        ),
        Long.MAX_VALUE,
        Double.MAX_VALUE,
    )
    override val playbackSession = UCPlaybackSession(
        ts,
        UUID.fromString(uuidString),
        user,
        client,
        payload,
        emptyMap(),
    )
}
