package com.tidal.sdk.player.events.converter

import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.common.model.BaseMediaProduct
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.UserSupplier
import com.tidal.sdk.player.events.model.AudioDownloadStatistics
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

internal class AudioDownloadStatisticsEventFactoryTest {

    private val trueTimeWrapper = mock<TrueTimeWrapper>()
    private val uuidWrapper = mock<UUIDWrapper>()
    private val userSupplier = mock<UserSupplier>()
    private val clientSupplier = mock<ClientSupplier>()
    private val audioDownloadStatisticsFactory = mock<AudioDownloadStatistics.Factory>()
    private val audioDownloadStatisticsEventFactory = AudioDownloadStatisticsEventFactory(
        trueTimeWrapper,
        uuidWrapper,
        userSupplier,
        clientSupplier,
        audioDownloadStatisticsFactory,
    )

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(
        trueTimeWrapper,
        uuidWrapper,
        userSupplier,
        clientSupplier,
        audioDownloadStatisticsFactory,
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
        val src = mock<AudioDownloadStatistics.Payload>()
        val expected = mock<AudioDownloadStatistics>()
        whenever(
            audioDownloadStatisticsFactory.create(
                currentTimeMillis,
                randomUUID,
                user,
                client,
                src,
                BaseMediaProduct.Extras.Collection(emptyMap()),
            ),
        ).thenReturn(expected)

        val actual = audioDownloadStatisticsEventFactory(
            src,
            BaseMediaProduct.Extras.Collection(emptyMap()),
        )

        verify(trueTimeWrapper).currentTimeMillis
        verify(uuidWrapper).randomUUID
        verify(userSupplier)()
        verify(clientSupplier)()
        verify(audioDownloadStatisticsFactory).create(
            currentTimeMillis,
            randomUUID,
            user,
            client,
            src,
            BaseMediaProduct.Extras.Collection(emptyMap()),
        )
        assertThat(actual).isSameAs(expected)
        verifyNoMoreInteractions(randomUUID, user, client, src, expected)
    }
}
