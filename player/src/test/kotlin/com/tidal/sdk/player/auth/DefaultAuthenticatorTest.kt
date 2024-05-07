package com.tidal.sdk.player.auth

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.auth.model.AuthResult
import com.tidal.sdk.auth.model.Credentials
import com.tidal.sdk.common.TidalMessage
import com.tidal.sdk.player.MockWebServerExtensions.enqueueResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Test that the [DefaultAuthenticator] refreshes token if not valid, logs in if needed, but also
 * retries the original request if the new token is valid, and variations of this.
 */
internal class DefaultAuthenticatorTest {

    @get:ExtendWith
    val server = MockWebServer()

    private var accessToken = ""

    private val request = Request.Builder()
        .url(server.url("/"))
        .header("Authorization", "Bearer accessToken")
        .build()

    @BeforeEach
    fun setUp() {
        accessToken = "accessToken"
    }

    @Test
    fun `Should not retry request`() {
        val credentialsProvider = object : CredentialsProvider {
            override val bus: Flow<TidalMessage>
                get() = throw IllegalAccessException("Not supported")

            override suspend fun getCredentials(apiErrorSubStatus: String?) =
                AuthResult.Success<Credentials>(null)

            override fun isUserLoggedIn() = throw IllegalAccessException("Not supported")
        }

        val defaultAuthenticator = DefaultAuthenticator(
            Gson(),
            credentialsProvider,
            RequestAuthorizationDelegate(emptyMap()),
        )
        val okHttpClient = OkHttpClient.Builder()
            .authenticator(defaultAuthenticator)
            .build()

        server.enqueue(MockResponse().setResponseCode(401))

        okHttpClient.newCall(request).execute().use {
            assertThat(it.code).isEqualTo(401)
        }
    }

    @Test
    fun `Should retry request`() {
        val credentialsProvider = object : CredentialsProvider {
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
                        token = "newAccessToken",
                    ),
                )

            override fun isUserLoggedIn() = throw IllegalAccessException("Not supported")
        }

        val defaultAuthenticator = DefaultAuthenticator(
            Gson(),
            credentialsProvider,
            RequestAuthorizationDelegate(emptyMap()),
        )
        val okHttpClient = OkHttpClient.Builder()
            .authenticator(defaultAuthenticator)
            .build()

        server.enqueueResponse("errors/401_6005.json", 401)
        server.enqueue(MockResponse())

        okHttpClient.newCall(request).execute().use {
            assertThat(it.code).isEqualTo(200)
            assertThat(it.request.header("Authorization"))
                .isEqualTo("Bearer newAccessToken")
        }
    }
}
