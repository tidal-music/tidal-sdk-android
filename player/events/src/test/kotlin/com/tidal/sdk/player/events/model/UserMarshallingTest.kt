package com.tidal.sdk.player.events.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import org.junit.jupiter.api.Test

internal class UserMarshallingTest {

    private val gson = Gson()

    @Test
    fun testMarshallingUser() {
        val id = 87L
        val clientId = 1000
        val sessionId = "a session id"
        val src = User(id, clientId, sessionId)

        val actual = gson.toJsonTree(src).asJsonObject

        assertThat(actual["id"].asLong).isEqualTo(id)
        assertThat(actual["clientId"].asInt).isEqualTo(clientId)
        assertThat(actual["sessionId"].asString).isEqualTo(sessionId)
    }

    @Test
    fun testUnmarshallingUser() {
        val id = 87L
        val clientId = 1000
        val sessionId = "a session id"
        val expected = User(id, clientId, sessionId)
        val src = gson.toJson(expected)

        val actual = gson.fromJson(src, User::class.java)

        assertThat(actual).isEqualTo(expected)
    }
}
