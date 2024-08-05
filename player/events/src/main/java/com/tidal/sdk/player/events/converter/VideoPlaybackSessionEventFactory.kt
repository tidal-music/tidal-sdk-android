package com.tidal.sdk.player.events.converter

import com.tidal.networktime.SNTPClient
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.common.ntpOrLocalClockTime
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.UserSupplier
import com.tidal.sdk.player.events.model.VideoPlaybackSession

internal class VideoPlaybackSessionEventFactory(
    private val sntpClient: SNTPClient,
    private val uuidWrapper: UUIDWrapper,
    private val userSupplier: UserSupplier,
    private val clientSupplier: ClientSupplier,
    private val videoPlaybackSessionFactory: VideoPlaybackSession.Factory,
) : EventFactory<VideoPlaybackSession.Payload> {

    override suspend fun invoke(payload: VideoPlaybackSession.Payload) = videoPlaybackSessionFactory
        .create(
            sntpClient.ntpOrLocalClockTime.inWholeMilliseconds,
            uuidWrapper.randomUUID,
            userSupplier(),
            clientSupplier(),
            payload,
        )
}
