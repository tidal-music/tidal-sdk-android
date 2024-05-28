package com.tidal.sdk.player.events.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class PlaybackInfoFetchPayloadMarshallingTest {

    private val streamingSessionId = "streamingSessionId"
    private val startTimestamp = 53L
    private val endTimestamp = 0L
    private val payloadFactory: (EndReason, String?, String?) -> PlaybackInfoFetch.Payload =
        { endReason, errorMessage, errorCode ->
            PlaybackInfoFetch.Payload(
                streamingSessionId,
                startTimestamp,
                endTimestamp,
                endReason,
                errorMessage,
                errorCode,
            )
        }
    private val gson = Gson()

    @ParameterizedTest
    @EnumSource(EndReason::class)
    fun testMarshallingPayloadWithErrorMessage(endReason: EndReason) =
        testMarshallingPayload(endReason, "errorMessage", "errorCode")

    @ParameterizedTest
    @EnumSource(EndReason::class)
    fun testMarshallingPayloadWithoutErrorMessage(endReason: EndReason) =
        testMarshallingPayload(endReason, null, null)

    private fun testMarshallingPayload(
        endReason: EndReason,
        errorMessage: String?,
        errorCode: String?,
    ) {
        val src = payloadFactory(endReason, errorMessage, errorCode)

        val actual = gson.toJsonTree(src).asJsonObject

        assertThat(actual["streamingSessionId"].asString).isEqualTo(streamingSessionId)
        assertThat(actual["startTimestamp"].asLong).isEqualTo(startTimestamp)
        assertThat(actual["endTimestamp"].asLong).isEqualTo(endTimestamp)
        assertThat(actual["endReason"].asString).isEqualTo(endReason.name)
        assertThat(actual["errorMessage"]?.asString).isEqualTo(errorMessage)
        assertThat(actual["errorCode"]?.asString).isEqualTo(errorCode)
    }

    @ParameterizedTest
    @EnumSource(EndReason::class)
    fun testUnmarshallingPayloadWithErrorMessage(endReason: EndReason) =
        testUnmarshallingPayload(endReason, "errorMessage", "errorCode")

    @ParameterizedTest
    @EnumSource(EndReason::class)
    fun testUnmarshallingPayloadWithoutErrorMessage(endReason: EndReason) =
        testUnmarshallingPayload(endReason, null, null)

    private fun testUnmarshallingPayload(
        endReason: EndReason,
        errorMessage: String?,
        errorCode: String?,
    ) {
        val expected = payloadFactory(endReason, errorMessage, errorCode)
        val src = gson.toJson(expected)

        val actual = gson.fromJson(src, PlaybackInfoFetch.Payload::class.java)

        assertThat(actual).isEqualTo(expected)
    }
}
