package com.tidal.sdk.player.events.converter

import com.tidal.networktime.SNTPClient
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.common.ntpOrLocalClockTime
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.UserSupplier
import com.tidal.sdk.player.events.model.AudioPlaybackStatistics

internal class AudioPlaybackStatisticsEventFactory(
    private val sntpClient: SNTPClient,
    private val uuidWrapper: UUIDWrapper,
    private val userSupplier: UserSupplier,
    private val clientSupplier: ClientSupplier,
    private val audioPlaybackStatisticsFactory: AudioPlaybackStatistics.Factory,
) : EventFactory<AudioPlaybackStatistics.Payload> {

    override suspend fun invoke(payload: AudioPlaybackStatistics.Payload) =
        audioPlaybackStatisticsFactory
            .create(
                sntpClient.ntpOrLocalClockTime.inWholeMilliseconds,
                uuidWrapper.randomUUID,
                userSupplier(),
                clientSupplier(),
                payload,
            )
}
