package com.tidal.sdk.player.events.converter

import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.UserSupplier
import com.tidal.sdk.player.events.model.BroadcastPlaybackStatistics
import com.tidal.sdk.player.events.model.Client
import com.tidal.sdk.player.events.model.User
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class BroadcastPlaybackStatisticsEventFactoryTest {

    private val trueTimeWrapper = mock<TrueTimeWrapper>()
    private val uuidWrapper = mock<UUIDWrapper>()
    private val userSupplier = mock<UserSupplier>()
    private val clientSupplier = mock<ClientSupplier>()
    private val broadcastPlaybackStatisticsFactory =
        mock<BroadcastPlaybackStatistics.Factory>()
    private val broadcastPlaybackStatisticsEventFactory =
        BroadcastPlaybackStatisticsEventFactory(
            trueTimeWrapper,
            uuidWrapper,
            userSupplier,
            clientSupplier,
            broadcastPlaybackStatisticsFactory,
        )

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(
        trueTimeWrapper,
        uuidWrapper,
        userSupplier,
        clientSupplier,
        broadcastPlaybackStatisticsFactory,
    )

    @Test
    fun invoke() = runBlocking {
        val currentTimeMillis = -3L
        whenever(trueTimeWrapper.currentTimeMillis).thenReturn(currentTimeMillis)
        val randomUUID = mock<UUID>()
        whenever(uuidWrapper.randomUUID).thenReturn(randomUUID)
        val user = mock<User>()
        whenever(userSupplier()).thenReturn(user)
        val client = mock<Client>()
        whenever(clientSupplier()).thenReturn(client)
        val payload = mock<BroadcastPlaybackStatistics.Payload>()
        val expected = mock<BroadcastPlaybackStatistics>()
        whenever(
            broadcastPlaybackStatisticsFactory.create(
                currentTimeMillis,
                randomUUID,
                user,
                client,
                payload,
                null,
            ),
        ).thenReturn(expected)

        val actual = broadcastPlaybackStatisticsEventFactory(payload, null)

        verify(trueTimeWrapper).currentTimeMillis
        verify(uuidWrapper).randomUUID
        verify(userSupplier)()
        verify(clientSupplier)()
        verify(broadcastPlaybackStatisticsFactory).create(
            currentTimeMillis,
            randomUUID,
            user,
            client,
            payload,
            null,
        )
        assertThat(actual).isSameAs(expected)
        verifyNoMoreInteractions(randomUUID, user, client, payload, expected)
    }
}
