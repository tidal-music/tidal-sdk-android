package com.tidal.sdk.auth.di

import com.tidal.sdk.auth.Auth
import com.tidal.sdk.auth.login.CodeChallengeBuilder
import com.tidal.sdk.auth.login.LoginRepository
import com.tidal.sdk.auth.login.LoginUriBuilder
import com.tidal.sdk.auth.model.AuthConfig
import com.tidal.sdk.auth.network.LoginService
import com.tidal.sdk.auth.network.createLoginService
import com.tidal.sdk.auth.storage.TokensStore
import com.tidal.sdk.auth.util.RetryPolicy
import com.tidal.sdk.auth.util.TimeProvider
import com.tidal.sdk.common.TidalMessage
import dagger.Module
import dagger.Provides
import dagger.Reusable
import de.jensklingenberg.ktorfit.Ktorfit
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.sync.Mutex
import retrofit2.Retrofit

@Module
internal class LoginModule {

    @Provides
    @Singleton
    fun provideLoginAssistant(
        loginRepository: LoginRepository,
    ) = Auth(loginRepository)

    @Provides
    @Reusable
    fun provideCodeChallengeBuilder() = CodeChallengeBuilder()

    @Provides
    @Singleton
    fun provideLoginUriBuilder(authConfig: AuthConfig): LoginUriBuilder {
        return LoginUriBuilder(
            authConfig.clientId,
            authConfig.clientUniqueKey,
            authConfig.tidalLoginServiceBaseUrl,
            authConfig.scopes,
        )
    }

    @Provides
    @Singleton
    fun provideLoginService(ktorfit: Ktorfit): LoginService {
        return ktorfit.createLoginService()
    }

    @Provides
    @Singleton
    @Suppress("LongParameterList")
    fun provideLoginRepository(
        authConfig: AuthConfig,
        timeProvider: TimeProvider,
        codeChallengeBuilder: CodeChallengeBuilder,
        loginUriBuilder: LoginUriBuilder,
        loginService: LoginService,
        tokensStore: TokensStore,
        mutex: Mutex,
        @Named("default") retryPolicy: RetryPolicy,
        bus: MutableSharedFlow<TidalMessage>,
    ): LoginRepository = LoginRepository(
        authConfig,
        timeProvider,
        codeChallengeBuilder,
        loginUriBuilder,
        loginService,
        tokensStore,
        retryPolicy,
        mutex,
        bus,
    )
}
