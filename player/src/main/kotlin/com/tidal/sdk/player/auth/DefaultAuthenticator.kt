package com.tidal.sdk.player.auth

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.tidal.sdk.auth.CredentialsProvider
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Response
import okhttp3.Route

/**
 * Implementation of [Authenticator] that will delegate decision of authenticating to the provided
 * [CredentialsProvider].
 *
 * The authenticate function is synchronized, so that if multiple requests receive 401, we will
 * make sure not all of those requests handle the same auth error at the same time. The idea is
 * that once [credentialsProvider] has done its job with getting a fresh token, subsequent requests
 * will simply swap out the Authorization header with the new one and retry the request.
 *
 * @param[credentialsProvider] A [CredentialsProvider] that provides the access token used in the
 * Authorization header of the request.
 */
internal class DefaultAuthenticator(
    private val gson: Gson,
    private val credentialsProvider: CredentialsProvider,
    private val requestAuthorizationDelegate: RequestAuthorizationDelegate,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response) = runBlocking {
        credentialsProvider.getCredentials(
            response.body
                ?.let { gson.fromJson(it.string(), JsonObject::class.java) }
                ?.get("subStatus")
                ?.asString,
        )
    }.successData?.let { requestAuthorizationDelegate(response.request, it) }
}
