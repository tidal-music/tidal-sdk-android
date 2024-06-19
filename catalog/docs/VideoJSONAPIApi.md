# VideoJSONAPIApi

All URIs are relative to *https://openapi.stage.tidal.com/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getVideo**](VideoJSONAPIApi.md#getVideo) | **GET** /videos/{id} | Get single video
[**getVideoAlbums**](VideoJSONAPIApi.md#getVideoAlbums) | **GET** /videos/{id}/relationships/albums | Relationship: albums
[**getVideoArtists**](VideoJSONAPIApi.md#getVideoArtists) | **GET** /videos/{id}/relationships/artists | Relationship: artists
[**getVideoProviders**](VideoJSONAPIApi.md#getVideoProviders) | **GET** /videos/{id}/relationships/providers | Relationship: providers
[**getVideos**](VideoJSONAPIApi.md#getVideos) | **GET** /videos | Get multiple videos


<a id="getVideo"></a>
# **getVideo**
> VideoDataDocument getVideo(id, countryCode, include)

Get single video

Retrieve video details by TIDAL video id.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = VideoJSONAPIApi()
val id : kotlin.String = 75623239 // kotlin.String | TIDAL video id
val countryCode : kotlin.String = US // kotlin.String | ISO 3166-1 alpha-2 country code
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned. Available options: artists, albums, providers
try {
    val result : VideoDataDocument = apiInstance.getVideo(id, countryCode, include)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VideoJSONAPIApi#getVideo")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VideoJSONAPIApi#getVideo")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| TIDAL video id |
 **countryCode** | **kotlin.String**| ISO 3166-1 alpha-2 country code |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned. Available options: artists, albums, providers | [optional]

### Return type

[**VideoDataDocument**](VideoDataDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

<a id="getVideoAlbums"></a>
# **getVideoAlbums**
> VideoRelationshipsDocument getVideoAlbums(id, countryCode, include)

Relationship: albums

Retrieve album details of the related video.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = VideoJSONAPIApi()
val id : kotlin.String = 75623239 // kotlin.String | TIDAL video id
val countryCode : kotlin.String = US // kotlin.String | ISO 3166-1 alpha-2 country code
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned. Available options: albums
try {
    val result : VideoRelationshipsDocument = apiInstance.getVideoAlbums(id, countryCode, include)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VideoJSONAPIApi#getVideoAlbums")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VideoJSONAPIApi#getVideoAlbums")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| TIDAL video id |
 **countryCode** | **kotlin.String**| ISO 3166-1 alpha-2 country code |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned. Available options: albums | [optional]

### Return type

[**VideoRelationshipsDocument**](VideoRelationshipsDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

<a id="getVideoArtists"></a>
# **getVideoArtists**
> VideoRelationshipsDocument getVideoArtists(id, countryCode, include)

Relationship: artists

Retrieve artist details of the related video.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = VideoJSONAPIApi()
val id : kotlin.String = 75623239 // kotlin.String | TIDAL video id
val countryCode : kotlin.String = US // kotlin.String | ISO 3166-1 alpha-2 country code
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned. Available options: artists
try {
    val result : VideoRelationshipsDocument = apiInstance.getVideoArtists(id, countryCode, include)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VideoJSONAPIApi#getVideoArtists")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VideoJSONAPIApi#getVideoArtists")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| TIDAL video id |
 **countryCode** | **kotlin.String**| ISO 3166-1 alpha-2 country code |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned. Available options: artists | [optional]

### Return type

[**VideoRelationshipsDocument**](VideoRelationshipsDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

<a id="getVideoProviders"></a>
# **getVideoProviders**
> VideoRelationshipsDocument getVideoProviders(id, countryCode, include)

Relationship: providers

This endpoint can be used to retrieve a list of video&#39;s related providers.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = VideoJSONAPIApi()
val id : kotlin.String = 75623239 // kotlin.String | TIDAL id of the video
val countryCode : kotlin.String = US // kotlin.String | ISO 3166-1 alpha-2 country code
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned. Available options: providers
try {
    val result : VideoRelationshipsDocument = apiInstance.getVideoProviders(id, countryCode, include)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VideoJSONAPIApi#getVideoProviders")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VideoJSONAPIApi#getVideoProviders")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| TIDAL id of the video |
 **countryCode** | **kotlin.String**| ISO 3166-1 alpha-2 country code |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned. Available options: providers | [optional]

### Return type

[**VideoRelationshipsDocument**](VideoRelationshipsDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

<a id="getVideos"></a>
# **getVideos**
> VideosDataDocument getVideos(countryCode, include, filterId, filterIsrc)

Get multiple videos

Retrieve multiple video details.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = VideoJSONAPIApi()
val countryCode : kotlin.String = US // kotlin.String | ISO 3166-1 alpha-2 country code
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned. Available options: artists, albums, providers
val filterId : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows to filter the collection of resources based on id attribute value
val filterIsrc : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows to filter the collection of resources based on isrc attribute value
try {
    val result : VideosDataDocument = apiInstance.getVideos(countryCode, include, filterId, filterIsrc)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VideoJSONAPIApi#getVideos")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VideoJSONAPIApi#getVideos")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **countryCode** | **kotlin.String**| ISO 3166-1 alpha-2 country code |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned. Available options: artists, albums, providers | [optional]
 **filterId** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows to filter the collection of resources based on id attribute value | [optional]
 **filterIsrc** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows to filter the collection of resources based on isrc attribute value | [optional]

### Return type

[**VideosDataDocument**](VideosDataDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

