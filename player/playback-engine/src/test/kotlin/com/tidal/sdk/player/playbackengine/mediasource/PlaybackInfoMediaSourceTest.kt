package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Timeline
import androidx.media3.exoplayer.source.LoadEventInfo
import androidx.media3.exoplayer.source.MediaPeriod
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.MediaSourceEventListener.EventDispatcher
import androidx.media3.exoplayer.source.realOnChildSourceInfoRefreshed
import androidx.media3.exoplayer.source.realRefreshSourceInfo
import androidx.media3.exoplayer.source.realReleaseSourceInternal
import androidx.media3.exoplayer.upstream.Allocator
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy.LoadErrorInfo
import androidx.media3.exoplayer.upstream.Loader
import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.sdk.player.common.ForwardingMediaProduct
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.playbackengine.mediasource.loadable.PlaybackInfoLoadable
import com.tidal.sdk.player.playbackengine.mediasource.loadable.PlaybackInfoLoadableLoaderCallback
import com.tidal.sdk.player.playbackengine.mediasource.loadable.PlaybackInfoLoadableLoaderCallbackFactory
import java.io.IOException
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class PlaybackInfoMediaSourceTest {

    private val forwardingMediaProduct = mock<ForwardingMediaProduct<MediaProduct>>()
    private val loadErrorHandlingPolicy = mock<LoadErrorHandlingPolicy>()
    private val loadable = mock<PlaybackInfoLoadable>()
    private val mediaItemId = "mediaItemId"
    private val expectedMediaItem = spy(
        MediaItem.Builder()
            .setMediaId(mediaItemId)
            .build(),
    )
    private val loadEventInfo = mock<LoadEventInfo>()
    private val loadEventInfoF = mock<(Long, Long) -> LoadEventInfo> {
        on { invoke(any(), any()) } doReturn loadEventInfo
    }
    private val loadErrorInfo = mock<LoadErrorInfo>()
    private val loadErrorInfoF: (LoadEventInfo, error: IOException, errorCount: Int) ->
    LoadErrorInfo = { _, _, _ -> loadErrorInfo }
    private val loader = mock<Loader>()
    private val callbackFactory = mock<PlaybackInfoLoadableLoaderCallbackFactory>()
    private val playbackInfoMediaSource =
        PlaybackInfoMediaSource(
            forwardingMediaProduct,
            loadErrorHandlingPolicy,
            loadable,
            expectedMediaItem,
            C.DATA_TYPE_MANIFEST,
            loadEventInfoF,
            loadErrorInfoF,
            loader,
            callbackFactory,
            emptyMap(),
        )

    @Test
    fun mediaItemShouldReturnTheMediaItem() {
        val actual = playbackInfoMediaSource.mediaItem

        assertThat(actual).isSameAs(expectedMediaItem)
    }

    @Test
    fun prepareSourceInternalShouldStartLoading() {
        val minimumLoadableRetryCount = 6
        val expectedElapsedRealtimeMs = 1000L
        val expectedLoadDurationMs = 0L

        val eventDispatcher = mock<EventDispatcher>()
        val eventDispatcherF = mock<() -> EventDispatcher> {
            on { invoke() } doReturn eventDispatcher
        }
        playbackInfoMediaSource.reflectionSetCreateEventDispatcherF(eventDispatcherF)
        val callback = mock<PlaybackInfoLoadableLoaderCallback>()
        whenever(
            callbackFactory.create(
                expectedMediaItem,
                C.DATA_TYPE_MANIFEST,
                eventDispatcher,
                loadEventInfoF,
                loadErrorInfoF,
                playbackInfoMediaSource.reflectionPrepareChildSourceF,
                emptyMap(),
            ),
        ).thenReturn(callback)

        whenever(loader.startLoading(loadable, callback, minimumLoadableRetryCount))
            .thenReturn(expectedElapsedRealtimeMs)
        whenever(loadErrorHandlingPolicy.getMinimumLoadableRetryCount(C.DATA_TYPE_MANIFEST))
            .thenReturn(minimumLoadableRetryCount)

        playbackInfoMediaSource.reflectionPrepareSourceInternalF()

        verify(loader).startLoading(loadable, callback, minimumLoadableRetryCount)
        verify(loadEventInfoF).invoke(expectedElapsedRealtimeMs, expectedLoadDurationMs)
        verify(eventDispatcher).loadStarted(loadEventInfo, C.DATA_TYPE_MANIFEST)
    }

    @Test
    fun releaseSourceInternalShouldReleaseLoader() {
        playbackInfoMediaSource.realReleaseSourceInternal()

        verify(loader).release()
    }

    @Test
    fun createPeriodForwardsCallToSelectedMediaSourceAndReturnsNewPeriod() {
        val id = mock<MediaSource.MediaPeriodId>()
        val allocator = mock<Allocator>()
        val startPositionUs = 123L
        val mediaPeriod = mock<MediaPeriod>()
        val selectedMediaSource = mock<MediaSource> {
            on { createPeriod(id, allocator, startPositionUs) } doReturn mediaPeriod
        }
        val callback = mock<PlaybackInfoLoadableLoaderCallback> {
            on { it.selectedMediaSource } doReturn selectedMediaSource
        }
        whenever(
            callbackFactory.create(
                expectedMediaItem,
                C.DATA_TYPE_MANIFEST,
                playbackInfoMediaSource.reflectionEventDispatcher,
                loadEventInfoF,
                loadErrorInfoF,
                playbackInfoMediaSource.reflectionPrepareChildSourceF,
                emptyMap(),
            ),
        ).thenReturn(callback)

        val actual = playbackInfoMediaSource.createPeriod(id, allocator, startPositionUs)

        verify(selectedMediaSource).createPeriod(id, allocator, startPositionUs)
        assertThat(actual).isSameAs(mediaPeriod)
    }

    @Test
    fun releasePeriodForwardsCallToSelectedMediaSource() {
        val mediaPeriod = mock<MediaPeriod>()
        val selectedMediaSource = mock<MediaSource>()
        val callback = mock<PlaybackInfoLoadableLoaderCallback> {
            on { it.selectedMediaSource } doReturn selectedMediaSource
        }
        whenever(
            callbackFactory.create(
                expectedMediaItem,
                C.DATA_TYPE_MANIFEST,
                playbackInfoMediaSource.reflectionEventDispatcher,
                loadEventInfoF,
                loadErrorInfoF,
                playbackInfoMediaSource.reflectionPrepareChildSourceF,
                emptyMap(),
            ),
        ).thenReturn(callback)

        playbackInfoMediaSource.releasePeriod(mediaPeriod)

        verify(selectedMediaSource).releasePeriod(mediaPeriod)
    }

    @Test
    fun maybeThrowSourceInfoRefreshErrorForwardsCallToLoader() {
        playbackInfoMediaSource.maybeThrowSourceInfoRefreshError()

        verify(loader).maybeThrowError()
    }

    @Test
    fun onChildSourceInfoRefreshedCallsRefreshSourceInfo() {
        val timeline = mock<Timeline>()
        val playbackInfoMediaSourceSpy = spy(playbackInfoMediaSource)
        playbackInfoMediaSourceSpy.realOnChildSourceInfoRefreshed(mock(), mock(), timeline)

        verify(playbackInfoMediaSourceSpy).realRefreshSourceInfo(timeline)
    }
}
