package com.tidal.sdk.player.streamingapi.network.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isSameInstanceAs
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.tidal.sdk.player.common.model.ApiError
import org.junit.After
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class ApiErrorFactoryTest {

    private val gson = mock<Gson>()
    private val apiErrorFactory = ApiError.Factory(gson)

    @After fun afterEach() = verifyNoMoreInteractions(gson)

    @ParameterizedTest
    @MethodSource("apiErrorSubStatuses")
    fun validStringDelegates(subStatus: ApiError.SubStatus) {
        val cause = mock<RuntimeException>()
        val json = "a valid json string"
        val status = 1
        val userMessage = "userMessage"
        val statusElement = mock<JsonElement> { on { asInt } doReturn status }
        val subStatusElement = mock<JsonElement> { on { asInt } doReturn subStatus.code }
        val userMessageElement = mock<JsonElement> { on { asString } doReturn userMessage }
        val jsonObject =
            mock<JsonObject> {
                on { get("status") } doReturn statusElement
                on { get("subStatus") } doReturn subStatusElement
                on { get("userMessage") } doReturn userMessageElement
            }
        whenever(gson.fromJson(json, JsonObject::class.java)) doReturn jsonObject

        val actual = apiErrorFactory.fromJsonStringOrCause(json, cause) as ApiError

        verify(gson).fromJson(json, JsonObject::class.java)
        verify(jsonObject)["status"]
        verify(statusElement).asInt
        verify(jsonObject)["subStatus"]
        verify(subStatusElement).asInt
        verify(jsonObject)["userMessage"]
        verify(userMessageElement).asString
        with(actual) {
            assertThat(this.status).isEqualTo(status)
            assertThat(this.subStatus).isEqualTo(subStatus)
            assertThat(this.userMessage).isEqualTo(userMessage)
        }
        verifyNoMoreInteractions(
            cause,
            statusElement,
            subStatusElement,
            userMessageElement,
            jsonObject,
        )
    }

    @Test
    fun invalidStringDelegates() {
        val cause = mock<RuntimeException>()
        val json = "an invalid json string"
        whenever(gson.fromJson(json, JsonObject::class.java)) doReturn null

        val actual = apiErrorFactory.fromJsonStringOrCause(json, cause)

        verify(gson).fromJson(json, JsonObject::class.java)
        assertThat(actual).isSameInstanceAs(cause)
        verifyNoInteractions(cause)
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
