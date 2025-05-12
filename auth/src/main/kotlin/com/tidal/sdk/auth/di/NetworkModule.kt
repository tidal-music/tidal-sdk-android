package com.tidal.sdk.auth.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tidal.sdk.auth.model.AuthConfig
import com.tidal.sdk.auth.network.NetworkLogLevel
import com.tidal.sdk.auth.util.AuthHttp
import com.tidal.sdk.common.d
import com.tidal.sdk.common.logger
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.CertificatePinner
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
internal class NetworkModule {

    private val authHttp by lazy { AuthHttp() }

    @Provides
    @Singleton
    fun provideOkHttpClient(config: AuthConfig): OkHttpClient {
        val builder = OkHttpClient.Builder()
        with(config) {
            if (logLevel != NetworkLogLevel.NONE) {
                logger.d { "Adding logging interceptor with level $logLevel" }
                builder.addInterceptor(getLoggingInterceptor(logLevel))
            }
            if (enableCertificatePinning) {
                builder.certificatePinner(getCertificatePinner(config))
            }
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(config: AuthConfig, okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        val jsonConverter = Json { ignoreUnknownKeys = true }.asConverterFactory(contentType)
        return Retrofit.Builder()
            .baseUrl(config.tidalAuthServiceBaseUrl)
            .client(okHttpClient)
            .addConverterFactory(jsonConverter)
            .build()
    }

    private fun getLoggingInterceptor(level: NetworkLogLevel): HttpLoggingInterceptor {
        val okhttpLevel = HttpLoggingInterceptor.Level.entries.first { it.name == level.name }
        val logging = HttpLoggingInterceptor.Logger { authHttp.log(it) }
        return HttpLoggingInterceptor(logging).apply { setLevel(okhttpLevel) }
    }

    private fun getCertificatePinner(config: AuthConfig): CertificatePinner {
        val authHost = config.tidalAuthServiceBaseUrl.toHttpUrl().host
        return CertificatePinner.Builder()
            .apply { CERTIFICATE_PINS.forEach { add(authHost, it) } }
            .build()
    }

    companion object {
        private val CERTIFICATE_PINS =
            arrayOf(
                "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=",
                "sha256/f0KW/FtqTjs108NpYj42SrGvOB2PpxIVM8nWxjPqJGE=",
                "sha256/NqvDJlas/GRcYbcWE8S/IceH9cq77kg0jVhZeAPXq8k=",
                "sha256/9+ze1cZgR9KO1kZrVDxA4HQ6voHRCSVNz4RdTCx4U8U=",
            )
    }
}
