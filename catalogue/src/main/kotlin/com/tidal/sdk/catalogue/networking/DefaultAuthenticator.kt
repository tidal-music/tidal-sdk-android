package com.tidal.sdk.catalogue.networking

import com.tidal.sdk.auth.CredentialsProvider
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**
 * Implementation of [Authenticator] that will delegate decision of authenticating to the provided
 * [CredentialsProvider].
 * @param[credentialsProvider] A [CredentialsProvider] that provides the access token used in the
 * Authorization header of the request.
 */
internal class DefaultAuthenticator(
    private val credentialsProvider: CredentialsProvider,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            credentialsProvider.getCredentials()
        }.successData?.let {
            response.request.newBuilder().header("Authorization", "Bearer ${it.token}").build()
        }
    }
}
