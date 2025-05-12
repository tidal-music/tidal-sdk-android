package com.tidal.sdk.auth

import com.tidal.sdk.auth.login.CodeChallengeBuilder
import com.tidal.sdk.auth.login.FakeTokensStore
import com.tidal.sdk.auth.login.LoginRepository
import com.tidal.sdk.auth.login.LoginUriBuilder
import com.tidal.sdk.auth.model.AuthConfig
import com.tidal.sdk.auth.util.RetryPolicy
import com.tidal.sdk.common.TidalMessage
import com.tidal.sdk.util.TEST_CLIENT_ID
import com.tidal.sdk.util.TEST_CLIENT_UNIQUE_KEY
import com.tidal.sdk.util.TEST_TIME_PROVIDER
import com.tidal.sdk.util.makeCredentials
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class TokenMutexTest {

    private var timeProvider = TEST_TIME_PROVIDER
    private val authConfig =
        AuthConfig(
            clientId = TEST_CLIENT_ID,
            clientUniqueKey = TEST_CLIENT_UNIQUE_KEY,
            clientSecret = "myVeryBestSecret",
            credentialsKey = "credentialsKey",
            scopes = setOf(),
            enableCertificatePinning = false,
        )

    private val tokenMutex = Mutex()
    private lateinit var fakeTokensStore: FakeTokensStore
    private lateinit var fakeLoginService: FakeLoginService
    private lateinit var loginRepository: LoginRepository

    private val messageBus: MutableSharedFlow<TidalMessage> = MutableSharedFlow()
    private val testRetryPolicy =
        object : RetryPolicy {
            override val numberOfRetries = 1
            override val delayMillis = 1
            override val delayFactor = 1
        }

    private lateinit var fakeTokenService: FakeTokenService
    private lateinit var tokenRepository: TokenRepository

    private fun createLoginRepository(
        loginService: FakeLoginService,
        tokensStore: FakeTokensStore = FakeTokensStore(""),
        retryPolicy: RetryPolicy = testRetryPolicy,
        bus: MutableSharedFlow<TidalMessage> = messageBus,
        loginBaseUrl: String = "https://login.tidal.com/",
    ) {
        fakeLoginService = loginService
        fakeTokensStore = tokensStore
        loginRepository =
            LoginRepository(
                authConfig,
                timeProvider,
                CodeChallengeBuilder(),
                LoginUriBuilder(
                    TEST_CLIENT_ID,
                    TEST_CLIENT_UNIQUE_KEY,
                    loginBaseUrl,
                    authConfig.scopes,
                ),
                loginService,
                tokensStore,
                retryPolicy,
                tokenMutex,
                bus,
            )
    }

    private fun createTokenRepository(
        tokenService: FakeTokenService,
        tokensStore: FakeTokensStore = FakeTokensStore(authConfig.credentialsKey),
        defaultRetrypolicy: RetryPolicy = testRetryPolicy,
        upgradeRetryPolicy: RetryPolicy = testRetryPolicy,
        bus: MutableSharedFlow<TidalMessage> = messageBus,
    ) {
        fakeTokenService = tokenService
        fakeTokensStore = tokensStore
        tokenRepository =
            TokenRepository(
                authConfig,
                TEST_TIME_PROVIDER,
                tokensStore,
                tokenService,
                defaultRetrypolicy,
                upgradeRetryPolicy,
                tokenMutex,
                bus,
            )
    }

    @Test
    fun `setCredentials execution waits until in-flight requests are done`() = runTest {
        // given
        createTokenRepository(FakeTokenService().apply { responseDelay = 1000 })
        createLoginRepository(FakeLoginService(), tokensStore = fakeTokensStore)

        val credentials = makeCredentials(clientId = "myForcedClientId", isExpired = true)
        val refreshToken = "myMostRefreshingToken"

        // when
        val firstJob = launch { tokenRepository.getCredentials(null) }
        val secondJob = launch {
            delay(10)
            loginRepository.setCredentials(credentials, refreshToken)
        }
        firstJob.join()
        secondJob.join()

        // then
        assert(fakeTokensStore.tokensList.first().refreshToken == null) {
            "The first saved Tokens should not contain a refreshToken, as none was set and we aren't logged in"
        }
        assert(fakeTokensStore.tokensList.last().refreshToken == refreshToken) {
            "The last saved Tokens should contain the refreshToken we have saved"
        }
    }
}
