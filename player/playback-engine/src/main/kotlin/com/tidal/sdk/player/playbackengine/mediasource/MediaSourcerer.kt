package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ConcatenatingMediaSource
import com.tidal.sdk.player.common.ForwardingMediaProduct
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.EventReporter
import com.tidal.sdk.player.events.model.StreamingSessionEnd
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.StreamingSession
import kotlin.properties.Delegates

/**
 * Manages [MediaSource]s we use for continuous playback of media into the provided [ExoPlayer]
 * instance.
 *
 * It always manages at most 2 items: current and next.
 * Note: The host application is responsible for managing the rest of the play queue.
 *
 * @param[playbackInfoMediaSourceFactory] A [PlaybackInfoMediaSourceFactory] to create
 * [PlaybackInfoMediaSource]s from [ForwardingMediaProduct]s.
 */
@Suppress("UnsafeOptInUsageError")
internal class MediaSourcerer(
    private val exoPlayer: ExoPlayer,
    private val playbackInfoMediaSourceFactory: PlaybackInfoMediaSourceFactory,
    private val explicitStreamingSessionCreator: StreamingSession.Creator.Explicit,
    private val implicitStreamingSessionCreator: StreamingSession.Creator.Implicit,
    private val eventReporter: EventReporter,
    private val trueTimeWrapper: TrueTimeWrapper,
) {

    var currentStreamingSession: StreamingSession? by Delegates.observable(null) { _, oldValue, _ ->
        oldValue?.reportEnd()
    }
        private set
    var nextStreamingSession: StreamingSession? = null
        private set

    /**
     * Load the provided [ForwardingMediaProduct].
     *
     * Use this when you have a [ForwardingMediaProduct] that you want to play immediately.
     *
     * Make sure to set the returned [ConcatenatingMediaSource] on the player and prepare it.
     */
    fun load(
        forwardingMediaProduct: ForwardingMediaProduct<MediaProduct>,
    ): PlaybackInfoMediaSource {
        val currentStreamingSession = explicitStreamingSessionCreator.createAndReportStart(
            forwardingMediaProduct.productType,
            forwardingMediaProduct.productId,
            forwardingMediaProduct.extras,
        )
        this.currentStreamingSession = currentStreamingSession
        return playbackInfoMediaSourceFactory.create(
            currentStreamingSession,
            forwardingMediaProduct,
        ).also {
            exoPlayer.setMediaSource(it)
        }
    }

    /**
     * Set the next [ForwardingMediaProduct].
     *
     * Use this when you have a [ForwardingMediaProduct] that you want to play after the current.
     */
    fun setNext(forwardingMediaProduct: ForwardingMediaProduct<MediaProduct>?):
        PlaybackInfoMediaSource? {
        if (exoPlayer.mediaItemCount == 2) {
            exoPlayer.removeMediaItem(1)
        }
        nextStreamingSession?.reportEnd()
        return if (forwardingMediaProduct != null) {
            val nextStreamingSession = implicitStreamingSessionCreator.createAndReportStart(
                forwardingMediaProduct.productType,
                forwardingMediaProduct.productId,
                forwardingMediaProduct.extras,
            )
            this.nextStreamingSession = nextStreamingSession
            playbackInfoMediaSourceFactory.create(
                nextStreamingSession,
                forwardingMediaProduct,
            ).also {
                exoPlayer.addMediaSource(it)
            }
        } else {
            nextStreamingSession = null
            null
        }
    }

    /**
     * This needs to be called when a transition from current to next has been done, in order to
     * remove the source that was playing.
     *
     * Note: The host application is responsible for updating the new next, so it can continue to
     * play continuously.
     */
    fun onCurrentItemFinished() {
        currentStreamingSession = nextStreamingSession
        nextStreamingSession = null
        exoPlayer.removeMediaItem(0)
    }

    /**
     * This needs to be called when repeat one is enabled and we start the same item again, in
     * order to create a new streaming session that we can use for reporting.
     */
    fun onRepeatOne(forwardingMediaProduct: ForwardingMediaProduct<*>) {
        currentStreamingSession = implicitStreamingSessionCreator.createAndReportStart(
            forwardingMediaProduct.productType,
            forwardingMediaProduct.productId,
            forwardingMediaProduct.extras,
        )
    }

    fun release() {
        exoPlayer.clearMediaItems()
        currentStreamingSession = null
        nextStreamingSession?.reportEnd()
        nextStreamingSession = null
    }

    private fun StreamingSession.reportEnd() = eventReporter.report(
        StreamingSessionEnd.Payload(id.toString(), trueTimeWrapper.currentTimeMillis),
        extras
    )
}
