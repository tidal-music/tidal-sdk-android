package com.tidal.sdk.player.events.converter

import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.UserSupplier
import com.tidal.sdk.player.events.model.PlaybackInfoFetch

internal class PlaybackInfoFetchEventFactory(
    private val trueTimeWrapper: TrueTimeWrapper,
    private val uuidWrapper: UUIDWrapper,
    private val userSupplier: UserSupplier,
    private val clientSupplier: ClientSupplier,
    private val playbackInfoFetchFactory: PlaybackInfoFetch.Factory,
) : EventFactory<PlaybackInfoFetch.Payload> {

    override suspend fun invoke(payload: PlaybackInfoFetch.Payload, extras: Map<String, String?>?) =
        playbackInfoFetchFactory.create(
            trueTimeWrapper.currentTimeMillis,
            uuidWrapper.randomUUID,
            userSupplier(),
            clientSupplier(),
            payload,
            extras,
        )
}
