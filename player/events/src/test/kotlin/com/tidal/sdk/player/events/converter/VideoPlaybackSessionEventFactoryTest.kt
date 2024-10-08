package com.tidal.sdk.player.events.converter

import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.UserSupplier
import com.tidal.sdk.player.events.model.Client
import com.tidal.sdk.player.events.model.User
import com.tidal.sdk.player.events.model.VideoPlaybackSession
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class VideoPlaybackSessionEventFactoryTest {

    private val trueTimeWrapper = mock<TrueTimeWrapper>()
    private val uuidWrapper = mock<UUIDWrapper>()
    private val userSupplier = mock<UserSupplier>()
    private val clientSupplier = mock<ClientSupplier>()
    private val videoPlaybackSessionFactory = mock<VideoPlaybackSession.Factory>()
    private val videoPlaybackSessionEventFactory = VideoPlaybackSessionEventFactory(
        trueTimeWrapper,
        uuidWrapper,
        userSupplier,
        clientSupplier,
        videoPlaybackSessionFactory,
    )

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(
        trueTimeWrapper,
        uuidWrapper,
        userSupplier,
        clientSupplier,
        videoPlaybackSessionFactory,
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
        val payload = mock<VideoPlaybackSession.Payload>()
        val expected = mock<VideoPlaybackSession>()
        whenever(
            videoPlaybackSessionFactory.create(
                currentTimeMillis,
                randomUUID,
                user,
                client,
                payload,
                null,
            ),
        ).thenReturn(expected)

        val actual = videoPlaybackSessionEventFactory(payload, null)

        verify(trueTimeWrapper).currentTimeMillis
        verify(uuidWrapper).randomUUID
        verify(userSupplier)()
        verify(clientSupplier)()
        verify(videoPlaybackSessionFactory).create(
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
