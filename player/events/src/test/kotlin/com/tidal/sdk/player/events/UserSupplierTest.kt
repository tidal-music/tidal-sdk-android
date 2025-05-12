package com.tidal.sdk.player.events

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isSameAs
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.auth.model.AuthResult
import com.tidal.sdk.auth.model.Credentials
import com.tidal.sdk.player.commonandroid.jwt.Base64JwtDecoder
import com.tidal.sdk.player.events.model.reflectionId
import com.tidal.sdk.player.events.model.reflectionSessionId
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

class UserSupplierTest {

    private val base64JwtDecoder = mock<Base64JwtDecoder>()
    private val credentialsProvider = mock<CredentialsProvider>()
    private val userSupplier = UserSupplier(base64JwtDecoder, credentialsProvider) { 1 }

    @AfterEach fun afterEach() = verifyNoMoreInteractions(base64JwtDecoder, credentialsProvider)

    @Test
    fun invokeHappyPath() = runBlocking {
        val token = "token"
        val credentials = mock<Credentials> { on { it.token } doReturn token }
        val authResult =
            mock<AuthResult.Success<Credentials>> { on { it.successData } doReturn credentials }
        val userIdString = "123"
        val userId = mock<JsonPrimitive> { on { it.jsonPrimitive.content } doReturn userIdString }
        val userIdKey = "uid"
        val sessionIdString = "123-abc"
        val sessionId =
            mock<JsonPrimitive> { on { it.jsonPrimitive.content } doReturn sessionIdString }
        val sessionIdKey = "sid"
        val claims =
            mock<JsonObject> {
                on { it[userIdKey] } doReturn userId
                on { it[sessionIdKey] } doReturn sessionId
            }
        whenever(credentialsProvider.getCredentials()).thenReturn(authResult)
        whenever(base64JwtDecoder.getClaims(token)).thenReturn(claims)

        val actualUser = userSupplier.invoke()

        assertThat(actualUser.reflectionId).isEqualTo(userIdString.toLong())
        assertThat(actualUser.reflectionSessionId).isEqualTo(sessionIdString)
        verify(credentialsProvider).getCredentials()
        verify(authResult).successData
        verify(base64JwtDecoder).getClaims(token)
        verify(claims)[userIdKey]
        verify(claims)[sessionIdKey]
        Unit
    }

    @Test
    fun invokeWithEmptyToken() = runBlocking {
        val credentials = mock<Credentials> { on { it.token } doReturn "" }
        val authResult =
            mock<AuthResult.Success<Credentials>> { on { it.successData } doReturn credentials }
        whenever(credentialsProvider.getCredentials()).thenReturn(authResult)

        val actualUser = userSupplier.invoke()

        assertThat(actualUser.reflectionId).isEqualTo(-1)
        assertThat(actualUser.reflectionSessionId).isSameAs("")
        verify(credentialsProvider).getCredentials()
        verify(authResult).successData
        Unit
    }
}
