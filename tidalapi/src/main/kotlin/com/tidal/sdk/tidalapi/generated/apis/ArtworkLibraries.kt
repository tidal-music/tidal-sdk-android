package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.ArtworkLibrariesArtworksMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtworkLibrariesMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtworkLibrariesRandomArtworkSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtworkLibrariesSingleResourceDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface ArtworkLibraries {

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageArtworkLibrariesGet(val value: kotlin.String) {
        @SerialName(value = "artworks") artworks("artworks"),
        @SerialName(value = "randomArtwork") randomArtwork("randomArtwork"),
    }

    /**
     * GET artworkLibraries Get multiple artworkLibraries. Retrieves multiple artworkLibraries by
     * available filters, or without if applicable. Responses:
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
     * @param filterName Library names (e.g. &#x60;album-defaults&#x60;)
     * @param include Include related resources. Available relationships: artworks, randomArtwork
     *   (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @return [ArtworkLibrariesMultiResourceDataDocument]
     */
    @GET("artworkLibraries")
    suspend fun artworkLibrariesGet(
        @Query("filter[name]")
        filterName: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
    ): Response<ArtworkLibrariesMultiResourceDataDocument>

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageArtworkLibrariesIdGet(val value: kotlin.String) {
        @SerialName(value = "artworks") artworks("artworks"),
        @SerialName(value = "randomArtwork") randomArtwork("randomArtwork"),
    }

    /**
     * GET artworkLibraries/{id} Get single artworkLibrarie. Retrieves single artworkLibrarie by id.
     * Responses:
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
     * @param id Artwork library ID
     * @param include Include related resources. Available relationships: artworks, randomArtwork
     *   (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @return [ArtworkLibrariesSingleResourceDataDocument]
     */
    @GET("artworkLibraries/{id}")
    suspend fun artworkLibrariesIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
    ): Response<ArtworkLibrariesSingleResourceDataDocument>

    /**
     * GET artworkLibraries/{id}/relationships/artworks Get artworks relationship
     * (\&quot;to-many\&quot;). Artworks in the library that have finished processing and are not
     * flagged or taken down, newest first Responses:
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
     * @param id Artwork library ID
     * @param include Include related resources. Available relationships: artworks (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [ArtworkLibrariesArtworksMultiRelationshipDataDocument]
     */
    @GET("artworkLibraries/{id}/relationships/artworks")
    suspend fun artworkLibrariesIdRelationshipsArtworksGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtworkLibrariesArtworksMultiRelationshipDataDocument>

    /**
     * GET artworkLibraries/{id}/relationships/randomArtwork Get randomArtwork relationship
     * (\&quot;to-one\&quot;). A random artwork from the library, used by clients to supply a
     * default artwork, for example for albums without cover art. Picked per request from the
     * artworks that have finished processing and are not flagged or taken down, and not stored;
     * empty when there are none Responses:
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
     * @param id Artwork library ID
     * @param include Include related resources. Available relationships: randomArtwork (optional)
     * @return [ArtworkLibrariesRandomArtworkSingleRelationshipDataDocument]
     */
    @GET("artworkLibraries/{id}/relationships/randomArtwork")
    suspend fun artworkLibrariesIdRelationshipsRandomArtworkGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtworkLibrariesRandomArtworkSingleRelationshipDataDocument>
}
