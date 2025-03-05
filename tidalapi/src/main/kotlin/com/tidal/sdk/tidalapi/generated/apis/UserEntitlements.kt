package com.tidal.sdk.tidalapi.generated.apis

import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.tidal.sdk.tidalapi.generated.models.ErrorDocument
import com.tidal.sdk.tidalapi.generated.models.UserEntitlementsSingleDataDocument

interface UserEntitlements {
    /**
     * Get the current users entitlements
     * Get the current users entitlements
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param include Allows the client to customize which related resources should be returned (optional)
     * @return [UserEntitlementsSingleDataDocument]
     */
    @GET("userEntitlements/me")
    suspend fun getMyUserEntitlements(@Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<UserEntitlementsSingleDataDocument>

    /**
     * Get user entitlements for user
     * Get user entitlements for user
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id User entitlements id
     * @param include Allows the client to customize which related resources should be returned (optional)
     * @return [UserEntitlementsSingleDataDocument]
     */
    @GET("userEntitlements/{id}")
    suspend fun getUserEntitlementsById(@Path("id") id: kotlin.String, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<UserEntitlementsSingleDataDocument>

}
