package com.tidal.sdk.player.events.converter

import android.content.res.Resources
import com.tidal.sdk.player.events.model.StreamingSessionStart
import com.tidal.sdk.player.events.util.ActiveMobileNetworkType
import com.tidal.sdk.player.events.util.ActiveNetworkType
import com.tidal.sdk.player.events.util.HardwarePlatform

internal class StreamingSessionStartPayloadDecorator(
    private val streamingSessionStartDecoratedPayloadFactory:
        StreamingSessionStart.DecoratedPayload.Factory, // ktlint-disable max-line-length
    // parameter-wrapping
    private val hardwarePlatform: HardwarePlatform,
    private val operatingSystemVersion: String,
    private val resources: Resources,
    private val activeNetworkType: ActiveNetworkType,
    private val activeMobileNetworkType: ActiveMobileNetworkType,
) {

    fun decorate(payload: StreamingSessionStart.Payload) =
        payload.run {
            streamingSessionStartDecoratedPayloadFactory.create(
                streamingSessionId,
                timestamp,
                isOfflineModeStart,
                startReason,
                hardwarePlatform.value,
                "Android",
                operatingSystemVersion,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                activeNetworkType.value,
                activeMobileNetworkType.value,
                sessionType,
                sessionProductType,
                sessionProductId,
            )
        }
}
