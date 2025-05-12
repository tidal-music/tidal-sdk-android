package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.exoplayer.ExoPlayer
import assertk.assertThat
import assertk.assertions.isNull
import assertk.assertions.isSameAs
import com.tidal.sdk.player.common.ForwardingMediaProduct
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.EventReporter
import com.tidal.sdk.player.events.model.StreamingSessionEnd
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.StreamingSession
import java.util.UUID
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class MediaSourcererTest {

    private val exoPlayer = mock<ExoPlayer>()
    private val playbackInfoMediaSourceFactory = mock<PlaybackInfoMediaSourceFactory>()
    private val explicitStreamingSessionCreator = mock<StreamingSession.Creator.Explicit>()
    private val implicitStreamingSessionCreator = mock<StreamingSession.Creator.Implicit>()
    private val eventReporter = mock<EventReporter>()
    private val trueTimeWrapper = mock<TrueTimeWrapper>()
    private val mediaSourcerer =
        MediaSourcerer(
            exoPlayer,
            playbackInfoMediaSourceFactory,
            explicitStreamingSessionCreator,
            implicitStreamingSessionCreator,
            eventReporter,
            trueTimeWrapper,
        )

    @AfterEach
    fun afterEach() =
        verifyNoMoreInteractions(
            exoPlayer,
            playbackInfoMediaSourceFactory,
            explicitStreamingSessionCreator,
            implicitStreamingSessionCreator,
            eventReporter,
            trueTimeWrapper,
        )

    @Test
    fun loadSetsMediaSource() {
        val productType = ProductType.TRACK
        val productId = "123"
        val mediaProduct = ForwardingMediaProduct(MediaProduct(productType, productId))
        val mediaSource = mock<PlaybackInfoMediaSource>()
        val explicitStreamingSessionId = mock<UUID>()
        val explicitStreamingSession =
            mock<StreamingSession.Explicit> { on { id } doReturn explicitStreamingSessionId }
        whenever(explicitStreamingSessionCreator.createAndReportStart(productType, productId, null))
            .thenReturn(explicitStreamingSession)
        whenever(playbackInfoMediaSourceFactory.create(explicitStreamingSession, mediaProduct))
            .thenReturn(mediaSource)

        mediaSourcerer.load(mediaProduct)

        assertThat(mediaSourcerer.reflectionCurrentStreamingSession)
            .isSameAs(explicitStreamingSession)
        verify(explicitStreamingSessionCreator).createAndReportStart(productType, productId, null)
        verify(playbackInfoMediaSourceFactory).create(explicitStreamingSession, mediaProduct)
        verify(exoPlayer).setMediaSource(mediaSource)
        verifyNoMoreInteractions(mediaSource, explicitStreamingSessionId)
    }

    @Test
    fun nextWithOneMediaSource() {
        val productType = ProductType.TRACK
        val productId = "456"
        val mediaProductNext = ForwardingMediaProduct(MediaProduct(productType, productId))
        val mediaSourceNext = mock<PlaybackInfoMediaSource>()
        val implicitStreamingSessionNextId = mock<UUID>()
        val implicitStreamingSessionNext =
            mock<StreamingSession.Implicit> { on { id } doReturn implicitStreamingSessionNextId }
        whenever(implicitStreamingSessionCreator.createAndReportStart(productType, productId, null))
            .thenReturn(implicitStreamingSessionNext)
        whenever(
                playbackInfoMediaSourceFactory.create(
                    implicitStreamingSessionNext,
                    mediaProductNext,
                )
            )
            .thenReturn(mediaSourceNext)
        whenever(exoPlayer.mediaItemCount) doReturn 1

        mediaSourcerer.setNext(mediaProductNext)

        assertThat(mediaSourcerer.reflectionNextStreamingSession)
            .isSameAs(implicitStreamingSessionNext)
        verify(exoPlayer).mediaItemCount
        verify(implicitStreamingSessionCreator).createAndReportStart(productType, productId, null)
        verify(playbackInfoMediaSourceFactory)
            .create(implicitStreamingSessionNext, mediaProductNext)
        verify(exoPlayer).addMediaSource(mediaSourceNext)
        verifyNoMoreInteractions(mediaSourceNext, implicitStreamingSessionNextId)
    }

    @Test
    fun nextWithTwoMediaSources() {
        val productType = ProductType.TRACK
        val productId = "789"
        val mediaProductNext = ForwardingMediaProduct(MediaProduct(productType, productId))
        val mediaSourceNewNext = mock<PlaybackInfoMediaSource>()
        val implicitStreamingSessionNewNextId = mock<UUID>()
        val implicitStreamingSessionNewNext =
            mock<StreamingSession.Implicit> { on { id } doReturn implicitStreamingSessionNewNextId }
        whenever(implicitStreamingSessionCreator.createAndReportStart(productType, productId, null))
            .thenReturn(implicitStreamingSessionNewNext)
        whenever(
                playbackInfoMediaSourceFactory.create(
                    implicitStreamingSessionNewNext,
                    mediaProductNext,
                )
            )
            .thenReturn(mediaSourceNewNext)
        whenever(exoPlayer.mediaItemCount) doReturn 2

        mediaSourcerer.setNext(mediaProductNext)

        assertThat(mediaSourcerer.reflectionNextStreamingSession)
            .isSameAs(implicitStreamingSessionNewNext)
        verify(exoPlayer).mediaItemCount
        verify(implicitStreamingSessionCreator).createAndReportStart(productType, productId, null)
        verify(playbackInfoMediaSourceFactory)
            .create(implicitStreamingSessionNewNext, mediaProductNext)
        verify(exoPlayer).removeMediaItem(1)
        verify(exoPlayer).addMediaSource(mediaSourceNewNext)
        verifyNoMoreInteractions(mediaSourceNewNext, implicitStreamingSessionNewNext)
    }

    @Test
    fun nextWithTwoMediaSourcesAndNullInput() {
        whenever(exoPlayer.mediaItemCount) doReturn 2

        mediaSourcerer.setNext(null)

        verify(exoPlayer).mediaItemCount
        verify(exoPlayer).removeMediaItem(1)
        assertThat(mediaSourcerer.reflectionNextStreamingSession).isNull()
    }

    @Test
    fun onCurrentItemFinished() {
        val currentStreamingSessionId = mock<UUID>()
        val currentStreamingSessionIdString = currentStreamingSessionId.toString()
        val currentStreamingSession =
            mock<StreamingSession.Explicit> { on { id } doReturn currentStreamingSessionId }
        mediaSourcerer.reflectionCurrentStreamingSession = currentStreamingSession
        val nextStreamingSession =
            mock<StreamingSession.Implicit>().apply {
                mediaSourcerer.reflectionNextStreamingSession = this
            }
        val endTimeMillis = -7L
        whenever(trueTimeWrapper.currentTimeMillis) doReturn endTimeMillis

        mediaSourcerer.onCurrentItemFinished()

        verify(exoPlayer).removeMediaItem(0)
        verify(currentStreamingSession).id
        verify(currentStreamingSession).extras
        verify(trueTimeWrapper).currentTimeMillis
        verify(eventReporter)
            .report(
                StreamingSessionEnd.Payload(currentStreamingSessionIdString, endTimeMillis),
                emptyMap(),
            )
        verifyNoMoreInteractions(
            currentStreamingSessionId,
            currentStreamingSession,
            nextStreamingSession,
        )
    }

    @Test
    fun onRepeatOne() {
        val currentStreamingSessionId = mock<UUID>()
        val currentStreamingSession =
            mock<StreamingSession.Explicit> { on { id } doReturn currentStreamingSessionId }
        mediaSourcerer.reflectionCurrentStreamingSession = currentStreamingSession
        val nextStreamingSession = mock<StreamingSession.Implicit> { on { id } doReturn mock() }
        mediaSourcerer.reflectionNextStreamingSession = nextStreamingSession
        val productType = ProductType.TRACK
        val productId = "123"
        val expectedNewStreamingSession = mock<StreamingSession.Implicit>()
        whenever(
                implicitStreamingSessionCreator.createAndReportStart(
                    productType,
                    productId,
                    emptyMap(),
                )
            )
            .thenReturn(expectedNewStreamingSession)
        val endTimeMillis = 123L
        whenever(trueTimeWrapper.currentTimeMillis).thenReturn(endTimeMillis)
        val mediaProduct =
            mock<ForwardingMediaProduct<*>> {
                on { it.productType } doReturn productType
                on { it.productId } doReturn productId
            }

        mediaSourcerer.onRepeatOne(mediaProduct)

        verify(implicitStreamingSessionCreator)
            .createAndReportStart(productType, productId, emptyMap())
        verify(eventReporter)
            .report(
                StreamingSessionEnd.Payload(currentStreamingSessionId.toString(), endTimeMillis),
                emptyMap(),
            )
        verify(trueTimeWrapper).currentTimeMillis
    }

    @Test
    fun release() {
        val currentStreamingSessionId = mock<UUID>()
        val currentStreamingSessionIdString = currentStreamingSessionId.toString()
        val currentStreamingSession =
            mock<StreamingSession.Explicit> { on { id } doReturn currentStreamingSessionId }
        val nextStreamingSessionId = mock<UUID>()
        val nextStreamingSessionIdString = nextStreamingSessionId.toString()
        mediaSourcerer.reflectionCurrentStreamingSession = currentStreamingSession
        val nextStreamingSession =
            mock<StreamingSession.Implicit> { on { id } doReturn nextStreamingSessionId }
        mediaSourcerer.reflectionNextStreamingSession = nextStreamingSession
        val endTimeMillis0 = -7L
        val endTimeMillis1 = Long.MIN_VALUE
        whenever(trueTimeWrapper.currentTimeMillis).doReturn(endTimeMillis0, endTimeMillis1)

        mediaSourcerer.release()

        verify(exoPlayer).clearMediaItems()
        assertThat(mediaSourcerer.reflectionCurrentStreamingSession).isNull()
        assertThat(mediaSourcerer.reflectionNextStreamingSession).isNull()
        verify(currentStreamingSession).id
        verify(currentStreamingSession).extras
        verify(nextStreamingSession).id
        verify(nextStreamingSession).extras
        verify(trueTimeWrapper, times(2)).currentTimeMillis
        verify(eventReporter)
            .report(
                StreamingSessionEnd.Payload(currentStreamingSessionIdString, endTimeMillis0),
                emptyMap(),
            )
        verify(eventReporter)
            .report(
                StreamingSessionEnd.Payload(nextStreamingSessionIdString, endTimeMillis1),
                emptyMap(),
            )
        verifyNoMoreInteractions(
            currentStreamingSessionId,
            currentStreamingSession,
            nextStreamingSession,
            nextStreamingSessionId,
        )
    }
}
