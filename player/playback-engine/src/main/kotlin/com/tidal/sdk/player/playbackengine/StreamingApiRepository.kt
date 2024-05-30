package com.tidal.sdk.player.playbackengine

import androidx.media3.exoplayer.drm.MediaDrmCallbackException
import com.tidal.sdk.player.common.ForwardingMediaProduct
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.EventReporter
import com.tidal.sdk.player.events.model.DrmLicenseFetch
import com.tidal.sdk.player.events.model.EndReason
import com.tidal.sdk.player.events.model.PlaybackInfoFetch
import com.tidal.sdk.player.playbackengine.audiomode.AudioModeRepository
import com.tidal.sdk.player.playbackengine.drm.MediaDrmCallbackExceptionFactory
import com.tidal.sdk.player.playbackengine.error.ErrorCodeFactory
import com.tidal.sdk.player.playbackengine.error.ErrorHandler
import com.tidal.sdk.player.playbackengine.quality.AudioQualityRepository
import com.tidal.sdk.player.playbackengine.quality.VideoQualityRepository
import com.tidal.sdk.player.streamingapi.StreamingApi
import com.tidal.sdk.player.streamingapi.drm.model.DrmLicense
import com.tidal.sdk.player.streamingapi.drm.model.DrmLicenseRequest
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackMode
import java.io.IOException

/**
 * Communicates with the streaming api, but with additional operations that do not belong in the
 * streaming api itself, such as event tracking and convenience methods only used in the playback
 * engine.
 *
 * @param[streamingApi] An instance of the [StreamingApi]
 * @param[audioQualityRepository] An instance of [AudioQualityRepository] which decides the
 * [AudioQuality] to be used in the request.
 * @param[videoQualityRepository] An instance of [VideoQualityRepository] which decides the
 * [VideoQuality] to be used in the request.
 * @param[trueTimeWrapper] An instance of [TrueTimeWrapper] to get the time for event reporting.
 * @param[mediaDrmCallbackExceptionFactory] An instance of [MediaDrmCallbackExceptionFactory] to
 * create a [MediaDrmCallbackException] for when the drm fails.
 * @param[eventReporter] An instance of [EventReporter] to report events to.
 * @param[errorHandler] An instance of [ErrorHandler] to get the error code for reporting.
 */
internal class StreamingApiRepository(
    private val streamingApi: StreamingApi,
    private val audioQualityRepository: AudioQualityRepository,
    private val videoQualityRepository: VideoQualityRepository,
    private val audioModeRepository: AudioModeRepository,
    private val trueTimeWrapper: TrueTimeWrapper,
    private val mediaDrmCallbackExceptionFactory: MediaDrmCallbackExceptionFactory,
    private val eventReporter: EventReporter,
    private val errorHandler: ErrorHandler,
) {

    /**
     * Get the drm license as [DrmLicense] for a given [DrmLicenseRequest].
     */
    @SuppressWarnings("TooGenericExceptionCaught") // We rethrow it, so no issue
    suspend fun getDrmLicense(drmLicenseRequest: DrmLicenseRequest): DrmLicense {
        val startTimestamp = trueTimeWrapper.currentTimeMillis
        var errorMessage: String? = null
        var errorCode: String? = null
        lateinit var endReason: EndReason
        try {
            val ret = streamingApi.getDrmLicense(drmLicenseRequest)
            endReason = EndReason.COMPLETE
            return ret
        } catch (throwable: Throwable) {
            endReason = EndReason.ERROR
            errorMessage = throwable.message
            errorCode = errorHandler.getErrorCode(throwable, ErrorCodeFactory.Extra.DrmLicenseFetch)
            throw mediaDrmCallbackExceptionFactory.create(throwable)
        } finally {
            eventReporter.report(
                DrmLicenseFetch.Payload(
                    drmLicenseRequest.streamingSessionId,
                    startTimestamp,
                    trueTimeWrapper.currentTimeMillis,
                    endReason,
                    errorMessage,
                    errorCode,
                ),
            )
        }
    }

    /**
     * Get the playback info as [PlaybackInfo] for a given [ForwardingMediaProduct], for streaming.
     */
    @SuppressWarnings("TooGenericExceptionCaught") // We rethrow it, so no issue
    suspend fun getPlaybackInfoForStreaming(
        streamingSessionId: String,
        forwardingMediaProduct: ForwardingMediaProduct<*>,
    ): PlaybackInfo {
        val startTimestamp = trueTimeWrapper.currentTimeMillis
        var errorMessage: String? = null
        var errorCode: String? = null
        lateinit var endReason: EndReason
        try {
            val ret = when (forwardingMediaProduct.productType) {
                ProductType.TRACK -> streamingApi.getTrackPlaybackInfo(
                    forwardingMediaProduct.productId.toInt(),
                    audioQualityRepository.streamingQuality,
                    PlaybackMode.STREAM,
                    audioModeRepository.immersiveAudio,
                    streamingSessionId,
                )

                ProductType.VIDEO -> streamingApi.getVideoPlaybackInfo(
                    forwardingMediaProduct.productId.toInt(),
                    videoQualityRepository.streamingQuality,
                    PlaybackMode.STREAM,
                    streamingSessionId,
                )

                ProductType.BROADCAST -> streamingApi.getBroadcastPlaybackInfo(
                    forwardingMediaProduct.productId,
                    streamingSessionId,
                    audioQualityRepository.streamingQuality,
                )

                ProductType.UC -> streamingApi.getUCPlaybackInfo(
                    forwardingMediaProduct.productId,
                    streamingSessionId,
                )
            }
            endReason = EndReason.COMPLETE
            return ret
        } catch (throwable: Throwable) {
            endReason = EndReason.ERROR
            errorMessage = throwable.message
            errorCode =
                errorHandler.getErrorCode(throwable, ErrorCodeFactory.Extra.PlaybackInfoFetch)
            throw IOException(throwable)
        } finally {
            eventReporter.report(
                PlaybackInfoFetch.Payload(
                    streamingSessionId,
                    startTimestamp,
                    trueTimeWrapper.currentTimeMillis,
                    endReason,
                    errorMessage,
                    errorCode,
                ),
            )
        }
    }

    /**
     * Get the playback info as [PlaybackInfo] for a given [ForwardingMediaProduct],
     * for offline playback.
     */
    suspend fun getPlaybackInfoForOfflinePlayback(
        streamingSessionId: String,
        forwardingMediaProduct: ForwardingMediaProduct<*>,
    ) = when (forwardingMediaProduct.productType) {
        ProductType.TRACK -> streamingApi.getOfflineTrackPlaybackInfo(
            forwardingMediaProduct.productId.toInt(),
            streamingSessionId,
        )

        ProductType.VIDEO -> streamingApi.getOfflineVideoPlaybackInfo(
            forwardingMediaProduct.productId.toInt(),
            streamingSessionId,
        )

        ProductType.BROADCAST, ProductType.UC -> throw IllegalArgumentException(
            "ProductType ${forwardingMediaProduct.productType} can't be offlined.",
        )
    }
}
