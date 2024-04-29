package com.tidal.sdk.player.events.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import org.junit.jupiter.api.Test

internal abstract class DownloadStatisticsMarshallingTest {

    abstract val ts: Long
    abstract val uuidString: String
    abstract val user: User
    abstract val client: Client
    abstract val payload: DownloadStatistics.Payload
    abstract val downloadStatistics: DownloadStatistics<out DownloadStatistics.Payload>
    private val gson = Gson()

    @Test
    fun testMarshallingDownloadStatistics() {
        val actual = gson.toJsonTree(downloadStatistics).asJsonObject

        assertThat(actual["ts"].asLong).isEqualTo(ts)
        assertThat(actual["uuid"].asString).isEqualTo(uuidString)
        assertThat(gson.fromJson(actual["user"], User::class.java)).isEqualTo(user)
        assertThat(gson.fromJson(actual["client"], Client::class.java)).isEqualTo(client)
        assertThat(gson.fromJson(actual["payload"], payload::class.java)).isEqualTo(payload)
    }

    @Test
    fun testUnmarshallingDownloadStatistics() {
        val src = gson.toJson(downloadStatistics)

        val actual = gson.fromJson(
            src,
            when (downloadStatistics) {
                is AudioDownloadStatistics -> AudioDownloadStatistics::class.java
                is VideoDownloadStatistics -> VideoDownloadStatistics::class.java
            },
        )

        assertThat(actual).isEqualTo(downloadStatistics)
    }
}
