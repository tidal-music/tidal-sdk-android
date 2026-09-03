package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.DynamicPagesModulesMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.DynamicPagesMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.DynamicPagesSubjectSingleRelationshipDataDocument
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
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param deviceType The type of device making the request
     * @param systemType The system type of the device making the request
     * @param clientVersion Client version number
     * @param filterPageType type of the page (e.g. &#x60;ARTIST&#x60;)
     * @param refreshSeed Stable seed used to keep dynamic page and module results consistent across
     *   a client session. (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: modules, subject (optional)
     * @param filterSubjectId The subject resource ID. Required except for HOME_FREE, where it must
     *   be omitted. (e.g. &#x60;67890&#x60;) (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: modules.items (optional)
     * @return [DynamicPagesMultiResourceDataDocument]
     */
    @GET("dynamicPages")
    suspend fun dynamicPagesGet(
        @Query("deviceType") deviceType: DeviceTypeDynamicPagesGet,
        @Query("systemType") systemType: SystemTypeDynamicPagesGet,
        @Query("clientVersion") clientVersion: kotlin.String,
        @Query("filter[pageType]")
        filterPageType: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("refreshSeed") refreshSeed: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[subject.id]")
        filterSubjectId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<DynamicPagesMultiResourceDataDocument>

    /** enum for parameter deviceType */
    enum class DeviceTypeDynamicPagesIdRelationshipsModulesGet(val value: kotlin.String) {
        @SerialName(value = "BROWSER") BROWSER("BROWSER"),
        @SerialName(value = "CAR") CAR("CAR"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "PHONE") PHONE("PHONE"),
        @SerialName(value = "TABLET") TABLET("TABLET"),
        @SerialName(value = "TV") TV("TV"),
    }

    /** enum for parameter systemType */
    enum class SystemTypeDynamicPagesIdRelationshipsModulesGet(val value: kotlin.String) {
        @SerialName(value = "ANDROID") ANDROID("ANDROID"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "TESLA") TESLA("TESLA"),
        @SerialName(value = "IOS") IOS("IOS"),
        @SerialName(value = "WEB") WEB("WEB"),
    }

    /**
     * Get modules relationship (\&quot;to-many\&quot;). Retrieves modules relationship. Responses:
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
     * @param id DynamicPages Id
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
     *   Available options: modules (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: modules.items (optional)
     * @return [DynamicPagesModulesMultiRelationshipDataDocument]
     */
    @GET("dynamicPages/{id}/relationships/modules")
    suspend fun dynamicPagesIdRelationshipsModulesGet(
        @Path("id") id: kotlin.String,
        @Query("deviceType") deviceType: DeviceTypeDynamicPagesIdRelationshipsModulesGet,
        @Query("systemType") systemType: SystemTypeDynamicPagesIdRelationshipsModulesGet,
        @Query("clientVersion") clientVersion: kotlin.String,
        @Query("refreshSeed") refreshSeed: kotlin.String? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<DynamicPagesModulesMultiRelationshipDataDocument>

    /**
     * Get subject relationship (\&quot;to-one\&quot;). Retrieves subject relationship. Responses:
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
     * @param id DynamicPages Id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: subject (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: subject (optional)
     * @return [DynamicPagesSubjectSingleRelationshipDataDocument]
     */
    @GET("dynamicPages/{id}/relationships/subject")
    suspend fun dynamicPagesIdRelationshipsSubjectGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<DynamicPagesSubjectSingleRelationshipDataDocument>
}
