package com.tidal.sdk.player.events.converter

import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.networktime.SNTPClient
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.UserSupplier
import com.tidal.sdk.player.events.model.Client
import com.tidal.sdk.player.events.model.User
import com.tidal.sdk.player.events.model.VideoDownloadStatistics
import java.util.UUID
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class VideoDownloadStatisticsEventFactoryTest {

    private val sntpClient = mock<SNTPClient>()
    private val uuidWrapper = mock<UUIDWrapper>()
    private val userSupplier = mock<UserSupplier>()
    private val clientSupplier = mock<ClientSupplier>()
    private val videoDownloadStatisticsFactory = mock<VideoDownloadStatistics.Factory>()
    private val videoDownloadStatisticsEventFactory = VideoDownloadStatisticsEventFactory(
        sntpClient,
        uuidWrapper,
        userSupplier,
        clientSupplier,
        videoDownloadStatisticsFactory,
    )

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(
        sntpClient,
        uuidWrapper,
        userSupplier,
        clientSupplier,
        videoDownloadStatisticsFactory,
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
        val src = mock<VideoDownloadStatistics.Payload>()
        val expected = mock<VideoDownloadStatistics>()
        whenever(
            videoDownloadStatisticsFactory.create(
                currentTime.inWholeMilliseconds,
                randomUUID,
                user,
                client,
                src,
            ),
        ).thenReturn(expected)

        val actual = videoDownloadStatisticsEventFactory(src)

        verify(sntpClient).epochTime
        verify(uuidWrapper).randomUUID
        verify(userSupplier)()
        verify(clientSupplier)()
        verify(videoDownloadStatisticsFactory).create(
            currentTime.inWholeMilliseconds,
            randomUUID,
            user,
            client,
            src,
        )
        assertThat(actual).isSameAs(expected)
        verifyNoMoreInteractions(randomUUID, user, client, src, expected)
    }
}
