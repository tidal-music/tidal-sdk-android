package com.tidal.sdk.player.playbackengine.mediasource.loadable

import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.source.LoadEventInfo
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.MediaSourceEventListener
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy
import androidx.media3.exoplayer.upstream.reflectionType
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isSameAs
import com.tidal.sdk.player.playbackengine.mediasource.TidalMediaSourceCreator
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import java.io.IOException
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class PlaybackInfoLoadableLoaderCallbackTest {

    private val mediaItem = mock<MediaItem>()
    private val tidalMediaSourceCreator = mock<TidalMediaSourceCreator>()
    private val loadErrorHandlingPolicy = mock<LoadErrorHandlingPolicy>()
    private val loadable = mock<PlaybackInfoLoadable>()
    private val eventDispatcher = mock<MediaSourceEventListener.EventDispatcher>()
    private val loadEventInfo = mock<LoadEventInfo>()
    private val loadEventInfoF = mock<(Long, Long) -> LoadEventInfo> {
        on { invoke(any(), any()) } doReturn loadEventInfo
    }
    private val loadErrorInfo = mock<LoadErrorHandlingPolicy.LoadErrorInfo>()

    private val loadErrorInfoF =
        mock<(LoadEventInfo, error: IOException, errorCount: Int) -> LoadErrorHandlingPolicy.LoadErrorInfo> {
            on { invoke(any(), any(), any()) } doReturn loadErrorInfo
        }
    private val prepareChildSourceF = mock<(MediaSource) -> Unit>()
    private val playbackInfoLoadableLoaderCallback = PlaybackInfoLoadableLoaderCallback(
        mediaItem,
        tidalMediaSourceCreator,
        loadErrorHandlingPolicy,
        C.DATA_TYPE_MANIFEST,
        eventDispatcher,
        loadEventInfoF,
        loadErrorInfoF,
        prepareChildSourceF,
        emptyMap(),
    )

    @Test
    fun onLoadCompletedShouldUpdateSelectedMediaSourceAndPrepareChildSource() {
        val playbackInfo = mock<PlaybackInfo.Track>()
        val mediaSource = mock<MediaSource>()
        val elapsedRealtimeMs = 1000L
        val loadDurationMs = 2000L

        whenever(loadable.playbackInfo).thenReturn(playbackInfo)
        whenever(tidalMediaSourceCreator(mediaItem, playbackInfo, emptyMap())).thenReturn(
            mediaSource,
        )

        playbackInfoLoadableLoaderCallback.onLoadCompleted(
            loadable,
            elapsedRealtimeMs,
            loadDurationMs,
        )

        verify(loadEventInfoF).invoke(elapsedRealtimeMs, loadDurationMs)
        verify(eventDispatcher).loadCompleted(loadEventInfo, C.DATA_TYPE_MANIFEST)
        verify(prepareChildSourceF).invoke(mediaSource)
        assertThat(playbackInfoLoadableLoaderCallback.selectedMediaSource).isSameAs(mediaSource)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun onLoadCanceledShouldDispatchEvent(released: Boolean) {
        val elapsedRealtimeMs = 1000L
        val loadDurationMs = 2000L

        playbackInfoLoadableLoaderCallback.onLoadCanceled(
            loadable,
            elapsedRealtimeMs,
            loadDurationMs,
            released,
        )

        verify(loadEventInfoF).invoke(elapsedRealtimeMs, loadDurationMs)
        verify(eventDispatcher).loadCanceled(loadEventInfo, C.DATA_TYPE_MANIFEST)
    }

    @Test
    fun onLoadErrorShouldDispatchEventAndReturnWithoutRetryAction() {
        testOnLoadError(C.TIME_UNSET, 3, true)
    }

    @Test
    fun onLoadErrorShouldDispatchEventAndReturnWithRetryAction() {
        testOnLoadError(100L, 0, false)
    }

    private fun testOnLoadError(
        retryDelayMs: Long,
        expectedRetryActionType: Int,
        wasCanceled: Boolean,
    ) {
        val elapsedRealtimeMs = 1000L
        val loadDurationMs = 2000L
        val error = IOException("")
        val errorCount = 1

        whenever(loadErrorHandlingPolicy.getRetryDelayMsFor(loadErrorInfo))
            .thenReturn(retryDelayMs)

        val actual = playbackInfoLoadableLoaderCallback.onLoadError(
            loadable,
            elapsedRealtimeMs,
            loadDurationMs,
            error,
            errorCount,
        )

        verify(loadEventInfoF).invoke(elapsedRealtimeMs, loadDurationMs)
        verify(loadErrorInfoF).invoke(loadEventInfo, error, errorCount)
        verify(eventDispatcher).loadError(loadEventInfo, C.DATA_TYPE_MANIFEST, error, wasCanceled)
        assertThat(actual.reflectionType).isEqualTo(expectedRetryActionType)
    }
}
