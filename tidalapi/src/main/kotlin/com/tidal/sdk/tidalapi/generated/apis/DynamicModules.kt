package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.DynamicModulesMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.DynamicModulesMultiResourceDataDocument
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface DynamicModules {

    /** enum for parameter deviceType */
    enum class DeviceTypeDynamicModulesGet(val value: kotlin.String) {
        @SerialName(value = "BROWSER") BROWSER("BROWSER"),
        @SerialName(value = "CAR") CAR("CAR"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "PHONE") PHONE("PHONE"),
        @SerialName(value = "TABLET") TABLET("TABLET"),
        @SerialName(value = "TV") TV("TV"),
    }

    /** enum for parameter systemType */
    enum class SystemTypeDynamicModulesGet(val value: kotlin.String) {
        @SerialName(value = "ANDROID") ANDROID("ANDROID"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "TESLA") TESLA("TESLA"),
        @SerialName(value = "IOS") IOS("IOS"),
        @SerialName(value = "WEB") WEB("WEB"),
    }

    /**
     * Get multiple dynamicModules. Retrieves multiple dynamicModules by available filters, or
     * without if applicable. Responses:
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
     *   Available options: items (optional)
     * @param filterId DynamicModules Id (e.g. &#x60;nejMcAhh5N8S3EQ4LaqysVdI0cZZ&#x60;) (optional)
     * @return [DynamicModulesMultiResourceDataDocument]
     */
    @GET("dynamicModules")
    suspend fun dynamicModulesGet(
        @Query("deviceType") deviceType: DeviceTypeDynamicModulesGet,
        @Query("systemType") systemType: SystemTypeDynamicModulesGet,
        @Query("clientVersion") clientVersion: kotlin.String,
        @Query("refreshId") refreshId: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<DynamicModulesMultiResourceDataDocument>

    /** enum for parameter deviceType */
    enum class DeviceTypeDynamicModulesIdRelationshipsItemsGet(val value: kotlin.String) {
        @SerialName(value = "BROWSER") BROWSER("BROWSER"),
        @SerialName(value = "CAR") CAR("CAR"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "PHONE") PHONE("PHONE"),
        @SerialName(value = "TABLET") TABLET("TABLET"),
        @SerialName(value = "TV") TV("TV"),
    }

    /** enum for parameter systemType */
    enum class SystemTypeDynamicModulesIdRelationshipsItemsGet(val value: kotlin.String) {
        @SerialName(value = "ANDROID") ANDROID("ANDROID"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "TESLA") TESLA("TESLA"),
        @SerialName(value = "IOS") IOS("IOS"),
        @SerialName(value = "WEB") WEB("WEB"),
    }

    /**
     * Get items relationship (\&quot;to-many\&quot;). Retrieves items relationship. Responses:
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
     * @param id DynamicModules Id
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
     *   Available options: items (optional)
     * @return [DynamicModulesMultiRelationshipDataDocument]
     */
    @GET("dynamicModules/{id}/relationships/items")
    suspend fun dynamicModulesIdRelationshipsItemsGet(
        @Path("id") id: kotlin.String,
        @Query("deviceType") deviceType: DeviceTypeDynamicModulesIdRelationshipsItemsGet,
        @Query("systemType") systemType: SystemTypeDynamicModulesIdRelationshipsItemsGet,
        @Query("clientVersion") clientVersion: kotlin.String,
        @Query("refreshId") refreshId: kotlin.Long? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<DynamicModulesMultiRelationshipDataDocument>
}
