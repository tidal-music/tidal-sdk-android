package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.DynamicPagesModulesMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.DynamicPagesMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.DynamicPagesSubjectSingleRelationshipDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface DynamicPages {

    /** enum for parameter deviceType */
    @Serializable
    enum class DeviceTypeDynamicPagesGet(val value: kotlin.String) {
        @SerialName(value = "BROWSER") BROWSER("BROWSER"),
        @SerialName(value = "CAR") CAR("CAR"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "PHONE") PHONE("PHONE"),
        @SerialName(value = "TABLET") TABLET("TABLET"),
        @SerialName(value = "TV") TV("TV"),
    }

    /** enum for parameter filterPageType */
    @Serializable
    enum class FilterPageTypeDynamicPagesGet(val value: kotlin.String) {
        @SerialName(value = "HOME_STATIC") HOME_STATIC("HOME_STATIC"),
        @SerialName(value = "HOME_UPLOADS") HOME_UPLOADS("HOME_UPLOADS"),
        @SerialName(value = "HOME_EDITORIAL") HOME_EDITORIAL("HOME_EDITORIAL"),
        @SerialName(value = "HOME_FREE") HOME_FREE("HOME_FREE"),
        @SerialName(value = "STUDIO_HOME") STUDIO_HOME("STUDIO_HOME"),
        @SerialName(value = "ARTIST") ARTIST("ARTIST"),
        @SerialName(value = "ALBUM") ALBUM("ALBUM"),
        @SerialName(value = "PLAYLIST") PLAYLIST("PLAYLIST"),
        @SerialName(value = "TRACK") TRACK("TRACK"),
        @SerialName(value = "VIDEO") VIDEO("VIDEO"),
    }

    /** enum for parameter systemType */
    @Serializable
    enum class SystemTypeDynamicPagesGet(val value: kotlin.String) {
        @SerialName(value = "ANDROID") ANDROID("ANDROID"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "TESLA") TESLA("TESLA"),
        @SerialName(value = "IOS") IOS("IOS"),
        @SerialName(value = "WEB") WEB("WEB"),
    }

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageDynamicPagesGet(val value: kotlin.String) {
        @SerialName(value = "modules") modules("modules"),
        @SerialName(value = "subject") subject("subject"),
    }

    /**
     * GET dynamicPages Get multiple dynamicPages. Retrieves multiple dynamicPages by available
     * filters, or without if applicable. Responses:
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
     * @param clientVersion Client version number
     * @param deviceType The type of device making the request
     * @param filterPageType Type of the page (e.g. &#x60;ARTIST&#x60;)
     * @param systemType The system type of the device making the request
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param filterSubject The page subject. Its type must match pageType. Required except for
     *   HOME_FREE, where it must be omitted. Also accepts &#x60;users:me&#x60;. (optional)
     * @param include Include related resources. Available relationships: modules, subject
     *   (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @param refreshSeed Stable seed used to keep dynamic page and module results consistent across
     *   a client session. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: modules.items (optional)
     * @return [DynamicPagesMultiResourceDataDocument]
     */
    @GET("dynamicPages")
    suspend fun dynamicPagesGet(
        @Query("clientVersion") clientVersion: kotlin.String,
        @Query("deviceType") deviceType: DeviceTypeDynamicPagesGet,
        @Query("filter[pageType]")
        filterPageType: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("systemType") systemType: SystemTypeDynamicPagesGet,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("filter[subject]") filterSubject: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("refreshSeed") refreshSeed: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<DynamicPagesMultiResourceDataDocument>

    /** enum for parameter deviceType */
    @Serializable
    enum class DeviceTypeDynamicPagesIdRelationshipsModulesGet(val value: kotlin.String) {
        @SerialName(value = "BROWSER") BROWSER("BROWSER"),
        @SerialName(value = "CAR") CAR("CAR"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "PHONE") PHONE("PHONE"),
        @SerialName(value = "TABLET") TABLET("TABLET"),
        @SerialName(value = "TV") TV("TV"),
    }

    /** enum for parameter systemType */
    @Serializable
    enum class SystemTypeDynamicPagesIdRelationshipsModulesGet(val value: kotlin.String) {
        @SerialName(value = "ANDROID") ANDROID("ANDROID"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "TESLA") TESLA("TESLA"),
        @SerialName(value = "IOS") IOS("IOS"),
        @SerialName(value = "WEB") WEB("WEB"),
    }

    /**
     * GET dynamicPages/{id}/relationships/modules Get modules relationship (\&quot;to-many\&quot;).
     * The page&#39;s modules, in display order. Responses:
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
     * @param clientVersion Client version number
     * @param deviceType The type of device making the request
     * @param systemType The system type of the device making the request
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Include related resources. Available relationships: modules (optional)
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param refreshSeed Stable seed used to keep dynamic page and module results consistent across
     *   a client session. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: modules.items (optional)
     * @return [DynamicPagesModulesMultiRelationshipDataDocument]
     */
    @GET("dynamicPages/{id}/relationships/modules")
    suspend fun dynamicPagesIdRelationshipsModulesGet(
        @Path("id") id: kotlin.String,
        @Query("clientVersion") clientVersion: kotlin.String,
        @Query("deviceType") deviceType: DeviceTypeDynamicPagesIdRelationshipsModulesGet,
        @Query("systemType") systemType: SystemTypeDynamicPagesIdRelationshipsModulesGet,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("refreshSeed") refreshSeed: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<DynamicPagesModulesMultiRelationshipDataDocument>

    /**
     * GET dynamicPages/{id}/relationships/subject Get subject relationship (\&quot;to-one\&quot;).
     * Retrieves subject relationship. Responses:
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
     * @param include Include related resources. Available relationships: subject (optional)
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
