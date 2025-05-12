package com.tidal.sdk.player.playbackengine.model

import androidx.media3.exoplayer.analytics.AnalyticsListener
import com.tidal.sdk.player.playbackengine.ExoPlayerPlaybackEngine
import com.tidal.sdk.player.playbackengine.mediasource.PlaybackInfoMediaSource
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.PlaybackSession
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.PlaybackStatistics
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

internal class DelayedMediaProductTransitionTest {

    private val from = mock<PlaybackInfoMediaSource>()
    private val to = mock<PlaybackInfoMediaSource>()
    private val eventTime = mock<AnalyticsListener.EventTime>()
    private val invokedAtMillis = -1L
    private val newPositionSeconds = 3.6536345
    private val delayedMediaProductTransition =
        DelayedMediaProductTransition(from, to, eventTime, invokedAtMillis, newPositionSeconds)

    @AfterEach fun afterEach() = verifyNoMoreInteractions(from, to, eventTime)

    @Test
    fun invoke() {
        val exoPlayerPlaybackEngine = mock<ExoPlayerPlaybackEngine>()
        val playbackContext = mock<PlaybackContext.Track>()
        val playbackStatistics = mock<PlaybackStatistics.Undetermined>()
        val playbackSession = mock<PlaybackSession.Audio>()

        delayedMediaProductTransition(
            exoPlayerPlaybackEngine,
            playbackContext,
            playbackStatistics,
            playbackSession,
        )

        verify(exoPlayerPlaybackEngine)
            .handleTransitionForRepeatOff(
                eventTime,
                invokedAtMillis,
                newPositionSeconds,
                playbackContext,
                playbackStatistics,
                playbackSession,
            )
        verifyNoMoreInteractions(
            exoPlayerPlaybackEngine,
            playbackContext,
            playbackStatistics,
            playbackSession,
        )
    }
}
