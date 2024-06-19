# ArtistJSONAPIApi

All URIs are relative to *https://openapi.stage.tidal.com/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getArtist**](ArtistJSONAPIApi.md#getArtist) | **GET** /artists/{id} | Get single artist
[**getArtistAlbums**](ArtistJSONAPIApi.md#getArtistAlbums) | **GET** /artists/{id}/relationships/albums | Relationship: albums
[**getArtistTracks**](ArtistJSONAPIApi.md#getArtistTracks) | **GET** /artists/{id}/relationships/tracks | Relationship: tracks
[**getArtistVideos**](ArtistJSONAPIApi.md#getArtistVideos) | **GET** /artists/{id}/relationships/videos | Relationship: videos
[**getArtists**](ArtistJSONAPIApi.md#getArtists) | **GET** /artists | Get multiple artists
[**getSimilarArtists**](ArtistJSONAPIApi.md#getSimilarArtists) | **GET** /artists/{id}/relationships/similarArtists | Relationship: similar artists


<a id="getArtist"></a>
# **getArtist**
> ArtistDataDocument getArtist(id, countryCode, include)

Get single artist

Retrieve artist details by TIDAL artist id.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ArtistJSONAPIApi()
val id : kotlin.String = 1566 // kotlin.String | TIDAL artist id
val countryCode : kotlin.String = US // kotlin.String | ISO 3166-1 alpha-2 country code
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned. Available options: albums, tracks, videos, similarArtists
try {
    val result : ArtistDataDocument = apiInstance.getArtist(id, countryCode, include)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ArtistJSONAPIApi#getArtist")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ArtistJSONAPIApi#getArtist")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| TIDAL artist id |
 **countryCode** | **kotlin.String**| ISO 3166-1 alpha-2 country code |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned. Available options: albums, tracks, videos, similarArtists | [optional]

### Return type

[**ArtistDataDocument**](ArtistDataDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

<a id="getArtistAlbums"></a>
# **getArtistAlbums**
> ArtistRelationshipsDocument getArtistAlbums(id, countryCode, include)

Relationship: albums

Retrieve album details of the related artist.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ArtistJSONAPIApi()
val id : kotlin.String = 1566 // kotlin.String | TIDAL artist id
val countryCode : kotlin.String = US // kotlin.String | ISO 3166-1 alpha-2 country code
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned. Available options: albums
try {
    val result : ArtistRelationshipsDocument = apiInstance.getArtistAlbums(id, countryCode, include)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ArtistJSONAPIApi#getArtistAlbums")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ArtistJSONAPIApi#getArtistAlbums")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| TIDAL artist id |
 **countryCode** | **kotlin.String**| ISO 3166-1 alpha-2 country code |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned. Available options: albums | [optional]

### Return type

[**ArtistRelationshipsDocument**](ArtistRelationshipsDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

<a id="getArtistTracks"></a>
# **getArtistTracks**
> ArtistRelationshipsDocument getArtistTracks(id, countryCode, include)

Relationship: tracks

Retrieve track details by related artist.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ArtistJSONAPIApi()
val id : kotlin.String = 1566 // kotlin.String | TIDAL artist id
val countryCode : kotlin.String = US // kotlin.String | ISO 3166-1 alpha-2 country code
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned. Available options: tracks
try {
    val result : ArtistRelationshipsDocument = apiInstance.getArtistTracks(id, countryCode, include)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ArtistJSONAPIApi#getArtistTracks")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ArtistJSONAPIApi#getArtistTracks")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| TIDAL artist id |
 **countryCode** | **kotlin.String**| ISO 3166-1 alpha-2 country code |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned. Available options: tracks | [optional]

### Return type

[**ArtistRelationshipsDocument**](ArtistRelationshipsDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

<a id="getArtistVideos"></a>
# **getArtistVideos**
> ArtistRelationshipsDocument getArtistVideos(id, countryCode, include)

Relationship: videos

Retrieve video details by related artist.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ArtistJSONAPIApi()
val id : kotlin.String = 1566 // kotlin.String | TIDAL artist id
val countryCode : kotlin.String = US // kotlin.String | ISO 3166-1 alpha-2 country code
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned. Available options: videos
try {
    val result : ArtistRelationshipsDocument = apiInstance.getArtistVideos(id, countryCode, include)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ArtistJSONAPIApi#getArtistVideos")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ArtistJSONAPIApi#getArtistVideos")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| TIDAL artist id |
 **countryCode** | **kotlin.String**| ISO 3166-1 alpha-2 country code |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned. Available options: videos | [optional]

### Return type

[**ArtistRelationshipsDocument**](ArtistRelationshipsDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

<a id="getArtists"></a>
# **getArtists**
> ArtistsDataDocument getArtists(countryCode, include, filterId)

Get multiple artists

Retrieve multiple artist details.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ArtistJSONAPIApi()
val countryCode : kotlin.String = US // kotlin.String | ISO 3166-1 alpha-2 country code
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned. Available options: albums, tracks, videos, similarArtists
val filterId : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows to filter the collection of resources based on id attribute value
try {
    val result : ArtistsDataDocument = apiInstance.getArtists(countryCode, include, filterId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ArtistJSONAPIApi#getArtists")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ArtistJSONAPIApi#getArtists")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **countryCode** | **kotlin.String**| ISO 3166-1 alpha-2 country code |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned. Available options: albums, tracks, videos, similarArtists | [optional]
 **filterId** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows to filter the collection of resources based on id attribute value | [optional]

### Return type

[**ArtistsDataDocument**](ArtistsDataDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

<a id="getSimilarArtists"></a>
# **getSimilarArtists**
> ArtistRelationshipsDocument getSimilarArtists(id, countryCode, include)

Relationship: similar artists

This endpoint can be used to retrieve a list of artists similar to the given artist.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ArtistJSONAPIApi()
val id : kotlin.String = 1566 // kotlin.String | TIDAL id of the artist
val countryCode : kotlin.String = US // kotlin.String | ISO 3166-1 alpha-2 country code
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned. Available options: similarArtists
try {
    val result : ArtistRelationshipsDocument = apiInstance.getSimilarArtists(id, countryCode, include)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ArtistJSONAPIApi#getSimilarArtists")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ArtistJSONAPIApi#getSimilarArtists")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| TIDAL id of the artist |
 **countryCode** | **kotlin.String**| ISO 3166-1 alpha-2 country code |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned. Available options: similarArtists | [optional]

### Return type

[**ArtistRelationshipsDocument**](ArtistRelationshipsDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

