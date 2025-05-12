package com.tidal.sdk.player.common.model

import androidx.annotation.Keep
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.tidal.sdk.player.common.model.ApiError.SubStatus.Companion.mapToSubStatus

/**
 * A representation of our api errors from our backend.
 *
 * @param[status] The http error code, e.g. 404 or 500.
 * @param[subStatus] The sub status, specifying what type of error it is.
 * @param[userMessage] A descriptive message to help the user know what went wrong.
 */
class ApiError
private constructor(
    cause: Throwable?,
    val status: Int?,
    val subStatus: SubStatus,
    val userMessage: String?,
) :
    RuntimeException(
        """${ApiError::class.simpleName}(
        status=$status,
        subStatus=${subStatus.code} (${subStatus::class.simpleName}),
        userMessage=$userMessage
    )
    """
            .trimIndent(),
        cause,
    ) {

    class Factory(private val gson: Gson) {

        fun fromJsonStringOrCause(json: String?, cause: RuntimeException) =
            try {
                gson.fromJson(json, JsonObject::class.java)?.run {
                    ApiError(
                        cause,
                        this["status"]?.asInt,
                        this["subStatus"]?.asInt.mapToSubStatus(),
                        this["userMessage"]?.asString,
                    )
                } ?: cause
            } catch (ignored: JsonSyntaxException) {
                cause
            }
    }

    /**
     * The different sub status codes sent from our backend. These tell us why something went wrong.
     */
    @Keep
    sealed class SubStatus(open val code: Int) {
        object GenericPlaybackError : SubStatus(GENERIC_PLAYBACK_ERROR)

        object NoStreamingPrivileges : SubStatus(NO_STREAMING_PRIVILEGES)

        object UserClientNotAuthorizedForOffline :
            SubStatus(USER_CLIENT_NOT_AUTHORIZED_FOR_OFFLINE)

        object UserMonthlyStreamQuotaExceeded : SubStatus(USER_MONTHLY_STREAM_QUOTA_EXCEEDED)

        object SessionNotFound : SubStatus(SESSION_NOT_FOUND)

        object UserNotFound : SubStatus(USER_NOT_FOUND)

        object ClientNotFound : SubStatus(CLIENT_NOT_FOUND)

        object ProductNotFound : SubStatus(PRODUCT_NOT_FOUND)

        object NoContentAvailableInProduct : SubStatus(NO_CONTENT_AVAILABLE_IN_PRODUCT)

        object NoContentMatchingRequest : SubStatus(NO_CONTENT_MATCHING_REQUEST)

        object NoContentMatchingSubscriptionLocation :
            SubStatus(NO_CONTENT_MATCHING_SUBSCRIPTION_LOCATION)

        object NoContentMatchingSubscriptionConfiguration :
            SubStatus(NO_CONTENT_MATCHING_SUBSCRIPTION_CONFIGURATION)

        object NoContentMatchingClient : SubStatus(NO_CONTENT_MATCHING_CLIENT)

        object NoContentMatchingPrePaywallLocation :
            SubStatus(NO_CONTENT_MATCHING_PRE_PAYWALL_LOCATION)

        data class Unknown(override val code: Int) : SubStatus(code)

        companion object {

            @SuppressWarnings("ComplexMethod")
            fun Int?.mapToSubStatus() =
                when (this) {
                    GENERIC_PLAYBACK_ERROR -> GenericPlaybackError
                    NO_STREAMING_PRIVILEGES -> NoStreamingPrivileges
                    USER_CLIENT_NOT_AUTHORIZED_FOR_OFFLINE -> UserClientNotAuthorizedForOffline
                    USER_MONTHLY_STREAM_QUOTA_EXCEEDED -> UserMonthlyStreamQuotaExceeded
                    SESSION_NOT_FOUND -> SessionNotFound
                    USER_NOT_FOUND -> UserNotFound
                    CLIENT_NOT_FOUND -> ClientNotFound
                    PRODUCT_NOT_FOUND -> ProductNotFound
                    NO_CONTENT_AVAILABLE_IN_PRODUCT -> NoContentAvailableInProduct
                    NO_CONTENT_MATCHING_REQUEST -> NoContentMatchingRequest
                    NO_CONTENT_MATCHING_SUBSCRIPTION_LOCATION ->
                        NoContentMatchingSubscriptionLocation
                    NO_CONTENT_MATCHING_SUBSCRIPTION_CONFIGURATION ->
                        NoContentMatchingSubscriptionConfiguration

                    NO_CONTENT_MATCHING_CLIENT -> NoContentMatchingClient
                    NO_CONTENT_MATCHING_PRE_PAYWALL_LOCATION -> NoContentMatchingPrePaywallLocation
                    null -> Unknown(-1)
                    else -> Unknown(this)
                }

            private const val GENERIC_PLAYBACK_ERROR = 4005
            private const val NO_STREAMING_PRIVILEGES = 4006
            private const val USER_CLIENT_NOT_AUTHORIZED_FOR_OFFLINE = 4007
            private const val USER_MONTHLY_STREAM_QUOTA_EXCEEDED = 4010
            private const val SESSION_NOT_FOUND = 4020
            private const val USER_NOT_FOUND = 4021
            private const val CLIENT_NOT_FOUND = 4022
            private const val PRODUCT_NOT_FOUND = 4023
            private const val NO_CONTENT_AVAILABLE_IN_PRODUCT = 4030
            private const val NO_CONTENT_MATCHING_REQUEST = 4031
            private const val NO_CONTENT_MATCHING_SUBSCRIPTION_LOCATION = 4032
            private const val NO_CONTENT_MATCHING_SUBSCRIPTION_CONFIGURATION = 4033
            private const val NO_CONTENT_MATCHING_CLIENT = 4034
            private const val NO_CONTENT_MATCHING_PRE_PAYWALL_LOCATION = 4035
        }
    }
}
