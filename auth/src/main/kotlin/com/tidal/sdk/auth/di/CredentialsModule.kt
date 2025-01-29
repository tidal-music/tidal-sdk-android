package com.tidal.sdk.auth.di

import com.tidal.sdk.auth.TokenRepository
import com.tidal.sdk.auth.model.AuthConfig
import com.tidal.sdk.auth.storage.TokensStore
import com.tidal.sdk.auth.token.TokenService
import com.tidal.sdk.auth.token.createTokenService
import com.tidal.sdk.auth.util.RetryPolicy
import com.tidal.sdk.auth.util.TimeProvider
import com.tidal.sdk.auth.util.UpgradeTokenRetryPolicy
import com.tidal.sdk.common.TidalMessage
import dagger.Module
import dagger.Provides
import de.jensklingenberg.ktorfit.Ktorfit
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.sync.Mutex
import retrofit2.Retrofit

@Module
internal class CredentialsModule {

    @Provides
    @Singleton
    @Named("upgrade")
    fun provideUpgradeRetryPolicy(): RetryPolicy = UpgradeTokenRetryPolicy()

    @Provides
    @Singleton
    fun provideTokenService(ktorfit: Ktorfit): TokenService {
        return ktorfit.createTokenService()
    }

    @Singleton
    @Provides
    fun provideTokenRepository(
        authConfig: AuthConfig,
        timeProvider: TimeProvider,
        tokensStore: TokensStore,
        tokenService: TokenService,
        @Named("default") defaultBackoffPolicy: RetryPolicy,
        @Named("upgrade") upgradeBackoffPolicy: RetryPolicy,
        mutex: Mutex,
        bus: MutableSharedFlow<TidalMessage>,
    ) = TokenRepository(
        authConfig,
        timeProvider,
        tokensStore,
        tokenService,
        defaultBackoffPolicy,
        upgradeBackoffPolicy,
        mutex,
        bus,
    )
}
