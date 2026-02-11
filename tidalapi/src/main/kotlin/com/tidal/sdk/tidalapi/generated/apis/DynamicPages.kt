package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.DynamicPagesMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.DynamicPagesMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.DynamicPagesSingleRelationshipDataDocument
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface DynamicPages {

    /** enum for parameter deviceType */
    enum class DeviceTypeDynamicPagesGet(val value: kotlin.String) {
        @SerialName(value = "BROWSER") BROWSER("BROWSER"),
        @SerialName(value = "CAR") CAR("CAR"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "PHONE") PHONE("PHONE"),
        @SerialName(value = "TABLET") TABLET("TABLET"),
        @SerialName(value = "TV") TV("TV"),
    }

    /** enum for parameter systemType */
    enum class SystemTypeDynamicPagesGet(val value: kotlin.String) {
        @SerialName(value = "ANDROID") ANDROID("ANDROID"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "TESLA") TESLA("TESLA"),
        @SerialName(value = "IOS") IOS("IOS"),
        @SerialName(value = "WEB") WEB("WEB"),
    }

    /**
     * Get multiple dynamicPages. Retrieves multiple dynamicPages by available filters, or without
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
     * @param deviceType The type of device making the request
     * @param systemType The system type of the device making the request
     * @param clientVersion Client version number
     * @param refreshId (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: dynamicModules, subject (optional)
     * @param filterPageType Filter by page type (optional)
     * @param filterSubjectId Filter by subject id (optional)
     * @return [DynamicPagesMultiResourceDataDocument]
     */
    @GET("dynamicPages")
    suspend fun dynamicPagesGet(
        @Query("deviceType") deviceType: DeviceTypeDynamicPagesGet,
        @Query("systemType") systemType: SystemTypeDynamicPagesGet,
        @Query("clientVersion") clientVersion: kotlin.String,
        @Query("refreshId") refreshId: kotlin.Long? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[pageType]")
        filterPageType: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[subject.id]")
        filterSubjectId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<DynamicPagesMultiResourceDataDocument>

    /** enum for parameter deviceType */
    enum class DeviceTypeDynamicPagesIdRelationshipsDynamicModulesGet(val value: kotlin.String) {
        @SerialName(value = "BROWSER") BROWSER("BROWSER"),
        @SerialName(value = "CAR") CAR("CAR"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "PHONE") PHONE("PHONE"),
        @SerialName(value = "TABLET") TABLET("TABLET"),
        @SerialName(value = "TV") TV("TV"),
    }

    /** enum for parameter systemType */
    enum class SystemTypeDynamicPagesIdRelationshipsDynamicModulesGet(val value: kotlin.String) {
        @SerialName(value = "ANDROID") ANDROID("ANDROID"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "TESLA") TESLA("TESLA"),
        @SerialName(value = "IOS") IOS("IOS"),
        @SerialName(value = "WEB") WEB("WEB"),
    }

    /**
     * Get dynamicModules relationship (\&quot;to-many\&quot;). Retrieves dynamicModules
     * relationship. Responses:
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
     * @param id DynamicPages Id
     * @param deviceType The type of device making the request
     * @param systemType The system type of the device making the request
     * @param clientVersion Client version number
     * @param refreshId (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: dynamicModules (optional)
     * @return [DynamicPagesMultiRelationshipDataDocument]
     */
    @GET("dynamicPages/{id}/relationships/dynamicModules")
    suspend fun dynamicPagesIdRelationshipsDynamicModulesGet(
        @Path("id") id: kotlin.String,
        @Query("deviceType") deviceType: DeviceTypeDynamicPagesIdRelationshipsDynamicModulesGet,
        @Query("systemType") systemType: SystemTypeDynamicPagesIdRelationshipsDynamicModulesGet,
        @Query("clientVersion") clientVersion: kotlin.String,
        @Query("refreshId") refreshId: kotlin.Long? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<DynamicPagesMultiRelationshipDataDocument>

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
     * @param id DynamicPages Id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: subject (optional)
     * @return [DynamicPagesSingleRelationshipDataDocument]
     */
    @GET("dynamicPages/{id}/relationships/subject")
    suspend fun dynamicPagesIdRelationshipsSubjectGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<DynamicPagesSingleRelationshipDataDocument>
}
