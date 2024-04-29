package com.tidal.sdk.eventproducer.model

import com.tidal.sdk.common.TidalMessage

sealed class Result<out T> {

    data class Success<out T>(val data: T?) : Result<T>()

    data class Failure(val message: TidalMessage?) : Result<Nothing>()

    /**
     * Convenience function to quickly check if this result is a [Result.Success]
     */
    val isSuccess get() = this is Success<*>

    /**
     * Convenience function to quickly check if this result is a [Result.Failure]
     */
    val isFailure get() = this is Failure

    /**
     * Helper property to directly access the data payload without having to
     * safequard it in every call
     */
    val successData: T?
        get() {
            return if (this is Success<T>) this.data else null
        }
}

/**
 * Creates a [Result.Success] with data payload
 */
fun <T> success(data: T?) = Result.Success(data)

/**
 * Creates a [Result.Failure]
 */
fun failure(message: TidalMessage? = null) = Result.Failure(message)
