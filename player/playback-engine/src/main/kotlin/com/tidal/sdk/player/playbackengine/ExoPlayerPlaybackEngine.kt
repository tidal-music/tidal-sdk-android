package com.tidal.sdk.player.playbackengine

import android.os.Handler
import android.view.SurfaceView
import androidx.annotation.RestrictTo
import androidx.media3.common.C
import androidx.media3.common.Format
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.common.VideoSize
import androidx.media3.exoplayer.DecoderReuseEvaluation
import androidx.media3.exoplayer.analytics.AnalyticsListener
import androidx.media3.exoplayer.analytics.AnalyticsListener.EventTime
import androidx.media3.exoplayer.hls.HlsManifest
import androidx.media3.exoplayer.source.ForwardingTimeline
import com.tidal.networktime.SNTPClient
import com.tidal.sdk.player.common.ForwardingMediaProduct
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.LoudnessNormalizationMode
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.common.ntpOrLocalClockTime
import com.tidal.sdk.player.events.EventReporter
import com.tidal.sdk.player.events.model.AudioPlaybackSession
import com.tidal.sdk.player.events.model.AudioPlaybackStatistics
import com.tidal.sdk.player.events.model.BroadcastPlaybackSession
import com.tidal.sdk.player.events.model.BroadcastPlaybackStatistics
import com.tidal.sdk.player.events.model.EndReason
import com.tidal.sdk.player.events.model.NotStartedPlaybackStatistics
import com.tidal.sdk.player.events.model.PlaybackSession.Payload.Action
import com.tidal.sdk.player.events.model.PlaybackStatistics.Payload.Adaptation
import com.tidal.sdk.player.events.model.PlaybackStatistics.Payload.Stall
import com.tidal.sdk.player.events.model.UCPlaybackSession
import com.tidal.sdk.player.events.model.UCPlaybackStatistics
import com.tidal.sdk.player.events.model.VideoPlaybackSession
import com.tidal.sdk.player.events.model.VideoPlaybackStatistics
import com.tidal.sdk.player.playbackengine.audiomode.AudioModeRepository
import com.tidal.sdk.player.playbackengine.dj.DjSessionManager
import com.tidal.sdk.player.playbackengine.dj.DjSessionStatus
import com.tidal.sdk.player.playbackengine.error.ErrorHandler
import com.tidal.sdk.player.playbackengine.mediasource.PlaybackInfoMediaSource
import com.tidal.sdk.player.playbackengine.mediasource.loadable.PlaybackInfoFetchException
import com.tidal.sdk.player.playbackengine.mediasource.loadable.PlaybackInfoListener
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.PlaybackSession
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.PlaybackStatistics
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.StartedStall
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.StreamingSession
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.UndeterminedPlaybackSessionResolver
import com.tidal.sdk.player.playbackengine.model.DelayedMediaProductTransition
import com.tidal.sdk.player.playbackengine.model.Event
import com.tidal.sdk.player.playbackengine.model.PlaybackContext
import com.tidal.sdk.player.playbackengine.model.PlaybackState
import com.tidal.sdk.player.playbackengine.outputdevice.OutputDevice
import com.tidal.sdk.player.playbackengine.outputdevice.OutputDeviceManager
import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayerFactory
import com.tidal.sdk.player.playbackengine.quality.AudioQualityRepository
import com.tidal.sdk.player.playbackengine.util.SynchronousSurfaceHolder
import com.tidal.sdk.player.playbackengine.view.AspectRatioAdjustingSurfaceView
import com.tidal.sdk.player.playbackengine.volume.VolumeHelper
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import com.tidal.sdk.player.streamingprivileges.StreamingPrivileges
import com.tidal.sdk.player.streamingprivileges.StreamingPrivilegesListener
import kotlin.properties.Delegates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.transformWhile
import kotlinx.coroutines.launch

private const val MS_IN_SECOND = 1000L

/**
 * The default implementation of [PlaybackEngine] that will use ExoPlayer to play media.
 */
@Suppress("TooManyFunctions", "LargeClass", "LongParameterList")
internal class ExoPlayerPlaybackEngine(
    private val coroutineScope: CoroutineScope,
    private val extendedExoPlayerFactory: ExtendedExoPlayerFactory,
    private val internalHandler: Handler,
    private val eventSink: MutableSharedFlow<Event>,
    private val synchronousSurfaceHolderFactory: SynchronousSurfaceHolder.Factory,
    private val streamingPrivileges: StreamingPrivileges,
    private val playbackContextFactory: PlaybackContextFactory,
    private val audioQualityRepository: AudioQualityRepository,
    private val audioModeRepository: AudioModeRepository,
    private val volumeHelper: VolumeHelper,
    private val sntpClient: SNTPClient,
    private val eventReporter: EventReporter,
    private val errorHandler: ErrorHandler,
    private val djSessionManager: DjSessionManager,
    private val undeterminedPlaybackSessionResolver: UndeterminedPlaybackSessionResolver,
    private val outputDeviceManager: OutputDeviceManager,
) : PlaybackEngine,
    StreamingPrivilegesListener,
    PlaybackInfoListener,
    AnalyticsListener,
    DjSessionManager.Listener {

    override val mediaProduct: MediaProduct?
        get() = forwardingMediaProduct?.delegate
    private val forwardingMediaProduct: ForwardingMediaProduct<MediaProduct>?
        get() = mediaSource?.forwardingMediaProduct

    /**
     * Holds the next [ForwardingMediaProduct]. May be null if not set.
     */
    private val nextForwardingMediaProduct: ForwardingMediaProduct<MediaProduct>?
        get() = nextMediaSource?.forwardingMediaProduct

    override var playbackContext: PlaybackContext? = null
        private set

    /**
     * Holds the next [playbackContext]. May be null if not set.
     */
    private var nextPlaybackContext: PlaybackContext? = null

    private var mediaSource: PlaybackInfoMediaSource? by Delegates.observable(null) { _, _, new ->
        currentPlaybackStatistics = null
        playbackContext = null
        nextMediaSource = null
        currentPlaybackSession = null
        djSessionManager.cleanUp()
        if (new?.forwardingMediaProduct?.productType == ProductType.BROADCAST) {
            djSessionManager.listener = this
        }
    }

    /**
     * Holds the next [PlaybackInfoMediaSource]. May be null if not set.
     */
    private var nextMediaSource: PlaybackInfoMediaSource? by Delegates.observable(null) { _, _, _ ->
        nextPlaybackStatistics = null
        nextPlaybackContext = null
        nextPlaybackSession = null
        delayedMediaProductTransition = null
    }

    private var delayedMediaProductTransition: DelayedMediaProductTransition? = null

    override var playbackState by Delegates.observable(PlaybackState.IDLE) { _, value, newValue ->
        if (newValue == value) {
            return@observable
        }
        coroutineScope.launch { eventSink.emit(Event.PlaybackStateChange(newValue)) }
        videoSurfaceView?.refreshKeepScreenOnBasedOnPlaybackState()
        if (newValue == PlaybackState.PLAYING) {
            streamingPrivileges.setStreamingPrivilegesListener(this)
            streamingPrivileges.acquireStreamingPrivileges()
        }
    }
        private set

    override val events: Flow<Event> = eventSink.transformWhile {
        emit(it)
        it !is Event.Release
    }

    override val assetPosition: Float
        get() = extendedExoPlayer.currentPositionMs.toFloat() / MS_IN_SECOND

    override val outputDevice: OutputDevice
        get() = outputDeviceManager.outputDevice

    override var streamingWifiAudioQuality: AudioQuality
        get() = audioQualityRepository.streamingWifiAudioQuality
        set(value) {
            audioQualityRepository.streamingWifiAudioQuality = value
        }

    override var streamingCellularAudioQuality: AudioQuality
        get() = audioQualityRepository.streamingCellularAudioQuality
        set(value) {
            audioQualityRepository.streamingCellularAudioQuality = value
        }

    override var loudnessNormalizationMode: LoudnessNormalizationMode
        get() = volumeHelper.loudnessNormalizationMode
        set(value) {
            volumeHelper.loudnessNormalizationMode = value
            updatePlayerVolume()
        }

    override var loudnessNormalizationPreAmp: Int
        get() = volumeHelper.loudnessNormalizationPreAmp
        set(value) {
            volumeHelper.loudnessNormalizationPreAmp = value
            updatePlayerVolume()
        }

    override var immersiveAudio: Boolean
        get() = audioModeRepository.immersiveAudio
        set(value) {
            audioModeRepository.immersiveAudio = value
        }

    private var extendedExoPlayer by Delegates.observable(
        extendedExoPlayerFactory.create(this, this),
    ) { _, oldValue, newValue ->
        videoSurfaceViewAndSurfaceHolder?.second?.let {
            oldValue.clearVideoSurfaceHolder(it)
            newValue.setVideoSurfaceHolder(it)
        }
    }

    override var videoSurfaceView: AspectRatioAdjustingSurfaceView?
        set(value) {
            if (value === videoSurfaceView) {
                return
            }
            val currentView = videoSurfaceViewAndSurfaceHolder?.first
            currentView?.post { currentView.keepScreenOn = false }
            val currentSurfaceHolder = videoSurfaceViewAndSurfaceHolder?.second
            if (currentSurfaceHolder != null) {
                extendedExoPlayer.clearVideoSurfaceHolder(currentSurfaceHolder)
            }
            if (value != null) {
                value.refreshKeepScreenOnBasedOnPlaybackState()
                val synchronousSurfaceHolder = synchronousSurfaceHolderFactory.create(value.holder)
                extendedExoPlayer.setVideoSurfaceHolder(synchronousSurfaceHolder)
                videoSurfaceViewAndSurfaceHolder = value to synchronousSurfaceHolder
                extendedExoPlayer.videoFormat?.let {
                    value.suggestedVideoDimen = AspectRatioAdjustingSurfaceView.SuggestedDimensions(
                        it.width,
                        it.height,
                    )
                }
            }
        }
        get() = videoSurfaceViewAndSurfaceHolder?.first

    private var videoSurfaceViewAndSurfaceHolder:
        Pair<AspectRatioAdjustingSurfaceView, SynchronousSurfaceHolder>? = null

    private var currentPlaybackStatistics: PlaybackStatistics? by
        Delegates.observable(null) { _, _, _ ->
            currentStall = null
        }

    private var nextPlaybackStatistics: PlaybackStatistics.Undetermined? = null

    private var currentStall: StartedStall? by Delegates.observable(null) { _, oldValue, _ ->
        oldValue?.complete()
    }

    private var currentPlaybackSession: PlaybackSession? = null

    private var nextPlaybackSession: PlaybackSession? = null

    var playerVolume: Float
        get() = extendedExoPlayer.volume
        set(value) {
            extendedExoPlayer.volume = value
        }

    private val isOperating: Boolean
        get() = playbackState in listOf(
            PlaybackState.PLAYING,
            PlaybackState.NOT_PLAYING,
            PlaybackState.STALLED,
        )

    init {
        outputDeviceManager.start {
            coroutineScope.launch { eventSink.emit(Event.OutputDeviceUpdated(it)) }
        }
    }

    override fun load(mediaProduct: MediaProduct) {
        val positionInSeconds =
            if (this.forwardingMediaProduct?.productType == ProductType.BROADCAST) {
                extendedExoPlayer.currentPositionSinceEpochMs
            } else {
                extendedExoPlayer.currentPositionMs
            }.toDouble() / MS_IN_SECOND
        reportEnd(EndReason.OTHER, endPositionSeconds = positionInSeconds)
        playbackState = PlaybackState.STALLED

        mediaSource = extendedExoPlayer.load(ForwardingMediaProduct(mediaProduct))
    }

    override fun setNext(mediaProduct: MediaProduct?) {
        if (!isOperating) return

        nextMediaSource =
            extendedExoPlayer.setNext(mediaProduct?.let { ForwardingMediaProduct(it) })
    }

    override fun play() {
        if (!isOperating) return
        val readCurrentPlaybackStatistics = currentPlaybackStatistics
        if (readCurrentPlaybackStatistics == null) {
            currentPlaybackStatistics =
                extendedExoPlayer.currentStreamingSession!!
                    .createUndeterminedPlaybackStatistics(
                        PlaybackStatistics.IdealStartTimestampMs.Known(
                            sntpClient.ntpOrLocalClockTime.inWholeMilliseconds,
                        ),
                    )
        } else if (
            readCurrentPlaybackStatistics is PlaybackStatistics.Undetermined &&
            readCurrentPlaybackStatistics.idealStartTimestampMs
            is PlaybackStatistics.IdealStartTimestampMs.Known
        ) {
            currentPlaybackStatistics = readCurrentPlaybackStatistics.copy(
                idealStartTimestampMs = PlaybackStatistics.IdealStartTimestampMs.Known(
                    sntpClient.ntpOrLocalClockTime.inWholeMilliseconds,
                ),
            )
        }
        extendedExoPlayer.play()
    }

    override fun pause() {
        if (!isOperating) return
        extendedExoPlayer.pause()
    }

    override fun seek(time: Float) {
        if (!isOperating) return
        extendedExoPlayer.seekTo(time.toLong())
    }

    override fun skipToNext() {
        if (!isOperating) return
        extendedExoPlayer.seekToNextMediaItem()
    }

    override fun setRepeatOne(enable: Boolean) {
        if (enable) {
            extendedExoPlayer.repeatMode = Player.REPEAT_MODE_ONE
        } else {
            extendedExoPlayer.repeatMode = Player.REPEAT_MODE_OFF
        }
    }

    override fun reset() {
        val positionInSeconds = if (forwardingMediaProduct?.productType == ProductType.BROADCAST) {
            extendedExoPlayer.currentPositionSinceEpochMs
        } else {
            extendedExoPlayer.currentPositionMs
        }.toDouble() / MS_IN_SECOND
        reportEnd(EndReason.OTHER, endPositionSeconds = positionInSeconds)
        resetInternal()
    }

    override fun release() {
        extendedExoPlayer.release()
        coroutineScope.launch {
            eventSink.emit(Event.Release)
            cancel()
        }
    }

    override fun onConnectionEstablished() {
        if (playbackState == PlaybackState.PLAYING) {
            streamingPrivileges.acquireStreamingPrivileges()
        }
    }

    override fun onStreamingPrivilegesRevoked(privilegedClientDisplayName: String?) {
        if (playbackState == PlaybackState.PLAYING) {
            coroutineScope.launch {
                eventSink.emit(Event.StreamingPrivilegesRevoked(privilegedClientDisplayName))
            }
            internalHandler.post {
                pause()
            }
        }
    }

    override fun onPlaybackInfoFetched(
        streamingSession: StreamingSession,
        forwardingMediaProduct: ForwardingMediaProduct<*>,
        playbackInfo: PlaybackInfo,
    ) {
        internalHandler.post {
            val currentForwardingMediaProduct = this.forwardingMediaProduct
            val nextForwardingMediaProduct = nextForwardingMediaProduct
            when (forwardingMediaProduct) {
                currentForwardingMediaProduct -> {
                    playbackContext = playbackContextFactory.create(
                        playbackInfo,
                        currentForwardingMediaProduct.delegate.referenceId,
                    )
                    internalHandler.post { updatePlayerVolume() }
                    val readPlaybackStatistics = currentPlaybackStatistics
                    currentPlaybackStatistics = undeterminedPlaybackSessionResolver(
                        if (readPlaybackStatistics != null &&
                            readPlaybackStatistics is PlaybackStatistics.Undetermined
                        ) {
                            readPlaybackStatistics
                        } else {
                            streamingSession.createUndeterminedPlaybackStatistics(
                                PlaybackStatistics.IdealStartTimestampMs.NotYetKnown,
                            )
                        },
                        playbackInfo,
                    )
                    currentPlaybackSession = streamingSession.createPlaybackSession(
                        playbackInfo,
                        currentForwardingMediaProduct,
                    )
                }

                nextForwardingMediaProduct -> {
                    nextPlaybackContext = playbackContextFactory.create(
                        playbackInfo,
                        nextForwardingMediaProduct.delegate.referenceId,
                    )
                    nextPlaybackStatistics = extendedExoPlayer.nextStreamingSession!!
                        .createUndeterminedPlaybackStatistics(
                            PlaybackStatistics.IdealStartTimestampMs.NotYetKnown,
                        )
                    nextPlaybackSession = streamingSession.createPlaybackSession(
                        playbackInfo,
                        nextForwardingMediaProduct,
                    )
                }
            }
            val delayedMediaProductTransition = delayedMediaProductTransition ?: return@post
            if (delayedMediaProductTransition
                    .run { from === mediaSource && to === nextMediaSource }
            ) {
                delayedMediaProductTransition(
                    this,
                    nextPlaybackContext!!,
                    nextPlaybackStatistics!!,
                    nextPlaybackSession!!,
                )
                this.delayedMediaProductTransition = null
            }
        }
    }

    override fun onDjSessionUpdated(productId: String, status: DjSessionStatus) {
        coroutineScope.launch {
            eventSink.emit(Event.DjSessionUpdate(productId, status))
        }
    }

    override fun onPlaybackSuppressionReasonChanged(
        eventTime: EventTime,
        playbackSuppressionReason: Int,
    ) {
        if (playbackSuppressionReason == Player.PLAYBACK_SUPPRESSION_REASON_NONE &&
            forwardingMediaProduct?.productType == ProductType.BROADCAST &&
            eventTime.correspondingForwardingMediaProductIfMatching === forwardingMediaProduct
        ) {
            extendedExoPlayer.seekToDefaultPosition()
        }

        val positionInSeconds = if (forwardingMediaProduct?.productType == ProductType.BROADCAST) {
            extendedExoPlayer.currentPositionSinceEpochMs
        } else {
            eventTime.currentPlaybackPositionMs
        }.toDouble() / MS_IN_SECOND
        val actionType = if (playbackSuppressionReason == Player.PLAYBACK_SUPPRESSION_REASON_NONE) {
            Action.Type.PLAYBACK_START
        } else {
            Action.Type.PLAYBACK_STOP
        }
        currentPlaybackSession?.actions?.add(
            Action(
                sntpClient.ntpOrLocalClockTime.inWholeMilliseconds,
                positionInSeconds,
                actionType,
            ),
        )
    }

    override fun onIsPlayingChanged(eventTime: EventTime, isPlaying: Boolean) {
        if (isPlaying) {
            currentPlaybackSession!!.apply {
                val positionInSeconds =
                    if (forwardingMediaProduct?.productType == ProductType.BROADCAST) {
                        extendedExoPlayer.currentPositionSinceEpochMs
                    } else {
                        eventTime.currentPlaybackPositionMs
                    }.toDouble() / MS_IN_SECOND
                startAssetPosition = positionInSeconds
            }
        }
    }

    override fun onPlaybackStateChanged(eventTime: EventTime, state: Int) {
        if (eventTime.correspondingForwardingMediaProductIfMatching !==
            forwardingMediaProduct
        ) {
            return
        }
        if (state == Player.STATE_BUFFERING) {
            playbackState = PlaybackState.STALLED
        } else if (state == Player.STATE_IDLE || state == Player.STATE_ENDED) {
            if (state == Player.STATE_ENDED) {
                val currentTimeMillis = sntpClient.ntpOrLocalClockTime.inWholeMilliseconds
                val positionSeconds =
                    if (forwardingMediaProduct?.productType == ProductType.BROADCAST) {
                        extendedExoPlayer.currentPositionSinceEpochMs
                    } else {
                        eventTime.currentPlaybackPositionMs
                    }.toDouble() / MS_IN_SECOND
                reportEnd(
                    EndReason.COMPLETE,
                    endTimestamp = currentTimeMillis,
                    endPositionSeconds = positionSeconds,
                )
                extendedExoPlayer.onCurrentItemFinished()
                val endedMediaProduct = forwardingMediaProduct!!
                val endedPlaybackContext = playbackContext!!
                coroutineScope.launch {
                    eventSink.emit(
                        Event.MediaProductEnded(
                            endedMediaProduct.delegate,
                            endedPlaybackContext,
                        ),
                    )
                }
            }
            resetInternal()
        } else if (state == Player.STATE_READY) {
            playbackState = if (extendedExoPlayer.playWhenReady) {
                PlaybackState.PLAYING
            } else {
                PlaybackState.NOT_PLAYING
            }
        }
    }

    @Suppress("LongMethod")
    override fun onPositionDiscontinuity(
        eventTime: EventTime,
        oldPosition: Player.PositionInfo,
        newPosition: Player.PositionInfo,
        reason: Int,
    ) {
        extendedExoPlayer.updatePosition(newPosition.positionMs)
        val oldPositionMs = if (forwardingMediaProduct?.productType == ProductType.BROADCAST) {
            extendedExoPlayer.getPositionSinceEpochMs(oldPosition.positionMs)
        } else {
            oldPosition.positionMs
        }

        val newPositionMs = if (forwardingMediaProduct?.productType == ProductType.BROADCAST) {
            extendedExoPlayer.getPositionSinceEpochMs(newPosition.positionMs)
        } else {
            newPosition.positionMs
        }

        val invokedAtMillis = sntpClient.ntpOrLocalClockTime.inWholeMilliseconds
        val oldPositionSeconds = oldPositionMs.toDouble() / MS_IN_SECOND
        val newPositionSeconds = newPositionMs.toDouble() / MS_IN_SECOND
        if (reason == Player.DISCONTINUITY_REASON_AUTO_TRANSITION) {
            reportEnd(
                EndReason.COMPLETE,
                endTimestamp = invokedAtMillis,
                endPositionSeconds = oldPositionSeconds,
            )

            when (extendedExoPlayer.repeatMode) {
                Player.REPEAT_MODE_OFF -> {
                    handleTransitionForRepeatOff(eventTime, invokedAtMillis, newPositionSeconds)
                }

                Player.REPEAT_MODE_ONE -> {
                    handleTransitionForRepeatOne(invokedAtMillis, newPositionSeconds)
                }

                Player.REPEAT_MODE_ALL -> {
                    throw UnsupportedOperationException(
                        "Unsupported repeat mode: ${Player.REPEAT_MODE_ALL}",
                    )
                }
            }
            updatePlayerVolume()
        } else if (reason == Player.DISCONTINUITY_REASON_SEEK) {
            if (oldPosition.mediaItemIndex != newPosition.mediaItemIndex) {
                reportEnd(
                    EndReason.OTHER,
                    endTimestamp = invokedAtMillis,
                    endPositionSeconds = oldPositionSeconds,
                )
                handleTransitionForRepeatOff(eventTime, invokedAtMillis, newPositionSeconds)
                return
            }
            if (!extendedExoPlayer.shouldStartPlaybackAfterUserAction()) {
                val stallPositionSeconds =
                    if (forwardingMediaProduct?.productType == ProductType.BROADCAST) {
                        extendedExoPlayer.getPositionSinceEpochMs(
                            eventTime.currentPlaybackPositionMs,
                        )
                    } else {
                        eventTime.currentPlaybackPositionMs
                    }.toDouble() / MS_IN_SECOND
                startStall(Stall.Reason.SEEK, stallPositionSeconds, invokedAtMillis)
            }
            currentPlaybackSession?.actions?.apply {
                add(Action(invokedAtMillis, oldPositionSeconds, Action.Type.PLAYBACK_STOP))
                add(Action(invokedAtMillis, newPositionSeconds, Action.Type.PLAYBACK_START))
            }
        }
    }

    fun handleTransitionForRepeatOff(
        eventTime: EventTime,
        invokedAtMillis: Long,
        newPositionSeconds: Double,
        targetPlaybackContext: PlaybackContext? = nextPlaybackContext,
        targetPlaybackStatistics: PlaybackStatistics.Undetermined? = nextPlaybackStatistics,
        targetPlaybackSession: PlaybackSession? = nextPlaybackSession,
    ) {
        if (delayedMediaProductTransition
                ?.run { from === mediaSource && to === nextMediaSource } != true
        ) {
            delayedMediaProductTransition = null
        }
        val nextMediaSource = nextMediaSource!!
        if (nextMediaSource.playbackInfo == null) {
            delayedMediaProductTransition = DelayedMediaProductTransition(
                mediaSource!!,
                nextMediaSource,
                eventTime,
                invokedAtMillis,
                newPositionSeconds,
            )
            return
        }
        mediaSource = nextMediaSource
        this.nextMediaSource = null
        extendedExoPlayer.onCurrentItemFinished()

        playbackContext = targetPlaybackContext!!
            .copy(eventTime.windowDurationMs.toFloat() / MS_IN_SECOND)
        currentPlaybackStatistics = undeterminedPlaybackSessionResolver(
            targetPlaybackStatistics!!.copy(
                idealStartTimestampMs =
                PlaybackStatistics.IdealStartTimestampMs.Known(invokedAtMillis),
            ),
            mediaSource!!.playbackInfo!!,
        ).toStarted(invokedAtMillis)
        currentPlaybackSession = targetPlaybackSession?.apply {
            startTimestamp = invokedAtMillis
            startAssetPosition = newPositionSeconds
        }

        emitMediaProductTransition(forwardingMediaProduct!!, playbackContext!!)
    }

    private fun handleTransitionForRepeatOne(invokedAtMillis: Long, newPositionSeconds: Double) {
        extendedExoPlayer.onRepeatOne(forwardingMediaProduct!!)
        val newStreamingSession = extendedExoPlayer.currentStreamingSession!!

        playbackContext = playbackContext!!.copy(newStreamingSession.id.toString())
        currentPlaybackStatistics = undeterminedPlaybackSessionResolver(
            newStreamingSession.createUndeterminedPlaybackStatistics(
                PlaybackStatistics.IdealStartTimestampMs.Known(invokedAtMillis),
            ),
            mediaSource!!.playbackInfo!!,
        ).toStarted(invokedAtMillis)
        currentPlaybackSession = newStreamingSession.createPlaybackSession(
            mediaSource!!.playbackInfo!!,
            forwardingMediaProduct!!,
        ).apply {
            startTimestamp = invokedAtMillis
            startAssetPosition = newPositionSeconds
        }

        emitMediaProductTransition(forwardingMediaProduct!!, playbackContext!!)
    }

    override fun onPlayWhenReadyChanged(eventTime: EventTime, playWhenReady: Boolean, reason: Int) {
        val positionInSeconds = if (forwardingMediaProduct?.productType == ProductType.BROADCAST) {
            extendedExoPlayer.currentPositionSinceEpochMs
        } else {
            eventTime.currentPlaybackPositionMs
        }.toDouble() / MS_IN_SECOND
        currentPlaybackSession?.actions?.add(
            Action(
                sntpClient.ntpOrLocalClockTime.inWholeMilliseconds,
                positionInSeconds,
                if (playWhenReady) Action.Type.PLAYBACK_START else Action.Type.PLAYBACK_STOP,
            ),
        )
        if (playWhenReady) {
            playbackState = if (extendedExoPlayer.playbackState == Player.STATE_READY) {
                PlaybackState.PLAYING
            } else {
                playbackState
            }
        } else {
            if (playbackState != PlaybackState.STALLED) {
                playbackState = PlaybackState.NOT_PLAYING
            }
        }
    }

    private fun updatePlayerVolume() {
        playerVolume = volumeHelper.getVolume(mediaSource?.playbackInfo)
    }

    override fun onTimelineChanged(eventTime: EventTime, reason: Int) {
        if (forwardingMediaProduct?.productType == ProductType.BROADCAST) {
            handleBroadcastTimelineChanged(reason)
            updateDuration(eventTime)
        } else {
            updateDuration(eventTime) {
                emitMediaProductTransition(forwardingMediaProduct!!, it)
            }
        }
    }

    private fun handleBroadcastTimelineChanged(reason: Int) {
        if (reason == Player.TIMELINE_CHANGE_REASON_SOURCE_UPDATE) {
            (extendedExoPlayer.currentManifest as? HlsManifest)?.let {
                val currentPositionSinceEpochMs = extendedExoPlayer.currentPositionSinceEpochMs
                currentPlaybackSession!!.apply {
                    startAssetPosition =
                        currentPositionSinceEpochMs.toDouble() / MS_IN_SECOND
                }
                djSessionManager.checkForUpdates(
                    it.mediaPlaylist.tags,
                    currentPositionSinceEpochMs,
                )
            }
        }
    }

    @Suppress("ReturnCount")
    private fun updateDuration(eventTime: EventTime, block: ((PlaybackContext) -> Unit)? = null) {
        if (eventTime.correspondingForwardingMediaProductIfMatching !== forwardingMediaProduct) {
            return
        }
        val durationMs = eventTime.windowDurationMs
        if (durationMs == C.TIME_UNSET) {
            return
        }
        val duration = durationMs.toFloat() / MS_IN_SECOND
        (playbackContext ?: return).also {
            if (it.duration != duration) {
                playbackContext = it.copy(duration).also { updatedPlaybackContext ->
                    block?.invoke(updatedPlaybackContext)
                }
            }
        }
    }

    override fun onAudioInputFormatChanged(
        eventTime: EventTime,
        format: Format,
        decoderReuseEvaluation: DecoderReuseEvaluation?,
    ) = trackNewAdaptation(eventTime, format)

    override fun onVideoInputFormatChanged(
        eventTime: EventTime,
        format: Format,
        decoderReuseEvaluation: DecoderReuseEvaluation?,
    ) {
        videoSurfaceView?.suggestedVideoDimen = AspectRatioAdjustingSurfaceView.SuggestedDimensions(
            format.width,
            format.height,
        )
        trackNewAdaptation(eventTime, format)
    }

    private fun emitMediaProductTransition(
        forwardingMediaProduct: ForwardingMediaProduct<MediaProduct>,
        playbackContext: PlaybackContext,
    ) {
        coroutineScope.launch {
            eventSink.emit(
                Event.MediaProductTransition(
                    forwardingMediaProduct.delegate,
                    playbackContext,
                ),
            )
        }
    }

    override fun onAudioPositionAdvancing(eventTime: EventTime, playoutStartSystemTimeMs: Long) {
        if (eventTime.correspondingForwardingMediaProductIfMatching !== forwardingMediaProduct) {
            return
        }
        val readCurrentPlaybackStatistics = currentPlaybackStatistics
        if (readCurrentPlaybackStatistics !is PlaybackStatistics.Success.Started) {
            val startTimestamp = sntpClient.ntpOrLocalClockTime.inWholeMilliseconds
            if (readCurrentPlaybackStatistics is PlaybackStatistics.Success.Prepared) {
                currentPlaybackStatistics = readCurrentPlaybackStatistics.toStarted(startTimestamp)
                currentPlaybackSession!!.apply {
                    this.startTimestamp = startTimestamp
                }
            }
        }
    }

    override fun onAudioUnderrun(
        eventTime: EventTime,
        bufferSize: Int,
        bufferSizeMs: Long,
        elapsedSinceLastFeedMs: Long,
    ) {
        if (eventTime.correspondingForwardingMediaProductIfMatching !== forwardingMediaProduct) {
            return
        }
        val positionInSeconds = if (forwardingMediaProduct?.productType == ProductType.BROADCAST) {
            extendedExoPlayer.currentPositionSinceEpochMs
        } else {
            eventTime.currentPlaybackPositionMs
        }.toDouble() / MS_IN_SECOND
        startStall(
            Stall.Reason.UNEXPECTED,
            positionInSeconds,
            sntpClient.ntpOrLocalClockTime.inWholeMilliseconds,
        )
    }

    @Suppress("LongMethod", "ComplexMethod")
    override fun onPlayerError(eventTime: EventTime, error: PlaybackException) {
        var crawler: Throwable? = error
        var playbackInfoFetchException: PlaybackInfoFetchException? = null

        var errorMessage = "${error.errorCodeName}: ${crawler?.message}"
        while (crawler?.cause != null) {
            crawler = crawler.cause
            errorMessage += " -> ${crawler?.message}"
            if (crawler is PlaybackInfoFetchException) {
                playbackInfoFetchException = crawler
            }
        }

        val eventError = errorHandler.getErrorEvent(error, forwardingMediaProduct?.productType)
        playbackInfoFetchException.report(errorMessage, eventError.errorCode)
        val matchingMediaProduct = eventTime.correspondingForwardingMediaProductIfMatching
        if (matchingMediaProduct === forwardingMediaProduct ||
            matchingMediaProduct === nextForwardingMediaProduct
        ) {
            if (matchingMediaProduct === forwardingMediaProduct) {
                val positionInSeconds =
                    if (forwardingMediaProduct?.productType == ProductType.BROADCAST) {
                        extendedExoPlayer.currentPositionSinceEpochMs
                    } else {
                        extendedExoPlayer.currentPositionMs
                    }.toDouble() / MS_IN_SECOND
                reportEnd(
                    EndReason.ERROR,
                    errorMessage,
                    eventError.errorCode,
                    positionInSeconds,
                )
            }
        }
        coroutineScope.launch {
            eventSink.emit(eventError)
        }
    }

    override fun onVideoSizeChanged(eventTime: EventTime, videoSize: VideoSize) {
        videoSurfaceView?.suggestedVideoDimen = videoSize.run {
            AspectRatioAdjustingSurfaceView.SuggestedDimensions(width, height)
        }
    }

    private fun PlaybackContext.copy(duration: Float): PlaybackContext {
        return when (this) {
            is PlaybackContext.Track -> this.copy(duration = duration)
            is PlaybackContext.Video -> this.copy(duration = duration)
        }
    }

    private fun PlaybackContext.copy(playbackSessionId: String): PlaybackContext {
        return when (this) {
            is PlaybackContext.Track -> this.copy(playbackSessionId = playbackSessionId)
            is PlaybackContext.Video -> this.copy(playbackSessionId = playbackSessionId)
        }
    }

    private fun SurfaceView.refreshKeepScreenOnBasedOnPlaybackState(): Boolean = post {
        keepScreenOn = playbackState == PlaybackState.PLAYING ||
            playbackState == PlaybackState.STALLED
    }

    @Suppress("LongMethod", "ReturnCount")
    private fun trackNewAdaptation(eventTime: EventTime, format: Format) {
        val updatedPlaybackStatisticsF = { playbackStatistics: PlaybackStatistics ->
            val positionInSeconds =
                if (forwardingMediaProduct?.productType == ProductType.BROADCAST) {
                    extendedExoPlayer.currentPositionSinceEpochMs
                } else {
                    eventTime.eventPlaybackPositionMs
                }.toDouble() / MS_IN_SECOND
            playbackStatistics + Adaptation(
                positionInSeconds,
                sntpClient.ntpOrLocalClockTime.inWholeMilliseconds,
                format.sampleMimeType ?: "",
                format.codecs ?: "",
                format.bitrate,
                format.width,
                format.height,
            )
        }
        when (eventTime.windowIndex) {
            0 -> {
                if (
                    eventTime.correspondingForwardingMediaProductIfMatching !==
                    forwardingMediaProduct
                ) {
                    return
                }
                if (eventTime.eventPlaybackPositionMs > 0) return
                currentPlaybackStatistics = updatedPlaybackStatisticsF(currentPlaybackStatistics!!)
            }

            1 -> {
                val nextPlaybackStatistics = nextPlaybackStatistics
                if (nextPlaybackStatistics == null) {
                    // It's possible to transition to an item before it's actually ready. This
                    // can cause an adaptation when it becomes ready, but by that time it will no
                    // longer be the next item, so we need to correct the window index, as well as
                    // to override the position to match
                    trackNewAdaptation(
                        eventTime.run {
                            EventTime(
                                realtimeMs,
                                object : ForwardingTimeline(timeline) {
                                    override fun getWindow(
                                        windowIndex: Int,
                                        window: Window,
                                        defaultPositionProjectionUs: Long,
                                    ): Window {
                                        return super.getWindow(
                                            1,
                                            window,
                                            defaultPositionProjectionUs,
                                        )
                                    }
                                },
                                0,
                                mediaPeriodId,
                                eventPlaybackPositionMs,
                                currentTimeline,
                                currentWindowIndex,
                                currentMediaPeriodId,
                                currentPlaybackPositionMs,
                                totalBufferedDurationMs,
                            )
                        },
                        format,
                    )
                    return
                }
                if (
                    eventTime.correspondingForwardingMediaProductIfMatching !==
                    nextForwardingMediaProduct
                ) {
                    return
                }
                this.nextPlaybackStatistics = updatedPlaybackStatisticsF(nextPlaybackStatistics)
                    as PlaybackStatistics.Undetermined
            }

            else -> error("Unexpected window index ${eventTime.windowIndex}")
        }
    }

    private fun startStall(
        reason: Stall.Reason,
        assetPositionSeconds: Double,
        currentTimeMillis: Long,
    ) {
        currentStall = StartedStall(reason, assetPositionSeconds, currentTimeMillis)
    }

    private fun StartedStall.complete() {
        (currentPlaybackStatistics as? PlaybackStatistics.Success.Started)?.let {
            currentPlaybackStatistics = it +
                Stall(
                    reason,
                    assetPositionSeconds,
                    startTimestamp,
                    sntpClient.ntpOrLocalClockTime.inWholeMilliseconds,
                )
        }
    }

    private fun resetInternal() {
        mediaSource = null
        nextMediaSource = null
        playbackState = PlaybackState.IDLE
        extendedExoPlayer.release()
        internalHandler.removeCallbacksAndMessages(null)
        extendedExoPlayer = extendedExoPlayerFactory.create(this, this)
    }

    @Suppress("LongMethod")
    private fun reportCurrentPlaybackStatistics(
        endReason: EndReason,
        errorMessage: String?,
        errorCode: String?,
        endTimestamp: Long,
    ) {
        val readCurrentPlaybackStatistics = currentPlaybackStatistics
        val prepared = readCurrentPlaybackStatistics?.getPrepared() ?: return
        val started = readCurrentPlaybackStatistics.getStarted()

        with(prepared) {
            eventReporter.report(
                when (this) {
                    is PlaybackStatistics.Success.Prepared.Audio ->
                        AudioPlaybackStatistics.Payload(
                            streamingSessionId.toString(),
                            idealStartTimestampMs.timestamp,
                            started?.actualStartTimestampMs,
                            actualProductId,
                            actualAssetPresentation,
                            actualAudioMode,
                            actualQuality,
                            mediaStorage,
                            versionedCdm.cdm,
                            versionedCdm.version,
                            started?.stalls ?: emptyList(),
                            adaptations,
                            endTimestamp,
                            endReason,
                            errorMessage,
                            errorCode,
                        )

                    is PlaybackStatistics.Success.Prepared.Video ->
                        VideoPlaybackStatistics.Payload(
                            streamingSessionId.toString(),
                            idealStartTimestampMs.timestamp,
                            started?.actualStartTimestampMs,
                            actualProductId,
                            actualStreamType,
                            actualAssetPresentation,
                            actualQuality,
                            mediaStorage,
                            versionedCdm.cdm,
                            versionedCdm.version,
                            started?.stalls ?: emptyList(),
                            adaptations,
                            endTimestamp,
                            endReason,
                            errorMessage,
                            errorCode,
                        )

                    is PlaybackStatistics.Success.Prepared.UC ->
                        UCPlaybackStatistics.Payload(
                            streamingSessionId.toString(),
                            idealStartTimestampMs.timestamp,
                            started?.actualStartTimestampMs,
                            actualProductId,
                            actualQuality,
                            mediaStorage,
                            versionedCdm.cdm,
                            versionedCdm.version,
                            started?.stalls ?: emptyList(),
                            adaptations,
                            endTimestamp,
                            endReason,
                            errorMessage,
                            errorCode,
                        )

                    is PlaybackStatistics.Success.Prepared.Broadcast ->
                        BroadcastPlaybackStatistics.Payload(
                            streamingSessionId.toString(),
                            idealStartTimestampMs.timestamp,
                            started?.actualStartTimestampMs,
                            actualProductId,
                            actualQuality,
                            mediaStorage,
                            versionedCdm.cdm,
                            versionedCdm.version,
                            started?.stalls ?: emptyList(),
                            adaptations,
                            endTimestamp,
                            endReason,
                            errorMessage,
                            errorCode,
                        )

                    else -> error("Illegal delegate type ${this::class.simpleName}")
                },
            )
        }
    }

    private fun PlaybackStatistics.getStarted() = when (this) {
        is PlaybackStatistics.Success.Started -> this
        else -> null
    }

    private fun PlaybackStatistics.getPrepared() = when (this) {
        is PlaybackStatistics.Success.Started -> this.prepared
        is PlaybackStatistics.Success.Prepared -> this
        else -> null
    }

    @Suppress("LongMethod")
    private fun reportCurrentPlaybackSession(endTimestamp: Long, endPositionSeconds: Double) {
        val readPlaybackSession = currentPlaybackSession ?: return
        if (readPlaybackSession.startTimestamp <= 0L) return
        with(readPlaybackSession) {
            eventReporter.report(
                when (this) {
                    is PlaybackSession.Audio -> AudioPlaybackSession.Payload(
                        playbackSessionId,
                        startTimestamp,
                        startAssetPosition,
                        requestedProductId,
                        actualProductId,
                        actualAssetPresentation,
                        actualAudioMode,
                        actualQuality,
                        sourceType,
                        sourceId,
                        actions,
                        endTimestamp,
                        endPositionSeconds,
                    )

                    is PlaybackSession.Video -> VideoPlaybackSession.Payload(
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
                        endTimestamp,
                        endPositionSeconds,
                    )

                    is PlaybackSession.Broadcast -> BroadcastPlaybackSession.Payload(
                        playbackSessionId,
                        startTimestamp,
                        startAssetPosition,
                        requestedProductId,
                        actualProductId,
                        actualQuality,
                        sourceType,
                        sourceId,
                        actions,
                        endTimestamp,
                        endPositionSeconds,
                    )

                    is PlaybackSession.UC -> UCPlaybackSession.Payload(
                        playbackSessionId,
                        startTimestamp,
                        startAssetPosition,
                        requestedProductId,
                        actualProductId,
                        actualQuality,
                        sourceType,
                        sourceId,
                        actions,
                        endTimestamp,
                        endPositionSeconds,
                    )
                },
            )
        }
    }

    private fun reportEnd(
        endReason: EndReason,
        errorMessage: String? = null,
        errorCode: String? = null,
        endPositionSeconds: Double,
        endTimestamp: Long = sntpClient.ntpOrLocalClockTime.inWholeMilliseconds,
    ) {
        reportCurrentPlaybackStatistics(endReason, errorMessage, errorCode, endTimestamp)
        reportCurrentPlaybackSession(endTimestamp, endPositionSeconds)
    }

    private fun PlaybackInfoFetchException?.report(errorMessage: String?, errorCode: String) {
        if (this == null) {
            return
        }
        val createUndeterminedPlaybackStatistics:
            StreamingSession.() -> PlaybackStatistics.Undetermined = {
                createUndeterminedPlaybackStatistics(
                    PlaybackStatistics.IdealStartTimestampMs.NotYetKnown,
                )
            }
        val playbackStatisticsForReporting = when (requestedMediaProduct) {
            forwardingMediaProduct ->
                currentPlaybackStatistics ?: extendedExoPlayer.currentStreamingSession!!
                    .createUndeterminedPlaybackStatistics().also {
                        currentPlaybackStatistics = it
                    }

            nextForwardingMediaProduct ->
                nextPlaybackStatistics ?: extendedExoPlayer.nextStreamingSession!!
                    .createUndeterminedPlaybackStatistics().also {
                        nextPlaybackStatistics = it
                    }

            else -> return
        }
        eventReporter.report(
            (playbackStatisticsForReporting as PlaybackStatistics.Undetermined).run {
                NotStartedPlaybackStatistics.Payload(
                    streamingSessionId.toString(),
                    idealStartTimestampMs.timestamp,
                    requestedMediaProduct.productType,
                    sntpClient.ntpOrLocalClockTime.inWholeMilliseconds,
                    errorMessage,
                    errorCode,
                    when (this@report) {
                        is PlaybackInfoFetchException.Cancellation -> EndReason.OTHER
                        is PlaybackInfoFetchException.Error -> EndReason.ERROR
                    },
                )
            },
        )
    }

    private val EventTime.windowDurationMs: Long
        get() = timeline.getWindow(windowIndex, Timeline.Window()).durationMs

    private val EventTime.correspondingForwardingMediaProductIfMatching
        get() = this.windowIndex.run {
            if (this >= timeline.windowCount) {
                return@run null
            }
            val targetForwardingMediaProduct = when (this) {
                0 -> forwardingMediaProduct
                1 -> nextForwardingMediaProduct
                else -> return@run null
            }
            if (timeline.getWindow(this, Timeline.Window()).mediaItem.mediaId.contentEquals(
                    targetForwardingMediaProduct.hashCode().toString(),
                )
            ) {
                targetForwardingMediaProduct
            } else {
                null
            }
        }

    @get:RestrictTo(RestrictTo.Scope.TESTS)
    @set:RestrictTo(RestrictTo.Scope.TESTS)
    var testMediaSource by this::mediaSource

    @get:RestrictTo(RestrictTo.Scope.TESTS)
    @set:RestrictTo(RestrictTo.Scope.TESTS)
    var testNextMediaSource by this::nextMediaSource
}
