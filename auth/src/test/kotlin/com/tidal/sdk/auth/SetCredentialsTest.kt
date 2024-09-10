package com.tidal.sdk.auth

import com.tidal.sdk.auth.login.CodeChallengeBuilder
import com.tidal.sdk.auth.login.FakeTokensStore
import com.tidal.sdk.auth.login.LoginRepository
import com.tidal.sdk.auth.login.LoginUriBuilder
import com.tidal.sdk.auth.model.AuthConfig
import com.tidal.sdk.auth.model.AuthResult
import com.tidal.sdk.auth.model.Credentials
import com.tidal.sdk.auth.network.NetworkingJobHandler
import com.tidal.sdk.auth.util.RetryPolicy
import com.tidal.sdk.common.TidalMessage
import com.tidal.sdk.util.TEST_CLIENT_ID
import com.tidal.sdk.util.TEST_CLIENT_UNIQUE_KEY
import com.tidal.sdk.util.TEST_TIME_PROVIDER
import com.tidal.sdk.util.makeCredentials
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class SetCredentialsTest {

    private var timeProvider = TEST_TIME_PROVIDER
    private val authConfig = AuthConfig(
        clientId = TEST_CLIENT_ID,
        clientUniqueKey = TEST_CLIENT_UNIQUE_KEY,
        clientSecret = "myVeryBestSecret",
        credentialsKey = "credentialsKey",
        scopes = setOf(),
        enableCertificatePinning = false,
    )

    private lateinit var fakeTokensStore: FakeTokensStore
    private lateinit var fakeLoginService: FakeLoginService
    private lateinit var loginRepository: LoginRepository

    private val messageBus: MutableSharedFlow<TidalMessage> = MutableSharedFlow()
    private val testRetryPolicy = object : RetryPolicy {
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
        networkingJobHandler: NetworkingJobHandler,
    ) {
        fakeLoginService = loginService
        fakeTokensStore = tokensStore
        loginRepository = LoginRepository(
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
            bus,
            networkingJobHandler,
        )
    }

    private fun createTokenRepository(
        tokenService: FakeTokenService,
        tokensStore: FakeTokensStore = FakeTokensStore(authConfig.credentialsKey),
        defaultRetrypolicy: RetryPolicy = testRetryPolicy,
        upgradeRetryPolicy: RetryPolicy = testRetryPolicy,
        bus: MutableSharedFlow<TidalMessage> = messageBus,
        networkingJobHandler: NetworkingJobHandler,
    ) {
        fakeTokenService = tokenService
        fakeTokensStore = tokensStore
        tokenRepository = TokenRepository(
            authConfig,
            TEST_TIME_PROVIDER,
            tokensStore,
            tokenService,
            defaultRetrypolicy,
            upgradeRetryPolicy,
            bus,
            networkingJobHandler,
        )
    }

    @Test
    fun `setCredentials should cancel jobs requesting the token endpoint`() = runTest {
        // given
        val networkingJobHandler = NetworkingJobHandler(this)
        createTokenRepository(
            FakeTokenService().apply { responseDelay = 1000 },
            networkingJobHandler = networkingJobHandler,
        )
        createLoginRepository(
            FakeLoginService(),
            tokensStore = fakeTokensStore,
            networkingJobHandler = networkingJobHandler,
        )

        val credentials =
            makeCredentials(
                userId = "myLittleUser",
                clientId = authConfig.clientId,
                isExpired = false
            )
        val refreshToken = "myMostRefreshingToken"
        val jobs = mutableListOf<Job>()
        val cancelledCreds = mutableListOf<AuthResult<Credentials>>()

        // when
        (0..3).forEach { _ ->
            jobs.add(
                launch {
                    cancelledCreds.add(tokenRepository.getCredentials(null))
                }
            )
        }

        jobs.add(
            launch {
                delay(10)
                loginRepository.setCredentials(credentials, refreshToken)
            }
        )
        jobs.forEach { it.join() }

        // then
        assert(fakeTokensStore.tokensList.size == 1) {
            "Since getCredentials gets cancelled, we should only have one set of Tokens saved"
        }
        assert(
            fakeTokensStore.tokensList.first().refreshToken == refreshToken,
        ) { "The saved Tokens should contain the refreshToken we passed to setCredentials" }
        assert(cancelledCreds.all { it is AuthResult.Failure }) {
            "All cancelled jobs should have returned a failure"
        }

        // when
        val savedCreds = tokenRepository.getCredentials(null)
        println(savedCreds)
        // then
        assert(savedCreds is AuthResult.Success) {
            "getCredentials after setCredentials should return a success"
        }
        assert((savedCreds as AuthResult.Success).data == credentials) {
            "The saved credentials should be the same as the ones we set"
        }
    }
}
