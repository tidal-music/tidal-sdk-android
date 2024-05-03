package com.tidal.sdk.auth.di

import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.auth.DefaultCredentialsProvider
import com.tidal.sdk.auth.TokenRepository
import com.tidal.sdk.auth.login.LoginRepository
import com.tidal.sdk.auth.util.DefaultRetryPolicy
import com.tidal.sdk.auth.util.DefaultTimeProvider
import com.tidal.sdk.auth.util.RetryPolicy
import com.tidal.sdk.auth.util.TimeProvider
import com.tidal.sdk.common.TidalMessage
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableSharedFlow

@Module
internal class AuthModule {

    @Provides
    @Singleton
    @Named("default")
    fun provideRetryPolicy(): RetryPolicy = DefaultRetryPolicy()

    @Provides
    @Singleton
    fun provideTimeProvider(): TimeProvider = DefaultTimeProvider()

    @Provides
    @Singleton
    fun provideCredentialsBus(): MutableSharedFlow<TidalMessage> = MutableSharedFlow()

    @Provides
    @Singleton
    @JvmSuppressWildcards
    fun provideCredentialsProvider(
        bus: MutableSharedFlow<TidalMessage>,
        loginRepository: LoginRepository,
        tokenRepository: TokenRepository,
    ): CredentialsProvider = DefaultCredentialsProvider(bus, loginRepository, tokenRepository)
}
