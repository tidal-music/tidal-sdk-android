package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.DynamicModulesMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.DynamicModulesMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.DynamicModulesSingleResourceDataDocument
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface DynamicModulesApi {

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
     * @param filterId DynamicModules Id (e.g. &#x60;nejMcAhh5N8S3EQ4LaqysVdI0cZZ&#x60;)
     * @param refreshSeed Stable seed used to keep dynamic page and module results consistent across
     *   a client session. (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: items (optional)
     * @return [DynamicModulesMultiResourceDataDocument]
     */
    @GET("dynamicModules")
    suspend fun dynamicModulesGet(
        @Query("deviceType") deviceType: DeviceTypeDynamicModulesGet,
        @Query("systemType") systemType: SystemTypeDynamicModulesGet,
        @Query("clientVersion") clientVersion: kotlin.String,
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("refreshSeed") refreshSeed: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<DynamicModulesMultiResourceDataDocument>

    /** enum for parameter deviceType */
    enum class DeviceTypeDynamicModulesIdGet(val value: kotlin.String) {
        @SerialName(value = "BROWSER") BROWSER("BROWSER"),
        @SerialName(value = "CAR") CAR("CAR"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "PHONE") PHONE("PHONE"),
        @SerialName(value = "TABLET") TABLET("TABLET"),
        @SerialName(value = "TV") TV("TV"),
    }

    /** enum for parameter systemType */
    enum class SystemTypeDynamicModulesIdGet(val value: kotlin.String) {
        @SerialName(value = "ANDROID") ANDROID("ANDROID"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "TESLA") TESLA("TESLA"),
        @SerialName(value = "IOS") IOS("IOS"),
        @SerialName(value = "WEB") WEB("WEB"),
    }

    /**
     * Get single dynamicModule. Retrieves single dynamicModule by id. Responses:
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
     * @param refreshSeed Stable seed used to keep dynamic page and module results consistent across
     *   a client session. (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: items (optional)
     * @return [DynamicModulesSingleResourceDataDocument]
     */
    @GET("dynamicModules/{id}")
    suspend fun dynamicModulesIdGet(
        @Path("id") id: kotlin.String,
        @Query("deviceType") deviceType: DeviceTypeDynamicModulesIdGet,
        @Query("systemType") systemType: SystemTypeDynamicModulesIdGet,
        @Query("clientVersion") clientVersion: kotlin.String,
        @Query("refreshSeed") refreshSeed: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<DynamicModulesSingleResourceDataDocument>

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
     * @param refreshSeed Stable seed used to keep dynamic page and module results consistent across
     *   a client session. (optional)
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
        @Query("refreshSeed") refreshSeed: kotlin.String? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<DynamicModulesMultiRelationshipDataDocument>
}
