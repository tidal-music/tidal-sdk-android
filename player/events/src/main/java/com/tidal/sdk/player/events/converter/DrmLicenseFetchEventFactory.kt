package com.tidal.sdk.player.events.converter

import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.UserSupplier
import com.tidal.sdk.player.events.model.DrmLicenseFetch

internal class DrmLicenseFetchEventFactory(
    private val trueTimeWrapper: TrueTimeWrapper,
    private val uuidWrapper: UUIDWrapper,
    private val userSupplier: UserSupplier,
    private val clientSupplier: ClientSupplier,
    private val drmLicenseFetchFactory: DrmLicenseFetch.Factory,
) : EventFactory<DrmLicenseFetch.Payload> {

    override suspend fun invoke(payload: DrmLicenseFetch.Payload) = drmLicenseFetchFactory
        .create(
            trueTimeWrapper.currentTimeMillis,
            uuidWrapper.randomUUID,
            userSupplier(),
            clientSupplier(),
            payload,
        )
}
