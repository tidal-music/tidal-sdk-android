package com.tidal.sdk.player.events.converter

import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.UserSupplier
import com.tidal.sdk.player.events.model.VideoDownloadStatistics

internal class VideoDownloadStatisticsEventFactory(
    private val trueTimeWrapper: TrueTimeWrapper,
    private val uuidWrapper: UUIDWrapper,
    private val userSupplier: UserSupplier,
    private val clientSupplier: ClientSupplier,
    private val videoDownloadStatisticsFactory: VideoDownloadStatistics.Factory,
) : EventFactory<VideoDownloadStatistics.Payload> {

    override suspend fun invoke(payload: VideoDownloadStatistics.Payload) =
        videoDownloadStatisticsFactory
            .create(
                trueTimeWrapper.currentTimeMillis,
                uuidWrapper.randomUUID,
                userSupplier(),
                clientSupplier(),
                payload,
            )
}
