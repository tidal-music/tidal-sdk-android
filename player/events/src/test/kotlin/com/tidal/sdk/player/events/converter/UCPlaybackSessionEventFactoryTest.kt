package com.tidal.sdk.player.events.converter

import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.networktime.SNTPClient
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.UserSupplier
import com.tidal.sdk.player.events.model.Client
import com.tidal.sdk.player.events.model.UCPlaybackSession
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

internal class UCPlaybackSessionEventFactoryTest {

    private val sntpClient = mock<SNTPClient>()
    private val uuidWrapper = mock<UUIDWrapper>()
    private val userSupplier = mock<UserSupplier>()
    private val clientSupplier = mock<ClientSupplier>()
    private val ucPlaybackSessionFactory = mock<UCPlaybackSession.Factory>()
    private val ucPlaybackSessionEventFactory = UCPlaybackSessionEventFactory(
        sntpClient,
        uuidWrapper,
        userSupplier,
        clientSupplier,
        ucPlaybackSessionFactory,
    )

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(
        sntpClient,
        uuidWrapper,
        userSupplier,
        clientSupplier,
        ucPlaybackSessionFactory,
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
        val payload = mock<UCPlaybackSession.Payload>()
        val expected = mock<UCPlaybackSession>()
        whenever(
            ucPlaybackSessionFactory.create(
                currentTime.inWholeMilliseconds,
                randomUUID,
                user,
                client,
                payload,
            ),
        ).thenReturn(expected)

        val actual = ucPlaybackSessionEventFactory(payload)

        verify(sntpClient).epochTime
        verify(uuidWrapper).randomUUID
        verify(userSupplier)()
        verify(clientSupplier)()
        verify(ucPlaybackSessionFactory).create(
            currentTime.inWholeMilliseconds,
            randomUUID,
            user,
            client,
            payload,
        )
        assertThat(actual).isSameAs(expected)
        verifyNoMoreInteractions(randomUUID, user, client, payload, expected)
    }
}
