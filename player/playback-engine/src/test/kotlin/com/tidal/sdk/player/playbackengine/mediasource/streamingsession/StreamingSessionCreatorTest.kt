package com.tidal.sdk.player.playbackengine.mediasource.streamingsession

import com.tidal.sdk.player.common.Configuration
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.EventReporter
import com.tidal.sdk.player.events.model.StreamingSessionStart
import java.util.UUID
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal abstract class StreamingSessionCreatorTest<T : StreamingSession.Factory> {

    protected abstract val startReason: StreamingSessionStart.StartReason
    protected abstract val factory: T
    protected val trueTimeWrapper = mock<TrueTimeWrapper>()
    protected val eventReporter = mock<EventReporter>()
    abstract val streamingSessionCreator: StreamingSession.Creator<T>

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(factory, trueTimeWrapper, eventReporter)

    @Test
    fun createAndReportStartCreatesAndReportsStart() {
        val id = mock<UUID>()
        val isOfflineModeStart = true
        val configuration = mock<Configuration> {
            on { it.isOfflineMode } doReturn isOfflineModeStart
        }
        val streamingSession = mock<StreamingSession.Explicit> {
            on { it.id } doReturn id
            on { it.configuration } doReturn configuration
        }
        whenever(factory.create()) doReturn streamingSession
        val expectedCurrentTimeMillis = -8L
        whenever(trueTimeWrapper.currentTimeMillis) doReturn expectedCurrentTimeMillis
        val productType = ProductType.TRACK
        val productId = "123"

        streamingSessionCreator.createAndReportStart(productType, productId)

        verify(factory).create()
        verify(trueTimeWrapper).currentTimeMillis
        verify(streamingSession).id
        verify(streamingSession).configuration
        verify(configuration).isOfflineMode
        verify(eventReporter).report(
            StreamingSessionStart.Payload(
                id.toString(),
                expectedCurrentTimeMillis,
                startReason,
                isOfflineModeStart,
                productType,
                productId,
            ),
        )
        verifyNoMoreInteractions(id, configuration, streamingSession)
    }
}
