package com.tidal.sdk.tidalapi.networking

import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.common.d
import com.tidal.sdk.common.logger
import com.tidal.sdk.tidalapi.generated.models.getOneOfSerializer
import java.io.File
import java.util.concurrent.TimeUnit
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

/**
 * @param[retryPolicy] The [RetryPolicy] driving automatic retry of failed requests. Defaults to
 *   [DefaultRetryPolicy] (the three-category model, retrying GET/HEAD only). Pass `null` to disable
 *   retry entirely.
 * @param[readTimeoutMillis] The OkHttp read timeout, in milliseconds. Defaults to
 *   [DEFAULT_READ_TIMEOUT_MILLIS] (5s). A read that exceeds it surfaces a `SocketTimeoutException`,
 *   which the retry interceptor classifies as a timeout. (The 5s default is below the timeout
 *   category's 8s base delay; the intended relationship is still being confirmed.)
 */
class RetrofitProvider
@JvmOverloads
constructor(
    private val cacheDir: File? = null,
    private val cacheSize: Long = DEFAULT_CACHE_SIZE,
    private val retryPolicy: RetryPolicy? = DefaultRetryPolicy(),
    private val readTimeoutMillis: Long = DEFAULT_READ_TIMEOUT_MILLIS,
) {

    private val converterFactories: List<Converter.Factory> =
        listOf(
            ScalarsConverterFactory.create(),
            createJsonSerializer().asConverterFactory("application/json".toMediaType()),
        )

    private fun provideOkHttpClient(credentialsProvider: CredentialsProvider): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(readTimeoutMillis, TimeUnit.MILLISECONDS)
            .apply { cacheDir?.let { cache(Cache(it, cacheSize)) } }
            .apply { retryPolicy?.let { addInterceptor(RetryInterceptor(it)) } }
            .addInterceptor(AuthInterceptor(credentialsProvider))
            .authenticator(DefaultAuthenticator(credentialsProvider))
            .addInterceptor(getLoggingInterceptor())
            .build()

    fun provideRetrofit(baseUrl: String, credentialsProvider: CredentialsProvider): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(provideOkHttpClient(credentialsProvider))
            .apply { converterFactories.forEach { addConverterFactory(it) } }
            .build()

    companion object {
        const val DEFAULT_CACHE_SIZE = 10L * 1024 * 1024 // 10 MB
        const val DEFAULT_READ_TIMEOUT_MILLIS = 5_000L
    }

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
