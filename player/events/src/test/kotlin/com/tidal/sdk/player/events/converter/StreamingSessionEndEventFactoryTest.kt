package com.tidal.sdk.player.events.converter

import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.UserSupplier
import com.tidal.sdk.player.events.model.Client
import com.tidal.sdk.player.events.model.StreamingSessionEnd
import com.tidal.sdk.player.events.model.User
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class StreamingSessionEndEventFactoryTest {

    private val trueTimeWrapper = mock<TrueTimeWrapper>()
    private val uuidWrapper = mock<UUIDWrapper>()
    private val userSupplier = mock<UserSupplier>()
    private val clientSupplier = mock<ClientSupplier>()
    private val streamingSessionEndFactory = mock<StreamingSessionEnd.Factory>()
    private val streamingSessionEndEventFactory = StreamingSessionEndEventFactory(
        trueTimeWrapper,
        uuidWrapper,
        userSupplier,
        clientSupplier,
        streamingSessionEndFactory,
    )

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(
        trueTimeWrapper,
        uuidWrapper,
        userSupplier,
        clientSupplier,
        streamingSessionEndFactory,
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
        val payload = mock<StreamingSessionEnd.Payload>()
        val expected = mock<StreamingSessionEnd>()
        whenever(
            streamingSessionEndFactory.create(
                currentTimeMillis,
                randomUUID,
                user,
                client,
                payload,
                null,
            ),
        ).thenReturn(expected)

        val actual = streamingSessionEndEventFactory(payload, null)

        verify(trueTimeWrapper).currentTimeMillis
        verify(uuidWrapper).randomUUID
        verify(userSupplier)()
        verify(clientSupplier)()
        verify(streamingSessionEndFactory).create(
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
