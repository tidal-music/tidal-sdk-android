# ProviderJSONAPIApi

All URIs are relative to *https://openapi.stage.tidal.com/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getProvider**](ProviderJSONAPIApi.md#getProvider) | **GET** /providers/{id} | Get single provider
[**getProviders**](ProviderJSONAPIApi.md#getProviders) | **GET** /providers | Get multiple providers


<a id="getProvider"></a>
# **getProvider**
> ProviderDataDocument getProvider(id, include)

Get single provider

Retrieve provider details by TIDAL provider id.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ProviderJSONAPIApi()
val id : kotlin.String = 771 // kotlin.String | TIDAL provider id
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned
try {
    val result : ProviderDataDocument = apiInstance.getProvider(id, include)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ProviderJSONAPIApi#getProvider")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ProviderJSONAPIApi#getProvider")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| TIDAL provider id |
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned | [optional]

### Return type

[**ProviderDataDocument**](ProviderDataDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

<a id="getProviders"></a>
# **getProviders**
> ProvidersDataDocument getProviders(include, filterId)

Get multiple providers

Retrieve multiple provider details.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ProviderJSONAPIApi()
val include : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows the client to customize which related resources should be returned
val filterId : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Allows to filter the collection of resources based on id attribute value
try {
    val result : ProvidersDataDocument = apiInstance.getProviders(include, filterId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ProviderJSONAPIApi#getProviders")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ProviderJSONAPIApi#getProviders")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **include** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows the client to customize which related resources should be returned | [optional]
 **filterId** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Allows to filter the collection of resources based on id attribute value | [optional]

### Return type

[**ProvidersDataDocument**](ProvidersDataDocument.md)

### Authorization


Configure OAuth2_Client_Credentials_grant_flow:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/vnd.api+json
 - **Accept**: application/vnd.api+json

