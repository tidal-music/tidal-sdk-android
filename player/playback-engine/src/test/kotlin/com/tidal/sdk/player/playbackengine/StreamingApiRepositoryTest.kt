package com.tidal.sdk.player.playbackengine

import androidx.media3.exoplayer.drm.MediaDrmCallbackException
import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.hasCause
import assertk.assertions.isInstanceOf
import assertk.assertions.isSameAs
import com.tidal.sdk.player.common.ForwardingMediaProduct
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.ProductQuality
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
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever

internal class StreamingApiRepositoryTest {

    private val streamingApi = mock<StreamingApi>()
    private val audioQualityRepository = mock<AudioQualityRepository>()
    private val videoQualityRepository = mock<VideoQualityRepository>()
    private val audioModeRepository = mock<AudioModeRepository>()
    private val trueTimeWrapper = mock<TrueTimeWrapper>()
    private val mediaDrmCallbackExceptionFactory = mock<MediaDrmCallbackExceptionFactory>()
    private val eventReporter = mock<EventReporter>()
    private val errorHandler = mock<ErrorHandler>()
    private val streamingApiRepository = StreamingApiRepository(
        streamingApi,
        audioQualityRepository,
        videoQualityRepository,
        audioModeRepository,
        trueTimeWrapper,
        mediaDrmCallbackExceptionFactory,
        eventReporter,
        errorHandler,
    )

    @Test
    fun getDrmLicenseOnSuccessCallReturnsAndReports() = runBlocking {
        val startTimestamp = 1L
        val endTimestamp = -2L
        val streamingSessionId = "streamingSessionId"
        val drmLicenseRequest = mock<DrmLicenseRequest> {
            on { it.streamingSessionId } doReturn streamingSessionId
        }
        val expectedDrmLicense = mock<DrmLicense>()
        whenever(trueTimeWrapper.currentTimeMillis).thenReturn(startTimestamp, endTimestamp)
        whenever(streamingApi.getDrmLicense(drmLicenseRequest)).thenReturn(expectedDrmLicense)

        val actual = streamingApiRepository.getDrmLicense(drmLicenseRequest, null)

        assertThat(actual).isSameAs(expectedDrmLicense)
        verify(eventReporter).report(
            DrmLicenseFetch.Payload(
                streamingSessionId,
                startTimestamp,
                endTimestamp,
                EndReason.COMPLETE,
                null,
                null,
            ),
            null,
        )
    }

    @Test
    fun getDrmLicenseOnFailureThrowsAndReports() = runBlocking {
        val startTimestamp = 1L
        val endTimestamp = -2L
        val streamingSessionId = "streamingSessionId"
        val drmLicenseRequest = mock<DrmLicenseRequest> {
            on { it.streamingSessionId } doReturn streamingSessionId
        }
        val errorMessage = "errorMessage"
        val runtimeException = mock<RuntimeException> {
            on { it.message } doReturn errorMessage
        }
        val errorCode = "errorCode"
        val mediaDrmCallbackException = mock<MediaDrmCallbackException>()
        whenever(trueTimeWrapper.currentTimeMillis).thenReturn(startTimestamp, endTimestamp)
        whenever(streamingApi.getDrmLicense(drmLicenseRequest)) doThrow runtimeException
        whenever(mediaDrmCallbackExceptionFactory.create(runtimeException))
            .thenReturn(mediaDrmCallbackException)
        whenever(
            errorHandler.getErrorCode(
                runtimeException,
                ErrorCodeFactory.Extra.DrmLicenseFetch,
            ),
        ).thenReturn(errorCode)

        assertFailure { streamingApiRepository.getDrmLicense(drmLicenseRequest, null) }
            .isSameAs(mediaDrmCallbackException)

        verify(eventReporter).report(
            DrmLicenseFetch.Payload(
                streamingSessionId,
                startTimestamp,
                endTimestamp,
                EndReason.ERROR,
                errorMessage,
                errorCode,
            ),
            null
        )
    }

    @Suppress("LongMethod")
    @ParameterizedTest
    @EnumSource(ProductType::class)
    fun testGetPlaybackInfoForStreamingOnSuccessCallReturnsAndReports(productType: ProductType) =
        runBlocking {
            val startTimestamp = 5L
            val endTimestamp = 0L
            val streamingSessionId = "streamingSessionId"
            val productId = "33"
            val mediaProduct = mock<ForwardingMediaProduct<*>> {
                on { it.productId } doReturn productId
                on { it.productType } doReturn productType
            }
            val productQuality: ProductQuality
            val playbackMode = PlaybackMode.STREAM
            val expected = mock<PlaybackInfo.Track>()
            when (productType) {
                ProductType.TRACK -> {
                    productQuality = mock<AudioQuality>()
                    whenever(audioQualityRepository.streamingQuality) doReturn productQuality
                    whenever(
                        streamingApi.getTrackPlaybackInfo(
                            productId.toInt(),
                            productQuality,
                            playbackMode,
                            false,
                            "streamingSessionId",
                        ),
                    )
                        .thenReturn(expected)
                }

                ProductType.VIDEO -> {
                    productQuality = mock<VideoQuality>()
                    whenever(videoQualityRepository.streamingQuality) doReturn productQuality
                    whenever(
                        streamingApi.getVideoPlaybackInfo(
                            productId.toInt(),
                            productQuality,
                            playbackMode,
                            "streamingSessionId",
                        ),
                    )
                        .thenReturn(expected)
                }

                ProductType.BROADCAST -> {
                    productQuality = mock<AudioQuality>()
                    whenever(audioQualityRepository.streamingQuality) doReturn productQuality
                    whenever(
                        streamingApi.getBroadcastPlaybackInfo(
                            productId,
                            streamingSessionId,
                            productQuality,
                        ),
                    ).thenReturn(expected)
                }

                ProductType.UC -> {
                    whenever(
                        streamingApi.getUCPlaybackInfo(
                            productId,
                            streamingSessionId,
                        ),
                    ).thenReturn(expected)
                }
            }
            whenever(trueTimeWrapper.currentTimeMillis).thenReturn(startTimestamp, endTimestamp)

            val actual = streamingApiRepository.getPlaybackInfoForStreaming(
                streamingSessionId,
                mediaProduct,
            )

            assertThat(actual).isSameAs(expected)
            verify(eventReporter).report(
                PlaybackInfoFetch.Payload(
                    streamingSessionId,
                    startTimestamp,
                    endTimestamp,
                    EndReason.COMPLETE,
                    null,
                    null,
                ),
                null,
            )
            when (productType) {
                ProductType.TRACK, ProductType.BROADCAST -> {
                    verify(audioQualityRepository).streamingQuality
                    verifyNoInteractions(videoQualityRepository)
                }

                ProductType.VIDEO -> {
                    verify(videoQualityRepository).streamingQuality
                    verifyNoInteractions(audioQualityRepository)
                }

                ProductType.UC -> {
                    verifyNoInteractions(audioQualityRepository)
                    verifyNoInteractions(videoQualityRepository)
                }
            }
        }

    @Suppress("LongMethod")
    @ParameterizedTest
    @EnumSource(ProductType::class)
    fun testGetPlaybackInfoForStreamingOnFailureCallReturnsAndReports(productType: ProductType) =
        runBlocking {
            val startTimestamp = -9L
            val endTimestamp = -4L
            val errorMessage = "errorMessage"
            val errorCode = "errorCode"
            whenever(trueTimeWrapper.currentTimeMillis).thenReturn(startTimestamp, endTimestamp)
            val streamingSessionId = "streamingSessionId"
            val productId = "33"
            val mediaProduct = mock<ForwardingMediaProduct<*>> {
                on { it.productId } doReturn productId
                on { it.productType } doReturn productType
            }
            val productQuality: ProductQuality
            val playbackMode = PlaybackMode.STREAM
            val runtimeException = mock<RuntimeException> {
                on { it.message } doReturn errorMessage
            }
            whenever(
                errorHandler.getErrorCode(
                    runtimeException,
                    ErrorCodeFactory.Extra.PlaybackInfoFetch,
                ),
            ).thenReturn(errorCode)
            when (productType) {
                ProductType.TRACK -> {
                    productQuality = mock<AudioQuality>()
                    whenever(audioQualityRepository.streamingQuality) doReturn productQuality
                    whenever(
                        streamingApi.getTrackPlaybackInfo(
                            productId.toInt(),
                            productQuality,
                            playbackMode,
                            false,
                            "streamingSessionId",
                        ),
                    )
                        .thenThrow(runtimeException)
                }

                ProductType.VIDEO -> {
                    productQuality = mock<VideoQuality>()
                    whenever(videoQualityRepository.streamingQuality) doReturn productQuality
                    whenever(
                        streamingApi.getVideoPlaybackInfo(
                            productId.toInt(),
                            productQuality,
                            playbackMode,
                            "streamingSessionId",
                        ),
                    )
                        .thenThrow(runtimeException)
                }

                ProductType.BROADCAST -> {
                    productQuality = mock<AudioQuality>()
                    whenever(audioQualityRepository.streamingQuality) doReturn productQuality
                    whenever(
                        streamingApi.getBroadcastPlaybackInfo(
                            productId,
                            streamingSessionId,
                            productQuality,
                        ),
                    ).thenThrow(runtimeException)
                }

                ProductType.UC -> {
                    whenever(
                        streamingApi.getUCPlaybackInfo(
                            productId,
                            streamingSessionId,
                        ),
                    ).thenThrow(runtimeException)
                }
            }

            assertFailure {
                streamingApiRepository.getPlaybackInfoForStreaming(
                    streamingSessionId,
                    mediaProduct,
                )
            }
                .isInstanceOf(IOException::class)
                .hasCause(runtimeException)

            verify(eventReporter).report(
                PlaybackInfoFetch.Payload(
                    streamingSessionId,
                    startTimestamp,
                    endTimestamp,
                    EndReason.ERROR,
                    errorMessage,
                    errorCode,
                ),
                null,
            )
        }
}
