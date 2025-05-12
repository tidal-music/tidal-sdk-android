package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.TrackManifestsSingleDataDocument
import retrofit2.Response
import retrofit2.http.*

interface TrackManifests {
    /**
     * Get single trackManifest. Retrieves single trackManifest by id. Responses:
     * - 200:
     * - 451: Unavailable For Legal Reasons
     * - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters,
     *   request body, etc.).
     * - 500: Internal Server Error. Something went wrong on the server party.
     * - 404: Resource not found. The requested resource is not found.
     * - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media
     *   type is set into Content-Type header.
     * - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     * - 406: Not acceptable. The server doesn't support any of the requested by client acceptable
     *   content types.
     * - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id
     * @param manifestType
     * @param formats
     * @param uriScheme
     * @param usage
     * @param adaptive
     * @return [TrackManifestsSingleDataDocument]
     */
    @GET("trackManifests/{id}")
    suspend fun trackManifestsIdGet(
        @Path("id") id: kotlin.String,
        @Query("manifestType") manifestType: kotlin.String,
        @Query("formats") formats: kotlin.String,
        @Query("uriScheme") uriScheme: kotlin.String,
        @Query("usage") usage: kotlin.String,
        @Query("adaptive") adaptive: kotlin.String,
    ): Response<TrackManifestsSingleDataDocument>
}
