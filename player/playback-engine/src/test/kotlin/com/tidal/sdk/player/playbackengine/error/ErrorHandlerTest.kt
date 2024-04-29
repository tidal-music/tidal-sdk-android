package com.tidal.sdk.player.playbackengine.error

import androidx.media3.common.PlaybackException
import androidx.media3.datasource.HttpDataSource
import androidx.media3.datasource.HttpDataSource.HttpDataSourceException
import androidx.media3.exoplayer.ExoPlaybackException
import androidx.media3.exoplayer.audio.AudioSink
import androidx.media3.exoplayer.drm.DrmSession.DrmSessionException
import androidx.media3.exoplayer.source.BehindLiveWindowException
import androidx.media3.exoplayer.upstream.Loader
import assertk.assertThat
import assertk.assertions.isInstanceOf
import assertk.assertions.isSameAs
import com.tidal.sdk.player.common.model.ApiError
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.playbackengine.model.Event
import com.tidal.sdk.player.playbackengine.offline.OfflineExpiredException
import com.tidal.sdk.player.playbackengine.offline.StorageException
import com.tidal.sdk.player.playbackengine.reflectionSetErrorCode
import com.tidal.sdk.player.playbackengine.reflectionSetType
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import retrofit2.HttpException

internal class ErrorHandlerTest {

    private val errorCodeFactory = mock<ErrorCodeFactory>()
    private val errorHandler = ErrorHandler(errorCodeFactory)

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(errorCodeFactory)

    @Test
    fun `getErrorCode should return correct errorCode for ApiError`() {
        val status = 499
        val subStatus = ApiError.SubStatus.GenericPlaybackError
        val throwable = mock<ApiError> {
            on { this.status } doReturn status
            on { this.subStatus } doReturn subStatus
        }
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForApiError(status, subStatus.code)).thenReturn(
            expectedErrorCode,
        )

        val actualErrorCode =
            errorHandler.getErrorCode(throwable, ErrorCodeFactory.Extra.DrmLicenseFetch)

        verify(errorCodeFactory).createForApiError(status, subStatus.code)
        assertThat(actualErrorCode).isSameAs(expectedErrorCode)
    }

    @Test
    fun `getErrorCode should return correct errorCode for timeouts`() {
        val throwable = mock<SocketTimeoutException>()
        val extra = ErrorCodeFactory.Extra.DrmLicenseFetch
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForTimeout(extra)).thenReturn(expectedErrorCode)

        val actualErrorCode =
            errorHandler.getErrorCode(throwable, extra)

        verify(errorCodeFactory).createForTimeout(extra)
        assertThat(actualErrorCode).isSameAs(expectedErrorCode)
    }

    @ParameterizedTest
    @ValueSource(
        classes = [
            ConnectException::class,
            UnknownHostException::class,
        ],
    )
    fun `getErrorCode should return correct errorCode for network connection errors`(
        ioException: Class<IOException>,
    ) {
        val throwable = ioException.getConstructor().newInstance()
        val extra = ErrorCodeFactory.Extra.DrmLicenseFetch
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForNetwork(extra)).thenReturn(expectedErrorCode)

        val actualErrorCode =
            errorHandler.getErrorCode(throwable, extra)

        verify(errorCodeFactory).createForNetwork(extra)
        assertThat(actualErrorCode).isSameAs(expectedErrorCode)
    }

    @Test
    fun `getErrorCode should return correct errorCode for unknown errors`() {
        val throwable = mock<Throwable>()
        val extra = ErrorCodeFactory.Extra.DrmLicenseFetch
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForOther(extra)).thenReturn(expectedErrorCode)

        val actualErrorCode =
            errorHandler.getErrorCode(throwable, extra)

        verify(errorCodeFactory).createForOther(extra)
        assertThat(actualErrorCode).isSameAs(expectedErrorCode)
    }

    @ParameterizedTest
    @MethodSource("apiErrorSubStatusesForNotAllowed")
    fun `getErrorEvent should return correct errorEvent for NotAllowed`(
        subStatus: ApiError.SubStatus,
    ) {
        val status = 499
        val throwable = mock<ApiError> {
            on { this.status } doReturn status
            on { this.subStatus } doReturn subStatus
        }
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForApiError(status, subStatus.code)).thenReturn(
            expectedErrorCode,
        )

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForApiError(status, subStatus.code)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.NotAllowed::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(throwable)
    }

    @ParameterizedTest
    @MethodSource("apiErrorSubStatusesForContentNotAvailableInLocation")
    fun `getErrorEvent should return correct errorEvent for ContentNotAvailableInLocation`(
        subStatus: ApiError.SubStatus,
    ) {
        val status = 499
        val throwable = mock<ApiError> {
            on { this.subStatus } doReturn subStatus
            on { this.status } doReturn status
        }
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForApiError(status, subStatus.code)).thenReturn(
            expectedErrorCode,
        )

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForApiError(status, subStatus.code)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.ContentNotAvailableInLocation::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(throwable)
    }

    @Test
    fun `getErrorEvent should return correct errorEvent for UserMonthlyStreamQuotaExceeded`() {
        val status = 499
        val subStatus = ApiError.SubStatus.UserMonthlyStreamQuotaExceeded
        val throwable = mock<ApiError> {
            on { this.status } doReturn status
            on { this.subStatus } doReturn subStatus
        }
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForApiError(status, subStatus.code)).thenReturn(
            expectedErrorCode,
        )

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForApiError(status, subStatus.code)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.MonthlyStreamQuotaExceeded::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(throwable)
    }

    @Test
    fun `getErrorEvent should return correct for NoContentMatchingSubscriptionConfiguration`() {
        val status = 499
        val subStatus = ApiError.SubStatus.NoContentMatchingSubscriptionConfiguration
        val throwable = mock<ApiError> {
            on { this.status } doReturn status
            on { this.subStatus } doReturn subStatus
        }
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForApiError(status, subStatus.code)).thenReturn(
            expectedErrorCode,
        )

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForApiError(status, subStatus.code)
        assertThat(actualErrorEvent)
            .isInstanceOf(Event.Error.ContentNotAvailableForSubscription::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(throwable)
    }

    @ParameterizedTest
    @ValueSource(ints = [429, 500, 501, 502, 503, 504])
    fun `getErrorEvent should return correct errorEvent for 429 and 5xx`(code: Int) {
        val cause = mock<HttpException> {
            on { code() } doReturn code
        }
        val subStatus = ApiError.SubStatus.Unknown(-1)
        val throwable = mock<ApiError> {
            on { this.cause } doReturn cause
            on { this.status } doReturn code
            on { this.subStatus } doReturn subStatus
        }
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForApiError(code, subStatus.code)).thenReturn(
            expectedErrorCode,
        )

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForApiError(code, subStatus.code)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.Retryable::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(throwable)
    }

    @ParameterizedTest
    @ValueSource(ints = [400, 401, 403, 404, 405, 416])
    fun `getErrorEvent should return correct errorEvent for 4xx except 429`(
        code: Int,
    ) {
        val cause = mock<HttpException> {
            on { code() } doReturn code
        }
        val subStatus = ApiError.SubStatus.Unknown(-1)
        val throwable = mock<ApiError> {
            on { this.cause } doReturn cause
            on { this.status } doReturn code
            on { this.subStatus } doReturn subStatus
        }
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForApiError(code, subStatus.code)).thenReturn(
            expectedErrorCode,
        )

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForApiError(code, subStatus.code)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.Unexpected::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(throwable)
    }

    @Test
    fun `getErrorEvent should return correct errorEvent for InvalidResponseCodeException`() {
        val responseCode = 500
        val cause = HttpDataSource.InvalidResponseCodeException(
            responseCode,
            null,
            null,
            emptyMap(),
            mock(),
            byteArrayOf(),
        )
        val exoPlayerErrorCode = 123
        val throwable = mock<ExoPlaybackException> {
            on { it.cause } doReturn cause
        }
        throwable.reflectionSetErrorCode(exoPlayerErrorCode)
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForInvalidResponseCode(responseCode, exoPlayerErrorCode))
            .thenReturn(expectedErrorCode)

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForInvalidResponseCode(responseCode, exoPlayerErrorCode)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.Retryable::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(cause)
    }

    @Test
    fun `getErrorEvent should return correct errorEvent for HttpDataSourceException`() {
        val type = HttpDataSourceException.TYPE_OPEN
        val cause =
            HttpDataSourceException(mock(), 0, type)
        val exoPlayerErrorCode = 123
        val throwable = mock<ExoPlaybackException> {
            on { it.cause } doReturn cause
        }
        throwable.reflectionSetErrorCode(exoPlayerErrorCode)
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForHttpDataSource(type, exoPlayerErrorCode)).thenReturn(
            expectedErrorCode,
        )

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForHttpDataSource(type, exoPlayerErrorCode)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.Network::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(cause)
    }

    @Test
    fun `getErrorEvent should return correct errorEvent for DrmSessionException for source`() {
        val cause = mock<DrmSessionException>()
        val exoPlayerErrorCode = 123
        val throwable = mock<ExoPlaybackException> {
            on { it.cause } doReturn cause
        }
        throwable.reflectionSetErrorCode(exoPlayerErrorCode)
        val extra = ErrorCodeFactory.Extra.PlayerSourceError
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForDrmSession(extra, exoPlayerErrorCode)).thenReturn(
            expectedErrorCode,
        )

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForDrmSession(extra, exoPlayerErrorCode)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.Retryable::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(cause)
    }

    @Test
    fun `getErrorEvent should return correct errorEvent for unexpectedLoader when broadcast`() {
        val cause = mock<Loader.UnexpectedLoaderException>()
        val exoPlayerErrorCode = 123
        val throwable = mock<ExoPlaybackException> {
            on { it.cause } doReturn cause
        }
        throwable.reflectionSetErrorCode(exoPlayerErrorCode)
        val extra = ErrorCodeFactory.Extra.Broadcast
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForUnexpectedLoader(extra, exoPlayerErrorCode)).thenReturn(
            expectedErrorCode,
        )

        val actualErrorEvent = errorHandler.getErrorEvent(throwable, ProductType.BROADCAST)

        verify(errorCodeFactory).createForUnexpectedLoader(extra, exoPlayerErrorCode)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.NotAllowed::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(cause)
    }

    @Test
    fun `getErrorEvent should return correct errorEvent for StorageException`() {
        val cause = mock<StorageException>()
        val exception = mock<Loader.UnexpectedLoaderException> {
            on { this.cause } doReturn cause
        }
        val exoPlayerErrorCode = 123
        val throwable = mock<ExoPlaybackException> {
            on { it.cause } doReturn exception
        }
        throwable.reflectionSetErrorCode(exoPlayerErrorCode)
        val extra = ErrorCodeFactory.Extra.Storage
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForOther(extra, exoPlayerErrorCode)).thenReturn(
            expectedErrorCode,
        )

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForOther(extra, exoPlayerErrorCode)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.NotAllowed::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(cause)
    }

    @Test
    fun `getErrorEvent should return correct errorEvent for UnexpectedLoaderException`() {
        val cause = mock<Loader.UnexpectedLoaderException>()
        val exoPlayerErrorCode = 123
        val throwable = mock<ExoPlaybackException> {
            on { it.cause } doReturn cause
        }
        throwable.reflectionSetErrorCode(exoPlayerErrorCode)
        val extra = ErrorCodeFactory.Extra.Other
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForUnexpectedLoader(extra, exoPlayerErrorCode)).thenReturn(
            expectedErrorCode,
        )

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForUnexpectedLoader(extra, exoPlayerErrorCode)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.Retryable::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(cause)
    }

    @Test
    fun `getErrorEvent should return correct errorEvent for BehindLiveWindowException`() {
        val cause = mock<BehindLiveWindowException>()
        val exoPlayerErrorCode = 123
        val throwable = mock<ExoPlaybackException> {
            on { it.cause } doReturn cause
        }
        throwable.reflectionSetErrorCode(exoPlayerErrorCode)
        val extra = ErrorCodeFactory.Extra.PlayerSourceError
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForBehindLiveWindow(extra, exoPlayerErrorCode)).thenReturn(
            expectedErrorCode,
        )

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForBehindLiveWindow(extra, exoPlayerErrorCode)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.Retryable::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(cause)
    }

    @Test
    fun `getErrorEvent should return correct errorEvent for unspecified io PlaybackException`() {
        val exoPlayerErrorCode = PlaybackException.ERROR_CODE_IO_UNSPECIFIED
        val throwable = mock<ExoPlaybackException>()
        throwable.reflectionSetErrorCode(exoPlayerErrorCode)
        throwable.reflectionSetType(ExoPlaybackException.TYPE_SOURCE)
        val extra = ErrorCodeFactory.Extra.PlayerSourceError
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForOther(extra, exoPlayerErrorCode))
            .thenReturn(expectedErrorCode)

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForOther(extra, exoPlayerErrorCode)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.Retryable::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(throwable)
    }

    @Test
    fun `getErrorEvent should return correct errorEvent for network failed PlaybackException`() {
        val exoPlayerErrorCode = PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED
        val throwable = mock<ExoPlaybackException>()
        throwable.reflectionSetErrorCode(exoPlayerErrorCode)
        throwable.reflectionSetType(ExoPlaybackException.TYPE_SOURCE)
        val extra = ErrorCodeFactory.Extra.PlayerSourceError
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForNetwork(extra, exoPlayerErrorCode))
            .thenReturn(expectedErrorCode)

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForNetwork(extra, exoPlayerErrorCode)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.Network::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(throwable)
    }

    @Test
    fun `getErrorEvent should return correct errorEvent for network timeout PlaybackException`() {
        val exoPlayerErrorCode = PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT
        val throwable = mock<ExoPlaybackException>()
        throwable.reflectionSetErrorCode(exoPlayerErrorCode)
        throwable.reflectionSetType(ExoPlaybackException.TYPE_SOURCE)
        val extra = ErrorCodeFactory.Extra.PlayerSourceError
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForTimeout(extra, exoPlayerErrorCode))
            .thenReturn(expectedErrorCode)

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForTimeout(extra, exoPlayerErrorCode)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.Network::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(throwable)
    }

    @Test
    fun `getErrorEvent should return correct errorEvent for timeouts on source`() {
        val cause = mock<SocketTimeoutException>()
        val exception = mock<IOException> {
            on { this.cause } doReturn cause
        }
        val throwable = mock<ExoPlaybackException> {
            on { it.cause } doReturn exception
        }
        val extra = ErrorCodeFactory.Extra.PlayerSourceError
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForTimeout(extra, 0)).thenReturn(expectedErrorCode)

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForTimeout(extra, 0)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.Network::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(cause)
    }

    @ParameterizedTest
    @ValueSource(
        classes = [
            ConnectException::class,
            UnknownHostException::class,
        ],
    )
    fun `getErrorEvent should return correct errorEvent for network connection errors on source`(
        ioException: Class<IOException>,
    ) {
        val cause = ioException.getConstructor().newInstance()
        val exception = mock<IOException> {
            on { this.cause } doReturn cause
        }
        val throwable = mock<ExoPlaybackException> {
            on { it.cause } doReturn exception
        }
        val extra = ErrorCodeFactory.Extra.PlayerSourceError
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForNetwork(extra, 0)).thenReturn(expectedErrorCode)

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForNetwork(extra, 0)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.Network::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(cause)
    }

    @Test
    fun `getErrorEvent should return correct errorEvent for unknown source errors`() {
        val exception = mock<IOException> {
            on { this.cause } doReturn mock()
        }
        val throwable = mock<ExoPlaybackException> {
            on { sourceException } doReturn exception
        }
        val extra = ErrorCodeFactory.Extra.PlayerSourceError
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForOther(extra, 0)).thenReturn(expectedErrorCode)

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForOther(extra, 0)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.Unexpected::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(throwable)
    }

    @Test
    fun `getErrorEvent should return correct errorEvent for DrmSessionException for renderer`() {
        val cause = mock<DrmSessionException>()
        val exoPlayerErrorCode = 123
        val throwable = mock<ExoPlaybackException> {
            on { it.cause } doReturn cause
        }
        throwable.reflectionSetErrorCode(exoPlayerErrorCode)
        throwable.reflectionSetType(ExoPlaybackException.TYPE_RENDERER)
        val extra = ErrorCodeFactory.Extra.PlayerRendererError
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForDrmSession(extra, exoPlayerErrorCode)).thenReturn(
            expectedErrorCode,
        )

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForDrmSession(extra, exoPlayerErrorCode)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.NotAllowed::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(cause)
    }

    @Test
    fun `getErrorEvent should return correct errorEvent for AudioSink_InitializationException`() {
        val cause = mock<AudioSink.InitializationException>()
        val exoPlayerErrorCode = 123
        val throwable = mock<ExoPlaybackException> {
            on { it.cause } doReturn cause
        }
        throwable.reflectionSetErrorCode(exoPlayerErrorCode)
        throwable.reflectionSetType(ExoPlaybackException.TYPE_RENDERER)
        val extra = ErrorCodeFactory.Extra.PlayerRendererError
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForAudioSinkInitialization(extra, exoPlayerErrorCode))
            .thenReturn(expectedErrorCode)

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForAudioSinkInitialization(extra, exoPlayerErrorCode)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.NotAllowed::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(cause)
    }

    @Test
    fun `getErrorEvent should return correct errorEvent for unknown renderer errors`() {
        val throwable = mock<ExoPlaybackException> {
            on { rendererException } doReturn mock()
        }
        throwable.reflectionSetType(ExoPlaybackException.TYPE_RENDERER)
        val extra = ErrorCodeFactory.Extra.PlayerRendererError
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForOther(extra, 0)).thenReturn(expectedErrorCode)

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForOther(extra, 0)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.Unexpected::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(throwable)
    }

    @Test
    fun `getErrorEvent should return correct errorEvent for unexpected errors`() {
        val throwable = mock<ExoPlaybackException> {
            on { unexpectedException } doReturn mock()
        }
        throwable.reflectionSetType(ExoPlaybackException.TYPE_UNEXPECTED)
        val extra = ErrorCodeFactory.Extra.PlayerUnexpectedError
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForOther(extra, 0)).thenReturn(expectedErrorCode)

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForOther(extra, 0)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.Unexpected::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(throwable)
    }

    @Test
    fun `getErrorEvent should return correct errorEvent for other errors`() {
        val throwable = mock<ExoPlaybackException>()
        throwable.reflectionSetErrorCode(PlaybackException.ERROR_CODE_REMOTE_ERROR)
        throwable.reflectionSetType(ExoPlaybackException.TYPE_REMOTE)
        val extra = ErrorCodeFactory.Extra.PlayerOtherError
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForOther(extra, PlaybackException.ERROR_CODE_REMOTE_ERROR))
            .thenReturn(expectedErrorCode)

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForOther(extra, PlaybackException.ERROR_CODE_REMOTE_ERROR)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.Unexpected::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(throwable)
    }

    @Test
    fun `getErrorEvent should return correct errorEvent for timeouts`() {
        val throwable = SocketTimeoutException()
        val extra = ErrorCodeFactory.Extra.Other
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForTimeout(extra, null)).thenReturn(
            expectedErrorCode,
        )

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForTimeout(extra, null)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.Network::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(throwable)
    }

    @ParameterizedTest
    @ValueSource(
        classes = [
            ConnectException::class,
            UnknownHostException::class,
        ],
    )
    fun `getErrorEvent should return correct errorEvent for network connection errors`(
        ioException: Class<IOException>,
    ) {
        val throwable = ioException.getConstructor().newInstance()
        val extra = ErrorCodeFactory.Extra.Other
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForNetwork(extra, null)).thenReturn(
            expectedErrorCode,
        )

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForNetwork(extra, null)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.Network::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(throwable)
    }

    @Test
    fun `getErrorEvent should return correct errorEvent for OfflineExpiredException`() {
        val extra = ErrorCodeFactory.Extra.OfflineExpired
        val throwable = mock<OfflineExpiredException>()
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForOther(extra, null)).thenReturn(expectedErrorCode)

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForOther(extra, null)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.NotAllowed::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(throwable)
    }

    @Test
    fun `getErrorEvent should return correct errorEvent for unknown errors`() {
        val throwable = IOException()
        val extra = ErrorCodeFactory.Extra.Other
        val expectedErrorCode = "errorCode"
        whenever(errorCodeFactory.createForOther(extra, null)).thenReturn(
            expectedErrorCode,
        )

        val actualErrorEvent = errorHandler.getErrorEvent(throwable)

        verify(errorCodeFactory).createForOther(extra, null)
        assertThat(actualErrorEvent).isInstanceOf(Event.Error.Unexpected::class)
        assertThat(actualErrorEvent.errorCode).isSameAs(expectedErrorCode)
        assertThat(actualErrorEvent.cause).isSameAs(throwable)
    }

    companion object {

        @JvmStatic
        @Suppress("UnusedPrivateMember")
        private fun apiErrorSubStatusesForNotAllowed() = setOf(
            Arguments.of(ApiError.SubStatus.GenericPlaybackError),
            Arguments.of(ApiError.SubStatus.NoStreamingPrivileges),
            Arguments.of(ApiError.SubStatus.UserClientNotAuthorizedForOffline),
            Arguments.of(ApiError.SubStatus.SessionNotFound),
            Arguments.of(ApiError.SubStatus.UserNotFound),
            Arguments.of(ApiError.SubStatus.ClientNotFound),
            Arguments.of(ApiError.SubStatus.ProductNotFound),
            Arguments.of(ApiError.SubStatus.NoContentAvailableInProduct),
            Arguments.of(ApiError.SubStatus.NoContentMatchingRequest),
            Arguments.of(ApiError.SubStatus.NoContentMatchingClient),
        )

        @JvmStatic
        @Suppress("UnusedPrivateMember")
        private fun apiErrorSubStatusesForContentNotAvailableInLocation() = setOf(
            Arguments.of(ApiError.SubStatus.NoContentMatchingSubscriptionLocation),
            Arguments.of(ApiError.SubStatus.NoContentMatchingPrePaywallLocation),
        )
    }
}
