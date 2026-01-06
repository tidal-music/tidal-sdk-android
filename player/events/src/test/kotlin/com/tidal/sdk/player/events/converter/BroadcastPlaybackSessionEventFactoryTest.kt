package com.tidal.sdk.player.events.converter

import assertk.assertThat
import assertk.assertions.isSameInstanceAs
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.UserSupplier
import com.tidal.sdk.player.events.model.BroadcastPlaybackSession
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

internal class BroadcastPlaybackSessionEventFactoryTest {

    private val trueTimeWrapper = mock<TrueTimeWrapper>()
    private val uuidWrapper = mock<UUIDWrapper>()
    private val userSupplier = mock<UserSupplier>()
    private val clientSupplier = mock<ClientSupplier>()
    private val broadcastPlaybackSessionFactory = mock<BroadcastPlaybackSession.Factory>()
    private val broadcastPlaybackSessionEventFactory =
        BroadcastPlaybackSessionEventFactory(
            trueTimeWrapper,
            uuidWrapper,
            userSupplier,
            clientSupplier,
            broadcastPlaybackSessionFactory,
        )

    @AfterEach
    fun afterEach() =
        verifyNoMoreInteractions(
            trueTimeWrapper,
            uuidWrapper,
            userSupplier,
            clientSupplier,
            broadcastPlaybackSessionFactory,
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
        val payload = mock<BroadcastPlaybackSession.Payload>()
        val expected = mock<BroadcastPlaybackSession>()
        whenever(
                broadcastPlaybackSessionFactory.create(
                    currentTimeMillis,
                    randomUUID,
                    user,
                    client,
                    payload,
                    emptyMap(),
                )
            )
            .thenReturn(expected)

        val actual = broadcastPlaybackSessionEventFactory(payload, emptyMap())

        verify(trueTimeWrapper).currentTimeMillis
        verify(uuidWrapper).randomUUID
        verify(userSupplier)()
        verify(clientSupplier)()
        verify(broadcastPlaybackSessionFactory)
            .create(currentTimeMillis, randomUUID, user, client, payload, emptyMap())
        assertThat(actual).isSameInstanceAs(expected)
        verifyNoMoreInteractions(randomUUID, user, client, payload, expected)
    }
}
