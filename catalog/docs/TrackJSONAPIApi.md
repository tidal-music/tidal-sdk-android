# TrackJSONAPIApi

All URIs are relative to *https://openapi.stage.tidal.com/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getSimilarAlbums**](TrackJSONAPIApi.md#getSimilarAlbums) | **GET** /tracks/{id}/relationships/similarTracks | Relationship: similar tracks
[**getTrack**](TrackJSONAPIApi.md#getTrack) | **GET** /tracks/{id} | Get single track
[**getTrackAlbums**](TrackJSONAPIApi.md#getTrackAlbums) | **GET** /tracks/{id}/relationships/albums | Relationship: albums
[**getTrackArtists**](TrackJSONAPIApi.md#getTrackArtists) | **GET** /tracks/{id}/relationships/artists | Relationship: artists
[**getTrackProviders**](TrackJSONAPIApi.md#getTrackProviders) | **GET** /tracks/{id}/relationships/providers | Relationship: providers
[**getTracks**](TrackJSONAPIApi.md#getTracks) | **GET** /tracks | Get multiple tracks


<a id="getSimilarAlbums"></a>
# **getSimilarAlbums**
> TrackRelationshipsDocument getSimilarAlbums(id, countryCode, include)

Relationship: similar tracks

This endpoint can be used to retrieve a list of tracks similar to the given track.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = TrackJSONAPIApi()
val id : kotlin.String = 251380837 // kotlin.String | TIDAL id of the track
val countryCode : kotlin.String = US // kotlin.String | ISO 3166-1 alpha-2 country code
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned. Available options: similarTracks
try {
    val result : TrackRelationshipsDocument = apiInstance.getSimilarAlbums(id, countryCode, include)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TrackJSONAPIApi#getSimilarAlbums")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TrackJSONAPIApi#getSimilarAlbums")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| TIDAL id of the track |
 **countryCode** | **kotlin.String**| ISO 3166-1 alpha-2 country code |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned. Available options: similarTracks | [optional]

### Return type

[**TrackRelationshipsDocument**](TrackRelationshipsDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

<a id="getTrack"></a>
# **getTrack**
> TrackDataDocument getTrack(id, countryCode, include)

Get single track

Retrieve track details by TIDAL track id.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = TrackJSONAPIApi()
val id : kotlin.String = 251380837 // kotlin.String | TIDAL track id
val countryCode : kotlin.String = US // kotlin.String | ISO 3166-1 alpha-2 country code
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned. Available options: artists, albums, providers, similarTracks
try {
    val result : TrackDataDocument = apiInstance.getTrack(id, countryCode, include)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TrackJSONAPIApi#getTrack")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TrackJSONAPIApi#getTrack")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| TIDAL track id |
 **countryCode** | **kotlin.String**| ISO 3166-1 alpha-2 country code |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned. Available options: artists, albums, providers, similarTracks | [optional]

### Return type

[**TrackDataDocument**](TrackDataDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

<a id="getTrackAlbums"></a>
# **getTrackAlbums**
> TrackRelationshipsDocument getTrackAlbums(id, countryCode, include)

Relationship: albums

Retrieve album details of the related track.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = TrackJSONAPIApi()
val id : kotlin.String = 251380837 // kotlin.String | TIDAL track id
val countryCode : kotlin.String = US // kotlin.String | ISO 3166-1 alpha-2 country code
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned. Available options: albums
try {
    val result : TrackRelationshipsDocument = apiInstance.getTrackAlbums(id, countryCode, include)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TrackJSONAPIApi#getTrackAlbums")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TrackJSONAPIApi#getTrackAlbums")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| TIDAL track id |
 **countryCode** | **kotlin.String**| ISO 3166-1 alpha-2 country code |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned. Available options: albums | [optional]

### Return type

[**TrackRelationshipsDocument**](TrackRelationshipsDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

<a id="getTrackArtists"></a>
# **getTrackArtists**
> TrackRelationshipsDocument getTrackArtists(id, countryCode, include)

Relationship: artists

Retrieve artist details of the related track.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = TrackJSONAPIApi()
val id : kotlin.String = 251380837 // kotlin.String | TIDAL track id
val countryCode : kotlin.String = US // kotlin.String | ISO 3166-1 alpha-2 country code
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned. Available options: artists
try {
    val result : TrackRelationshipsDocument = apiInstance.getTrackArtists(id, countryCode, include)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TrackJSONAPIApi#getTrackArtists")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TrackJSONAPIApi#getTrackArtists")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| TIDAL track id |
 **countryCode** | **kotlin.String**| ISO 3166-1 alpha-2 country code |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned. Available options: artists | [optional]

### Return type

[**TrackRelationshipsDocument**](TrackRelationshipsDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

<a id="getTrackProviders"></a>
# **getTrackProviders**
> TrackRelationshipsDocument getTrackProviders(id, countryCode, include)

Relationship: providers

This endpoint can be used to retrieve a list of track&#39;s related providers.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = TrackJSONAPIApi()
val id : kotlin.String = 251380837 // kotlin.String | TIDAL id of the track
val countryCode : kotlin.String = US // kotlin.String | ISO 3166-1 alpha-2 country code
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned. Available options: providers
try {
    val result : TrackRelationshipsDocument = apiInstance.getTrackProviders(id, countryCode, include)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TrackJSONAPIApi#getTrackProviders")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TrackJSONAPIApi#getTrackProviders")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| TIDAL id of the track |
 **countryCode** | **kotlin.String**| ISO 3166-1 alpha-2 country code |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned. Available options: providers | [optional]

### Return type

[**TrackRelationshipsDocument**](TrackRelationshipsDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

<a id="getTracks"></a>
# **getTracks**
> TracksDataDocument getTracks(countryCode, include, filterId, filterIsrc)

Get multiple tracks

Retrieve multiple track details.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = TrackJSONAPIApi()
val countryCode : kotlin.String = US // kotlin.String | ISO 3166-1 alpha-2 country code
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned. Available options: artists, albums, providers, similarTracks
val filterId : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows to filter the collection of resources based on id attribute value
val filterIsrc : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows to filter the collection of resources based on isrc attribute value
try {
    val result : TracksDataDocument = apiInstance.getTracks(countryCode, include, filterId, filterIsrc)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TrackJSONAPIApi#getTracks")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TrackJSONAPIApi#getTracks")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **countryCode** | **kotlin.String**| ISO 3166-1 alpha-2 country code |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned. Available options: artists, albums, providers, similarTracks | [optional]
 **filterId** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows to filter the collection of resources based on id attribute value | [optional]
 **filterIsrc** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows to filter the collection of resources based on isrc attribute value | [optional]

### Return type

[**TracksDataDocument**](TracksDataDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

