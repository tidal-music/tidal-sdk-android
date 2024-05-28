package com.tidal.sdk.player.auth

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.auth.model.AuthResult
import com.tidal.sdk.auth.model.Credentials
import com.tidal.sdk.common.TidalMessage
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * Test that the [AuthorizationInterceptor] sets correct Authorization header on the request.
 */
internal class AuthorizationInterceptorTest {

    @get:ExtendWith
    val server = MockWebServer()

    private var accessToken = ""

    private val credentialsProvider = object : CredentialsProvider {
        override val bus: Flow<TidalMessage>
            get() = throw IllegalAccessException("Not supported")

        override suspend fun getCredentials(apiErrorSubStatus: String?) =
            AuthResult.Success(
                Credentials(
                    clientId = "clientId",
                    requestedScopes = emptySet(),
                    clientUniqueKey = null,
                    grantedScopes = emptySet(),
                    userId = null,
                    expires = null,
                    token = accessToken,
                ),
            )

        override fun isUserLoggedIn() = throw IllegalAccessException("Not supported")
    }

    private val shouldAddAuthorizationHeader = mock<ShouldAddAuthorizationHeader>()

    private val authorizationInterceptor = AuthorizationInterceptor(
        credentialsProvider,
        RequestAuthorizationDelegate(emptyMap()),
        shouldAddAuthorizationHeader,
    )

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authorizationInterceptor)
        .build()

    private val request = Request.Builder()
        .url(server.url("/"))
        .build()

    @BeforeEach
    fun setUp() {
        accessToken = "accessToken"
    }

    @Test
    fun `Should add correct authorization header to the request`() {
        server.enqueue(MockResponse())

        whenever(shouldAddAuthorizationHeader("localhost")).thenReturn(true)

        okHttpClient.newCall(request).execute().use {
            assertThat(it.request.header("Authorization"))
                .isEqualTo("Bearer accessToken")
        }
    }

    @Test
    fun `Should not add authorization header to the request`() {
        server.enqueue(MockResponse())

        whenever(shouldAddAuthorizationHeader("localhost")).thenReturn(false)

        okHttpClient.newCall(request).execute().use {
            assertThat(it.request.header("Authorization"))
                .isNull()
        }
    }

    @Test
    fun `Should add correct authorization header to the request after it has changed`() {
        server.enqueue(MockResponse())
        server.enqueue(MockResponse())

        whenever(shouldAddAuthorizationHeader("localhost")).thenReturn(true)

        okHttpClient.newCall(request).execute().use {
            assertThat(it.request.header("Authorization"))
                .isEqualTo("Bearer accessToken")
        }

        accessToken = "newAccessToken"

        okHttpClient.newCall(request).execute().use {
            assertThat(it.request.header("Authorization"))
                .isEqualTo("Bearer newAccessToken")
        }
    }
}
