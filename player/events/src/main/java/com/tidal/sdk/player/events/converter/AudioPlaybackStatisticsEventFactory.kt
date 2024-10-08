package com.tidal.sdk.player.events.converter

import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.common.model.BaseMediaProduct.Extras
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.UserSupplier
import com.tidal.sdk.player.events.model.AudioPlaybackStatistics

internal class AudioPlaybackStatisticsEventFactory(
    private val trueTimeWrapper: TrueTimeWrapper,
    private val uuidWrapper: UUIDWrapper,
    private val userSupplier: UserSupplier,
    private val clientSupplier: ClientSupplier,
    private val audioPlaybackStatisticsFactory: AudioPlaybackStatistics.Factory,
) : EventFactory<AudioPlaybackStatistics.Payload> {

    override suspend fun invoke(
        payload: AudioPlaybackStatistics.Payload,
        extras: Extras?,
    ) = audioPlaybackStatisticsFactory.create(
        trueTimeWrapper.currentTimeMillis,
        uuidWrapper.randomUUID,
        userSupplier(),
        clientSupplier(),
        payload,
        extras,
    )
}
