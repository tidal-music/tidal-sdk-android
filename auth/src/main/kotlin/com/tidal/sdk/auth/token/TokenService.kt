package com.tidal.sdk.auth.token

import com.tidal.sdk.auth.model.RefreshResponse
import com.tidal.sdk.auth.model.UpgradeResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

internal interface TokenService {

    @FormUrlEncoded
    @POST("oauth2/token")
    suspend fun getTokenFromRefreshToken(
        @Field("client_id") clientId: String,
        @Field("refresh_token") refreshToken: String,
        @Field("grant_type") grantType: String,
        @Field("scope") scope: String,
    ): RefreshResponse

    @FormUrlEncoded
    @POST("oauth2/token")
    suspend fun getTokenFromClientSecret(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String,
        @Field("scope") scope: String,
    ): RefreshResponse

    @FormUrlEncoded
    @POST("oauth2/token")
    suspend fun upgradeToken(
        @Field("refresh_token") refreshToken: String,
        @Field("client_unique_key") clientUniqueKey: String?,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String?,
        @Field("scope") scopes: String,
        @Field("grant_type") grantType: String,
    ): UpgradeResponse
}
