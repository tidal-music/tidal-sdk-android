package com.tidal.sdk.player.events.converter

import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.common.model.Extras
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.UserSupplier
import com.tidal.sdk.player.events.model.BroadcastPlaybackStatistics

internal class BroadcastPlaybackStatisticsEventFactory(
    private val trueTimeWrapper: TrueTimeWrapper,
    private val uuidWrapper: UUIDWrapper,
    private val userSupplier: UserSupplier,
    private val clientSupplier: ClientSupplier,
    private val broadcastPlaybackStatisticsFactory: BroadcastPlaybackStatistics.Factory,
) : EventFactory<BroadcastPlaybackStatistics.Payload> {

    override suspend fun invoke(payload: BroadcastPlaybackStatistics.Payload, extras: Extras?) =
        broadcastPlaybackStatisticsFactory.create(
            trueTimeWrapper.currentTimeMillis,
            uuidWrapper.randomUUID,
            userSupplier(),
            clientSupplier(),
            payload,
            extras,
        )
}
