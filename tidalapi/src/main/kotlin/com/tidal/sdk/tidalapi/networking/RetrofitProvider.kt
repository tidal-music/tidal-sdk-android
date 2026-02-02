package com.tidal.sdk.tidalapi.networking

import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.common.d
import com.tidal.sdk.common.logger
import com.tidal.sdk.tidalapi.generated.TidalApiClient.Companion.DEFAULT_CACHE_SIZE
import com.tidal.sdk.tidalapi.generated.models.getOneOfSerializer
import java.io.File
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitProvider {

    private val converterFactories: List<Converter.Factory> =
        listOf(
            ScalarsConverterFactory.create(),
            createJsonSerializer().asConverterFactory("application/json".toMediaType()),
        )

    private fun provideOkHttpClientBuilder(
        credentialsProvider: CredentialsProvider,
        cacheDir: File?,
        cacheSize: Long,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .apply { cacheDir?.let { cache(Cache(it, cacheSize)) } }
            .addInterceptor(AuthInterceptor(credentialsProvider))
            .authenticator(DefaultAuthenticator(credentialsProvider))
            .addInterceptor(getLoggingInterceptor())
            .build()

    fun provideRetrofit(
        baseUrl: String,
        credentialsProvider: CredentialsProvider,
        cacheDir: File? = null,
        cacheSize: Long = DEFAULT_CACHE_SIZE,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(provideOkHttpClientBuilder(credentialsProvider, cacheDir, cacheSize))
            .apply { converterFactories.forEach { addConverterFactory(it) } }
            .build()

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor.Logger { String.logger.d { it } }
        return HttpLoggingInterceptor(logging).apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
    }

    private fun createJsonSerializer(): Json {
        val oneOfSerializer = SerializersModule { include(getOneOfSerializer()) }

        return Json {
            classDiscriminator = "type"
            ignoreUnknownKeys = true
            serializersModule = oneOfSerializer
        }
    }
}
