package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import com.tidal.sdk.tidalapi.generated.models.ReactionsAuthorSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ReactionsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.ReactionsCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ReactionsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ReactionsOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ReactionsSubjectSingleRelationshipDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface Reactions {

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageReactionsGet(val value: kotlin.String) {
        @SerialName(value = "author") author("author"),
        @SerialName(value = "owners") owners("owners"),
        @SerialName(value = "subject") subject("subject"),
    }

    /**
     * GET reactions Get multiple reactions. Lists reactions on a source file or comment, newest
     * first. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param filterSubject The subject whose reactions to return.
     * @param filterEmoji Filter by emoji (e.g. &#x60;👍&#x60;) (optional)
     * @param include Include related resources. Available relationships: author, owners, subject
     *   (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: author.albums (optional)
     * @return [ReactionsMultiResourceDataDocument]
     */
    @GET("reactions")
    suspend fun reactionsGet(
        @Query("filter[subject]") filterSubject: kotlin.String,
        @Query("filter[emoji]")
        filterEmoji: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ReactionsMultiResourceDataDocument>

    /**
     * DELETE reactions/{id} Delete single reaction. Deletes existing reaction. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Reaction Id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @return [MutationResponseDocument]
     */
    @DELETE("reactions/{id}")
    suspend fun reactionsIdDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
    ): Response<MutationResponseDocument>

    /**
     * GET reactions/{id}/relationships/author Get author relationship (\&quot;to-one\&quot;). The
     * artist who left the reaction. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Reaction Id
     * @param include Include related resources. Available relationships: author (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: author.albums (optional)
     * @return [ReactionsAuthorSingleRelationshipDataDocument]
     */
    @GET("reactions/{id}/relationships/author")
    suspend fun reactionsIdRelationshipsAuthorGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ReactionsAuthorSingleRelationshipDataDocument>

    /**
     * GET reactions/{id}/relationships/owners Get owners relationship (\&quot;to-many\&quot;).
     * Retrieves owners relationship. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Reaction Id
     * @param include Include related resources. Available relationships: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [ReactionsOwnersMultiRelationshipDataDocument]
     */
    @GET("reactions/{id}/relationships/owners")
    suspend fun reactionsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ReactionsOwnersMultiRelationshipDataDocument>

    /**
     * GET reactions/{id}/relationships/subject Get subject relationship (\&quot;to-one\&quot;).
     * Retrieves subject relationship. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Reaction Id
     * @param include Include related resources. Available relationships: subject (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: subject.author.albums (optional)
     * @return [ReactionsSubjectSingleRelationshipDataDocument]
     */
    @GET("reactions/{id}/relationships/subject")
    suspend fun reactionsIdRelationshipsSubjectGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ReactionsSubjectSingleRelationshipDataDocument>

    /**
     * POST reactions Create single reaction. Adds the caller&#39;s emoji reaction on a subject and
     * exact time offsets. Omit both offsets for the whole subject, provide startOffset for a point,
     * or both for a section. Offsets are supported only on source files and must be nonnegative,
     * with endOffset at or after startOffset. Each user can add different emojis at the same
     * position or section. Repeating the same emoji returns the existing reaction with its UUID and
     * creation time unchanged. Responses:
     * - 201: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @param reactionsCreateOperationPayload (optional)
     * @return [ReactionsCreateSingleResourceDataDocument]
     */
    @POST("reactions")
    suspend fun reactionsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body reactionsCreateOperationPayload: ReactionsCreateOperationPayload? = null,
    ): Response<ReactionsCreateSingleResourceDataDocument>
}
