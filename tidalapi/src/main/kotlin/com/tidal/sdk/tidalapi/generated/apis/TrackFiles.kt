package com.tidal.sdk.tidalapi.generated.apis

import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.tidal.sdk.tidalapi.generated.models.ErrorDocument
import com.tidal.sdk.tidalapi.generated.models.TrackFilesSingleDataDocument

interface TrackFiles {
    /**
     * Get single trackFile.
     * Retrieves single trackFile by id.
     * Responses:
     *  - 200: 
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id 
     * @param formats 
     * @param usage 
     * @return [TrackFilesSingleDataDocument]
     */
    @GET("trackFiles/{id}")
    suspend fun trackFilesIdGet(@Path("id") id: kotlin.String, @Query("formats") formats: kotlin.String, @Query("usage") usage: kotlin.String): Response<TrackFilesSingleDataDocument>

}
