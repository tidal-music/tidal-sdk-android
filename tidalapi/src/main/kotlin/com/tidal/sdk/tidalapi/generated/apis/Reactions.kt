package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.CreateReactionPayload
import com.tidal.sdk.tidalapi.generated.models.ReactionsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ReactionsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ReactionsSingleResourceDataDocument
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface Reactions {

    /** enum for parameter stats */
    enum class StatsReactionsGet(val value: kotlin.String) {
        @SerialName(value = "ALL") ALL("ALL"),
        @SerialName(value = "COUNTS_BY_TYPE") COUNTS_BY_TYPE("COUNTS_BY_TYPE"),
        @SerialName(value = "TOTAL_COUNT") TOTAL_COUNT("TOTAL_COUNT"),
    }

    /**
     * Get multiple reactions. Retrieves multiple reactions by available filters, or without if
     * applicable. Responses:
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
     * @param stats (optional)
     * @param statsOnly (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: ownerProfiles (optional)
     * @param filterOwnerId Filter by owner id (optional)
     * @param filterReactedResourceId Filter by resource ID (optional)
     * @param filterReactedResourceType Filter by resource type (optional)
     * @param filterReactionType Filter by reaction type (optional)
     * @return [ReactionsMultiResourceDataDocument]
     */
    @GET("reactions")
    suspend fun reactionsGet(
        @Query("stats") stats: StatsReactionsGet? = null,
        @Query("statsOnly") statsOnly: kotlin.Boolean? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[owner.id]")
        filterOwnerId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[reactedResource.id]")
        filterReactedResourceId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? =
            null,
        @Query("filter[reactedResource.type]")
        filterReactedResourceType: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? =
            null,
        @Query("filter[reactionType]")
        filterReactionType: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ReactionsMultiResourceDataDocument>

    /**
     * Delete single reaction. Deletes existing reaction. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Reaction Id
     * @return [Unit]
     */
    @DELETE("reactions/{id}")
    suspend fun reactionsIdDelete(@Path("id") id: kotlin.String): Response<Unit>

    /**
     * Get ownerProfiles relationship (\&quot;to-many\&quot;). Retrieves ownerProfiles relationship.
     * Responses:
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
     * @param id Reaction Id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: ownerProfiles (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [ReactionsMultiRelationshipDataDocument]
     */
    @GET("reactions/{id}/relationships/ownerProfiles")
    suspend fun reactionsIdRelationshipsOwnerProfilesGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ReactionsMultiRelationshipDataDocument>

    /**
     * Create single reaction. Creates a new reaction. Responses:
     * - 201: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param createReactionPayload (optional)
     * @return [ReactionsSingleResourceDataDocument]
     */
    @POST("reactions")
    suspend fun reactionsPost(
        @Body createReactionPayload: CreateReactionPayload? = null
    ): Response<ReactionsSingleResourceDataDocument>
}
