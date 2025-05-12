package com.tidal.sdk.player.streamingapi.network.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isSameAs
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.tidal.sdk.player.common.model.ApiError
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyNoMoreInteractions

internal class ApiErrorFactoryBlackBoxTest {

    private val gson = Gson()
    private val apiErrorFactory = ApiError.Factory(gson)

    @ParameterizedTest
    @MethodSource("apiErrorSubStatuses")
    fun happyPath(subStatus: ApiError.SubStatus) {
        val cause = mock<RuntimeException>()
        val status = 1
        val userMessage = "userMessage"
        val json =
            gson.toJson(
                JsonObject().apply {
                    addProperty("status", status)
                    addProperty("subStatus", subStatus.code)
                    addProperty("userMessage", userMessage)
                }
            )

        val actual = apiErrorFactory.fromJsonStringOrCause(json, cause) as ApiError

        with(actual) {
            assertThat(this.status).isEqualTo(status)
            assertThat(this.subStatus).isEqualTo(subStatus)
            assertThat(this.userMessage).isEqualTo(userMessage)
        }
        verifyNoMoreInteractions(cause)
    }

    @Test
    fun nullJson() {
        val cause = mock<RuntimeException>()
        val actual = apiErrorFactory.fromJsonStringOrCause(null, cause)

        assertThat(actual).isSameAs(cause)
        verifyNoMoreInteractions(cause)
    }

    companion object {

        @JvmStatic
        @Suppress("UnusedPrivateMember")
        private fun apiErrorSubStatuses() =
            setOf(
                Arguments.of(ApiError.SubStatus.GenericPlaybackError),
                Arguments.of(ApiError.SubStatus.NoStreamingPrivileges),
                Arguments.of(ApiError.SubStatus.UserClientNotAuthorizedForOffline),
                Arguments.of(ApiError.SubStatus.UserMonthlyStreamQuotaExceeded),
                Arguments.of(ApiError.SubStatus.SessionNotFound),
                Arguments.of(ApiError.SubStatus.UserNotFound),
                Arguments.of(ApiError.SubStatus.ClientNotFound),
                Arguments.of(ApiError.SubStatus.ProductNotFound),
                Arguments.of(ApiError.SubStatus.NoContentAvailableInProduct),
                Arguments.of(ApiError.SubStatus.NoContentMatchingRequest),
                Arguments.of(ApiError.SubStatus.NoContentMatchingSubscriptionLocation),
                Arguments.of(ApiError.SubStatus.NoContentMatchingSubscriptionConfiguration),
                Arguments.of(ApiError.SubStatus.NoContentMatchingClient),
                Arguments.of(ApiError.SubStatus.NoContentMatchingPrePaywallLocation),
                Arguments.of(ApiError.SubStatus.Unknown(-1)),
                Arguments.of(ApiError.SubStatus.Unknown(1234)),
            )
    }
}
