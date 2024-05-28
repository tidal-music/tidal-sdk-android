package com.tidal.sdk.player.events.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import com.tidal.sdk.player.common.model.ProductType
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class StreamingSessionStartDecoratedPayloadMarshallingTest {

    private val streamingSessionId = "streamingSessionId"
    private val timestamp = 53L
    private val isOfflineModeStart = false
    private val hardwarePlatform = "hardwarePlatform"
    private val operatingSystem = "operatingSystem"
    private val operatingSystemVersion = "operatingSystemVersion"
    private val screenWidth = 0
    private val screenHeight = 7
    private val mobileNetworkType = "mobileNetworkType"
    private val sessionType = StreamingSessionStart.SessionType.PLAYBACK
    private val sessionProductId = "123"
    private val payloadFactory: (
        StreamingSessionStart.StartReason,
        StreamingSessionStart.NetworkType,
        ProductType,
    ) -> StreamingSessionStart.DecoratedPayload = { startReason, networkType, sessionProductType ->
        StreamingSessionStart.DecoratedPayload(
            streamingSessionId,
            timestamp,
            isOfflineModeStart,
            startReason,
            hardwarePlatform,
            operatingSystem,
            operatingSystemVersion,
            screenWidth,
            screenHeight,
            networkType,
            mobileNetworkType,
            sessionType,
            sessionProductType,
            sessionProductId,
        )
    }
    private val gson = Gson()

    @ParameterizedTest
    @MethodSource("combinationsForEnumAndNullableFieldsInPayloads")
    fun testMarshallingPayload(
        startReason: StreamingSessionStart.StartReason,
        networkType: StreamingSessionStart.NetworkType,
        sessionProductType: ProductType,
    ) {
        val src = payloadFactory(startReason, networkType, sessionProductType)

        val actual = gson.toJsonTree(src).asJsonObject

        assertThat(actual["streamingSessionId"].asString).isEqualTo(streamingSessionId)
        assertThat(actual["timestamp"].asLong).isEqualTo(timestamp)
        assertThat(actual["isOfflineModeStart"].asBoolean).isEqualTo(isOfflineModeStart)
        assertThat(actual["startReason"].asString).isEqualTo(startReason.name)
        assertThat(actual["hardwarePlatform"].asString).isEqualTo(hardwarePlatform)
        assertThat(actual["operatingSystem"].asString).isEqualTo(operatingSystem)
        assertThat(actual["operatingSystemVersion"].asString).isEqualTo(operatingSystemVersion)
        assertThat(actual["screenWidth"].asInt).isEqualTo(screenWidth)
        assertThat(actual["screenHeight"].asInt).isEqualTo(screenHeight)
        assertThat(actual["networkType"].asString).isEqualTo(networkType.name)
        assertThat(actual["mobileNetworkType"]?.asString).isEqualTo(mobileNetworkType)
        assertThat(actual["sessionType"].asString).isEqualTo(sessionType.name)
        assertThat(actual["sessionProductType"].asString).isEqualTo(sessionProductType.name)
        assertThat(actual["sessionProductId"].asString).isEqualTo(sessionProductId)
    }

    @ParameterizedTest
    @MethodSource("combinationsForEnumAndNullableFieldsInPayloads")
    fun testUnmarshallingPayload(
        startReason: StreamingSessionStart.StartReason,
        networkType: StreamingSessionStart.NetworkType,
        sessionProductType: ProductType,
    ) {
        val expected = payloadFactory(startReason, networkType, sessionProductType)
        val src = gson.toJson(expected)

        val actual = gson.fromJson(src, StreamingSessionStart.DecoratedPayload::class.java)

        assertThat(actual).isEqualTo(expected)
    }

    companion object {

        @JvmStatic
        @Suppress("UnusedPrivateMember")
        private fun combinationsForEnumAndNullableFieldsInPayloads(): Iterable<Arguments> {
            val arguments = mutableSetOf<Arguments>()
            StreamingSessionStart.StartReason.values().forEach { startReason ->
                StreamingSessionStart.NetworkType.values().forEach { networkType ->
                    ProductType.values().forEach { productType: ProductType ->
                        arguments.add(Arguments.of(startReason, networkType, productType))
                    }
                }
            }
            return arguments
        }
    }
}
