package com.tidal.sdk.player.streamingapi.playbackinfo.mapper

import com.tidal.sdk.player.common.model.ApiError
import retrofit2.HttpException

/**
 * A mapper for [ApiError] that takes in an [ApiError.Factory] to help with the mapping if the
 * exception is an [HttpException] with a recognized api sub-status.
 */
internal class ApiErrorMapper(private val apiErrorFactory: ApiError.Factory) {

    fun map(e: HttpException) =
        apiErrorFactory.fromJsonStringOrCause(e.response()?.errorBody()?.string(), e)
}
