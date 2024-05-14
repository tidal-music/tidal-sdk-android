package com.tidal.sdk.player.events.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ClientMarshallingTest {

    private val gson = Gson()

    @ParameterizedTest
    @EnumSource(Client.DeviceType::class)
    fun testMarshallingClient(deviceType: Client.DeviceType) {
        val token = "a token"
        val deviceTypeString = when (deviceType) {
            Client.DeviceType.ANDROID_AUTO -> "androidAuto"
            Client.DeviceType.TV -> "tv"
            Client.DeviceType.TABLET -> "tablet"
            Client.DeviceType.PHONE -> "phone"
        }
        val src = Client(token, deviceType)

        val actual = gson.toJsonTree(src).asJsonObject

        assertThat(actual["token"].asString).isEqualTo(token)
        assertThat(actual["deviceType"].asString).isEqualTo(deviceTypeString)
        assertThat(actual["platform"].asString).isEqualTo("android")
    }

    @ParameterizedTest
    @EnumSource(Client.DeviceType::class)
    fun testUnmarshallingClient(deviceType: Client.DeviceType) {
        val token = "a token"
        val expected = Client(token, deviceType)
        val src = gson.toJson(expected)

        val actual = gson.fromJson(src, Client::class.java)

        assertThat(actual).isEqualTo(expected)
    }
}
