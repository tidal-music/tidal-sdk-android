package com.tidal.sdk.player.di

import android.content.Context
import com.google.gson.Gson
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.auth.model.Credentials
import com.tidal.sdk.player.auth.AuthorizationInterceptor
import com.tidal.sdk.player.auth.DefaultAuthenticator
import com.tidal.sdk.player.auth.RequestAuthorizationDelegate
import com.tidal.sdk.player.auth.ShouldAddAuthorizationHeader
import com.tidal.sdk.player.common.Common
import com.tidal.sdk.player.interceptor.NonIntrusiveHttpLoggingInterceptor
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.Reusable
import java.io.File
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
@Suppress("TooManyFunctions")
internal object NetworkModule {

    @Provides
    @Reusable
    @Named("appSpecificCacheDir")
    fun appSpecificCacheDir(context: Context) = context.cacheDir

    @Provides
    @Reusable
    @Named("okHttpCacheDir")
    fun okHttpCacheDir(@Named("appSpecificCacheDir") appSpecificCacheDir: File) =
        File(appSpecificCacheDir, OKHTTP_CACHE_DIR)

    @Provides
    @Reusable
    fun cache(@Named("okHttpCacheDir") okHttpCacheDir: File) =
        Cache(okHttpCacheDir, OKHTTP_CACHE_SIZE)

    @Provides
    @Reusable
    fun endpointsRequiringCredentialLevels() = mapOf(
        "${Common.TIDAL_API_ENDPOINT_V1}rt/connect" to setOf(Credentials.Level.USER),
    )

    @Provides
    @Reusable
    fun requestAuthorizationDelegate(
        endpointsRequiringCredentialLevels:
        @JvmSuppressWildcards Map<String, Set<Credentials.Level>>,
    ) = RequestAuthorizationDelegate(endpointsRequiringCredentialLevels)

    @Provides
    @Reusable
    fun shouldAddAuthorizationHeader() = ShouldAddAuthorizationHeader()

    @Provides
    @Reusable
    fun authorizationInterceptor(
        credentialsProvider: CredentialsProvider,
        requestAuthorizationDelegate: RequestAuthorizationDelegate,
        shouldAddAuthorizationHeader: ShouldAddAuthorizationHeader,
    ) = AuthorizationInterceptor(
        credentialsProvider,
        requestAuthorizationDelegate,
        shouldAddAuthorizationHeader,
    )

    @Provides
    @Reusable
    fun defaultAuthenticator(
        gson: Gson,
        credentialsProvider: CredentialsProvider,
        requestAuthorizationDelegate: RequestAuthorizationDelegate,
    ) = DefaultAuthenticator(gson, credentialsProvider, requestAuthorizationDelegate)

    @Provides
    @Reusable
    @Named("basicLevelHttpLoggingInterceptor")
    fun basicLevelHttpLoggingInterceptor() = HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.BASIC,
    )

    @Provides
    @Reusable
    @Named("bodyLevelHttpLoggingInterceptor")
    fun bodyLevelHttpLoggingInterceptor() = HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.BODY,
    )

    @Provides
    @Reusable
    fun nonIntrusiveHttpLoggingInterceptor(
        @Named("basicLevelHttpLoggingInterceptor")
        basicLevelHttpLoggingInterceptor: HttpLoggingInterceptor,
        @Named("bodyLevelHttpLoggingInterceptor")
        bodyLevelHttpLoggingInterceptor: HttpLoggingInterceptor,
    ) = NonIntrusiveHttpLoggingInterceptor(
        basicLevelHttpLoggingInterceptor,
        bodyLevelHttpLoggingInterceptor,
    )

    @Provides
    @Singleton
    @LocalWithAuth
    fun okHttpClient(
        okHttpClient: OkHttpClient,
        authorizationInterceptor: AuthorizationInterceptor,
        defaultAuthenticator: DefaultAuthenticator,
        @Named("isDebuggable") isDebuggable: Boolean,
        httpLoggingInterceptor: Lazy<NonIntrusiveHttpLoggingInterceptor>,
    ) = okHttpClient.newBuilder()
        .addInterceptor(authorizationInterceptor)
        .authenticator(defaultAuthenticator)
        .apply {
            if (isDebuggable) {
                addNetworkInterceptor(httpLoggingInterceptor.get())
            }
        }.build()

    @Provides
    @Singleton
    @LocalWithCacheAndAuth
    fun okHttpClientWithCacheAndAuth(
        @LocalWithAuth
        okHttpClient: OkHttpClient,
        okHttpCache: Cache,
    ) = okHttpClient.newBuilder()
        .cache(okHttpCache)
        .build()
}

private const val OKHTTP_CACHE_DIR = "okhttp"
private const val OKHTTP_CACHE_SIZE = (1024 * 1024 * 50).toLong()

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class LocalWithAuth

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class LocalWithCacheAndAuth
