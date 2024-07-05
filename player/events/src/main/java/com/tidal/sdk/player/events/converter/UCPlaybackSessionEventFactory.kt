package com.tidal.sdk.player.events.converter

import com.tidal.networktime.SNTPClient
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.common.ntpOrLocalClockTime
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.UserSupplier
import com.tidal.sdk.player.events.model.UCPlaybackSession

internal class UCPlaybackSessionEventFactory(
    private val sntpClient: SNTPClient,
    private val uuidWrapper: UUIDWrapper,
    private val userSupplier: UserSupplier,
    private val clientSupplier: ClientSupplier,
    private val ucPlaybackSessionFactory: UCPlaybackSession.Factory,
) : EventFactory<UCPlaybackSession.Payload> {

    override suspend fun invoke(payload: UCPlaybackSession.Payload) =
        ucPlaybackSessionFactory
            .create(
                sntpClient.ntpOrLocalClockTime.inWholeMilliseconds,
                uuidWrapper.randomUUID,
                userSupplier(),
                clientSupplier(),
                payload,
            )
}
