package com.tidal.sdk.player.playbackengine

import androidx.media3.common.C
import androidx.media3.common.ParserException
import androidx.media3.datasource.HttpDataSource.InvalidResponseCodeException
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy.LoadErrorInfo
import androidx.media3.exoplayer.upstream.Loader.UnexpectedLoaderException
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.tidal.sdk.player.common.model.ApiError
import com.tidal.sdk.player.playbackengine.offline.OfflineExpiredException
import com.tidal.sdk.player.playbackengine.offline.StorageException
import java.io.FileNotFoundException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyNoMoreInteractions
import retrofit2.HttpException

internal class PlayerLoadErrorHandlingPolicyTest {

    private val gson = Gson()
    private val apiErrorFactory = ApiError.Factory(gson)
    private val loadErrorHandlingPolicy = mock<LoadErrorHandlingPolicy>()
    private val playerLoadErrorHandlingPolicy =
        PlayerLoadErrorHandlingPolicy(loadErrorHandlingPolicy)

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(loadErrorHandlingPolicy)

    @ParameterizedTest
    @ValueSource(ints = [400, 401, 403, 404, 405, 416])
    fun getRetryDelayMsForShouldNotRetryFor4xxInvalidResponseCode(responseCode: Int) {
        val exception = InvalidResponseCodeException(
            responseCode,
            null,
            null,
            emptyMap(),
            mock(),
            byteArrayOf(),
        )
        val loadErrorInfo = LoadErrorInfo(mock(), mock(), exception, 0)

        val actualRetryDelayMs = playerLoadErrorHandlingPolicy.getRetryDelayMsFor(loadErrorInfo)

        assertThat(actualRetryDelayMs).isEqualTo(C.TIME_UNSET)
    }

    @ParameterizedTest
    @ValueSource(ints = [400, 401, 403, 404, 405, 416])
    fun getRetryDelayMsForShouldNotRetryFor4xxApiError(responseCode: Int) {
        val json = gson.toJson(
            JsonObject().apply { addProperty("status", responseCode) },
        )
        val apiError = apiErrorFactory.fromJsonStringOrCause(json, mock())
        val exception = IOException(apiError)
        val loadErrorInfo = LoadErrorInfo(mock(), mock(), exception, 0)

        val actualRetryDelayMs = playerLoadErrorHandlingPolicy.getRetryDelayMsFor(loadErrorInfo)

        assertThat(actualRetryDelayMs).isEqualTo(C.TIME_UNSET)
    }

    @ParameterizedTest
    @MethodSource("nonRetryableExceptions")
    fun getRetryDelayMsForShouldNotRetryForSpecificExceptions(ioException: IOException) {
        val loadErrorInfo = LoadErrorInfo(mock(), mock(), ioException, 0)

        val actualRetryDelayMs = playerLoadErrorHandlingPolicy.getRetryDelayMsFor(loadErrorInfo)

        assertThat(actualRetryDelayMs).isEqualTo(C.TIME_UNSET)
    }

    @Test
    fun getRetryDelayMsForShouldNotRetryForUnexpectedLoaderException() {
        val exception = UnexpectedLoaderException(Throwable())
        val loadErrorInfo = LoadErrorInfo(mock(), mock(), exception, 0)

        val actualRetryDelayMs = playerLoadErrorHandlingPolicy.getRetryDelayMsFor(loadErrorInfo)

        assertThat(actualRetryDelayMs).isEqualTo(C.TIME_UNSET)
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, 0, 1, 2, 3, 4, 5, 10])
    fun getRetryDelayMsForShouldRetry(errorCount: Int) {
        val loadErrorInfo = LoadErrorInfo(mock(), mock(), mock(), errorCount)

        val actualRetryDelayMs = playerLoadErrorHandlingPolicy.getRetryDelayMsFor(loadErrorInfo)

        val expectedErrorCount: Long = when (errorCount) {
            1 -> 500
            2 -> 1_000
            3 -> 2_000
            else -> 5_000
        }
        assertThat(actualRetryDelayMs).isEqualTo(expectedErrorCount)
    }

    @ParameterizedTest
    @ValueSource(
        classes = [
            SocketTimeoutException::class,
            UnknownHostException::class,
            ConnectException::class,
        ],
    )
    fun getRetryDelayMsForShouldRetryForTimeoutOrNetworkErrorAsCause(causeClass: Class<Throwable>) {
        val exception = IOException(causeClass.getConstructor().newInstance())
        val loadErrorInfo = LoadErrorInfo(mock(), mock(), exception, 1)

        val actualRetryDelayMs = playerLoadErrorHandlingPolicy.getRetryDelayMsFor(loadErrorInfo)

        assertThat(actualRetryDelayMs).isEqualTo(500)
    }

    @ParameterizedTest
    @ValueSource(
        classes = [
            SocketTimeoutException::class,
            UnknownHostException::class,
            ConnectException::class,
        ],
    )
    fun getRetryDelayMsForShouldRetryForTimeoutOrNetworkErrorAsMainException(
        ioExceptionClass: Class<IOException>,
    ) {
        val exception = ioExceptionClass.getConstructor().newInstance()
        val loadErrorInfo = LoadErrorInfo(mock(), mock(), exception, 1)

        val actualRetryDelayMs = playerLoadErrorHandlingPolicy.getRetryDelayMsFor(loadErrorInfo)

        assertThat(actualRetryDelayMs).isEqualTo(500)
    }

    @ParameterizedTest
    @ValueSource(ints = [500, 501, 502, 503, 504])
    fun getRetryDelayMsForShouldRetryFor5xxApiError(responseCode: Int) {
        val json = gson.toJson(
            JsonObject().apply { addProperty("status", responseCode) },
        )
        val apiError = apiErrorFactory.fromJsonStringOrCause(json, mock())
        val exception = IOException(apiError)
        val loadErrorInfo = LoadErrorInfo(mock(), mock(), exception, 1)

        val actualRetryDelayMs = playerLoadErrorHandlingPolicy.getRetryDelayMsFor(loadErrorInfo)

        assertThat(actualRetryDelayMs).isEqualTo(500)
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, 0, 1, 2, 3, 4, 5, 10])
    fun getRetryDelayMsForShouldRetryFor500ApiError(errorCount: Int) {
        val json = gson.toJson(
            JsonObject().apply { addProperty("status", 500) },
        )
        val apiError = apiErrorFactory.fromJsonStringOrCause(json, mock())
        val exception = IOException(apiError)
        val loadErrorInfo = LoadErrorInfo(mock(), mock(), exception, errorCount)

        val actualRetryDelayMs = playerLoadErrorHandlingPolicy.getRetryDelayMsFor(loadErrorInfo)

        val expectedErrorCount: Long = if (errorCount <= 3) {
            when (errorCount) {
                1 -> 500
                2 -> 1_000
                3 -> 2_000
                else -> 5_000
            }
        } else {
            C.TIME_UNSET
        }
        assertThat(actualRetryDelayMs).isEqualTo(expectedErrorCount)
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, 0, 1, 2, 3, 4, 5, 10])
    fun getRetryDelayMsForShouldRetryFor429ApiError(errorCount: Int) {
        val httpException = mock<HttpException> {
            on { this.code() } doReturn 429
        }
        val exception = IOException(httpException)
        val loadErrorInfo = LoadErrorInfo(mock(), mock(), exception, errorCount)

        val actualRetryDelayMs = playerLoadErrorHandlingPolicy.getRetryDelayMsFor(loadErrorInfo)

        val expectedErrorCount: Long = when (errorCount) {
            1 -> 500
            2 -> 1_000
            3 -> 2_000
            else -> 5_000
        }
        assertThat(actualRetryDelayMs).isEqualTo(expectedErrorCount)
    }

    companion object {

        @JvmStatic
        fun nonRetryableExceptions() = setOf(
            ParserException.createForUnsupportedContainerFeature(null),
            FileNotFoundException(),
            StorageException(),
            OfflineExpiredException(),
        )
    }
}
