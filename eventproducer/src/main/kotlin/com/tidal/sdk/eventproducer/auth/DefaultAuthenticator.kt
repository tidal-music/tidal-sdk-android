package com.tidal.sdk.eventproducer.auth

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**
 * Implementation of [Authenticator] that will delegate decision of authenticating to the provided
 * [AuthProvider].
 *
 * The authenticate function is synchronized, so that if multiple requests receive 401, we will
 * make sure not all of those requests handle the same auth error at the same time. The idea is
 * that once [authProvider] has done its job with getting a fresh token, subsequent requests will
 * simply swap out the Authorization header with the new one and retry the request.
 *
 * @param[authProvider] An [AuthProvider] that provides the access token used in the
 * Authorization header of the request.
 */
@Singleton
class DefaultAuthenticator @Inject constructor(
    private val authProvider: AuthProvider,
) : Authenticator {

    @Synchronized
    override fun authenticate(route: Route?, response: Response): Request? {
        val shouldRetryRequest = runBlocking {
            authProvider.handleAuthError(response)
        }

        return if (shouldRetryRequest) {
            response.createNewRequest()
        } else {
            null
        }
    }

    /**
     * Make a copy of the request with a new Authorization header.
     */
    private fun Response.createNewRequest(): Request {
        return request
            .newBuilder()
            .header(AUTHORIZATION_HEADER_NAME, "Bearer ${authProvider.token}")
            .build()
    }

    companion object {

        private const val AUTHORIZATION_HEADER_NAME = "Authorization"
    }
}
