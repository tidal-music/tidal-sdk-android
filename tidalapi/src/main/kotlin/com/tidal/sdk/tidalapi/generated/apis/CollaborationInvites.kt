package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.CollaborationInvitesCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.CollaborationInvitesMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.CollaborationInvitesMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.CollaborationInvitesSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.CollaborationInvitesSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface CollaborationInvites {
    /**
     * Get multiple collaborationInvites. Retrieves multiple collaborationInvites by available
     * filters, or without if applicable. Responses:
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
     *   Available options: owners, subject (optional)
     * @param filterCode Invite code (optional)
     * @return [CollaborationInvitesMultiResourceDataDocument]
     */
    @GET("collaborationInvites")
    suspend fun collaborationInvitesGet(
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[code]")
        filterCode: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<CollaborationInvitesMultiResourceDataDocument>

    /**
     * Delete single collaborationInvite. Deletes existing collaborationInvite. Responses:
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
     * @param id Collaboration invite id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @return [Unit]
     */
    @DELETE("collaborationInvites/{id}")
    suspend fun collaborationInvitesIdDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
    ): Response<Unit>

    /**
     * Get single collaborationInvite. Retrieves single collaborationInvite by id. Responses:
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
     * @param id Collaboration invite id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners, subject (optional)
     * @return [CollaborationInvitesSingleResourceDataDocument]
     */
    @GET("collaborationInvites/{id}")
    suspend fun collaborationInvitesIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<CollaborationInvitesSingleResourceDataDocument>

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
     * @param id Collaboration invite id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [CollaborationInvitesMultiRelationshipDataDocument]
     */
    @GET("collaborationInvites/{id}/relationships/owners")
    suspend fun collaborationInvitesIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<CollaborationInvitesMultiRelationshipDataDocument>

    /**
     * Get subject relationship (\&quot;to-one\&quot;). Retrieves subject relationship. Responses:
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
     * @param id Collaboration invite id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: subject (optional)
     * @return [CollaborationInvitesSingleRelationshipDataDocument]
     */
    @GET("collaborationInvites/{id}/relationships/subject")
    suspend fun collaborationInvitesIdRelationshipsSubjectGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<CollaborationInvitesSingleRelationshipDataDocument>

    /**
     * Create single collaborationInvite. Creates a new collaborationInvite. Responses:
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
     * @param collaborationInvitesCreateOperationPayload (optional)
     * @return [CollaborationInvitesSingleResourceDataDocument]
     */
    @POST("collaborationInvites")
    suspend fun collaborationInvitesPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        collaborationInvitesCreateOperationPayload: CollaborationInvitesCreateOperationPayload? =
            null,
    ): Response<CollaborationInvitesSingleResourceDataDocument>
}
