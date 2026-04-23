package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.AcceptedTermsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.AcceptedTermsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.AcceptedTermsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.AcceptedTermsSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.AcceptedTermsSingleResourceDataDocument
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface AcceptedTerms {

    /** enum for parameter filterTermsTermsType */
    enum class FilterTermsTermsTypeAcceptedTermsGet(val value: kotlin.String) {
        @SerialName(value = "DEVELOPER") DEVELOPER("DEVELOPER"),
        @SerialName(value = "UPLOAD_MARKETPLACE") UPLOAD_MARKETPLACE("UPLOAD_MARKETPLACE"),
    }

    /**
     * Get multiple acceptedTerms. Retrieves multiple acceptedTerms by available filters, or without
     * if applicable. Responses:
     * - 200: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners, terms (optional)
     * @param filterOwnersId User id. Use &#x60;me&#x60; for the authenticated user (optional)
     * @param filterTermsIsLatestVersion Filter by terms.isLatestVersion (optional)
     * @param filterTermsTermsType One of: DEVELOPER, UPLOAD_MARKETPLACE (e.g.
     *   &#x60;DEVELOPER&#x60;) (optional)
     * @return [AcceptedTermsMultiResourceDataDocument]
     */
    @GET("acceptedTerms")
    suspend fun acceptedTermsGet(
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[terms.isLatestVersion]")
        filterTermsIsLatestVersion: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? =
            null,
        @Query("filter[terms.termsType]")
        filterTermsTermsType: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<AcceptedTermsMultiResourceDataDocument>

    /**
     * Get owners relationship (\&quot;to-many\&quot;). Retrieves owners relationship. Responses:
     * - 200: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Accepted terms id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [AcceptedTermsMultiRelationshipDataDocument]
     */
    @GET("acceptedTerms/{id}/relationships/owners")
    suspend fun acceptedTermsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<AcceptedTermsMultiRelationshipDataDocument>

    /**
     * Get terms relationship (\&quot;to-one\&quot;). Retrieves terms relationship. Responses:
     * - 200: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Accepted terms id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: terms (optional)
     * @return [AcceptedTermsSingleRelationshipDataDocument]
     */
    @GET("acceptedTerms/{id}/relationships/terms")
    suspend fun acceptedTermsIdRelationshipsTermsGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<AcceptedTermsSingleRelationshipDataDocument>

    /**
     * Create single acceptedTerm. Creates a new acceptedTerm. Responses:
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
     * @param acceptedTermsCreateOperationPayload (optional)
     * @return [AcceptedTermsSingleResourceDataDocument]
     */
    @POST("acceptedTerms")
    suspend fun acceptedTermsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body acceptedTermsCreateOperationPayload: AcceptedTermsCreateOperationPayload? = null,
    ): Response<AcceptedTermsSingleResourceDataDocument>
}
