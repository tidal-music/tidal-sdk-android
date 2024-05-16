package com.tidal.sdk.eventproducer.auth

import okhttp3.Response

/**
 * A simple interface for getting an access token and client id to be used by the event producer
 */
interface AuthProvider {

    val token: String?
    val clientId: String?

    /**
     * Should handle any auth error that occurs.
     *
     * The [response] should help you decide what action to take.
     *
     * @param[response] The failing api response.
     * @return[Boolean] Return true if we should retry the request, false otherwise. Returning true
     * will query this provider for a new access token, so make sure that a refreshed token
     * is reflected in that call.
     */
    suspend fun handleAuthError(response: Response): Boolean
}
