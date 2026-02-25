package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ManualArtistClaimsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.ManualArtistClaimsSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface ManualArtistClaims {
    /**
     * Create single manualArtistClaim. Creates a new manualArtistClaim. Responses:
     * - 201: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 409: A request with this idempotency key is currently being processed
     * - 415: Unsupported request payload media type or content encoding
     * - 422: Idempotency key was already used with a different request payload
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param manualArtistClaimsCreateOperationPayload (optional)
     * @return [ManualArtistClaimsSingleResourceDataDocument]
     */
    @POST("manualArtistClaims")
    suspend fun manualArtistClaimsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        manualArtistClaimsCreateOperationPayload: ManualArtistClaimsCreateOperationPayload? = null,
    ): Response<ManualArtistClaimsSingleResourceDataDocument>
}
