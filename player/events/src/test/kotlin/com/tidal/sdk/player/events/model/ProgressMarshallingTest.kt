package com.tidal.sdk.player.events.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import com.tidal.sdk.player.common.model.ProductType
import java.util.UUID
import org.junit.jupiter.api.Test

internal class ProgressMarshallingTest {

    private val ts = 0L
    private val uuidString = "123e4567-e89b-12d3-a456-426614174000"
    private val user = User(-3L, 0, "sessionId")
    private val client = Client("token", Client.DeviceType.TV, "version")
    private val payload = Progress.Payload(
        Progress.Payload.Playback(
            "mediaProductId",
            1,
            2,
            ProductType.VIDEO,
            Progress.Payload.Playback.Source("type", "id"),
        ),
    )
    private val progress = Progress(ts, UUID.fromString(uuidString), user, client, payload)
    private val gson = Gson()

    @Test
    fun testMarshallingProgress() {
        val actual = gson.toJsonTree(progress).asJsonObject

        assertThat(actual["ts"].asLong).isEqualTo(ts)
        assertThat(actual["uuid"].asString).isEqualTo(uuidString)
        assertThat(gson.fromJson(actual["user"], User::class.java)).isEqualTo(user)
        assertThat(gson.fromJson(actual["client"], Client::class.java)).isEqualTo(client)
        assertThat(gson.fromJson(actual["payload"], payload::class.java)).isEqualTo(payload)
    }

    @Test
    fun testUnmarshallingProgress() {
        val src = gson.toJson(progress)

        val actual = gson.fromJson(src, Progress::class.java)

        assertThat(actual).isEqualTo(progress)
    }
}
