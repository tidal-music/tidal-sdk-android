package com.tidal.sdk.player.playbackengine

import android.os.Handler
import android.os.Looper
import android.view.SurfaceHolder
import androidx.media3.common.C
import androidx.media3.common.Format
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.common.VideoSize
import androidx.media3.datasource.cache.Cache
import androidx.media3.exoplayer.DecoderReuseEvaluation
import androidx.media3.exoplayer.analytics.AnalyticsListener.EventTime
import androidx.media3.exoplayer.hls.HlsManifest
import androidx.media3.exoplayer.hls.playlist.HlsMediaPlaylist
import androidx.media3.exoplayer.hls.playlist.reflectionSetTags
import androidx.media3.exoplayer.hls.reflectionSetMediaPlaylist
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import assertk.assertions.isSameAs
import com.tidal.sdk.player.common.ForwardingMediaProduct
import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.LoudnessNormalizationMode
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.EventReporter
import com.tidal.sdk.player.events.model.PlaybackSession.Payload.Action
import com.tidal.sdk.player.events.model.PlaybackStatistics.Payload.Adaptation
import com.tidal.sdk.player.events.model.PlaybackStatistics.Payload.Stall
import com.tidal.sdk.player.events.model.PlaybackStatistics.Payload.Stall.Reason
import com.tidal.sdk.player.events.model.VideoPlaybackSession
import com.tidal.sdk.player.playbackengine.audiomode.AudioModeRepository
import com.tidal.sdk.player.playbackengine.dj.DjSessionManager
import com.tidal.sdk.player.playbackengine.dj.DjSessionStatus
import com.tidal.sdk.player.playbackengine.dj.HlsTags
import com.tidal.sdk.player.playbackengine.error.ErrorHandler
import com.tidal.sdk.player.playbackengine.mediasource.PlaybackInfoMediaSource
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.PlaybackSession
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.PlaybackStatistics
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.StartedStall
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.StreamingSession
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.UndeterminedPlaybackSessionResolver
import com.tidal.sdk.player.playbackengine.model.Event
import com.tidal.sdk.player.playbackengine.model.PlaybackContext
import com.tidal.sdk.player.playbackengine.model.PlaybackState
import com.tidal.sdk.player.playbackengine.outputdevice.OutputDeviceManager
import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayer
import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayerFactory
import com.tidal.sdk.player.playbackengine.player.PlayerCache
import com.tidal.sdk.player.playbackengine.quality.AudioQualityRepository
import com.tidal.sdk.player.playbackengine.util.SynchronousSurfaceHolder
import com.tidal.sdk.player.playbackengine.view.AspectRatioAdjustingSurfaceView
import com.tidal.sdk.player.playbackengine.volume.VolumeHelper
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import com.tidal.sdk.player.streamingprivileges.StreamingPrivileges
import java.util.UUID
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assumptions.assumeFalse
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.NullSource
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.any
import org.mockito.kotlin.argThat
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.reset
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

@Suppress("LargeClass")
internal class ExoPlayerPlaybackEngineTest {

    private val coroutineScope = spy(CoroutineScope(Dispatchers.Default))
    private val initialExtendedExoPlayer = mock<ExtendedExoPlayer>()
    private val extendedExoPlayerFactory = mock<ExtendedExoPlayerFactory>()
    private val internalHandler = mock<Handler>()
    private val events = spy(MutableSharedFlow<Event>())
    private val synchronousSurfaceHolderFactory = mock<SynchronousSurfaceHolder.Factory>()
    private val streamingPrivileges = mock<StreamingPrivileges>()
    private val playbackContextFactory = mock<PlaybackContextFactory>()
    private val audioQualityRepository = mock<AudioQualityRepository>()
    private val audioModeRepository = mock<AudioModeRepository>()
    private val volumeHelper = mock<VolumeHelper>()
    private val trueTimeWrapper = mock<TrueTimeWrapper>()
    private val eventReporter = mock<EventReporter>()
    private val errorHandler = mock<ErrorHandler>()
    private val djSessionManager = mock<DjSessionManager>()
    private val undeterminedPlaybackSessionResolver = mock<UndeterminedPlaybackSessionResolver>()
    private val outputDeviceManager = mock<OutputDeviceManager>()
    private val playerCache = mock<PlayerCache.Internal>()
    private lateinit var playbackEngine: ExoPlayerPlaybackEngine

    private val forwardingMediaProduct =
        ForwardingMediaProduct(MediaProduct(ProductType.TRACK, "1"))
    private val mediaSource = mock<PlaybackInfoMediaSource> {
        on { it.forwardingMediaProduct } doReturn forwardingMediaProduct
    }

    @BeforeEach
    fun beforeEach() {
        whenever(extendedExoPlayerFactory.create(any(), any()))
            .thenReturn(initialExtendedExoPlayer)
        playbackEngine = ExoPlayerPlaybackEngine(
            coroutineScope,
            extendedExoPlayerFactory,
            internalHandler,
            events,
            synchronousSurfaceHolderFactory,
            streamingPrivileges,
            playbackContextFactory,
            audioQualityRepository,
            audioModeRepository,
            volumeHelper,
            trueTimeWrapper,
            eventReporter,
            errorHandler,
            djSessionManager,
            undeterminedPlaybackSessionResolver,
            outputDeviceManager,
            playerCache,
        )
    }

    @Test
    fun getMediaProductShouldReturnNullWhenNotInitialized() {
        assertThat(playbackEngine.mediaProduct).isNull()
    }

    @Test
    fun getPlaybackContextShouldReturnNullWhenNotInitialized() {
        assertThat(playbackEngine.playbackContext).isNull()
    }

    @Test
    fun getPlaybackStateShouldReturnIdleWhenNotInitialized() {
        assertThat(playbackEngine.playbackState).isEqualTo(PlaybackState.IDLE)
    }

    @Test
    fun assetPositionShouldReturn0WhenNotInitialized() {
        assertThat(playbackEngine.assetPosition).isEqualTo(0f)
    }

    @Test
    fun assetPositionShouldReturnPositiveFloatWhenExoHasPositivePosition() {
        whenever(initialExtendedExoPlayer.currentPositionMs).thenReturn(5000)
        assertThat(playbackEngine.assetPosition).isEqualTo(5f)
    }

    @ParameterizedTest
    @EnumSource(AudioQuality::class)
    fun getStreamingWifiAudioQuality(audioQuality: AudioQuality) {
        whenever(audioQualityRepository.streamingWifiAudioQuality).doReturn(audioQuality)

        val actual = playbackEngine.streamingWifiAudioQuality

        assertThat(actual).isEqualTo(audioQuality)
        verify(audioQualityRepository).streamingWifiAudioQuality
        verifyNoMoreInteractions(audioQualityRepository)
    }

    @ParameterizedTest
    @EnumSource(AudioQuality::class)
    fun setStreamingWifiAudioQuality(audioQuality: AudioQuality) {
        playbackEngine.streamingWifiAudioQuality = audioQuality

        verify(audioQualityRepository).streamingWifiAudioQuality = audioQuality
        verifyNoMoreInteractions(audioQualityRepository)
    }

    @ParameterizedTest
    @EnumSource(AudioQuality::class)
    fun getStreamingCellularAudioQuality(audioQuality: AudioQuality) {
        whenever(audioQualityRepository.streamingCellularAudioQuality).doReturn(audioQuality)

        val actual = playbackEngine.streamingCellularAudioQuality

        assertThat(actual).isEqualTo(audioQuality)
        verify(audioQualityRepository).streamingCellularAudioQuality
        verifyNoMoreInteractions(audioQualityRepository)
    }

    @ParameterizedTest
    @EnumSource(AudioQuality::class)
    fun setStreamingCellularAudioQuality(audioQuality: AudioQuality) {
        playbackEngine.streamingCellularAudioQuality = audioQuality

        verify(audioQualityRepository).streamingCellularAudioQuality = audioQuality
        verifyNoMoreInteractions(audioQualityRepository)
    }

    @ParameterizedTest
    @EnumSource(LoudnessNormalizationMode::class)
    fun getLoudnessNormalizationMode(loudnessNormalizationMode: LoudnessNormalizationMode) {
        whenever(volumeHelper.loudnessNormalizationMode).doReturn(loudnessNormalizationMode)

        val actual = playbackEngine.loudnessNormalizationMode

        assertThat(actual).isEqualTo(loudnessNormalizationMode)
        verify(volumeHelper).loudnessNormalizationMode
        verifyNoMoreInteractions(volumeHelper)
    }

    @ParameterizedTest
    @EnumSource(LoudnessNormalizationMode::class)
    fun setLoudnessNormalizationMode(loudnessNormalizationMode: LoudnessNormalizationMode) {
        val playbackInfo = mock<PlaybackInfo.Track>()
        val currentMediaSource = mock<PlaybackInfoMediaSource> {
            on { it.playbackInfo } doReturn playbackInfo
        }
        playbackEngine.testMediaSource = currentMediaSource

        playbackEngine.loudnessNormalizationMode = loudnessNormalizationMode

        verify(volumeHelper).loudnessNormalizationMode = loudnessNormalizationMode
        verify(volumeHelper).getVolume(playbackInfo)
        verifyNoMoreInteractions(volumeHelper)
    }

    @Test
    fun getLoudnessNormalizationPreAmp() {
        val preAmp = 4
        whenever(volumeHelper.loudnessNormalizationPreAmp).doReturn(preAmp)

        val actual = playbackEngine.loudnessNormalizationPreAmp

        assertThat(actual).isEqualTo(preAmp)
        verify(volumeHelper).loudnessNormalizationPreAmp
        verifyNoMoreInteractions(volumeHelper)
    }

    @Test
    fun setLoudnessNormalizationPreAmp() {
        val preAmp = 4
        val playbackInfo = mock<PlaybackInfo.Track>()
        val currentMediaSource = mock<PlaybackInfoMediaSource> {
            on { it.playbackInfo } doReturn playbackInfo
        }
        playbackEngine.testMediaSource = currentMediaSource

        playbackEngine.loudnessNormalizationPreAmp = preAmp

        verify(volumeHelper).loudnessNormalizationPreAmp = preAmp
        verify(volumeHelper).getVolume(playbackInfo)
        verifyNoMoreInteractions(volumeHelper)
    }

    @Test
    fun loadShouldSetCorrectPropertiesAndPreparePlayerWithCorrectMediaSource() = runBlocking {
        playbackEngine.load(forwardingMediaProduct.delegate)

        assertThat(playbackEngine.mediaProduct).isEqualTo(forwardingMediaProduct.delegate)
        assertThat(playbackEngine.testNextMediaSource).isEqualTo(null)
        assertThat(playbackEngine.playbackContext).isEqualTo(null)
        assertThat(playbackEngine.reflectionNextPlaybackContext).isEqualTo(null)
        assertThat(playbackEngine.playbackState).isEqualTo(PlaybackState.STALLED)
        verify(initialExtendedExoPlayer)
            .load(argThat { delegate === forwardingMediaProduct.delegate })
    }

    @Test
    fun nextShouldDoNothingIfPlaybackStateIsIdle() = runBlocking {
        playbackEngine.setNext(forwardingMediaProduct.delegate)

        verify(initialExtendedExoPlayer, never())
            .setNext(argThat { delegate === forwardingMediaProduct.delegate })
    }

    @ParameterizedTest
    @NullSource
    @MethodSource("nextMediaProducts")
    fun nextShouldSucceedIfPlaybackStateIsNotIdle(nextMediaProduct: MediaProduct?) = runBlocking {
        playbackEngine.load(forwardingMediaProduct.delegate)

        playbackEngine.setNext(nextMediaProduct)

        verify(initialExtendedExoPlayer).setNext(
            if (nextMediaProduct == null) {
                null
            } else {
                argThat { delegate === nextMediaProduct }
            },
        )
    }

    @Test
    fun playShouldDoNothingIfPlaybackStateIsIdle() {
        playbackEngine.play()

        verify(initialExtendedExoPlayer, never()).play()
    }

    @Test
    fun playShouldSucceedIfPlaybackStateIsNotIdle() {
        whenever(
            initialExtendedExoPlayer.load(argThat { delegate === forwardingMediaProduct.delegate })
        ).thenReturn(mediaSource)
        playbackEngine.load(forwardingMediaProduct.delegate)
        val streamingSession = mock<StreamingSession.Explicit>()
        whenever(initialExtendedExoPlayer.currentStreamingSession) doReturn streamingSession
        val idealStartTimestampMs = 8L
        whenever(trueTimeWrapper.currentTimeMillis) doReturn idealStartTimestampMs

        playbackEngine.play()

        verify(initialExtendedExoPlayer).currentStreamingSession
        verify(trueTimeWrapper, atLeastOnce()).currentTimeMillis
        verify(streamingSession).createUndeterminedPlaybackStatistics(
            PlaybackStatistics.IdealStartTimestampMs.Known(idealStartTimestampMs),
            null,
        )
        verify(initialExtendedExoPlayer).play()
    }

    @Test
    fun pauseShouldDoNothingIfPlaybackStateIsIdle() {
        playbackEngine.pause()

        verify(initialExtendedExoPlayer, never()).pause()
    }

    @Test
    fun pauseShouldSucceedIfPlaybackStateIsNotIdle() {
        playbackEngine.load(forwardingMediaProduct.delegate)

        playbackEngine.pause()

        verify(initialExtendedExoPlayer).pause()
    }

    @Test
    fun seekShouldDoNothingIfPlaybackStateIsIdle() {
        playbackEngine.seek(10f)

        verify(initialExtendedExoPlayer, never()).seekTo(10f.toLong())
    }

    @Test
    fun seekShouldSucceedIfPlaybackStateIsNotIdle() {
        playbackEngine.load(forwardingMediaProduct.delegate)
        val seekToMs = 10f

        playbackEngine.seek(seekToMs)

        verify(initialExtendedExoPlayer).seekTo(seekToMs.toLong())
    }

    @Test
    fun resetShouldResetThePlaybackEngine() {
        playbackEngine.reset()

        verify(internalHandler).removeCallbacksAndMessages(null)
        assertThat(playbackEngine.mediaProduct).isNull()
        assertThat(playbackEngine.reflectionCurrentPlaybackStatistics).isNull()
        assertThat(playbackEngine.testNextMediaSource).isNull()
        assertThat(playbackEngine.reflectionNextPlaybackStatistics).isNull()
        assertThat(playbackEngine.playbackContext).isNull()
        assertThat(playbackEngine.reflectionNextPlaybackContext).isNull()
        assertThat(playbackEngine.reflectionCurrentPlaybackSession).isNull()
        assertThat(playbackEngine.reflectionNextPlaybackSession).isNull()
        assertThat(playbackEngine.playbackState).isEqualTo(PlaybackState.IDLE)
    }

    @Test
    fun resetShouldResetThePlaybackEngineIfLoadIsCalled() {
        playbackEngine.load(forwardingMediaProduct.delegate)
        playbackEngine.reset()

        verify(internalHandler).removeCallbacksAndMessages(null)
        assertThat(playbackEngine.mediaProduct).isNull()
        assertThat(playbackEngine.reflectionCurrentPlaybackStatistics).isNull()
        assertThat(playbackEngine.testNextMediaSource).isNull()
        assertThat(playbackEngine.reflectionNextPlaybackStatistics).isNull()
        assertThat(playbackEngine.playbackContext).isNull()
        assertThat(playbackEngine.reflectionNextPlaybackContext).isNull()
        assertThat(playbackEngine.reflectionCurrentPlaybackSession).isNull()
        assertThat(playbackEngine.reflectionNextPlaybackSession).isNull()
        assertThat(playbackEngine.playbackState).isEqualTo(PlaybackState.IDLE)
    }

    @Test
    fun playbackStateChangesEmitCorrespondingEventAndRefreshesVideoSurfaceViewKeepScreenOn() {
        val newState = PlaybackState.STALLED
        assumeTrue(playbackEngine.playbackState != newState)
        val videoSurfaceView = mock<AspectRatioAdjustingSurfaceView>()
        val synchronousSurfaceHolder =
            mock<SynchronousSurfaceHolder>()
        playbackEngine.reflectionVideoSurfaceViewAndSurfaceHolder =
            videoSurfaceView to synchronousSurfaceHolder
        val keepScreenOnCaptor = argumentCaptor<Runnable>()

        runBlocking {
            launch {
                playbackEngine.reflectionSetPlaybackState(newState)
                verify(videoSurfaceView).post(keepScreenOnCaptor.capture())
            }
            withTimeout(3000) {
                assertThat(playbackEngine.events.first())
                    .isEqualTo(Event.PlaybackStateChange(newState))
            }
        }
        verifyNoMoreInteractions(
            videoSurfaceView,
            synchronousSurfaceHolder,
        )

        keepScreenOnCaptor.firstValue.run()

        verify(videoSurfaceView).keepScreenOn = true
        verifyNoMoreInteractions(
            videoSurfaceView,
            synchronousSurfaceHolder,
        )
    }

    @Test
    fun playbackStateUpdateWithSameValueDoesNotCauseAnEmissionOrInteractionsWithVideoSurfaceView() {
        val newState = PlaybackState.STALLED
        assumeTrue(playbackEngine.playbackState != newState)
        val videoSurfaceView = mock<AspectRatioAdjustingSurfaceView>()
        val synchronousSurfaceHolder = mock<SynchronousSurfaceHolder>()
        playbackEngine.reflectionVideoSurfaceViewAndSurfaceHolder =
            videoSurfaceView to synchronousSurfaceHolder
        val keepScreenOnCaptor = argumentCaptor<Runnable>()

        runBlocking {
            launch {
                playbackEngine.reflectionSetPlaybackState(playbackEngine.playbackState)
                verifyNoInteractions(videoSurfaceView)
                playbackEngine.reflectionSetPlaybackState(newState)
                verify(videoSurfaceView).post(keepScreenOnCaptor.capture())
            }
            withTimeout(3000) {
                assertThat(playbackEngine.events.first())
                    .isEqualTo(Event.PlaybackStateChange(newState))
            }
        }
        verifyNoMoreInteractions(videoSurfaceView, synchronousSurfaceHolder)

        keepScreenOnCaptor.firstValue.run()

        verify(videoSurfaceView).keepScreenOn = true
        verifyNoMoreInteractions(videoSurfaceView, synchronousSurfaceHolder)
    }

    @Test
    fun releaseReleasesExtendedExoPlayerAndQuitsTheLooperAndReleasesTheCache() {
        val looper = mock<Looper>()
        val cache = mock<Cache>()
        whenever(playerCache.cache) doReturn cache

        playbackEngine.release()

        verify(initialExtendedExoPlayer).release()
        verify(playerCache).cache
        verify(cache).release()
        verifyNoMoreInteractions(initialExtendedExoPlayer, looper, cache)
    }

    @Test
    fun automaticallyInitializesPlayerUsingFactory() =
        assertSame(initialExtendedExoPlayer, playbackEngine.reflectionExtendedExoPlayer)

    @Test
    fun resetReleasesCurrentExoPlayerAndCreatesANewOne() {
        val newExtendedExoPlayer = mock<ExtendedExoPlayer>()
        whenever(extendedExoPlayerFactory.create(playbackEngine, playbackEngine))
            .thenReturn(newExtendedExoPlayer)

        playbackEngine.reset()

        verify(initialExtendedExoPlayer).currentPositionMs
        verify(initialExtendedExoPlayer).release()
        verifyNoMoreInteractions(initialExtendedExoPlayer, newExtendedExoPlayer)
        assertSame(newExtendedExoPlayer, playbackEngine.reflectionExtendedExoPlayer)
    }

    @Test
    fun exoPlayerRecreationDispatchesVideoSurfaceViewCalls() {
        val oldExtendedExoPlayer = mock<ExtendedExoPlayer>()
        val newExtendedExoPlayer = mock<ExtendedExoPlayer>()
        val videoSurfaceView = mock<AspectRatioAdjustingSurfaceView>()
        val surfaceHolder = mock<SynchronousSurfaceHolder>()
        playbackEngine.reflectionVideoSurfaceViewAndSurfaceHolder =
            videoSurfaceView to surfaceHolder
        playbackEngine.reflectionExtendedExoPlayer = oldExtendedExoPlayer
        reset(oldExtendedExoPlayer)

        playbackEngine.reflectionExtendedExoPlayer = newExtendedExoPlayer

        verify(oldExtendedExoPlayer).clearVideoSurfaceHolder(surfaceHolder)
        verify(newExtendedExoPlayer).setVideoSurfaceHolder(surfaceHolder)
        verifyNoMoreInteractions(
            oldExtendedExoPlayer,
            newExtendedExoPlayer,
            videoSurfaceView,
            surfaceHolder,
        )
    }

    @Suppress("LongMethod")
    @Test
    fun surfaceViewSetterRefreshesKeepScreenOnAndUpdatesSurfaceViewBindingToExoPlayer() {
        val extendedExoPlayer = mock<ExtendedExoPlayer>()
        val oldSurfaceHolder = mock<SynchronousSurfaceHolder>()
        val oldVideoSurfaceView = mock<AspectRatioAdjustingSurfaceView>()
        val newSurfaceHolder = mock<SurfaceHolder>()
        val newVideoSurfaceView = mock<AspectRatioAdjustingSurfaceView> {
            on { it.holder } doReturn newSurfaceHolder
        }
        val synchronousSurfaceHolder = mock<SynchronousSurfaceHolder>()
        playbackEngine.reflectionSetPlaybackState(PlaybackState.PLAYING)
        playbackEngine.reflectionExtendedExoPlayer = extendedExoPlayer
        playbackEngine.reflectionVideoSurfaceViewAndSurfaceHolder =
            oldVideoSurfaceView to oldSurfaceHolder
        reset(extendedExoPlayer)
        whenever(synchronousSurfaceHolderFactory.create(newSurfaceHolder))
            .thenReturn(synchronousSurfaceHolder)
        val keepScreenOnCaptor = argumentCaptor<Runnable>()
        val formatWidth = 1
        val formatHeight = 3
        val format = Format.Builder()
            .setWidth(formatWidth)
            .setHeight(formatHeight)
            .build()
        whenever(extendedExoPlayer.videoFormat) doReturn format

        playbackEngine.videoSurfaceView = newVideoSurfaceView

        verify(extendedExoPlayer).clearVideoSurfaceHolder(oldSurfaceHolder)
        verify(oldVideoSurfaceView).post(keepScreenOnCaptor.capture())
        verify(newVideoSurfaceView).post(keepScreenOnCaptor.capture())
        verify(extendedExoPlayer).setVideoSurfaceHolder(synchronousSurfaceHolder)
        verify(newVideoSurfaceView).holder
        verify(synchronousSurfaceHolderFactory).create(newSurfaceHolder)
        verify(extendedExoPlayer).videoFormat
        verify(newVideoSurfaceView).suggestedVideoDimen =
            AspectRatioAdjustingSurfaceView.SuggestedDimensions(formatWidth, formatHeight)
        verifyNoMoreInteractions(
            extendedExoPlayer,
            oldVideoSurfaceView,
            newVideoSurfaceView,
            newSurfaceHolder,
            oldSurfaceHolder,
            synchronousSurfaceHolder,
        )

        keepScreenOnCaptor.firstValue.run()

        verify(oldVideoSurfaceView).keepScreenOn = false
        verifyNoMoreInteractions(
            extendedExoPlayer,
            oldVideoSurfaceView,
            newVideoSurfaceView,
            newSurfaceHolder,
            oldSurfaceHolder,
            synchronousSurfaceHolder,
        )

        keepScreenOnCaptor.secondValue.run()

        verify(newVideoSurfaceView).keepScreenOn = true
        verifyNoMoreInteractions(
            extendedExoPlayer,
            oldVideoSurfaceView,
            newVideoSurfaceView,
            newSurfaceHolder,
            oldSurfaceHolder,
            synchronousSurfaceHolder,
        )
    }

    @Test
    fun onPlaybackStateSetToPlaySetsListener() {
        playbackEngine.reflectionSetPlaybackState(PlaybackState.PLAYING)

        verify(streamingPrivileges).setStreamingPrivilegesListener(playbackEngine)
    }

    @Test
    fun onConnectionEstablishedAcquiresStreamingPrivilegesIfPlaying() {
        playbackEngine.reflectionSetPlaybackState(PlaybackState.PLAYING)
        reset(streamingPrivileges)

        playbackEngine.onConnectionEstablished()

        verify(streamingPrivileges).acquireStreamingPrivileges()
    }

    @ParameterizedTest
    @EnumSource(PlaybackState::class)
    fun onConnectionEstablishedDoesNotAcquireStreamingPrivilegesIfNotPlaying(
        playbackState: PlaybackState,
    ) {
        assumeFalse(playbackState == PlaybackState.PLAYING)
        playbackEngine.reflectionSetPlaybackState(playbackState)
        reset(streamingPrivileges)

        playbackEngine.onConnectionEstablished()

        verifyNoMoreInteractions(streamingPrivileges)
    }

    @ParameterizedTest
    @EnumSource(PlaybackState::class)
    fun onStreamingPrivilegesRevokedWhileNotPlayingDoesNotPauseNorDispatchEvent(
        playbackState: PlaybackState,
    ) {
        assumeFalse(playbackState == PlaybackState.PLAYING)
        val privilegedClientDisplayName = "a privileged client"
        if (playbackState != PlaybackState.IDLE) {
            playbackEngine.reflectionSetPlaybackState(playbackState)
        }

        runBlocking {
            playbackEngine.onStreamingPrivilegesRevoked(privilegedClientDisplayName)

            verify(events, never()).emit(
                Event.StreamingPrivilegesRevoked(
                    privilegedClientDisplayName,
                ),
            )
            verify(internalHandler, never()).post(any())
            verify(initialExtendedExoPlayer, never()).pause()
        }
    }

    @Test
    fun onStreamingPrivilegesRevokedWhilePlayingPausesAndDispatchesEvent() {
        whenever(internalHandler.post(any())).thenAnswer {
            (it.arguments.single() as Runnable).run()
            true
        }
        val privilegedClientDisplayName = "a privileged client"
        playbackEngine.reflectionSetPlaybackState(PlaybackState.PLAYING)

        runBlocking {
            launch {
                playbackEngine.onStreamingPrivilegesRevoked(privilegedClientDisplayName)
            }
            withTimeout(3000) {
                /*
                 * Can't use the exact pattern as is in other tests because it causes it to be flaky
                 * as the reflectionSetPlaybackState call above will cause a playback state change
                 * event that will race the event that we are trying to assert on
                 */
                assertThat(
                    playbackEngine.events.filter { it is Event.StreamingPrivilegesRevoked }.first(),
                ).isEqualTo(Event.StreamingPrivilegesRevoked(privilegedClientDisplayName))
            }
        }

        verify(initialExtendedExoPlayer).pause()
    }

    @Suppress("LongMethod")
    @Test
    fun onPlaybackInfoFetchedForCurrentMediaProduct() {
        val actualProductId = 123
        val referenceId = "referenceId"
        val currentMediaProduct = mock<MediaProduct> {
            on { it.referenceId } doReturn referenceId
        }
        val currentForwardingMediaProduct = mock<ForwardingMediaProduct<MediaProduct>> {
            on { it.delegate } doReturn currentMediaProduct
        }
        val streamingSession = mock<StreamingSession.Explicit>()
        val playbackInfo = mock<PlaybackInfo.Track> {
            on { it.trackId } doReturn actualProductId
        }
        val currentMediaSource = mock<PlaybackInfoMediaSource> {
            on { it.forwardingMediaProduct } doReturn currentForwardingMediaProduct
            on { it.playbackInfo } doReturn playbackInfo
        }
        playbackEngine.testMediaSource = currentMediaSource
        val playbackContext = mock<PlaybackContext.Track>()
        whenever(internalHandler.post(any())).thenAnswer {
            (it.arguments.single() as Runnable).run()
            true
        }
        whenever(volumeHelper.getVolume(playbackInfo)).thenReturn(1.0F)
        whenever(playbackContextFactory.create(playbackInfo, referenceId))
            .thenReturn(playbackContext)
        val streamingSessionId = mock<UUID>()
        val playbackStatistics = mock<PlaybackStatistics.Success.Prepared.Audio> {
            on { it.streamingSessionId } doReturn streamingSessionId
        }
        val previousPlaybackStatistics = mock<PlaybackStatistics.Undetermined>()
        playbackEngine.reflectionCurrentPlaybackStatistics = previousPlaybackStatistics
        whenever(
            undeterminedPlaybackSessionResolver(
                previousPlaybackStatistics,
                playbackInfo,
                emptyMap(),
            ),
        ).thenReturn(playbackStatistics)
        val playbackSession = mock<PlaybackSession.Audio>()
        whenever(
            streamingSession.createPlaybackSession(playbackInfo, currentForwardingMediaProduct),
        ).thenReturn(playbackSession)

        playbackEngine
            .onPlaybackInfoFetched(streamingSession, currentForwardingMediaProduct, playbackInfo)

        assertThat(playbackEngine.reflectionCurrentPlaybackStatistics).isSameAs(playbackStatistics)
        verify(currentMediaSource, atLeastOnce()).forwardingMediaProduct
        verify(currentForwardingMediaProduct, atLeastOnce()).delegate
        verify(currentMediaProduct).referenceId
        verify(playbackContextFactory).create(playbackInfo, referenceId)
        assertThat(playbackEngine.playbackContext).isEqualTo(playbackContext)
        assertThat(playbackEngine.reflectionNextPlaybackContext).isNull()
        assertThat(playbackEngine.reflectionCurrentPlaybackSession).isSameAs(playbackSession)
        verify(volumeHelper).getVolume(playbackInfo)
        verify(initialExtendedExoPlayer).volume = 1.0F
        verify(undeterminedPlaybackSessionResolver)(
            previousPlaybackStatistics,
            playbackInfo,
            emptyMap(),
        )
        verify(streamingSession).createPlaybackSession(playbackInfo, currentForwardingMediaProduct)
        verifyNoMoreInteractions(
            currentMediaProduct,
            streamingSession,
            playbackStatistics,
            streamingSessionId,
            previousPlaybackStatistics,
            playbackSession,
        )
    }

    @Test
    fun onPlaybackInfoFetchedForNextMediaProduct() {
        val productId = 123
        val referenceId = "referenceId"
        val nextMediaProduct = mock<MediaProduct> {
            on { it.referenceId } doReturn referenceId
        }
        val nextForwardingMediaProduct = mock<ForwardingMediaProduct<MediaProduct>> {
            on { it.delegate } doReturn nextMediaProduct
        }
        val nextMediaSource = mock<PlaybackInfoMediaSource> {
            on { it.forwardingMediaProduct } doReturn nextForwardingMediaProduct
        }
        playbackEngine.testNextMediaSource = nextMediaSource
        val streamingSession = mock<StreamingSession.Implicit>()
        val playbackInfo = mock<PlaybackInfo.Video> {
            on { it.videoId } doReturn productId
        }
        val playbackContext = mock<PlaybackContext.Track>()
        whenever(playbackContextFactory.create(playbackInfo, referenceId))
            .thenReturn(playbackContext)
        val playbackStatistics = mock<PlaybackStatistics.Undetermined>()
        whenever(initialExtendedExoPlayer.nextStreamingSession) doReturn streamingSession
        whenever(
            streamingSession.createUndeterminedPlaybackStatistics(
                PlaybackStatistics.IdealStartTimestampMs.NotYetKnown,
                emptyMap(),
            ),
        ).thenReturn(playbackStatistics)
        whenever(internalHandler.post(any())).then {
            (it.arguments.single() as Runnable).run()
            false
        }

        playbackEngine
            .onPlaybackInfoFetched(streamingSession, nextForwardingMediaProduct, playbackInfo)

        assertThat(playbackEngine.reflectionNextPlaybackStatistics).isSameAs(playbackStatistics)
        verify(nextMediaSource).forwardingMediaProduct
        verify(nextForwardingMediaProduct).delegate
        verify(nextMediaProduct).referenceId
        verify(playbackContextFactory).create(playbackInfo, referenceId)
        assertThat(playbackEngine.playbackContext).isNull()
        assertThat(playbackEngine.reflectionNextPlaybackContext).isEqualTo(playbackContext)
        verify(nextForwardingMediaProduct).extras
        verify(streamingSession).createUndeterminedPlaybackStatistics(
            PlaybackStatistics.IdealStartTimestampMs.NotYetKnown,
            emptyMap(),
        )
        verify(streamingSession).createPlaybackSession(playbackInfo, nextForwardingMediaProduct)
        verifyNoMoreInteractions(
            nextMediaProduct,
            nextForwardingMediaProduct,
            streamingSession,
            playbackStatistics,
        )
    }

    @Test
    fun onDjSessionUpdatedDispatchesEvent() {
        val productId = "123-abc"
        val status = DjSessionStatus.PAUSED

        runBlocking {
            launch {
                playbackEngine.onDjSessionUpdated(productId, status)
            }
            withTimeout(3000) {
                assertThat(playbackEngine.events.first())
                    .isEqualTo(Event.DjSessionUpdate(productId, status))
            }
        }

        verifyNoMoreInteractions(initialExtendedExoPlayer)
    }

    @ParameterizedTest
    @ValueSource(
        ints = [
            Player.PLAYBACK_SUPPRESSION_REASON_NONE,
            Player.PLAYBACK_SUPPRESSION_REASON_TRANSIENT_AUDIO_FOCUS_LOSS,
        ],
    )
    fun onPlaybackSuppressionReasonChangedShouldSetActionForTrack(reason: Int) =
        testOnPlaybackSuppressionReasonChanged(reason, ProductType.TRACK)

    @ParameterizedTest
    @ValueSource(
        ints = [
            Player.PLAYBACK_SUPPRESSION_REASON_NONE,
            Player.PLAYBACK_SUPPRESSION_REASON_TRANSIENT_AUDIO_FOCUS_LOSS,
        ],
    )
    fun onPlaybackSuppressionReasonChangedShouldSetActionForVideo(reason: Int) =
        testOnPlaybackSuppressionReasonChanged(reason, ProductType.VIDEO)

    @ParameterizedTest
    @ValueSource(
        ints = [
            Player.PLAYBACK_SUPPRESSION_REASON_NONE,
            Player.PLAYBACK_SUPPRESSION_REASON_TRANSIENT_AUDIO_FOCUS_LOSS,
        ],
    )
    fun onPlaybackSuppressionReasonChangedShouldSetActionAndPotentiallySeekForBroadcast(
        reason: Int,
    ) = testOnPlaybackSuppressionReasonChanged(reason, ProductType.BROADCAST)

    @Suppress("LongMethod")
    private fun testOnPlaybackSuppressionReasonChanged(reason: Int, productType: ProductType) {
        val forwardingMediaProduct = mock<ForwardingMediaProduct<MediaProduct>> {
            on { it.productType } doReturn productType
        }
        val mediaSource = mock<PlaybackInfoMediaSource> {
            on { it.forwardingMediaProduct } doReturn forwardingMediaProduct
        }
        playbackEngine.testMediaSource = mediaSource
        val mediaItem = MediaItem.Builder()
            .setMediaId(forwardingMediaProduct.hashCode().toString())
            .build()
        val windowZero = Timeline.Window()
            .set(
                Unit,
                mediaItem,
                null,
                -1L,
                -1L,
                -1L,
                false,
                false,
                null,
                -1L,
                -1L,
                -1,
                -1,
                -1L,
            )
        val timeline = mock<Timeline> {
            on { it.windowCount } doReturn 1
            on { it.getWindow(eq(0), any()) } doReturn windowZero
        }
        val currentPlaybackPositionMs = -12314351234
        val eventTime = spy(
            EventTime(
                -1,
                timeline,
                0,
                null,
                -5,
                Timeline.EMPTY,
                -1,
                null,
                currentPlaybackPositionMs,
                -1,
            ),
        )
        val currentPositionSinceEpochMs = 123L
        whenever(initialExtendedExoPlayer.currentPositionSinceEpochMs)
            .thenReturn(currentPositionSinceEpochMs)
        val positionSeconds = if (productType == ProductType.BROADCAST) {
            currentPositionSinceEpochMs
        } else {
            currentPlaybackPositionMs
        }.toDouble() / 1_000
        val actions = mock<MutableList<Action>>()
        val currentPlaybackSession = mock<PlaybackSession.Audio> {
            on { it.actions } doReturn actions
        }
        playbackEngine.reflectionCurrentPlaybackSession = currentPlaybackSession
        val currentTimeMills = -1L
        whenever(trueTimeWrapper.currentTimeMillis) doReturn currentTimeMills
        val actionType = if (reason == Player.PLAYBACK_SUPPRESSION_REASON_NONE) {
            Action.Type.PLAYBACK_START
        } else {
            Action.Type.PLAYBACK_STOP
        }

        playbackEngine.onPlaybackSuppressionReasonChanged(
            eventTime,
            reason,
        )

        verify(forwardingMediaProduct, atLeastOnce()).productType
        verify(mediaSource, atLeastOnce()).forwardingMediaProduct
        if (productType == ProductType.BROADCAST) {
            if (reason == Player.PLAYBACK_SUPPRESSION_REASON_NONE) {
                verify(initialExtendedExoPlayer).seekToDefaultPosition()
            }
            verify(initialExtendedExoPlayer).currentPositionSinceEpochMs
        }
        verify(currentPlaybackSession).actions
        verify(trueTimeWrapper).currentTimeMillis
        verify(actions).add(
            Action(
                currentTimeMills,
                positionSeconds,
                actionType,
            ),
        )
        verifyNoMoreInteractions(
            forwardingMediaProduct,
            mediaSource,
            eventTime,
            currentPlaybackSession,
            actions,
            initialExtendedExoPlayer,
        )
    }

    @ParameterizedTest
    @EnumSource(ProductType::class)
    @Suppress("LongMethod")
    fun onIsPlayingChangedToTrueUpdatesPlaybackState(
        productType: ProductType,
    ) {
        val currentPlaybackPositionMs = -12314351234
        val eventTime = spy(
            EventTime(
                -1,
                Timeline.EMPTY,
                -1,
                null,
                -1,
                Timeline.EMPTY,
                -1,
                null,
                currentPlaybackPositionMs,
                -1,
            ),
        )
        val forwardingMediaProduct = mock<ForwardingMediaProduct<MediaProduct>> {
            on { it.productType } doReturn productType
        }
        val mediaSource = mock<PlaybackInfoMediaSource> {
            on { it.forwardingMediaProduct } doReturn forwardingMediaProduct
        }
        playbackEngine.testMediaSource = mediaSource
        val currentPlaybackSession = mock<PlaybackSession.Audio>()
        playbackEngine.reflectionCurrentPlaybackSession = currentPlaybackSession
        val currentPositionSinceEpochMs = 123L
        whenever(initialExtendedExoPlayer.currentPositionSinceEpochMs)
            .thenReturn(currentPositionSinceEpochMs)
        val positionSeconds = if (productType == ProductType.BROADCAST) {
            currentPositionSinceEpochMs
        } else {
            currentPlaybackPositionMs
        }.toDouble() / 1_000

        playbackEngine.onIsPlayingChanged(eventTime, true)

        verify(forwardingMediaProduct, atLeastOnce()).productType
        verify(mediaSource, atLeastOnce()).forwardingMediaProduct
        verify(currentPlaybackSession).startAssetPosition = positionSeconds
        assertThat(playbackEngine.playbackState).isEqualTo(PlaybackState.IDLE)
        verifyNoMoreInteractions(
            eventTime,
            forwardingMediaProduct,
            eventTime,
            currentPlaybackSession,
        )
    }

    @ParameterizedTest
    @EnumSource(PlaybackState::class)
    fun onIsPlayingChangedToFalseWhileNotPlayingDoesNotClosePlaybackReportOrUpdatePlaybackState(
        playbackState: PlaybackState,
    ) {
        assumeFalse(playbackState == PlaybackState.PLAYING)
        val eventTime = mock<EventTime>()
        playbackEngine.reflectionSetPlaybackState(playbackState)

        playbackEngine.onIsPlayingChanged(eventTime, false)

        assertThat(playbackEngine.playbackState).isEqualTo(playbackState)
        verifyNoMoreInteractions(eventTime)
    }

    @Test
    fun onPlaybackStateChangedToBufferingChangesStateToStalled() {
        val mediaItem = MediaItem.Builder()
            .setMediaId(forwardingMediaProduct.hashCode().toString())
            .build()
        val window = Timeline.Window()
            .set(
                Unit,
                mediaItem,
                null,
                -1L,
                -1L,
                -1L,
                false,
                false,
                null,
                -1L,
                -1L,
                -1,
                -1,
                -1L,
            )
        val timeline = mock<Timeline> {
            on { it.windowCount } doReturn 1
            on { it.getWindow(eq(0), any()) } doReturn window
        }
        val eventTime = EventTime(
            -1L,
            timeline,
            0,
            null,
            -1L,
            Timeline.EMPTY,
            -1,
            null,
            -1L,
            -1L,
        )

        playbackEngine.onPlaybackStateChanged(eventTime, Player.STATE_BUFFERING)

        assertThat(playbackEngine.playbackState).isEqualTo(PlaybackState.STALLED)
    }

    @Test
    fun onPlaybackStateChangedToReadyChangesStateToNotPlaying() {
        val mediaItem = MediaItem.Builder()
            .setMediaId(forwardingMediaProduct.hashCode().toString())
            .build()
        val window = Timeline.Window()
            .set(
                Unit,
                mediaItem,
                null,
                -1L,
                -1L,
                -1L,
                false,
                false,
                null,
                -1L,
                -1L,
                -1,
                -1,
                -1L,
            )
        val timeline = mock<Timeline> {
            on { it.windowCount } doReturn 1
            on { it.getWindow(eq(0), any()) } doReturn window
        }
        val eventTime = EventTime(
            -1L,
            timeline,
            0,
            null,
            -1L,
            Timeline.EMPTY,
            -1,
            null,
            -1L,
            -1L,
        )

        playbackEngine.onPlaybackStateChanged(eventTime, Player.STATE_READY)

        assertThat(playbackEngine.playbackState).isEqualTo(PlaybackState.NOT_PLAYING)
    }

    @Test
    fun onPlaybackStateChangedToIdleResetsToIdle() {
        val mediaItem = MediaItem.Builder()
            .setMediaId(forwardingMediaProduct.hashCode().toString())
            .build()
        val window = Timeline.Window()
            .set(
                Unit,
                mediaItem,
                null,
                -1L,
                -1L,
                -1L,
                false,
                false,
                null,
                -1L,
                -1L,
                -1,
                -1,
                -1L,
            )
        val timeline = mock<Timeline> {
            on { it.windowCount } doReturn 1
            on { it.getWindow(eq(0), any()) } doReturn window
        }
        val eventTime = EventTime(
            -1L,
            timeline,
            0,
            null,
            -1L,
            Timeline.EMPTY,
            -1,
            null,
            -1L,
            -1L,
        )

        playbackEngine.onPlaybackStateChanged(eventTime, Player.STATE_IDLE)

        verify(internalHandler).removeCallbacksAndMessages(null)
        assertThat(playbackEngine.mediaProduct).isNull()
        assertThat(playbackEngine.testNextMediaSource).isNull()
        assertThat(playbackEngine.playbackContext).isNull()
        assertThat(playbackEngine.reflectionNextPlaybackContext).isNull()
        assertThat(playbackEngine.playbackState).isEqualTo(PlaybackState.IDLE)
    }

    @Suppress("LongMethod")
    @Test
    fun onPlaybackStateChangedToEndedResetsToIdleAndReports() {
        playbackEngine.testMediaSource = mediaSource
        val playbackContext = mock<PlaybackContext.Track>()
        playbackEngine.reflectionPlaybackContext = playbackContext
        val actions = mutableListOf<Action>()
        val playbackSessionId = mock<UUID>()
        val startTimestamp = 2L
        val startAssetPosition = 3.4
        val requestedProductId = "5"
        val actualProductId = "-8"
        val actualAssetPresentation = AssetPresentation.PREVIEW
        val actualQuality = VideoQuality.AUDIO_ONLY
        val sourceType = "sourceType"
        val sourceId = "sourceId"
        val currentPlaybackSession = mock<PlaybackSession.Video> {
            on { it.playbackSessionId } doReturn playbackSessionId
            on { it.startTimestamp } doReturn startTimestamp
            on { it.startAssetPosition } doReturn startAssetPosition
            on { it.requestedProductId } doReturn requestedProductId
            on { it.actualProductId } doReturn actualProductId
            on { it.actualAssetPresentation } doReturn actualAssetPresentation
            on { it.actualQuality } doReturn actualQuality
            on { it.sourceType } doReturn sourceType
            on { it.sourceId } doReturn sourceId
            on { it.actions } doReturn actions
        }
        playbackEngine.reflectionCurrentPlaybackSession = currentPlaybackSession
        val currentTimeMillis = 12345L
        whenever(trueTimeWrapper.currentTimeMillis) doReturn currentTimeMillis
        val currentPlaybackPositionMs = -12314351234

        val mediaItem = MediaItem.Builder()
            .setMediaId(forwardingMediaProduct.hashCode().toString())
            .build()
        val window = Timeline.Window()
            .set(
                Unit,
                mediaItem,
                null,
                -1L,
                -1L,
                -1L,
                false,
                false,
                null,
                -1L,
                -1L,
                -1,
                -1,
                -1L,
            )
        val timeline = mock<Timeline> {
            on { it.windowCount } doReturn 1
            on { it.getWindow(eq(0), any()) } doReturn window
        }
        val eventTime = spy(
            EventTime(
                -1L,
                timeline,
                0,
                null,
                -1,
                Timeline.EMPTY,
                -1,
                null,
                currentPlaybackPositionMs,
                -1,
            ),
        )

        runBlocking {
            launch {
                playbackEngine.onPlaybackStateChanged(eventTime, Player.STATE_ENDED)
            }
            withTimeout(3000) {
                assertThat(playbackEngine.events.first())
                    .isEqualTo(
                        Event.MediaProductEnded(
                            forwardingMediaProduct.delegate,
                            playbackContext,
                        ),
                    )
            }
        }

        verify(currentPlaybackSession).playbackSessionId
        verify(currentPlaybackSession, atLeastOnce()).startTimestamp
        verify(currentPlaybackSession).startAssetPosition
        verify(currentPlaybackSession).requestedProductId
        verify(currentPlaybackSession).actualProductId
        verify(currentPlaybackSession).actualAssetPresentation
        verify(currentPlaybackSession).actualQuality
        verify(currentPlaybackSession).sourceType
        verify(currentPlaybackSession).sourceId
        verify(currentPlaybackSession).actions
        verify(currentPlaybackSession).extras
        verify(eventReporter).report(
            VideoPlaybackSession.Payload(
                playbackSessionId,
                startTimestamp,
                startAssetPosition,
                requestedProductId,
                actualProductId,
                actualAssetPresentation,
                actualQuality,
                sourceType,
                sourceId,
                actions,
                currentTimeMillis,
                currentPlaybackPositionMs.toDouble() / 1_000,
            ),
            emptyMap(),
        )
        verify(internalHandler).removeCallbacksAndMessages(null)
        assertThat(playbackEngine.mediaProduct).isNull()
        assertThat(playbackEngine.testNextMediaSource).isNull()
        assertThat(playbackEngine.playbackContext).isNull()
        assertThat(playbackEngine.reflectionNextPlaybackContext).isNull()
        assertThat(playbackEngine.playbackState).isEqualTo(PlaybackState.IDLE)
        verifyNoMoreInteractions(currentPlaybackSession, playbackSessionId, eventTime)
    }

    @ParameterizedTest
    @ValueSource(
        ints = [
            Player.DISCONTINUITY_REASON_SEEK_ADJUSTMENT,
            Player.DISCONTINUITY_REASON_SKIP,
            Player.DISCONTINUITY_REASON_REMOVE,
            Player.DISCONTINUITY_REASON_INTERNAL,
        ],
    )
    fun onPositionDiscontinuityShouldOnlyUpdatePosWhenNotAutoTransitionOrSeeking(reason: Int) {
        val forwardingMediaProduct = mock<ForwardingMediaProduct<MediaProduct>>()
        val mediaSource = mock<PlaybackInfoMediaSource> {
            on { it.forwardingMediaProduct } doReturn forwardingMediaProduct
        }
        val nextMediaProduct = mock<MediaProduct>()
        val nextForwardingMediaProduct = mock<ForwardingMediaProduct<MediaProduct>> {
            on { it.delegate } doReturn nextMediaProduct
        }
        val nextMediaSource = mock<PlaybackInfoMediaSource> {
            on { it.forwardingMediaProduct } doReturn nextForwardingMediaProduct
        }
        val playbackContext = mock<PlaybackContext.Track>()
        val nextPlaybackContext = mock<PlaybackContext.Track>()
        playbackEngine.testMediaSource = mediaSource
        playbackEngine.testNextMediaSource = nextMediaSource
        playbackEngine.reflectionPlaybackContext = playbackContext
        playbackEngine.reflectionNextPlaybackContext = nextPlaybackContext
        val positionMs = 87L
        val newPositionInfo = spy(
            Player.PositionInfo(
                null,
                -1,
                null,
                -1,
                positionMs,
                -1,
                -1,
                -1,
            ),
        )

        playbackEngine.onPositionDiscontinuity(mock(), mock(), newPositionInfo, reason)

        verify(initialExtendedExoPlayer).updatePosition(positionMs)
        assertThat(playbackEngine.mediaProduct).isSameAs(forwardingMediaProduct.delegate)
        assertThat(playbackEngine.testNextMediaSource).isSameAs(nextMediaSource)
        assertThat(playbackEngine.playbackContext).isSameAs(playbackContext)
        assertThat(playbackEngine.reflectionNextPlaybackContext).isSameAs(nextPlaybackContext)
        verifyNoMoreInteractions(initialExtendedExoPlayer, newPositionInfo)
    }

    @Suppress("LongMethod")
    @ParameterizedTest
    @EnumSource(ProductType::class)
    fun onPositionDiscontinuityWhenAutoTransitionShouldEmitMediaProductTransitionAndUpdateEventInfoForRepeatModeOff(
        productType: ProductType,
    ) = runBlocking {
        val duration = 24.seconds
        val nextMediaProduct = mock<MediaProduct>()
        val nextForwardingMediaProduct = mock<ForwardingMediaProduct<MediaProduct>> {
            on { it.delegate } doReturn nextMediaProduct
            on { it.productType } doReturn productType
        }
        val playbackInfo = mock<PlaybackInfo.Track>()
        val nextMediaSource = mock<PlaybackInfoMediaSource> {
            on { it.forwardingMediaProduct } doReturn nextForwardingMediaProduct
            on { it.playbackInfo } doReturn playbackInfo
        }
        val nextPlaybackContext = PlaybackContext.Track(
            AudioMode.STEREO,
            AudioQuality.HIGH,
            320_000,
            16,
            "aac",
            44100,
            "123",
            AssetPresentation.FULL,
            duration.toDouble(DurationUnit.SECONDS).toFloat(),
            AssetSource.ONLINE,
            "123-abc",
            "123-abc",
        )
        playbackEngine.testMediaSource = mock()
        playbackEngine.testNextMediaSource = nextMediaSource
        playbackEngine.reflectionPlaybackContext = mock<PlaybackContext.Track>()
        playbackEngine.reflectionNextPlaybackContext = nextPlaybackContext
        val startTimestampMs = -1L
        whenever(trueTimeWrapper.currentTimeMillis) doReturn startTimestampMs
        val startedPlaybackStatistics = mock<PlaybackStatistics.Success.Started>()
        val preparedPlaybackStatistics = mock<PlaybackStatistics.Success.Prepared.Audio> {
            on { it.toStarted(startTimestampMs) } doReturn startedPlaybackStatistics
        }
        val undeterminedPlaybackStatisticsWithIdealStartTimestampMs =
            mock<PlaybackStatistics.Undetermined>()
        val undeterminedPlaybackStatistics = mock<PlaybackStatistics.Undetermined> {
            on {
                copy(
                    idealStartTimestampMs =
                    PlaybackStatistics.IdealStartTimestampMs.Known(startTimestampMs),
                )
            }.thenReturn(undeterminedPlaybackStatisticsWithIdealStartTimestampMs)
        }
        whenever(
            undeterminedPlaybackSessionResolver(
                undeterminedPlaybackStatisticsWithIdealStartTimestampMs,
                playbackInfo,
                emptyMap(),
            ),
        ).thenReturn(preparedPlaybackStatistics)
        playbackEngine.reflectionNextPlaybackStatistics = undeterminedPlaybackStatistics
        val nextPlaybackSession = mock<PlaybackSession.Audio>()
        playbackEngine.reflectionNextPlaybackSession = nextPlaybackSession
        val eventPlaybackPositionMs = Long.MAX_VALUE
        val mediaItem = MediaItem.Builder()
            .setMediaId(forwardingMediaProduct.hashCode().toString())
            .build()
        val window = Timeline.Window()
            .set(
                Unit,
                mediaItem,
                null,
                -1L,
                -1L,
                -1L,
                false,
                false,
                null,
                -1L,
                duration.toLong(DurationUnit.MICROSECONDS),
                -1,
                -1,
                -1L,
            )
        val windowIndex = 0
        val timeline = mock<Timeline> {
            on { it.windowCount } doReturn 1
            on { it.getWindow(eq(windowIndex), any()) } doReturn window
        }
        val eventTime = EventTime(
            -1,
            timeline,
            windowIndex,
            null,
            eventPlaybackPositionMs,
            Timeline.EMPTY,
            -1,
            null,
            -1,
            -1,
        )
        val oldPositionInfo = spy(
            Player.PositionInfo(
                null,
                -1,
                null,
                -1,
                12459L,
                -1,
                -1,
                -1,
            ),
        )
        val positionMs = 87L
        val newPositionInfo = spy(
            Player.PositionInfo(
                null,
                -1,
                null,
                -1,
                positionMs,
                -1,
                -1,
                -1,
            ),
        )
        whenever(initialExtendedExoPlayer.repeatMode).thenReturn(Player.REPEAT_MODE_OFF)
        whenever(volumeHelper.getVolume(playbackInfo)).thenReturn(1.0F)

        runBlocking {
            launch {
                playbackEngine.onPositionDiscontinuity(
                    eventTime,
                    oldPositionInfo,
                    newPositionInfo,
                    Player.DISCONTINUITY_REASON_AUTO_TRANSITION,
                )
            }
            withTimeout(3000) {
                assertThat(playbackEngine.events.first())
                    .isEqualTo(
                        Event.MediaProductTransition(nextMediaProduct, nextPlaybackContext),
                    )
            }
        }

        assertThat(playbackEngine.mediaProduct).isSameAs(nextMediaProduct)
        assertThat(playbackEngine.testNextMediaSource).isNull()
        assertThat(playbackEngine.playbackContext).isEqualTo(nextPlaybackContext)
        assertThat(playbackEngine.reflectionNextPlaybackContext).isNull()
        assertThat(playbackEngine.reflectionCurrentPlaybackStatistics)
            .isSameAs(startedPlaybackStatistics)
        assertThat(playbackEngine.reflectionNextPlaybackStatistics).isNull()
        assertThat(playbackEngine.reflectionCurrentPlaybackSession).isEqualTo(nextPlaybackSession)
        assertThat(playbackEngine.reflectionNextPlaybackSession).isNull()
        verify(initialExtendedExoPlayer).updatePosition(positionMs)
        verify(initialExtendedExoPlayer).onCurrentItemFinished()
        verify(volumeHelper).getVolume(playbackInfo)
        verify(initialExtendedExoPlayer).volume = 1.0F
        verify(trueTimeWrapper).currentTimeMillis
        verify(undeterminedPlaybackSessionResolver)
            .invoke(
                undeterminedPlaybackStatisticsWithIdealStartTimestampMs,
                playbackInfo,
                emptyMap(),
            )
        verify(preparedPlaybackStatistics).toStarted(startTimestampMs)
        verify(nextPlaybackSession).startTimestamp = startTimestampMs
        verify(nextPlaybackSession).startAssetPosition = positionMs.toDouble() / 1_000
        verify(timeline).getWindow(eq(windowIndex), any())
        verifyNoMoreInteractions(
            playbackInfo,
            startedPlaybackStatistics,
            undeterminedPlaybackStatisticsWithIdealStartTimestampMs,
            nextPlaybackSession,
            oldPositionInfo,
            newPositionInfo,
            timeline,
        )
    }

    @Suppress("LongMethod")
    @ParameterizedTest
    @EnumSource(ProductType::class)
    fun onPositionDiscontinuityWhenAutoTransitionShouldEmitMediaProductTransitionAndUpdateEventInfoForRepeatModeOne(
        productType: ProductType,
    ) {
        val duration = 24000L
        val productId = "123"
        val sourceType = "sourceType"
        val sourceId = "sourceId"
        val currentMediaProduct = mock<MediaProduct> {
            on { it.productId } doReturn productId
            on { it.productType } doReturn productType
            on { it.sourceType } doReturn sourceType
            on { it.sourceId } doReturn sourceId
        }
        val currentForwardingMediaProduct = ForwardingMediaProduct(currentMediaProduct)
        val playbackInfo = mock<PlaybackInfo.Track>()
        val mediaSource = mock<PlaybackInfoMediaSource> {
            on { it.forwardingMediaProduct } doReturn currentForwardingMediaProduct
            on { it.playbackInfo } doReturn playbackInfo
        }
        val currentPlaybackContext = PlaybackContext.Track(
            AudioMode.STEREO,
            AudioQuality.HIGH,
            320_000,
            16,
            "aac",
            44100,
            "123",
            AssetPresentation.FULL,
            duration.toFloat() / 1000,
            AssetSource.ONLINE,
            "123-abc",
            "123-abc",
        )
        playbackEngine.testMediaSource = mediaSource
        playbackEngine.reflectionPlaybackContext = currentPlaybackContext
        val startTimestampMs = -1L
        whenever(trueTimeWrapper.currentTimeMillis) doReturn startTimestampMs
        val eventPlaybackPositionMs = Long.MAX_VALUE
        val eventTime = EventTime(
            -1,
            Timeline.EMPTY,
            -1,
            null,
            eventPlaybackPositionMs,
            mock(),
            0,
            null,
            -1,
            -1,
        )
        val oldPositionMs = 12459L
        val oldPositionInfo = spy(
            Player.PositionInfo(
                null,
                -1,
                null,
                -1,
                oldPositionMs,
                -1,
                -1,
                -1,
            ),
        )
        val positionMs = 87L
        val newPositionInfo = spy(
            Player.PositionInfo(
                null,
                -1,
                null,
                -1,
                positionMs,
                -1,
                -1,
                -1,
            ),
        )
        whenever(initialExtendedExoPlayer.repeatMode).thenReturn(Player.REPEAT_MODE_ONE)
        whenever(volumeHelper.getVolume(null)).thenReturn(1.0F)
        whenever(initialExtendedExoPlayer.getPositionSinceEpochMs(positionMs))
            .thenReturn(positionMs)
        val currentPlaybackSession = mock<PlaybackSession.Audio> {
            on { it.startAssetPosition } doReturn 43.0
        }
        val newStreamingSessionId = mock<UUID>()
        val startedPlaybackStatistics = mock<PlaybackStatistics.Success.Started>()
        val preparedPlaybackStatistics = mock<PlaybackStatistics.Success.Prepared.Audio> {
            on { it.toStarted(startTimestampMs) } doReturn startedPlaybackStatistics
        }
        val undeterminedPlaybackStatistics = mock<PlaybackStatistics.Undetermined>()
        whenever(
            undeterminedPlaybackSessionResolver(
                undeterminedPlaybackStatistics,
                playbackInfo,
                emptyMap(),
            ),
        ).thenReturn(preparedPlaybackStatistics)
        val newStreamingSession = mock<StreamingSession.Implicit> {
            on { it.id } doReturn newStreamingSessionId
            on {
                it.createPlaybackSession(playbackInfo, currentForwardingMediaProduct)
            } doReturn currentPlaybackSession
            on {
                createUndeterminedPlaybackStatistics(
                    PlaybackStatistics.IdealStartTimestampMs.Known(startTimestampMs),
                    emptyMap(),
                )
            }.thenReturn(undeterminedPlaybackStatistics)
        }
        whenever(initialExtendedExoPlayer.currentStreamingSession).thenReturn(newStreamingSession)
        val newCurrentPlaybackContext =
            currentPlaybackContext.copy(playbackSessionId = newStreamingSessionId.toString())

        runBlocking {
            launch {
                playbackEngine.onPositionDiscontinuity(
                    eventTime,
                    oldPositionInfo,
                    newPositionInfo,
                    Player.DISCONTINUITY_REASON_AUTO_TRANSITION,
                )
            }
            withTimeout(3000) {
                assertThat(playbackEngine.events.first())
                    .isEqualTo(
                        Event.MediaProductTransition(
                            currentMediaProduct,
                            newCurrentPlaybackContext,
                        ),
                    )
            }
        }

        assertThat(playbackEngine.mediaProduct).isSameAs(currentMediaProduct)
        assertThat(playbackEngine.testNextMediaSource).isNull()
        assertThat(playbackEngine.playbackContext).isEqualTo(newCurrentPlaybackContext)
        assertThat(playbackEngine.reflectionNextPlaybackContext).isNull()
        assertThat(playbackEngine.reflectionCurrentPlaybackStatistics)
            .isSameAs(startedPlaybackStatistics)
        assertThat(playbackEngine.reflectionNextPlaybackStatistics).isNull()
        assertThat(playbackEngine.reflectionCurrentPlaybackSession)
            .isEqualTo(currentPlaybackSession)
        assertThat(playbackEngine.reflectionNextPlaybackSession).isNull()
        verify(initialExtendedExoPlayer).updatePosition(positionMs)
        verify(initialExtendedExoPlayer).onRepeatOne(currentForwardingMediaProduct)
        verify(volumeHelper).getVolume(playbackInfo)
        verify(trueTimeWrapper).currentTimeMillis
        verify(preparedPlaybackStatistics).toStarted(startTimestampMs)
        verify(currentPlaybackSession).startTimestamp = startTimestampMs
        verify(currentPlaybackSession).startAssetPosition = positionMs.toDouble() / 1_000
        verifyNoMoreInteractions(
            startedPlaybackStatistics,
            preparedPlaybackStatistics,
            undeterminedPlaybackStatistics,
            currentPlaybackSession,
            oldPositionInfo,
            newPositionInfo,
        )
    }

    @Test
    fun onPositionDiscontinuityWhenAutoTransitionShouldThrowExceptionForRepeatModeAll() {
        whenever(initialExtendedExoPlayer.repeatMode).thenReturn(Player.REPEAT_MODE_ALL)

        runBlocking {
            launch {
                assertThrows<UnsupportedOperationException> {
                    playbackEngine.onPositionDiscontinuity(
                        mock(),
                        mock(),
                        mock(),
                        Player.DISCONTINUITY_REASON_AUTO_TRANSITION,
                    )
                }
            }
        }

        verify(initialExtendedExoPlayer).updatePosition(any())
    }

    @Suppress("LongMethod")
    @Test
    fun onPositionDiscontinuityDueToSeekShouldCreateStallIfShouldNotStartPlaybackAfterUserAction() {
        val currentPlaybackPositionMs = 5L
        val eventTime = EventTime(
            -1,
            Timeline.EMPTY,
            -1,
            null,
            -1,
            Timeline.EMPTY,
            -1,
            null,
            currentPlaybackPositionMs,
            -1,
        )
        val oldPositionMs = 345678L
        val oldPosition = spy(
            Player.PositionInfo(
                null,
                0,
                null,
                -1,
                oldPositionMs,
                -1L,
                -1,
                -1,
            ),
        )
        val newPositionMs = 44444L
        val newPosition = spy(
            Player.PositionInfo(
                null,
                0,
                null,
                -1,
                newPositionMs,
                -1L,
                -1,
                -1,
            ),
        )
        val extendedExoPlayer = mock<ExtendedExoPlayer>()
        playbackEngine.reflectionExtendedExoPlayer = extendedExoPlayer
        whenever(extendedExoPlayer.shouldStartPlaybackAfterUserAction()) doReturn false
        val currentTimeMills = -80L
        whenever(trueTimeWrapper.currentTimeMillis) doReturn currentTimeMills
        val actions = mock<MutableList<Action>>()
        val currentPlaybackSession = mock<PlaybackSession.Audio> {
            on { it.actions } doReturn actions
        }
        playbackEngine.reflectionCurrentPlaybackSession = currentPlaybackSession

        playbackEngine.onPositionDiscontinuity(
            eventTime,
            oldPosition,
            newPosition,
            Player.DISCONTINUITY_REASON_SEEK,
        )

        verify(extendedExoPlayer).updatePosition(newPositionMs)
        verify(extendedExoPlayer).shouldStartPlaybackAfterUserAction()
        verify(trueTimeWrapper).currentTimeMillis
        verify(currentPlaybackSession).actions
        inOrder(actions).apply {
            verify(actions).add(
                Action(
                    currentTimeMills,
                    oldPositionMs.toDouble() / 1_000,
                    Action.Type.PLAYBACK_STOP,
                ),
            )
            verify(actions).add(
                Action(
                    currentTimeMills,
                    newPositionMs.toDouble() / 1_000,
                    Action.Type.PLAYBACK_START,
                ),
            )
        }
        assertThat(playbackEngine.reflectionCurrentStall).isEqualTo(
            StartedStall(
                Reason.SEEK,
                currentPlaybackPositionMs.toDouble() / 1_000,
                currentTimeMills,
            ),
        )
        verifyNoMoreInteractions(
            oldPosition,
            newPosition,
            extendedExoPlayer,
            actions,
            currentPlaybackSession,
        )
    }

    @Suppress("LongMethod")
    @Test
    fun onPositionDiscontinuityDueToSeekShouldNotCreateStallIfShouldStartPlaybackAfterUserAction() {
        val eventTime = mock<EventTime>()
        val oldPositionMs = 345678L
        val oldPosition = spy(
            Player.PositionInfo(
                null,
                0,
                null,
                -1,
                oldPositionMs,
                -1L,
                -1,
                -1,
            ),
        )
        val newPositionMs = 44444L
        val newPosition = spy(
            Player.PositionInfo(
                null,
                0,
                null,
                -1,
                newPositionMs,
                -1L,
                -1,
                -1,
            ),
        )
        val extendedExoPlayer = mock<ExtendedExoPlayer>()
        playbackEngine.reflectionExtendedExoPlayer = extendedExoPlayer
        whenever(extendedExoPlayer.shouldStartPlaybackAfterUserAction()) doReturn true
        val currentTimeMills = -80L
        whenever(trueTimeWrapper.currentTimeMillis) doReturn currentTimeMills
        val actions = mock<MutableList<Action>>()
        val currentPlaybackSession = mock<PlaybackSession.Audio> {
            on { it.actions } doReturn actions
        }
        playbackEngine.reflectionCurrentPlaybackSession = currentPlaybackSession

        playbackEngine.onPositionDiscontinuity(
            eventTime,
            oldPosition,
            newPosition,
            Player.DISCONTINUITY_REASON_SEEK,
        )

        verify(extendedExoPlayer).updatePosition(newPositionMs)
        verify(extendedExoPlayer).shouldStartPlaybackAfterUserAction()
        verify(trueTimeWrapper).currentTimeMillis
        verify(currentPlaybackSession).actions
        inOrder(actions).apply {
            verify(actions).add(
                Action(
                    currentTimeMills,
                    oldPositionMs.toDouble() / 1_000,
                    Action.Type.PLAYBACK_STOP,
                ),
            )
            verify(actions).add(
                Action(
                    currentTimeMills,
                    newPositionMs.toDouble() / 1_000,
                    Action.Type.PLAYBACK_START,
                ),
            )
        }
        assertThat(playbackEngine.reflectionCurrentStall).isNull()
        verifyNoMoreInteractions(
            eventTime,
            oldPosition,
            newPosition,
            extendedExoPlayer,
            actions,
            currentPlaybackSession,
        )
    }

    @Test
    fun onTimelineChangedShouldDoNothingIfDurationIsUnset() {
        val duration = C.TIME_UNSET
        val forwardingMediaProduct = mock<ForwardingMediaProduct<MediaProduct>>()
        val mediaSource = mock<PlaybackInfoMediaSource> {
            on { it.forwardingMediaProduct } doReturn forwardingMediaProduct
        }
        playbackEngine.testMediaSource = mediaSource
        val mediaItem = MediaItem.Builder()
            .setMediaId(forwardingMediaProduct.hashCode().toString())
            .build()
        val window = Timeline.Window()
            .set(
                Unit,
                mediaItem,
                null,
                -1L,
                -1L,
                -1L,
                false,
                false,
                null,
                -1L,
                duration,
                -1,
                -1,
                -1L,
            )
        val windowIndex = 0
        val timeline = mock<Timeline> {
            on { it.windowCount } doReturn 1
            on { it.getWindow(eq(windowIndex), any()) } doReturn window
        }
        val eventTime = EventTime(
            -1L,
            timeline,
            windowIndex,
            null,
            -1L,
            Timeline.EMPTY,
            windowIndex,
            null,
            -1L,
            -1L,
        )

        playbackEngine.onTimelineChanged(eventTime, Player.TIMELINE_CHANGE_REASON_SOURCE_UPDATE)

        verify(timeline, atLeastOnce()).getWindow(eq(windowIndex), any())
        verify(timeline).windowCount
        verifyNoMoreInteractions(initialExtendedExoPlayer, timeline)
        verifyNoInteractions(coroutineScope, events)
    }

    @Test
    fun onTimelineChangedShouldDoNothingIfDurationHasNotChanged() {
        val forwardingMediaProduct = mock<ForwardingMediaProduct<MediaProduct>>()
        val mediaSource = mock<PlaybackInfoMediaSource> {
            on { it.forwardingMediaProduct } doReturn forwardingMediaProduct
        }
        playbackEngine.testMediaSource = mediaSource
        val duration = 123.milliseconds
        val playbackContext = mock<PlaybackContext.Track> {
            on { it.duration } doReturn duration.toDouble(DurationUnit.SECONDS).toFloat()
        }
        playbackEngine.reflectionPlaybackContext = playbackContext
        val mediaItem = MediaItem.Builder()
            .setMediaId(forwardingMediaProduct.hashCode().toString())
            .build()
        val window = Timeline.Window()
            .set(
                Unit,
                mediaItem,
                null,
                -1L,
                -1L,
                -1L,
                false,
                false,
                null,
                -1L,
                duration.toLong(DurationUnit.MICROSECONDS),
                -1,
                -1,
                -1L,
            )
        val windowIndex = 0
        val timeline = mock<Timeline> {
            on { it.windowCount } doReturn 1
            on { it.getWindow(eq(windowIndex), any()) } doReturn window
        }
        val eventTime = EventTime(
            -1L,
            timeline,
            windowIndex,
            null,
            -1L,
            Timeline.EMPTY,
            -1,
            null,
            -1L,
            -1L,
        )

        playbackEngine.onTimelineChanged(eventTime, Player.TIMELINE_CHANGE_REASON_SOURCE_UPDATE)

        assertThat(playbackEngine.playbackContext).isSameAs(playbackContext)
        verify(timeline, atLeastOnce()).windowCount
        verify(timeline, atLeastOnce()).getWindow(eq(windowIndex), any())
        verifyNoMoreInteractions(initialExtendedExoPlayer, timeline)
        verifyNoInteractions(coroutineScope, events)
    }

    @Suppress("LongMethod")
    @Test
    fun onTimelineChangedShouldEmitMediaProductTransition() {
        val duration = 24.seconds
        val mediaProduct = mock<MediaProduct>()
        val forwardingMediaProduct = mock<ForwardingMediaProduct<MediaProduct>> {
            on { it.delegate } doReturn mediaProduct
        }
        val mediaSource = mock<PlaybackInfoMediaSource> {
            on { it.forwardingMediaProduct } doReturn forwardingMediaProduct
        }
        val playbackContext = PlaybackContext.Track(
            AudioMode.STEREO,
            AudioQuality.HIGH,
            320_000,
            16,
            "aac",
            44100,
            "123",
            AssetPresentation.FULL,
            C.TIME_UNSET.toFloat() / 1_000,
            AssetSource.ONLINE,
            "123-abc",
            "123-abc",
        )
        val updatedPlaybackContext =
            playbackContext.copy(duration = duration.toDouble(DurationUnit.SECONDS).toFloat())
        playbackEngine.testMediaSource = mediaSource
        playbackEngine.reflectionPlaybackContext = playbackContext
        val mediaItem = MediaItem.Builder()
            .setMediaId(forwardingMediaProduct.hashCode().toString())
            .build()
        val window = Timeline.Window()
            .set(
                Unit,
                mediaItem,
                null,
                -1L,
                -1L,
                -1L,
                false,
                false,
                null,
                -1L,
                duration.toLong(DurationUnit.MICROSECONDS),
                -1,
                -1,
                -1L,
            )
        val windowIndex = 0
        val timeline = mock<Timeline> {
            on { it.windowCount } doReturn 1
            on { it.getWindow(eq(windowIndex), any()) } doReturn window
        }
        val eventTime = EventTime(
            -1L,
            timeline,
            windowIndex,
            null,
            -1L,
            Timeline.EMPTY,
            -1,
            null,
            -1L,
            -1L,
        )

        runBlocking {
            launch {
                playbackEngine.onTimelineChanged(
                    eventTime,
                    Player.TIMELINE_CHANGE_REASON_SOURCE_UPDATE,
                )
            }
            withTimeout(3000) {
                assertThat(playbackEngine.events.first())
                    .isEqualTo(
                        Event.MediaProductTransition(
                            forwardingMediaProduct.delegate,
                            updatedPlaybackContext,
                        ),
                    )
            }
        }

        assertThat(playbackEngine.mediaProduct).isSameAs(mediaProduct)
        assertThat(playbackEngine.playbackContext).isEqualTo(updatedPlaybackContext)
        verify(timeline, atLeastOnce()).getWindow(eq(windowIndex), any())
        verify(timeline).windowCount
        verifyNoMoreInteractions(initialExtendedExoPlayer, timeline)
    }

    @Test
    fun onTimelineChangedShouldCheckForDjUpdatesIfBroadcast() {
        playbackEngine.testMediaSource = mock {
            on { it.forwardingMediaProduct }
                .thenReturn(ForwardingMediaProduct(MediaProduct(ProductType.BROADCAST, "123")))
        }
        val playbackSession = mock<PlaybackSession.Audio>()
        playbackEngine.reflectionCurrentPlaybackSession = playbackSession
        val currentPositionSinceEpochMs = 123L
        whenever(initialExtendedExoPlayer.currentPositionSinceEpochMs)
            .thenReturn(currentPositionSinceEpochMs)
        val tags = HlsTags.TAGS_WITH_SINGLE_DATE_RANGE
        val hlsMediaPlaylist = mock<HlsMediaPlaylist>()
        hlsMediaPlaylist.reflectionSetTags(tags)
        val hlsManifest = mock<HlsManifest>()
        hlsManifest.reflectionSetMediaPlaylist(hlsMediaPlaylist)
        whenever(initialExtendedExoPlayer.currentManifest).thenReturn(hlsManifest)
        val duration = 24000L
        val window = mock<Timeline.Window> {
            on { it.durationMs } doReturn duration
        }
        val currentWindowIndex = 83
        val currentTimeline = mock<Timeline> {
            on { it.getWindow(eq(currentWindowIndex), any()) } doReturn window
        }
        val eventTime = EventTime(
            -1L,
            Timeline.EMPTY,
            -1,
            null,
            -1L,
            currentTimeline,
            currentWindowIndex,
            null,
            -1L,
            -1L,
        )

        playbackEngine.onTimelineChanged(eventTime, Player.TIMELINE_CHANGE_REASON_SOURCE_UPDATE)

        verify(initialExtendedExoPlayer).currentManifest
        verify(initialExtendedExoPlayer).currentPositionSinceEpochMs
        verify(playbackSession).startAssetPosition = currentPositionSinceEpochMs.toDouble() / 1_000
        verify(djSessionManager).checkForUpdates(tags, currentPositionSinceEpochMs)
        verifyNoMoreInteractions(initialExtendedExoPlayer)
        verifyNoInteractions(coroutineScope, events)
    }

    @Test
    fun onAudioPositionAdvancingShouldDoNothingIfCurrentPlaybackStatisticsAreStarted() {
        val forwardingMediaProduct = mock<ForwardingMediaProduct<MediaProduct>>()
        val mediaSource = mock<PlaybackInfoMediaSource> {
            on { it.forwardingMediaProduct } doReturn forwardingMediaProduct
        }
        playbackEngine.testMediaSource = mediaSource
        val mediaItem = MediaItem.Builder()
            .setMediaId(forwardingMediaProduct.hashCode().toString())
            .build()
        val windowZero = Timeline.Window()
            .set(
                Unit,
                mediaItem,
                null,
                -1L,
                -1L,
                -1L,
                false,
                false,
                null,
                -1L,
                -1L,
                -1,
                -1,
                -1L,
            )
        val timeline = mock<Timeline> {
            on { it.getWindow(eq(0), any()) } doReturn windowZero
        }
        val eventTime = EventTime(
            -1,
            timeline,
            0,
            null,
            -8,
            Timeline.EMPTY,
            -1,
            null,
            -1,
            -1,
        )
        val currentPlaybackStatistics = mock<PlaybackStatistics.Success.Started>()
        playbackEngine.reflectionCurrentPlaybackStatistics = currentPlaybackStatistics

        playbackEngine.onAudioPositionAdvancing(eventTime, -7)

        assertThat(playbackEngine.reflectionCurrentPlaybackStatistics)
            .isSameAs(currentPlaybackStatistics)
        verifyNoMoreInteractions(currentPlaybackStatistics)
    }

    @Suppress("LongMethod")
    @Test
    fun onAudioPositionAdvancingShouldRecordStartTimestampIfCurrentPlaybackStatisticsArePrepared() {
        val forwardingMediaProduct = mock<ForwardingMediaProduct<MediaProduct>>()
        val mediaSource = mock<PlaybackInfoMediaSource> {
            on { it.forwardingMediaProduct } doReturn forwardingMediaProduct
        }
        playbackEngine.testMediaSource = mediaSource
        val mediaItem = MediaItem.Builder()
            .setMediaId(forwardingMediaProduct.hashCode().toString())
            .build()
        val windowZero = Timeline.Window()
            .set(
                Unit,
                mediaItem,
                null,
                -1L,
                -1L,
                -1L,
                false,
                false,
                null,
                -1L,
                -1L,
                -1,
                -1,
                -1L,
            )
        val timeline = mock<Timeline> {
            on { it.windowCount } doReturn 1
            on { it.getWindow(eq(0), any()) } doReturn windowZero
        }
        val eventTime = EventTime(
            -1,
            timeline,
            0,
            null,
            -123,
            Timeline.EMPTY,
            -1,
            null,
            -1,
            -1,
        )
        val currentTimeMillis = 5L
        whenever(trueTimeWrapper.currentTimeMillis) doReturn currentTimeMillis
        val startedPlaybackStatistics = mock<PlaybackStatistics.Success.Started>()
        val preparedPlaybackStatistics = mock<PlaybackStatistics.Success.Prepared.Audio> {
            on { toStarted(currentTimeMillis) } doReturn startedPlaybackStatistics
        }
        playbackEngine.reflectionCurrentPlaybackStatistics = preparedPlaybackStatistics
        val currentPlaybackSession = mock<PlaybackSession.Audio>()
        playbackEngine.reflectionCurrentPlaybackSession = currentPlaybackSession

        playbackEngine.onAudioPositionAdvancing(eventTime, Long.MAX_VALUE)

        verify(trueTimeWrapper).currentTimeMillis
        verify(preparedPlaybackStatistics).toStarted(currentTimeMillis)
        verify(currentPlaybackSession).startTimestamp = currentTimeMillis
        assertThat(playbackEngine.reflectionCurrentPlaybackStatistics)
            .isSameAs(startedPlaybackStatistics)
        verifyNoMoreInteractions(
            startedPlaybackStatistics,
            preparedPlaybackStatistics,
            currentPlaybackSession,
        )
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1])
    fun testAudioTrackAdaptationOnValidWindowIndex(targetWindowIndex: Int) {
        val decoderReuseEvaluation = mock<DecoderReuseEvaluation>()

        testTrackAdaptationOnValidWindowIndex(targetWindowIndex) { eventTime, format ->
            onAudioInputFormatChanged(eventTime, format, decoderReuseEvaluation)
        }

        verifyNoMoreInteractions(decoderReuseEvaluation)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1])
    fun testVideoTrackAdaptationOnValidWindowIndex(targetWindowIndex: Int) {
        val decoderReuseEvaluation = mock<DecoderReuseEvaluation>()

        testTrackAdaptationOnValidWindowIndex(targetWindowIndex) { eventTime, format ->
            onVideoInputFormatChanged(eventTime, format, decoderReuseEvaluation)
        }

        verifyNoMoreInteractions(decoderReuseEvaluation)
    }

    @Suppress("LongMethod", "ThrowsCount")
    private fun testTrackAdaptationOnValidWindowIndex(
        targetWindowIndex: Int,
        call: ExoPlayerPlaybackEngine.(EventTime, Format) -> Unit,
    ) {
        val eventPlaybackPositionMs = 0L
        val eventTime = EventTime(
            -1,
            Timeline.EMPTY,
            targetWindowIndex,
            null,
            eventPlaybackPositionMs,
            Timeline.EMPTY,
            -1,
            null,
            -1,
            -1,
        )
        val currentTimeMillis = -38L
        whenever(trueTimeWrapper.currentTimeMillis) doReturn currentTimeMillis
        val sampleMimeType = "sampleMimeType"
        val codecs = "codecs"
        val bitrate = Int.MAX_VALUE
        val width = 0
        val height = Int.MIN_VALUE
        val format = Format.Builder()
            .setSampleMimeType(sampleMimeType)
            .setCodecs(codecs)
            .setPeakBitrate(bitrate)
            .setWidth(width)
            .setHeight(height)
            .build()
        val currentPlaybackStatistics = mock<PlaybackStatistics.Success.Prepared.Audio>()
        val nextPlaybackStatistics = mock<PlaybackStatistics.Undetermined>()
        when (targetWindowIndex) {
            0 -> playbackEngine.reflectionCurrentPlaybackStatistics = currentPlaybackStatistics
            1 -> playbackEngine.reflectionNextPlaybackStatistics = nextPlaybackStatistics
        }
        val targetPlaybackStatistics = when (targetWindowIndex) {
            0 -> currentPlaybackStatistics
            1 -> nextPlaybackStatistics
            else -> throw IllegalArgumentException("Invalid index $targetWindowIndex")
        }
        val targetAdaptation = Adaptation(
            eventPlaybackPositionMs.toDouble() / 1_000,
            currentTimeMillis,
            sampleMimeType,
            codecs,
            bitrate,
            width,
            height,
        )
        val updatedPlaybackStatistics = when (targetWindowIndex) {
            0 -> mock<PlaybackStatistics.Success.Prepared.Audio>()
            1 -> mock<PlaybackStatistics.Undetermined>()
            else -> throw IllegalArgumentException("Invalid index $targetWindowIndex")
        }
        whenever(targetPlaybackStatistics + targetAdaptation) doReturn updatedPlaybackStatistics

        playbackEngine.call(eventTime, format)

        verify(trueTimeWrapper).currentTimeMillis
        verify(targetPlaybackStatistics) + targetAdaptation
        assertThat(
            when (targetWindowIndex) {
                0 -> playbackEngine.reflectionCurrentPlaybackStatistics
                1 -> playbackEngine.reflectionNextPlaybackStatistics
                else ->
                    throw IllegalArgumentException("Expected valid index, found $targetWindowIndex")
            },
        ).isSameAs(updatedPlaybackStatistics)
        verifyNoMoreInteractions(targetPlaybackStatistics, updatedPlaybackStatistics)
    }

    @Test
    fun testAudioTrackAdaptationOnCurrentWindowIndexWithInvalidPosition() {
        val decoderReuseEvaluation = mock<DecoderReuseEvaluation>()

        testTrackAdaptationOnCurrentWindowIndexWithInvalidPosition { eventTime, format ->
            onAudioInputFormatChanged(eventTime, format, decoderReuseEvaluation)
        }

        verifyNoMoreInteractions(decoderReuseEvaluation)
    }

    @Test
    fun testVideoTrackAdaptationOnCurrentWindowIndexWithInvalidPosition() {
        val decoderReuseEvaluation = mock<DecoderReuseEvaluation>()

        testTrackAdaptationOnCurrentWindowIndexWithInvalidPosition { eventTime, format ->
            onVideoInputFormatChanged(eventTime, format, decoderReuseEvaluation)
        }

        verifyNoMoreInteractions(decoderReuseEvaluation)
    }

    private fun testTrackAdaptationOnCurrentWindowIndexWithInvalidPosition(
        call: ExoPlayerPlaybackEngine.(EventTime, Format) -> Unit,
    ) {
        val eventPlaybackPositionMs = 123_456L
        val forwardingMediaProduct = mock<ForwardingMediaProduct<MediaProduct>>()
        val mediaSource = mock<PlaybackInfoMediaSource> {
            on { it.forwardingMediaProduct } doReturn forwardingMediaProduct
        }
        playbackEngine.testMediaSource = mediaSource
        val mediaItem = MediaItem.Builder()
            .setMediaId(forwardingMediaProduct.hashCode().toString())
            .build()
        val windowZero = Timeline.Window()
            .set(
                Unit,
                mediaItem,
                null,
                -1L,
                -1L,
                -1L,
                false,
                false,
                null,
                -1L,
                -1L,
                -1,
                -1,
                -1L,
            )
        val timeline = mock<Timeline> {
            on { it.getWindow(eq(0), any()) } doReturn windowZero
        }
        val eventTime = EventTime(
            -1,
            timeline,
            0,
            null,
            eventPlaybackPositionMs,
            Timeline.EMPTY,
            -1,
            null,
            -1,
            -1,
        )

        playbackEngine.call(eventTime, mock())
    }

    @Test
    fun testAudioTrackAdaptationOnInvalidWindowIndex() {
        val decoderReuseEvaluation = mock<DecoderReuseEvaluation>()

        testTrackAdaptationOnInvalidWindowIndex { eventTime, format ->
            onAudioInputFormatChanged(eventTime, format, decoderReuseEvaluation)
        }

        verifyNoMoreInteractions(decoderReuseEvaluation)
    }

    @Test
    fun testVideoTrackAdaptationOnInvalidWindowIndex() {
        val decoderReuseEvaluation = mock<DecoderReuseEvaluation>()

        testTrackAdaptationOnInvalidWindowIndex { eventTime, format ->
            onVideoInputFormatChanged(eventTime, format, decoderReuseEvaluation)
        }

        verifyNoMoreInteractions(decoderReuseEvaluation)
    }

    private fun testTrackAdaptationOnInvalidWindowIndex(
        call: ExoPlayerPlaybackEngine.(EventTime, Format) -> Unit,
    ) {
        val format = mock<Format>()

        assertThrows<IllegalStateException> {
            playbackEngine.call(
                EventTime(
                    -1,
                    Timeline.EMPTY,
                    -2,
                    null,
                    -3,
                    Timeline.EMPTY,
                    -4,
                    null,
                    -5,
                    -6,
                ),
                format,
            )
        }

        assertThat(playbackEngine.reflectionCurrentPlaybackStatistics).isNull()
        assertThat(playbackEngine.reflectionNextPlaybackStatistics).isNull()
    }

    @Test
    fun setCurrentStallCompletesPreviousCurrentStallIfItWasNotNull() {
        val currentPlaybackStatistics = mock<PlaybackStatistics.Success.Started>()
        playbackEngine.reflectionCurrentPlaybackStatistics = currentPlaybackStatistics
        val reason = mock<Reason>()
        val assetPositionSeconds = 0.0
        val startTimestamp = -1L
        val initialCurrentStartedStall = mock<StartedStall> {
            on { it.reason } doReturn reason
            on { it.assetPositionSeconds } doReturn assetPositionSeconds
            on { it.startTimestamp } doReturn startTimestamp
        }
        playbackEngine.reflectionCurrentStall = initialCurrentStartedStall
        val currentTimeMillis = 2L
        whenever(trueTimeWrapper.currentTimeMillis) doReturn currentTimeMillis
        val completedStall = Stall(
            reason,
            assetPositionSeconds,
            startTimestamp,
            currentTimeMillis,
        )
        val updatedPlaybackStatistics = mock<PlaybackStatistics.Success.Started>()
        whenever(currentPlaybackStatistics + completedStall) doReturn updatedPlaybackStatistics

        playbackEngine.reflectionSetDelegatedCurrentStall(null)

        verify(initialCurrentStartedStall).reason
        verify(initialCurrentStartedStall).assetPositionSeconds
        verify(initialCurrentStartedStall).startTimestamp
        verify(trueTimeWrapper).currentTimeMillis
        verify(currentPlaybackStatistics) + completedStall
        assertThat(playbackEngine.reflectionCurrentStall).isNull()
        assertThat(playbackEngine.reflectionCurrentPlaybackStatistics)
            .isSameAs(updatedPlaybackStatistics)
        verifyNoMoreInteractions(
            currentPlaybackStatistics,
            initialCurrentStartedStall,
            reason,
            updatedPlaybackStatistics,
        )
    }

    @Test
    fun setCurrentStallDoesNothingIfPreviousCurrentStallIfItWasNotNull() {
        val newCurrentStall = mock<StartedStall>()

        playbackEngine.reflectionSetDelegatedCurrentStall(newCurrentStall)

        assertThat(playbackEngine.reflectionCurrentStall).isSameAs(newCurrentStall)
        verifyNoMoreInteractions(newCurrentStall)
    }

    @Test
    fun onAudioUnderrunShouldCreateUnexpectedStall() {
        val currentPlaybackPositionMs = 5123132515
        val eventTime = EventTime(
            -1,
            Timeline.EMPTY,
            -1,
            null,
            -1,
            Timeline.EMPTY,
            -1,
            null,
            currentPlaybackPositionMs,
            -1,
        )
        val currentTimeMillis = Long.MAX_VALUE
        whenever(trueTimeWrapper.currentTimeMillis) doReturn currentTimeMillis

        playbackEngine.onAudioUnderrun(eventTime, -1, 0, Long.MIN_VALUE)

        assertThat(playbackEngine.reflectionCurrentStall).isEqualTo(
            StartedStall(
                Reason.UNEXPECTED,
                currentPlaybackPositionMs.toDouble() / 1_000,
                currentTimeMillis,
            ),
        )
    }

    @Test
    fun onVideoSizeChanged() {
        val videoSurfaceView = mock<AspectRatioAdjustingSurfaceView>()
        val surfaceHolder = mock<SynchronousSurfaceHolder>()
        playbackEngine.reflectionVideoSurfaceViewAndSurfaceHolder =
            videoSurfaceView to surfaceHolder
        val eventTime = mock<EventTime>()
        val width = -5
        val height = -3
        val videoSize = VideoSize(width, height)

        playbackEngine.onVideoSizeChanged(eventTime, videoSize)

        verify(videoSurfaceView).suggestedVideoDimen =
            AspectRatioAdjustingSurfaceView.SuggestedDimensions(width, height)
        verifyNoMoreInteractions(eventTime, videoSurfaceView, surfaceHolder)
    }

    companion object {

        @JvmStatic
        @Suppress("UnusedPrivateMember")
        private fun nextMediaProducts() = setOf(
            Arguments.of(MediaProduct(ProductType.TRACK, "2")),
            Arguments.of(MediaProduct(ProductType.VIDEO, "3")),
        )
    }
}
