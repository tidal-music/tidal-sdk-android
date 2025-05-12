package com.tidal.sdk.player.events.converter

import android.content.res.Resources
import android.util.DisplayMetrics
import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.events.model.StreamingSessionStart
import com.tidal.sdk.player.events.util.ActiveMobileNetworkType
import com.tidal.sdk.player.events.util.ActiveNetworkType
import com.tidal.sdk.player.events.util.HardwarePlatform
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class StreamingSessionStartPayloadDecoratorTest {

    private val streamingSessionStartDecoratedPayloadFactory =
        mock<StreamingSessionStart.DecoratedPayload.Factory>()
    private val hardwarePlatform = mock<HardwarePlatform>()
    private val operatingSystemVersion = "operatingSystemVersion"
    private val resources = mock<Resources>()
    private val activeNetworkType = mock<ActiveNetworkType>()
    private val activeMobileNetworkType = mock<ActiveMobileNetworkType>()
    private val streamingSessionStartPayloadDecorator =
        StreamingSessionStartPayloadDecorator(
            streamingSessionStartDecoratedPayloadFactory,
            hardwarePlatform,
            operatingSystemVersion,
            resources,
            activeNetworkType,
            activeMobileNetworkType,
        )

    @AfterEach
    fun afterEach() =
        verifyNoMoreInteractions(
            streamingSessionStartDecoratedPayloadFactory,
            hardwarePlatform,
            resources,
            activeNetworkType,
            activeMobileNetworkType,
        )

    @Test
    @Suppress("LongMethod")
    fun decorate() {
        val payload = mock<StreamingSessionStart.Payload>()
        val streamingSessionId = "streamingSessionId"
        whenever(payload.streamingSessionId) doReturn streamingSessionId
        val timestamp = 3L
        whenever(payload.timestamp) doReturn timestamp
        val isOfflineStart = true
        whenever(payload.isOfflineModeStart) doReturn isOfflineStart
        val startReason = StreamingSessionStart.StartReason.IMPLICIT
        whenever(payload.startReason) doReturn startReason
        val hardwarePlatformValue = "hardwarePlatformValue"
        whenever(hardwarePlatform.value) doReturn hardwarePlatformValue
        val widthPixels = Int.MAX_VALUE
        val heightPixels = Int.MIN_VALUE
        val displayMetrics =
            mock<DisplayMetrics>().apply {
                this.widthPixels = widthPixels
                this.heightPixels = heightPixels
            }
        whenever(resources.displayMetrics) doReturn displayMetrics
        val activeNetworkTypeValue = StreamingSessionStart.NetworkType.WIFI
        whenever(activeNetworkType.value) doReturn activeNetworkTypeValue
        val activeMobileNetworkTypeValue = "activeMobileNetworkTypeValue"
        whenever(activeMobileNetworkType.value) doReturn activeMobileNetworkTypeValue
        val sessionType = StreamingSessionStart.SessionType.PLAYBACK
        whenever(payload.sessionType) doReturn sessionType
        val sessionProductType = ProductType.TRACK
        whenever(payload.sessionProductType) doReturn sessionProductType
        val sessionProductId = "123"
        whenever(payload.sessionProductId) doReturn sessionProductId
        val expected = mock<StreamingSessionStart.DecoratedPayload>()
        whenever(
                streamingSessionStartDecoratedPayloadFactory.create(
                    streamingSessionId,
                    timestamp,
                    isOfflineStart,
                    startReason,
                    hardwarePlatformValue,
                    "Android",
                    operatingSystemVersion,
                    widthPixels,
                    heightPixels,
                    activeNetworkTypeValue,
                    activeMobileNetworkTypeValue,
                    sessionType,
                    sessionProductType,
                    sessionProductId,
                )
            )
            .thenReturn(expected)

        val actual = streamingSessionStartPayloadDecorator.decorate(payload)

        verify(payload).streamingSessionId
        verify(payload).timestamp
        verify(payload).isOfflineModeStart
        verify(payload).startReason
        verify(hardwarePlatform).value
        verify(resources, times(2)).displayMetrics
        verify(activeNetworkType).value
        verify(activeMobileNetworkType).value
        verify(payload).sessionType
        verify(payload).sessionProductType
        verify(payload).sessionProductId
        verify(streamingSessionStartDecoratedPayloadFactory)
            .create(
                streamingSessionId,
                timestamp,
                isOfflineStart,
                startReason,
                hardwarePlatformValue,
                "Android",
                operatingSystemVersion,
                widthPixels,
                heightPixels,
                activeNetworkTypeValue,
                activeMobileNetworkTypeValue,
                sessionType,
                sessionProductType,
                sessionProductId,
            )
        assertThat(actual).isSameAs(expected)
        verifyNoMoreInteractions(displayMetrics, payload, expected)
    }
}
