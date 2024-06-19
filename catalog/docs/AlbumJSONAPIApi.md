# AlbumJSONAPIApi

All URIs are relative to *https://openapi.stage.tidal.com/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAlbum**](AlbumJSONAPIApi.md#getAlbum) | **GET** /albums/{id} | Get single album
[**getAlbumArtists**](AlbumJSONAPIApi.md#getAlbumArtists) | **GET** /albums/{id}/relationships/artists | Relationship: artists
[**getAlbumItems**](AlbumJSONAPIApi.md#getAlbumItems) | **GET** /albums/{id}/relationships/items | Relationship: items
[**getAlbumProviders**](AlbumJSONAPIApi.md#getAlbumProviders) | **GET** /albums/{id}/relationships/providers | Relationship: providers
[**getAlbums**](AlbumJSONAPIApi.md#getAlbums) | **GET** /albums | Get multiple albums
[**getSimilarAlbums1**](AlbumJSONAPIApi.md#getSimilarAlbums1) | **GET** /albums/{id}/relationships/similarAlbums | Relationship: similar albums


<a id="getAlbum"></a>
# **getAlbum**
> AlbumDataDocument getAlbum(id, countryCode, include)

Get single album

Retrieve album details by TIDAL album id.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = AlbumJSONAPIApi()
val id : kotlin.String = 251380836 // kotlin.String | TIDAL album id
val countryCode : kotlin.String = US // kotlin.String | ISO 3166-1 alpha-2 country code
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned. Available options: artists, items, providers, similarAlbums
try {
    val result : AlbumDataDocument = apiInstance.getAlbum(id, countryCode, include)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AlbumJSONAPIApi#getAlbum")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AlbumJSONAPIApi#getAlbum")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| TIDAL album id |
 **countryCode** | **kotlin.String**| ISO 3166-1 alpha-2 country code |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned. Available options: artists, items, providers, similarAlbums | [optional]

### Return type

[**AlbumDataDocument**](AlbumDataDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

<a id="getAlbumArtists"></a>
# **getAlbumArtists**
> AlbumRelationshipsDocument getAlbumArtists(id, countryCode, include)

Relationship: artists

Retrieve artist details of the related album.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = AlbumJSONAPIApi()
val id : kotlin.String = 251380836 // kotlin.String | TIDAL album id
val countryCode : kotlin.String = US // kotlin.String | ISO 3166-1 alpha-2 country code
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned. Available options: artists
try {
    val result : AlbumRelationshipsDocument = apiInstance.getAlbumArtists(id, countryCode, include)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AlbumJSONAPIApi#getAlbumArtists")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AlbumJSONAPIApi#getAlbumArtists")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| TIDAL album id |
 **countryCode** | **kotlin.String**| ISO 3166-1 alpha-2 country code |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned. Available options: artists | [optional]

### Return type

[**AlbumRelationshipsDocument**](AlbumRelationshipsDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

<a id="getAlbumItems"></a>
# **getAlbumItems**
> AlbumRelationshipsDocument getAlbumItems(id, countryCode, include)

Relationship: items

Retrieve album item details.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = AlbumJSONAPIApi()
val id : kotlin.String = 251380836 // kotlin.String | TIDAL album id
val countryCode : kotlin.String = US // kotlin.String | ISO 3166-1 alpha-2 country code
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned. Available options: items
try {
    val result : AlbumRelationshipsDocument = apiInstance.getAlbumItems(id, countryCode, include)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AlbumJSONAPIApi#getAlbumItems")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AlbumJSONAPIApi#getAlbumItems")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| TIDAL album id |
 **countryCode** | **kotlin.String**| ISO 3166-1 alpha-2 country code |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned. Available options: items | [optional]

### Return type

[**AlbumRelationshipsDocument**](AlbumRelationshipsDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

<a id="getAlbumProviders"></a>
# **getAlbumProviders**
> AlbumRelationshipsDocument getAlbumProviders(id, countryCode, include)

Relationship: providers

This endpoint can be used to retrieve a list of album&#39;s related providers.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = AlbumJSONAPIApi()
val id : kotlin.String = 251380836 // kotlin.String | TIDAL id of the album
val countryCode : kotlin.String = US // kotlin.String | ISO 3166-1 alpha-2 country code
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned. Available options: providers
try {
    val result : AlbumRelationshipsDocument = apiInstance.getAlbumProviders(id, countryCode, include)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AlbumJSONAPIApi#getAlbumProviders")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AlbumJSONAPIApi#getAlbumProviders")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| TIDAL id of the album |
 **countryCode** | **kotlin.String**| ISO 3166-1 alpha-2 country code |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned. Available options: providers | [optional]

### Return type

[**AlbumRelationshipsDocument**](AlbumRelationshipsDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

<a id="getAlbums"></a>
# **getAlbums**
> AlbumsDataDocument getAlbums(countryCode, include, filterId, filterBarcodeId)

Get multiple albums

Retrieve multiple album details.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = AlbumJSONAPIApi()
val countryCode : kotlin.String = US // kotlin.String | ISO 3166-1 alpha-2 country code
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned. Available options: artists, items, providers, similarAlbums
val filterId : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows to filter the collection of resources based on id attribute value
val filterBarcodeId : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows to filter the collection of resources based on barcodeId attribute value
try {
    val result : AlbumsDataDocument = apiInstance.getAlbums(countryCode, include, filterId, filterBarcodeId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AlbumJSONAPIApi#getAlbums")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AlbumJSONAPIApi#getAlbums")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **countryCode** | **kotlin.String**| ISO 3166-1 alpha-2 country code |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned. Available options: artists, items, providers, similarAlbums | [optional]
 **filterId** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows to filter the collection of resources based on id attribute value | [optional]
 **filterBarcodeId** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows to filter the collection of resources based on barcodeId attribute value | [optional]

### Return type

[**AlbumsDataDocument**](AlbumsDataDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

<a id="getSimilarAlbums1"></a>
# **getSimilarAlbums1**
> AlbumRelationshipsDocument getSimilarAlbums1(id, countryCode, include)

Relationship: similar albums

This endpoint can be used to retrieve a list of albums similar to the given album.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = AlbumJSONAPIApi()
val id : kotlin.String = 251380836 // kotlin.String | TIDAL id of the album
val countryCode : kotlin.String = US // kotlin.String | ISO 3166-1 alpha-2 country code
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned. Available options: similarAlbums
try {
    val result : AlbumRelationshipsDocument = apiInstance.getSimilarAlbums1(id, countryCode, include)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AlbumJSONAPIApi#getSimilarAlbums1")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AlbumJSONAPIApi#getSimilarAlbums1")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| TIDAL id of the album |
 **countryCode** | **kotlin.String**| ISO 3166-1 alpha-2 country code |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned. Available options: similarAlbums | [optional]

### Return type

[**AlbumRelationshipsDocument**](AlbumRelationshipsDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

