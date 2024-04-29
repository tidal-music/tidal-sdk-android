package com.tidal.sdk.auth.model

import com.tidal.sdk.common.TidalMessage

sealed class AuthResult<out T> {

    data class Success<out T>(val data: T?) : AuthResult<T>()

    // deliberately using message here atm, might change later
    data class Failure(val message: TidalMessage?) : AuthResult<Nothing>()

    /**
     * Convenience function to quickly check if this result is a [AuthResult.Success]
     */
    val isSuccess get() = this is Success<*>

    /**
     * Convenience function to quickly check if this result is a [AuthResult.Failure]
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
 * Creates a [AuthResult.Success] with data payload
 */
fun <T> success(data: T?) = AuthResult.Success(data)

/**
 * Creates a [AuthResult.Failure] with all fields
 */
fun failure(message: TidalMessage? = null) = AuthResult.Failure(message)
