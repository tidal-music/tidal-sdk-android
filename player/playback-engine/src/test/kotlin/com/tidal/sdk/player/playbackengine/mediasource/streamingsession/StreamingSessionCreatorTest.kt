package com.tidal.sdk.player.playbackengine.mediasource.streamingsession

import com.tidal.networktime.SNTPClient
import com.tidal.sdk.player.common.Configuration
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.events.EventReporter
import com.tidal.sdk.player.events.model.StreamingSessionStart
import java.util.UUID
import kotlin.time.Duration.Companion.milliseconds
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
    protected val sntpClient = mock<SNTPClient>()
    protected val eventReporter = mock<EventReporter>()
    abstract val streamingSessionCreator: StreamingSession.Creator<T>

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(factory, sntpClient, eventReporter)

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
        val expectedCurrentTime = -8.milliseconds
        whenever(sntpClient.epochTime) doReturn expectedCurrentTime
        val productType = ProductType.TRACK
        val productId = "123"

        streamingSessionCreator.createAndReportStart(productType, productId)

        verify(factory).create()
        verify(sntpClient).epochTime
        verify(streamingSession).id
        verify(streamingSession).configuration
        verify(configuration).isOfflineMode
        verify(eventReporter).report(
            StreamingSessionStart.Payload(
                id.toString(),
                expectedCurrentTime.inWholeMilliseconds,
                startReason,
                isOfflineModeStart,
                productType,
                productId,
            ),
        )
        verifyNoMoreInteractions(id, configuration, streamingSession)
    }
}
