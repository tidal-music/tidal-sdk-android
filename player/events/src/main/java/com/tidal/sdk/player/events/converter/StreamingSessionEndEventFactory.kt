package com.tidal.sdk.player.events.converter

import com.tidal.networktime.SNTPClient
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.common.ntpOrLocalClockTime
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.UserSupplier
import com.tidal.sdk.player.events.model.StreamingSessionEnd

internal class StreamingSessionEndEventFactory(
    private val sntpClient: SNTPClient,
    private val uuidWrapper: UUIDWrapper,
    private val userSupplier: UserSupplier,
    private val clientSupplier: ClientSupplier,
    private val streamingSessionEndFactory: StreamingSessionEnd.Factory,
) : EventFactory<StreamingSessionEnd.Payload> {

    override suspend fun invoke(payload: StreamingSessionEnd.Payload) = streamingSessionEndFactory
        .create(
            sntpClient.ntpOrLocalClockTime.inWholeMilliseconds,
            uuidWrapper.randomUUID,
            userSupplier(),
            clientSupplier(),
            payload,
        )
}
