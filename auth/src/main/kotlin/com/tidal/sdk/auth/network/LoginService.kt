package com.tidal.sdk.auth.network

import com.tidal.sdk.auth.model.DeviceAuthorizationResponse
import com.tidal.sdk.auth.model.LoginResponse
import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.POST

internal interface LoginService {

    @FormUrlEncoded
    @POST("oauth2/token")
    suspend fun getTokenWithCodeVerifier(
        @Field("code") code: String,
        @Field("client_id") clientId: String,
        @Field("grant_type") grantType: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("scope") scopes: String,
        @Field("code_verifier") codeVerifier: String,
        @Field("client_unique_key") clientUniqueKey: String?,
    ): LoginResponse

    @FormUrlEncoded
    @POST("oauth2/device_authorization")
    suspend fun getDeviceAuthorization(
        @Field("client_id") clientId: String,
        @Field("scope") scope: String,
    ): DeviceAuthorizationResponse

    @FormUrlEncoded
    @POST("oauth2/token")
    suspend fun getTokenFromDeviceCode(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String?,
        @Field("device_code") deviceCode: String,
        @Field("grant_type") grantType: String,
        @Field("scope") scopes: String,
        @Field("client_unique_key") clientUniqueKey: String?,
    ): LoginResponse
}
