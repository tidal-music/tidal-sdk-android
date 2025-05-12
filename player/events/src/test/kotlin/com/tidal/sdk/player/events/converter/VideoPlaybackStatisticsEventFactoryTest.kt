package com.tidal.sdk.player.events.converter

import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.UserSupplier
import com.tidal.sdk.player.events.model.Client
import com.tidal.sdk.player.events.model.User
import com.tidal.sdk.player.events.model.VideoPlaybackStatistics
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class VideoPlaybackStatisticsEventFactoryTest {

    private val trueTimeWrapper = mock<TrueTimeWrapper>()
    private val uuidWrapper = mock<UUIDWrapper>()
    private val userSupplier = mock<UserSupplier>()
    private val clientSupplier = mock<ClientSupplier>()
    private val videoPlaybackStatisticsFactory = mock<VideoPlaybackStatistics.Factory>()
    private val videoPlaybackStatisticsEventFactory =
        VideoPlaybackStatisticsEventFactory(
            trueTimeWrapper,
            uuidWrapper,
            userSupplier,
            clientSupplier,
            videoPlaybackStatisticsFactory,
        )

    @AfterEach
    fun afterEach() =
        verifyNoMoreInteractions(
            trueTimeWrapper,
            uuidWrapper,
            userSupplier,
            clientSupplier,
            videoPlaybackStatisticsFactory,
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
        val payload = mock<VideoPlaybackStatistics.Payload>()
        val expected = mock<VideoPlaybackStatistics>()
        whenever(
                videoPlaybackStatisticsFactory.create(
                    currentTimeMillis,
                    randomUUID,
                    user,
                    client,
                    payload,
                    emptyMap(),
                )
            )
            .thenReturn(expected)

        val actual = videoPlaybackStatisticsEventFactory(payload, emptyMap())

        verify(trueTimeWrapper).currentTimeMillis
        verify(uuidWrapper).randomUUID
        verify(userSupplier)()
        verify(clientSupplier)()
        verify(videoPlaybackStatisticsFactory)
            .create(currentTimeMillis, randomUUID, user, client, payload, emptyMap())
        assertThat(actual).isSameAs(expected)
        verifyNoMoreInteractions(randomUUID, user, client, payload, expected)
    }
}
