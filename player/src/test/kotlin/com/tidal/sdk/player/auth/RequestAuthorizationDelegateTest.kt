package com.tidal.sdk.player.auth

import assertk.assertThat
import assertk.assertions.isNull
import assertk.assertions.isSameAs
import com.tidal.sdk.auth.model.Credentials
import okhttp3.HttpUrl
import okhttp3.Request
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class RequestAuthorizationDelegateTest {

    private val endpointsRequiringCredentialLevels = mock<Map<String, Set<Credentials.Level>>>()
    private val requestAuthorizationDelegate = RequestAuthorizationDelegate(
        endpointsRequiringCredentialLevels,
    )

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(endpointsRequiringCredentialLevels)

    @Test
    fun invokeEndpointDoesNotHaveLevelRequirement() {
        val requestUrl = mock<HttpUrl>()
        val expected = mock<Request>()
        val tokenString = "token"
        val newBuilder = mock<Request.Builder> {
            on { header(HEADER_AUTHORIZATION, "${TokenType.BEARER.type} $tokenString") }
                .thenReturn(it)
            on { build() } doReturn expected
        }
        val request = mock<Request> {
            on { url } doReturn requestUrl
            on { newBuilder() } doReturn newBuilder
        }
        val credentials = mock<Credentials> {
            on { token } doReturn tokenString
        }
        whenever(endpointsRequiringCredentialLevels[requestUrl.toString()]) doReturn null

        val actual = requestAuthorizationDelegate(request, credentials)

        verify(request).url
        verify(endpointsRequiringCredentialLevels)[requestUrl.toString()]
        verify(request).newBuilder()
        verify(credentials).token
        inOrder(newBuilder) {
            verify(newBuilder)
                .header(HEADER_AUTHORIZATION, "${TokenType.BEARER.type} $tokenString")
            verify(newBuilder).build()
        }
        assertThat(actual).isSameAs(expected)
        verifyNoMoreInteractions(requestUrl, expected, newBuilder, request, credentials)
    }

    @Test
    fun invokeEndpointRequiresMissingLevel() {
        val requestUrl = mock<HttpUrl>()
        val request = mock<Request> {
            on { url } doReturn requestUrl
        }
        val credentialLevel = mock<Credentials.Level>()
        val credentials = mock<Credentials> {
            on { level } doReturn credentialLevel
        }
        val allowedLevelSet = mock<Set<Credentials.Level>>()
        whenever(endpointsRequiringCredentialLevels[requestUrl.toString()]) doReturn allowedLevelSet
        whenever(allowedLevelSet.contains(credentialLevel)) doReturn false

        val actual = requestAuthorizationDelegate(request, credentials)

        verify(request).url
        verify(endpointsRequiringCredentialLevels)[requestUrl.toString()]
        verify(credentials).level
        verify(allowedLevelSet).contains(credentialLevel)
        assertThat(actual).isNull()
        verifyNoMoreInteractions(
            requestUrl,
            request,
            credentialLevel,
            credentials,
            allowedLevelSet,
        )
    }

    @Test
    fun invokeHappyPath() {
        val requestUrl = mock<HttpUrl>()
        val expected = mock<Request>()
        val tokenString = "token"
        val newBuilder = mock<Request.Builder> {
            on { header(HEADER_AUTHORIZATION, "${TokenType.BEARER.type} $tokenString") }
                .thenReturn(it)
            on { build() } doReturn expected
        }
        val request = mock<Request> {
            on { url } doReturn requestUrl
            on { newBuilder() } doReturn newBuilder
        }
        val credentialLevel = mock<Credentials.Level>()
        val credentials = mock<Credentials> {
            on { token } doReturn tokenString
            on { level } doReturn credentialLevel
        }
        val allowedLevelSet = mock<Set<Credentials.Level>>()
        whenever(endpointsRequiringCredentialLevels[requestUrl.toString()]) doReturn allowedLevelSet
        whenever(allowedLevelSet.contains(credentialLevel)) doReturn true

        val actual = requestAuthorizationDelegate(request, credentials)

        verify(request).url
        verify(endpointsRequiringCredentialLevels)[requestUrl.toString()]
        verify(credentials).level
        verify(allowedLevelSet).contains(credentialLevel)
        verify(request).newBuilder()
        verify(credentials).token
        inOrder(newBuilder) {
            verify(newBuilder)
                .header(HEADER_AUTHORIZATION, "${TokenType.BEARER.type} $tokenString")
            verify(newBuilder).build()
        }
        assertThat(actual).isSameAs(expected)
        verifyNoMoreInteractions(
            requestUrl,
            expected,
            newBuilder,
            request,
            credentialLevel,
            credentials,
            allowedLevelSet,
        )
    }
}
