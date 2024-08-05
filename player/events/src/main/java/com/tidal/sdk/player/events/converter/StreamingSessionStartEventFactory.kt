package com.tidal.sdk.player.events.converter

import com.tidal.networktime.SNTPClient
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.common.ntpOrLocalClockTime
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.UserSupplier
import com.tidal.sdk.player.events.model.StreamingSessionStart

internal class StreamingSessionStartEventFactory(
    private val sntpClient: SNTPClient,
    private val uuidWrapper: UUIDWrapper,
    private val userSupplier: UserSupplier,
    private val clientSupplier: ClientSupplier,
    private val streamingSessionStartPayloadDecorator: StreamingSessionStartPayloadDecorator,
    private val streamingSessionStartFactory: StreamingSessionStart.Factory,
) : EventFactory<StreamingSessionStart.Payload> {

    override suspend fun invoke(payload: StreamingSessionStart.Payload) =
        streamingSessionStartFactory
            .create(
                sntpClient.ntpOrLocalClockTime.inWholeMilliseconds,
                uuidWrapper.randomUUID,
                userSupplier(),
                clientSupplier(),
                streamingSessionStartPayloadDecorator.decorate(payload),
            )
}
