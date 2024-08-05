package com.tidal.sdk.player.events.converter

import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.networktime.SNTPClient
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.UserSupplier
import com.tidal.sdk.player.events.model.Client
import com.tidal.sdk.player.events.model.StreamingSessionStart
import com.tidal.sdk.player.events.model.User
import java.util.UUID
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class StreamingSessionStartEventFactoryTest {

    private val sntpClient = mock<SNTPClient>()
    private val uuidWrapper = mock<UUIDWrapper>()
    private val userSupplier = mock<UserSupplier>()
    private val clientSupplier = mock<ClientSupplier>()
    private val streamingSessionStartPayloadDecorator =
        mock<StreamingSessionStartPayloadDecorator>()
    private val streamingSessionStartFactory = mock<StreamingSessionStart.Factory>()
    private val streamingSessionStartEventFactory = StreamingSessionStartEventFactory(
        sntpClient,
        uuidWrapper,
        userSupplier,
        clientSupplier,
        streamingSessionStartPayloadDecorator,
        streamingSessionStartFactory,
    )

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(
        sntpClient,
        uuidWrapper,
        userSupplier,
        clientSupplier,
        streamingSessionStartPayloadDecorator,
        streamingSessionStartFactory,
    )

    @Test
    fun invoke() = runBlocking {
        val currentTime = -3.milliseconds
        whenever(sntpClient.epochTime).thenReturn(currentTime)
        val randomUUID = mock<UUID>()
        whenever(uuidWrapper.randomUUID).thenReturn(randomUUID)
        val user = mock<User>()
        whenever(userSupplier()).thenReturn(user)
        val client = mock<Client>()
        whenever(clientSupplier()).thenReturn(client)
        val payload = mock<StreamingSessionStart.Payload>()
        val decoratedPayload = mock<StreamingSessionStart.DecoratedPayload>()
        whenever(streamingSessionStartPayloadDecorator.decorate(payload))
            .thenReturn(decoratedPayload)
        val expected = mock<StreamingSessionStart>()
        whenever(
            streamingSessionStartFactory.create(
                currentTime.inWholeMilliseconds,
                randomUUID,
                user,
                client,
                decoratedPayload,
            ),
        ).thenReturn(expected)

        val actual = streamingSessionStartEventFactory(payload)

        verify(sntpClient).epochTime
        verify(uuidWrapper).randomUUID
        verify(userSupplier)()
        verify(clientSupplier)()
        verify(streamingSessionStartPayloadDecorator).decorate(payload)
        verify(streamingSessionStartFactory).create(
            currentTime.inWholeMilliseconds,
            randomUUID,
            user,
            client,
            decoratedPayload,
        )
        assertThat(actual).isSameAs(expected)
        verifyNoMoreInteractions(randomUUID, user, client, payload, decoratedPayload, expected)
    }
}
