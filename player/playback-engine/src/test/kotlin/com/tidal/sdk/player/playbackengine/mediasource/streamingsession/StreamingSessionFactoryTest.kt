package com.tidal.sdk.player.playbackengine.mediasource.streamingsession

import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.sdk.player.common.Configuration
import com.tidal.sdk.player.common.UUIDWrapper
import java.util.UUID
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal abstract class StreamingSessionFactoryTest {

    private val uuidWrapper = mock<UUIDWrapper>()
    private val configuration = mock<Configuration>()
    protected abstract val streamingSessionFactoryF:
        (UUIDWrapper, Configuration) -> StreamingSession.Factory
    private val streamingSessionFactory by lazy {
        streamingSessionFactoryF(uuidWrapper, configuration)
    }

    @AfterEach
    fun tearDown() = verifyNoMoreInteractions(uuidWrapper, configuration)

    @Test
    fun createCreatesInstanceWithProvidedData() {
        val expectedUUID = mock<UUID>()
        whenever(uuidWrapper.randomUUID) doReturn expectedUUID

        val actual = streamingSessionFactory.create(emptyMap())

        verify(uuidWrapper).randomUUID
        assertThat(actual.id).isSameAs(expectedUUID)
        verifyNoMoreInteractions(expectedUUID)
    }
}
