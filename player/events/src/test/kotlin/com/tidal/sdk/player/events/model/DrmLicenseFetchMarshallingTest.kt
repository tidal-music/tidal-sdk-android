package com.tidal.sdk.player.events.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import java.util.UUID
import org.junit.jupiter.api.Test

internal class DrmLicenseFetchMarshallingTest {

    private val ts = 0L
    private val uuidString = "123e4567-e89b-12d3-a456-426614174000"
    private val user = User(-3L, 0, "sessionId")
    private val client = Client("token", Client.DeviceType.TV, "version")
    private val payload = DrmLicenseFetch.Payload(
        "streamingSessionId",
        1L,
        1L,
        EndReason.COMPLETE,
        "errorMessage",
        "errorCode",
    )
    private val drmLicenseFetch = DrmLicenseFetch(
        ts,
        UUID.fromString(uuidString),
        user,
        client,
        payload,
        null,
    )
    private val gson = Gson()

    @Test
    fun testMarshallingDrmLicenseFetch() {
        val actual = gson.toJsonTree(drmLicenseFetch).asJsonObject

        assertThat(actual["ts"].asLong).isEqualTo(ts)
        assertThat(actual["uuid"].asString).isEqualTo(uuidString)
        assertThat(gson.fromJson(actual["user"], User::class.java)).isEqualTo(user)
        assertThat(gson.fromJson(actual["client"], Client::class.java)).isEqualTo(client)
        assertThat(gson.fromJson(actual["payload"], payload::class.java)).isEqualTo(payload)
    }

    @Test
    fun testUnmarshallingDrmLicenseFetch() {
        val src = gson.toJson(drmLicenseFetch)

        val actual = gson.fromJson(src, DrmLicenseFetch::class.java)

        assertThat(actual).isEqualTo(drmLicenseFetch)
    }
}
