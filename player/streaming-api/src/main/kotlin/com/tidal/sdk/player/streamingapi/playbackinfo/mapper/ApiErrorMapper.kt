package com.tidal.sdk.player.streamingapi.playbackinfo.mapper

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.tidal.sdk.player.common.model.ApiError
import com.tidal.sdk.tidalapi.generated.models.TrackFilesReadById403ResponseBodyErrorsInner.Code as ForbiddenCode
import com.tidal.sdk.tidalapi.generated.models.TrackFilesReadById404ResponseBodyErrorsInner.Code as NotFoundCode
import retrofit2.HttpException

/**
 * A mapper for [ApiError] that takes in an [ApiError.Factory] to help with the mapping if the
 * exception is an [HttpException] with a recognized api sub-status.
 */
internal class ApiErrorMapper(
    private val apiErrorFactory: ApiError.Factory,
    private val gson: Gson,
) {

    fun map(e: HttpException) =
        apiErrorFactory.fromJsonStringOrCause(e.response()?.errorBody()?.string(), e)

    fun mapJsonApi(e: HttpException): RuntimeException =
        try {
            val firstError =
                gson
                    .fromJson(e.response()?.errorBody()?.string(), JsonObject::class.java)
                    ?.getAsJsonArray("errors")
                    ?.firstOrNull()
                    ?.takeIf { it.isJsonObject }
                    ?.asJsonObject
            if (firstError != null) {
                apiErrorFactory.create(
                    cause = e,
                    status = firstError["status"]?.asString?.toIntOrNull(),
                    subStatus = mapCodeToSubStatus(firstError["code"]?.asString),
                    userMessage = firstError["detail"]?.asString,
                )
            } else {
                e
            }
        } catch (_: JsonSyntaxException) {
            e
        }

    companion object {
        private fun mapCodeToSubStatus(code: String?): ApiError.SubStatus =
            when (code) {
                ForbiddenCode.GEO_RESTRICTED.value ->
                    ApiError.SubStatus.NoContentMatchingSubscriptionLocation
                ForbiddenCode.PURCHASE_REQUIRED.value ->
                    ApiError.SubStatus.NoContentMatchingSubscriptionConfiguration
                ForbiddenCode.CONCURRENT_PLAYBACK.value -> ApiError.SubStatus.NoStreamingPrivileges
                ForbiddenCode.CLIENT_NOT_ENTITLED.value,
                ForbiddenCode.PREREQUISITE_MISSING.value,
                NotFoundCode.CONTENT_NOT_FOUND.value -> ApiError.SubStatus.GenericPlaybackError
                else -> ApiError.SubStatus.Unknown(-1)
            }
    }
}
