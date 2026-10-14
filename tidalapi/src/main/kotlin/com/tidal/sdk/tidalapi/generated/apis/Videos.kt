package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.VideosAlbumsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.VideosArtistsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.VideosCreditsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.VideosMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.VideosProvidersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.VideosReplacementSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.VideosSimilarVideosMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.VideosSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.VideosSuggestedVideosMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.VideosThumbnailArtMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.VideosUsageRulesSingleRelationshipDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface Videos {

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageVideosGet(val value: kotlin.String) {
        @SerialName(value = "albums") albums("albums"),
        @SerialName(value = "artists") artists("artists"),
        @SerialName(value = "credits") credits("credits"),
        @SerialName(value = "providers") providers("providers"),
        @SerialName(value = "replacement") replacement("replacement"),
        @SerialName(value = "similarVideos") similarVideos("similarVideos"),
        @SerialName(value = "suggestedVideos") suggestedVideos("suggestedVideos"),
        @SerialName(value = "thumbnailArt") thumbnailArt("thumbnailArt"),
        @SerialName(value = "usageRules") usageRules("usageRules"),
    }

    /**
     * GET videos Get multiple videos. Retrieves multiple videos by available filters, or without if
     * applicable. Responses:
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
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Include related resources. Available relationships: albums, artists, credits,
     *   providers, replacement, similarVideos, suggestedVideos, thumbnailArt, usageRules (optional)
     * @param filterId List of video IDs (e.g. &#x60;75623239&#x60;) (optional)
     * @param filterIsrc List of ISRCs (e.g. &#x60;QMJMT1701237&#x60;) (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: albums (optional)
     * @return [VideosMultiResourceDataDocument]
     */
    @GET("videos")
    suspend fun videosGet(
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[isrc]")
        filterIsrc: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<VideosMultiResourceDataDocument>

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageVideosIdGet(val value: kotlin.String) {
        @SerialName(value = "albums") albums("albums"),
        @SerialName(value = "artists") artists("artists"),
        @SerialName(value = "credits") credits("credits"),
        @SerialName(value = "providers") providers("providers"),
        @SerialName(value = "replacement") replacement("replacement"),
        @SerialName(value = "similarVideos") similarVideos("similarVideos"),
        @SerialName(value = "suggestedVideos") suggestedVideos("suggestedVideos"),
        @SerialName(value = "thumbnailArt") thumbnailArt("thumbnailArt"),
        @SerialName(value = "usageRules") usageRules("usageRules"),
    }

    /**
     * GET videos/{id} Get single video. Retrieves single video by id. Responses:
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
     * @param id Video id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Include related resources. Available relationships: albums, artists, credits,
     *   providers, replacement, similarVideos, suggestedVideos, thumbnailArt, usageRules (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: albums (optional)
     * @return [VideosSingleResourceDataDocument]
     */
    @GET("videos/{id}")
    suspend fun videosIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<VideosSingleResourceDataDocument>

    /**
     * GET videos/{id}/relationships/albums Get albums relationship (\&quot;to-many\&quot;).
     * Retrieves albums relationship. Responses:
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
     * @param id Video id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Include related resources. Available relationships: albums (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: albums (optional)
     * @return [VideosAlbumsMultiRelationshipDataDocument]
     */
    @GET("videos/{id}/relationships/albums")
    suspend fun videosIdRelationshipsAlbumsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<VideosAlbumsMultiRelationshipDataDocument>

    /**
     * GET videos/{id}/relationships/artists Get artists relationship (\&quot;to-many\&quot;).
     * Retrieves artists relationship. Responses:
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
     * @param id Video id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Include related resources. Available relationships: artists (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: artists.albums (optional)
     * @return [VideosArtistsMultiRelationshipDataDocument]
     */
    @GET("videos/{id}/relationships/artists")
    suspend fun videosIdRelationshipsArtistsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<VideosArtistsMultiRelationshipDataDocument>

    /**
     * GET videos/{id}/relationships/credits Get credits relationship (\&quot;to-many\&quot;).
     * Retrieves credits relationship. Responses:
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
     * @param id Video id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Include related resources. Available relationships: credits (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: credits.artist.albums (optional)
     * @return [VideosCreditsMultiRelationshipDataDocument]
     */
    @GET("videos/{id}/relationships/credits")
    suspend fun videosIdRelationshipsCreditsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<VideosCreditsMultiRelationshipDataDocument>

    /**
     * GET videos/{id}/relationships/providers Get providers relationship (\&quot;to-many\&quot;).
     * Retrieves providers relationship. Responses:
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
     * @param id Video id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Include related resources. Available relationships: providers (optional)
     * @return [VideosProvidersMultiRelationshipDataDocument]
     */
    @GET("videos/{id}/relationships/providers")
    suspend fun videosIdRelationshipsProvidersGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<VideosProvidersMultiRelationshipDataDocument>

    /**
     * GET videos/{id}/relationships/replacement Get replacement relationship
     * (\&quot;to-one\&quot;). Retrieves replacement relationship. Responses:
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
     * @param id Video id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Include related resources. Available relationships: replacement (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: replacement (optional)
     * @return [VideosReplacementSingleRelationshipDataDocument]
     */
    @GET("videos/{id}/relationships/replacement")
    suspend fun videosIdRelationshipsReplacementGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<VideosReplacementSingleRelationshipDataDocument>

    /**
     * GET videos/{id}/relationships/similarVideos Get similarVideos relationship
     * (\&quot;to-many\&quot;). Retrieves similarVideos relationship. Responses:
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
     * @param id Video id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Include related resources. Available relationships: similarVideos (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: similarVideos (optional)
     * @return [VideosSimilarVideosMultiRelationshipDataDocument]
     */
    @GET("videos/{id}/relationships/similarVideos")
    suspend fun videosIdRelationshipsSimilarVideosGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<VideosSimilarVideosMultiRelationshipDataDocument>

    /**
     * GET videos/{id}/relationships/suggestedVideos Get suggestedVideos relationship
     * (\&quot;to-many\&quot;). Retrieves suggestedVideos relationship. Responses:
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
     * @param id Video id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Include related resources. Available relationships: suggestedVideos (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: suggestedVideos (optional)
     * @return [VideosSuggestedVideosMultiRelationshipDataDocument]
     */
    @GET("videos/{id}/relationships/suggestedVideos")
    suspend fun videosIdRelationshipsSuggestedVideosGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<VideosSuggestedVideosMultiRelationshipDataDocument>

    /**
     * GET videos/{id}/relationships/thumbnailArt Get thumbnailArt relationship
     * (\&quot;to-many\&quot;). Retrieves thumbnailArt relationship. Responses:
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
     * @param id Video id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Include related resources. Available relationships: thumbnailArt (optional)
     * @return [VideosThumbnailArtMultiRelationshipDataDocument]
     */
    @GET("videos/{id}/relationships/thumbnailArt")
    suspend fun videosIdRelationshipsThumbnailArtGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<VideosThumbnailArtMultiRelationshipDataDocument>

    /**
     * GET videos/{id}/relationships/usageRules Get usageRules relationship (\&quot;to-one\&quot;).
     * Retrieves usageRules relationship. Responses:
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
     * @param id Video id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Include related resources. Available relationships: usageRules (optional)
     * @return [VideosUsageRulesSingleRelationshipDataDocument]
     */
    @GET("videos/{id}/relationships/usageRules")
    suspend fun videosIdRelationshipsUsageRulesGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<VideosUsageRulesSingleRelationshipDataDocument>
}
