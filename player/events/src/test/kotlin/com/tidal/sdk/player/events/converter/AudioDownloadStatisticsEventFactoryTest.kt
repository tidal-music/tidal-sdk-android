package com.tidal.sdk.player.events.converter

import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.networktime.SNTPClient
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.UserSupplier
import com.tidal.sdk.player.events.model.AudioDownloadStatistics
import com.tidal.sdk.player.events.model.Client
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

internal class AudioDownloadStatisticsEventFactoryTest {

    private val sntpClient = mock<SNTPClient>()
    private val uuidWrapper = mock<UUIDWrapper>()
    private val userSupplier = mock<UserSupplier>()
    private val clientSupplier = mock<ClientSupplier>()
    private val audioDownloadStatisticsFactory = mock<AudioDownloadStatistics.Factory>()
    private val audioDownloadStatisticsEventFactory = AudioDownloadStatisticsEventFactory(
        sntpClient,
        uuidWrapper,
        userSupplier,
        clientSupplier,
        audioDownloadStatisticsFactory,
    )

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(
        sntpClient,
        uuidWrapper,
        userSupplier,
        clientSupplier,
        audioDownloadStatisticsFactory,
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
        val src = mock<AudioDownloadStatistics.Payload>()
        val expected = mock<AudioDownloadStatistics>()
        whenever(
            audioDownloadStatisticsFactory.create(
                currentTime.inWholeMilliseconds,
                randomUUID,
                user,
                client,
                src,
            ),
        ).thenReturn(expected)

        val actual = audioDownloadStatisticsEventFactory(src)

        verify(sntpClient).epochTime
        verify(uuidWrapper).randomUUID
        verify(userSupplier)()
        verify(clientSupplier)()
        verify(audioDownloadStatisticsFactory).create(
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
