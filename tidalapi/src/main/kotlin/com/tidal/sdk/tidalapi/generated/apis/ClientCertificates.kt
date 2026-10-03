package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ClientCertificatesCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.ClientCertificatesCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ClientCertificatesOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ClientCertificatesSingleResourceDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface ClientCertificates {

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageClientCertificatesIdGet(val value: kotlin.String) {
        @SerialName(value = "owners") owners("owners")
    }

    /**
     * GET clientCertificates/{id} Get single clientCertificate. Retrieves single clientCertificate
     * by id. Responses:
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
     * @param id TIDAL Connect client certificate identifier
     * @param include Include related resources. Available relationships: owners (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @return [ClientCertificatesSingleResourceDataDocument]
     */
    @GET("clientCertificates/{id}")
    suspend fun clientCertificatesIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
    ): Response<ClientCertificatesSingleResourceDataDocument>

    /**
     * GET clientCertificates/{id}/relationships/owners Get owners relationship
     * (\&quot;to-many\&quot;). Retrieves owners relationship. Responses:
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
     * @param id TIDAL Connect client certificate identifier
     * @param include Include related resources. Available relationships: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [ClientCertificatesOwnersMultiRelationshipDataDocument]
     */
    @GET("clientCertificates/{id}/relationships/owners")
    suspend fun clientCertificatesIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ClientCertificatesOwnersMultiRelationshipDataDocument>

    /**
     * POST clientCertificates Create single clientCertificate. Creates a new clientCertificate.
     * Responses:
     * - 201: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused with a different payload
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param clientCertificatesCreateOperationPayload (optional)
     * @return [ClientCertificatesCreateSingleResourceDataDocument]
     */
    @POST("clientCertificates")
    suspend fun clientCertificatesPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        clientCertificatesCreateOperationPayload: ClientCertificatesCreateOperationPayload? = null,
    ): Response<ClientCertificatesCreateSingleResourceDataDocument>
}
