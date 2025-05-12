package com.tidal.sdk.player.playbackengine

import androidx.media3.common.C
import androidx.media3.common.ParserException
import androidx.media3.datasource.HttpDataSource
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy.LoadErrorInfo
import androidx.media3.exoplayer.upstream.Loader.UnexpectedLoaderException
import com.tidal.sdk.player.common.model.ApiError
import com.tidal.sdk.player.playbackengine.offline.OfflineExpiredException
import com.tidal.sdk.player.playbackengine.offline.StorageException
import java.io.FileNotFoundException
import java.io.IOException
import retrofit2.HttpException

private const val MAX_5XX_RETRIES = 3
private const val MAX_RETRY_INTERVAL_MS = 5_000L
private const val TOO_MANY_REQUESTS_STATUS = 429

internal class PlayerLoadErrorHandlingPolicy(
    private val loadErrorHandlingPolicy: LoadErrorHandlingPolicy
) : LoadErrorHandlingPolicy by loadErrorHandlingPolicy {

    @Suppress("MagicNumber") private val responseCode4xxRange = (400..499)

    @Suppress("MagicNumber") private val responseCode5xxRange = (500..599)

    @Suppress("MagicNumber") private val backoffIntervalsMs = listOf(500L, 1_000L, 2_000L)

    override fun getRetryDelayMsFor(loadErrorInfo: LoadErrorInfo): Long {
        if (shouldNotRetry(loadErrorInfo.exception, loadErrorInfo.errorCount)) {
            return C.TIME_UNSET
        }

        val retryIndex = loadErrorInfo.errorCount - 1
        return if (retryIndex > -1 && retryIndex < backoffIntervalsMs.size) {
            backoffIntervalsMs[retryIndex]
        } else {
            MAX_RETRY_INTERVAL_MS
        }
    }

    private fun shouldNotRetry(exception: IOException, errorCount: Int): Boolean {
        val responseCode = getResponseCode(exception)
        return (responseCode in responseCode5xxRange && errorCount > MAX_5XX_RETRIES) ||
            isNonRetryable4xxResponse(responseCode) ||
            exception is ParserException ||
            exception is FileNotFoundException ||
            exception is UnexpectedLoaderException ||
            exception is StorageException ||
            exception is OfflineExpiredException
    }

    private fun isNonRetryable4xxResponse(responseCode: Int) =
        responseCode in responseCode4xxRange.minus(TOO_MANY_REQUESTS_STATUS)

    private fun getResponseCode(exception: IOException): Int {
        return when {
            exception is HttpDataSource.InvalidResponseCodeException -> exception.responseCode
            exception.cause is ApiError -> (exception.cause as ApiError).status!!
            exception.cause is HttpException -> (exception.cause as HttpException).code()
            else -> -1
        }
    }
}
