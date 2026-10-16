package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.UserFeedItemsActorsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserFeedItemsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserFeedItemsSubjectSingleRelationshipDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface UserFeedItems {

    /** enum for parameter filterSurface */
    @Serializable
    enum class FilterSurfaceUserFeedItemsGet(val value: kotlin.String) {
        @SerialName(value = "INBOX") INBOX("INBOX"),
        @SerialName(value = "FEED") FEED("FEED"),
    }

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageUserFeedItemsGet(val value: kotlin.String) {
        @SerialName(value = "actors") actors("actors"),
        @SerialName(value = "subject") subject("subject"),
    }

    /**
     * GET userFeedItems Get multiple userFeedItems. The authenticated user&#39;s activity on the
     * selected feed surface. Responses:
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
     * @param filterOwnersId User id. Use &#x60;me&#x60; for the authenticated user
     * @param filterSurface Feed surface to read (e.g. &#x60;INBOX&#x60;)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Include related resources. Available relationships: actors, subject (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: actors.albums (optional)
     * @return [UserFeedItemsMultiResourceDataDocument]
     */
    @GET("userFeedItems")
    suspend fun userFeedItemsGet(
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("filter[surface]") filterSurface: FilterSurfaceUserFeedItemsGet,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<UserFeedItemsMultiResourceDataDocument>

    /**
     * GET userFeedItems/{id}/relationships/actors Get actors relationship (\&quot;to-many\&quot;).
     * Artists attributed to the activity. Always empty for &#x60;ALBUM_FAVOURITED&#x60;,
     * &#x60;TRACK_FAVOURITED&#x60; and &#x60;TRACK_PLAYLISTED&#x60;, which are counts only, and for
     * &#x60;HISTORY_MIX_READY&#x60; and &#x60;TRACK_PLAY_MILESTONE&#x60;, which nobody else acted
     * on. Responses:
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
     * @param id Opaque identifier of a user feed item.
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Include related resources. Available relationships: actors (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: actors.albums (optional)
     * @return [UserFeedItemsActorsMultiRelationshipDataDocument]
     */
    @GET("userFeedItems/{id}/relationships/actors")
    suspend fun userFeedItemsIdRelationshipsActorsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<UserFeedItemsActorsMultiRelationshipDataDocument>

    /**
     * GET userFeedItems/{id}/relationships/subject Get subject relationship (\&quot;to-one\&quot;).
     * The resource the activity concerns. For &#x60;HISTORY_MIX_READY&#x60;, the viewer&#39;s
     * history mix as a playlist. Responses:
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
     * @param id Opaque identifier of a user feed item.
     * @param include Include related resources. Available relationships: subject (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: subject (optional)
     * @return [UserFeedItemsSubjectSingleRelationshipDataDocument]
     */
    @GET("userFeedItems/{id}/relationships/subject")
    suspend fun userFeedItemsIdRelationshipsSubjectGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<UserFeedItemsSubjectSingleRelationshipDataDocument>
}
