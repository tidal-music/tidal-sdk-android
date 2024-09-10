package com.tidal.sdk.auth

import app.cash.turbine.test
import com.tidal.sdk.auth.FakeTokenService.CallType
import com.tidal.sdk.auth.login.FakeTokensStore
import com.tidal.sdk.auth.model.ApiErrorSubStatus
import com.tidal.sdk.auth.model.AuthConfig
import com.tidal.sdk.auth.model.AuthResult
import com.tidal.sdk.auth.model.CredentialsUpdatedMessage
import com.tidal.sdk.auth.model.Tokens
import com.tidal.sdk.auth.network.NetworkingJobHandler
import com.tidal.sdk.auth.util.RetryPolicy
import com.tidal.sdk.auth.util.UpgradeTokenRetryPolicy
import com.tidal.sdk.auth.util.buildTestHttpException
import com.tidal.sdk.common.RetryableError
import com.tidal.sdk.common.TidalMessage
import com.tidal.sdk.util.TEST_CLIENT_ID
import com.tidal.sdk.util.TEST_CLIENT_UNIQUE_KEY
import com.tidal.sdk.util.TEST_TIME_PROVIDER
import com.tidal.sdk.util.makeCredentials
import java.io.IOException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TokenRepositoryTest {

    private val messageBus: MutableSharedFlow<TidalMessage> = MutableSharedFlow()
    private val testRetryPolicy = object : RetryPolicy {
        override val numberOfRetries = 1
        override val delayMillis = 1
        override val delayFactor = 1
    }

    private lateinit var authConfig: AuthConfig
    private lateinit var fakeTokensStore: FakeTokensStore
    private lateinit var fakeTokenService: FakeTokenService
    private lateinit var tokenRepository: TokenRepository

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

    private fun createAuthConfig(
        clientId: String = TEST_CLIENT_ID,
        clientUniqueKey: String = TEST_CLIENT_UNIQUE_KEY,
        scopes: Set<String> = setOf(),
        secret: String? = null,
    ) {
        authConfig = AuthConfig(
            clientId = clientId,
            clientUniqueKey = clientUniqueKey,
            clientSecret = secret,
            credentialsKey = "credentialsKey",
            scopes = scopes,
            enableCertificatePinning = false,
        )
    }

    @BeforeEach
    fun setup() {
        createAuthConfig()
    }

    @Test
    fun `getCredentials returns a stored token if it is not yet expired`() = runTest {
        // given
        val credentials = makeCredentials(
            userId = "valid",
            isExpired = false,
        )
        val tokens = Tokens(
            credentials,
            "refreshToken",
        )
        createTokenRepository(FakeTokenService(), networkingJobHandler = NetworkingJobHandler(this))
        fakeTokensStore.saveTokens(tokens)

        // when
        val result = tokenRepository.getCredentials(null)

        // then
        assert(fakeTokensStore.gets == 2) {
            "There should have been two calls to the TokensStore"
        }
        assert(result.successData!! == tokens.credentials) {
            "Returned Credentials should be the same that was last stored via Tokens"
        }
    }

    @Test
    fun `getCredentials returns Credentials without token if called when logged out and no clientSecret is set`() =
        runTest {
            // given
            createTokenRepository(
                FakeTokenService(),
                networkingJobHandler = NetworkingJobHandler(this),
            )

            // when
            val result = tokenRepository.getCredentials(null)

            // then
            assert(result.successData!!.token == null) {
                "If logged out, calling getCredentials() should return an Credentials with token = null"
            }
        }

    @Test
    fun `getCredentials gets a new one from backend and returns it if stored token is expired`() =
        runTest {
            // given
            val credentials = makeCredentials(
                userId = "invalid",
                isExpired = true,
            )
            val tokens = Tokens(
                credentials,
                "refreshToken",
            )
            createTokenRepository(
                FakeTokenService(),
                networkingJobHandler = NetworkingJobHandler(this),
            )
            fakeTokensStore.saveTokens(tokens)

            // when
            val result = tokenRepository.getCredentials(null)

            // then
            assert(fakeTokenService.calls.any { it == CallType.Refresh }) {
                "If the stored token is about to expire, a call to TokenService should be made"
            }
            assert(result.successData!!.isExpired(TEST_TIME_PROVIDER).not()) {
                "The returned token should be the one retrieved from the service"
            }
        }

    @Test
    fun `getCredentials refreshes the token if the subStatus should trigger a refresh`() = runTest {
        // given
        val credentials = makeCredentials(
            userId = "valid",
            isExpired = false,
        )
        val tokens = Tokens(
            credentials,
            "refreshToken",
        )
        createTokenRepository(FakeTokenService(), networkingJobHandler = NetworkingJobHandler(this))
        fakeTokensStore.saveTokens(tokens)
        ApiErrorSubStatus.entries.filter { it.shouldTriggerRefresh }.forEach { status ->
            // when
            tokenRepository.getCredentials(status.value)
            // then
            assert(fakeTokenService.calls.any { it == CallType.Refresh }) {
                "If the subStatus triggers a refresh, a call to TokenService should be made"
            }
        }
    }

    @Test
    fun `getCredentials does not refresh the token if the subStatus does not trigger a refresh`() =
        runTest {
            // given
            val imaginarySubStatus = "50123"
            val credentials = makeCredentials(
                userId = "valid",
                isExpired = false,
            )
            val tokens = Tokens(
                credentials,
                "refreshToken",
            )
            createTokenRepository(
                FakeTokenService(),
                networkingJobHandler = NetworkingJobHandler(this),
            )
            fakeTokensStore.saveTokens(tokens)

            // when
            tokenRepository.getCredentials(imaginarySubStatus)
            // then
            assert(fakeTokenService.calls.none { it == CallType.Refresh }) {
                "If the subStatus is not on the list, no call to TokenService should be made"
            }
        }

    @Test
    fun `getCredentials retries and finally returns a RetryableError if refreshing from backend fails`() =
        runTest {
            // given
            val credentials = makeCredentials(
                userId = "valid",
                isExpired = true,
            )
            val tokens = Tokens(
                credentials,
                "refreshToken",
            )
            val service = FakeTokenService().apply {
                throwableToThrow = buildTestHttpException(503)
            }
            val expectedCalls = testRetryPolicy.numberOfRetries + 1
            createTokenRepository(service, networkingJobHandler = NetworkingJobHandler(this))
            fakeTokensStore.saveTokens(tokens)

            // when
            val result = tokenRepository.getCredentials(null)

            // then
            assert(
                fakeTokenService.calls.filter {
                    it == CallType.Refresh
                }.size == expectedCalls,
            ) {
                "If the stored token is invalid, the correct number of call attempts to TokenService should be made"
            }
            assert((result as AuthResult.Failure).message is RetryableError) {
                "If the backend call fails, a RetryableError should be returned"
            }
        }

    @Test
    fun `getCredentials returns a lover level token if backend fails with a 400 error`() = runTest {
        // given
        val credentials = makeCredentials(
            userId = "valid",
            isExpired = true,
        )
        val tokens = Tokens(
            credentials,
            "refreshToken",
        )
        val service = FakeTokenService().apply {
            throwableToThrow = buildTestHttpException(400)
        }
        createTokenRepository(service, networkingJobHandler = NetworkingJobHandler(this))
        fakeTokensStore.saveTokens(tokens)

        // when
        val result = tokenRepository.getCredentials(null)

        // then
        assert(
            fakeTokenService.calls.filter {
                it == CallType.Refresh
            }.size == 1,
        ) {
            "On 400 errors, no retries should be made"
        }
        assert(result.successData!!.userId == null) {
            "When a 400 error is received"
        }
        assert(fakeTokensStore.last()!!.credentials == result.successData) {
            "The lower privileges token should have been saved in the store"
        }
    }

    @Test
    fun `getCredentials returns a lover level token if backend fails with a 401 error`() = runTest {
        // given
        val credentials = makeCredentials(
            userId = "valid",
            isExpired = true,
        )
        val tokens = Tokens(
            credentials,
            "refreshToken",
        )
        val service = FakeTokenService().apply {
            throwableToThrow = buildTestHttpException(401)
        }
        createTokenRepository(service, networkingJobHandler = NetworkingJobHandler(this))
        fakeTokensStore.saveTokens(tokens)

        // when
        val result = tokenRepository.getCredentials(null)

        // then
        assert(
            fakeTokenService.calls.filter {
                it == CallType.Refresh
            }.size == 1,
        ) {
            "On 401 errors, no retries should be made"
        }
        assert(result.successData!!.userId == null) {
            "When a 401 error is received"
        }
        assert(fakeTokensStore.last()!!.credentials == result.successData) {
            "The lower privileges token should have been saved in the store"
        }
    }

    @Test
    fun `getCredentials stores a freshly retrieved token in CredentialsStore `() = runTest {
        // given
        val credentials = makeCredentials(
            userId = "valid",
            isExpired = true,
        )
        val tokens = Tokens(
            credentials,
            "refreshToken",
        )
        createTokenRepository(FakeTokenService(), networkingJobHandler = NetworkingJobHandler(this))
        fakeTokensStore.saveTokens(tokens)

        // when
        tokenRepository.getCredentials(null)

        // then
        assert(fakeTokensStore.saves == 2) {
            "The freshly retrieved token should have benn saved"
        }
    }

    @Test
    fun `getCredentials storing a freshly retrieved token the store triggers the emission of a CredentialsUpdatedMessage `() =
        runTest {
            messageBus.test {
                // given
                val credentials = makeCredentials(
                    userId = "valid",
                    isExpired = true,
                )
                val tokens = Tokens(
                    credentials,
                    "refreshToken",
                )
                createTokenRepository(
                    FakeTokenService(),
                    networkingJobHandler = NetworkingJobHandler(this),
                )
                fakeTokensStore.saveTokens(tokens)

                // when
                tokenRepository.getCredentials(null)

                // then
                assert(awaitItem() is CredentialsUpdatedMessage) {
                    "A CredentialsUpdatedMessage should have been sent after saving"
                }
            }
        }

    @Test
    fun `when no refresh token is present and the access token is expired, getCredentials gets a token using the client secret when present`() =
        runTest {
            // given
            val credentials = makeCredentials(
                userId = "valid",
                isExpired = true,
            )
            val tokens = Tokens(
                credentials,
                null,
            )
            val secret = "myLittleSecret"
            createAuthConfig(secret = secret)
            createTokenRepository(
                FakeTokenService(),
                networkingJobHandler = NetworkingJobHandler(this),
            )
            fakeTokensStore.saveTokens(tokens)

            // when
            tokenRepository.getCredentials(null)
            val result = fakeTokensStore.last()

            // then
            assert(fakeTokenService.calls.any { it == CallType.Secret }) {
                "If the stored token is expired, a call to TokenService.getTokenFromClientSecret should be made"
            }
            assert(fakeTokensStore.saves == 2) {
                "If a new token is retrieved, it is stored"
            }
            assert(result!!.refreshToken == null) {
                "Retrieved credentials should have no refreshToken"
            }
        }

    @Test
    fun `getCredentials returns a client Credentials when secret is available but no credentials are stored`() =
        runTest {
            // given
            val secret = "myLittleSecret"
            createAuthConfig(secret = secret)
            createTokenRepository(
                FakeTokenService(),
                networkingJobHandler = NetworkingJobHandler(this),
            )

            // when
            tokenRepository.getCredentials(null)
            val result = fakeTokensStore.last()

            // then
            assert(fakeTokenService.calls.any { it == CallType.Secret }) {
                "If the stored token is about to expire, a call to TokenService.getTokenFromClientSecret should be made"
            }
            assert(fakeTokensStore.saves == 1) {
                "If a new token is retrieved, it is stored"
            }
            assert(result!!.refreshToken == null) {
                "Retrieved credentials should have no refreshToken"
            }
        }

    @Test
    fun `getCredentials returns a user Credentials when secret is available but refreshToken is present`() =
        runTest {
            // given
            val credentials = makeCredentials(
                userId = "valid",
                isExpired = true,
            )
            val tokens = Tokens(
                credentials,
                "refreshToken is present",
            )
            val secret = "myLittleSecret"
            createAuthConfig(secret = secret)
            createTokenRepository(
                FakeTokenService(),
                networkingJobHandler = NetworkingJobHandler(this),
            )
            fakeTokensStore.saveTokens(tokens)

            // when
            tokenRepository.getCredentials(null)
            val result = fakeTokensStore.last()

            // then
            assert(fakeTokenService.calls.size == 1) {
                "A call to the service should have been made"
            }
            assert(fakeTokensStore.saves == 2) {
                "If a new token is retrieved, it is stored"
            }
            assert(result!!.refreshToken != null) {
                "Retrieved credentials should have a refreshToken"
            }
        }

    @Test
    fun `if a refreshToken is available, it is still in store after refresh`() = runTest {
        // given
        val credentials = makeCredentials(
            userId = "valid",
            isExpired = true,
        )
        val refreshToken = "refreshToken is present"
        val tokens = Tokens(
            credentials,
            refreshToken,
        )
        val secret = "myLittleSecret"
        createAuthConfig(secret = secret)
        createTokenRepository(FakeTokenService(), networkingJobHandler = NetworkingJobHandler(this))
        fakeTokensStore.saveTokens(tokens)

        // when
        tokenRepository.getCredentials(null)

        // then
        assert(fakeTokensStore.saves == 2) {
            "A refreshed token should have been saved"
        }
        assert(fakeTokensStore.last()!!.refreshToken == refreshToken) {
            "An existing refreshToken should not disappear during refresh"
        }

        // when
        tokenRepository.getCredentials(null)

        // then
        assert(fakeTokensStore.saves == 2) {
            "No further token should have been saved since the current one is still valid"
        }
        assert(fakeTokensStore.last()!!.refreshToken == refreshToken) {
            "An existing refreshToken should not disappear during refresh"
        }
    }

    @Test
    fun `On getCredentials call, an upgrade request is issued if clientId has changed`() = runTest {
        // given
        val credentials = makeCredentials(
            userId = "userId",
            isExpired = true,
        )
        val refreshToken = "refreshToken"
        val tokens = Tokens(
            credentials,
            refreshToken,
        )
        val secret = "myLittleSecret"
        createAuthConfig(clientId = "someClientId", secret = secret)
        fakeTokensStore = FakeTokensStore(authConfig.credentialsKey).apply {
            saveTokens(tokens)
        }

        // when
        createAuthConfig(clientId = "anotherClientId", secret = secret)
        createTokenRepository(
            tokenService = FakeTokenService(),
            tokensStore = fakeTokensStore,
            networkingJobHandler = NetworkingJobHandler(this),
        )

        val result = tokenRepository.getCredentials(null)

        // then
        assert(fakeTokenService.calls.filter { it == CallType.Upgrade }.size == 1) {
            "TokenService should have been called once"
        }
        assert(result.successData!!.token == "upgradeCredentials") {
            "The returned result should contain the token received in the upgrade resonse"
        }
        assert(fakeTokensStore.last()!!.refreshToken!! == "upgradeRefreshToken") {
            "The new credentials saved in the store should contain the upgraded refreshToken"
        }
    }

    @Test
    fun `upgradeToken calls should be retried as specified in UpgradePolicy`() = runTest {
        // given
        val credentials = makeCredentials(
            userId = "userId",
            isExpired = true,
        )
        val refreshToken = "refreshToken"
        val tokens = Tokens(
            credentials,
            refreshToken,
        )
        val secret = "myLittleSecret"
        val upgradeRetryPolicy = UpgradeTokenRetryPolicy()
        createAuthConfig(clientId = "someClientId", secret = secret)
        fakeTokensStore = FakeTokensStore(authConfig.credentialsKey).apply {
            saveTokens(tokens)
        }

        // when
        createAuthConfig(clientId = "anotherClientId", secret = secret)
        createTokenRepository(
            tokenService = FakeTokenService().apply {
                throwableToThrow = buildTestHttpException(503)
            },
            tokensStore = fakeTokensStore,
            upgradeRetryPolicy = UpgradeTokenRetryPolicy(),
            networkingJobHandler = NetworkingJobHandler(this),
        )
        val expectedRetries = upgradeRetryPolicy.numberOfRetries + 1
        tokenRepository.getCredentials(null)

        // then
        assert(fakeTokenService.calls.filter { it == CallType.Upgrade }.size == expectedRetries) {
            "TokenService should have been called $expectedRetries times"
        }

        // when
        fakeTokenService.calls.clear()
        createAuthConfig(clientId = "anotherClientId", secret = secret)
        createTokenRepository(
            tokenService = FakeTokenService().apply {
                throwableToThrow = buildTestHttpException(401)
            },
            tokensStore = fakeTokensStore,
            upgradeRetryPolicy = UpgradeTokenRetryPolicy(),
            networkingJobHandler = NetworkingJobHandler(this),
        )
        tokenRepository.getCredentials(null)

        // then
        assert(fakeTokenService.calls.filter { it == CallType.Upgrade }.size == expectedRetries) {
            "TokenService should have been called $expectedRetries times"
        }

        // when
        fakeTokenService.calls.clear()
        createAuthConfig(clientId = "anotherClientId", secret = secret)
        createTokenRepository(
            tokenService = FakeTokenService().apply {
                throwableToThrow = IOException()
            },
            tokensStore = fakeTokensStore,
            upgradeRetryPolicy = UpgradeTokenRetryPolicy(),
            networkingJobHandler = NetworkingJobHandler(this),
        )
        tokenRepository.getCredentials(null)

        // then
        assert(fakeTokenService.calls.filter { it == CallType.Upgrade }.size == expectedRetries) {
            "TokenService should have been called $expectedRetries times"
        }
    }

    @Test
    fun `Failed upgradeToken calls should return a RetryableError`() = runTest {
        // given
        val credentials = makeCredentials(
            userId = "userId",
            isExpired = true,
        )
        val refreshToken = "refreshToken"
        val tokens = Tokens(
            credentials,
            refreshToken,
        )
        val secret = "myLittleSecret"
        createAuthConfig(clientId = "someClientId", secret = secret)
        fakeTokensStore = FakeTokensStore(authConfig.credentialsKey).apply {
            saveTokens(tokens)
        }

        // when
        createAuthConfig(clientId = "anotherClientId", secret = secret)
        createTokenRepository(
            tokenService = FakeTokenService().apply {
                throwableToThrow = buildTestHttpException(503)
            },
            tokensStore = fakeTokensStore,
            upgradeRetryPolicy = UpgradeTokenRetryPolicy(),
            networkingJobHandler = NetworkingJobHandler(this),

        )
        val result1 = tokenRepository.getCredentials(null)

        // when
        createTokenRepository(
            tokenService = FakeTokenService().apply {
                throwableToThrow = buildTestHttpException(503)
            },
            tokensStore = fakeTokensStore,
            upgradeRetryPolicy = UpgradeTokenRetryPolicy(),
            networkingJobHandler = NetworkingJobHandler(this),
        )
        fakeTokenService.throwableToThrow = buildTestHttpException(401)
        val result2 = tokenRepository.getCredentials(null)

        // when
        createTokenRepository(
            tokenService = FakeTokenService().apply {
                throwableToThrow = buildTestHttpException(503)
            },
            tokensStore = fakeTokensStore,
            upgradeRetryPolicy = UpgradeTokenRetryPolicy(),
            networkingJobHandler = NetworkingJobHandler(this),
        )
        fakeTokenService.throwableToThrow = buildTestHttpException(503)
        val result3 = tokenRepository.getCredentials(null)

        // then
        setOf(result1, result2, result3).forEach {
            assert((it as AuthResult.Failure).message!! is RetryableError) {
                "Every fauled token upgrade should result in a RetryableError"
            }
        }
    }

    @Test
    fun `getLatestTokens returns tokens from memory if possible`() = runTest {
        // given
        val credentials = makeCredentials(
            userId = "valid",
            isExpired = false,
        )
        val tokens = Tokens(
            credentials,
            "refreshToken",
        )
        createTokenRepository(FakeTokenService(), networkingJobHandler = NetworkingJobHandler(this))
        fakeTokensStore.saveTokens(tokens)

        // when
        val result = tokenRepository.getLatestTokens()

        // then
        assert(result == tokens) {
            "The returned credentials should be the same as the ones held in the token store"
        }
        assert(fakeTokensStore.gets == 1) {
            "The token store should have been queried once"
        }
        assert(fakeTokensStore.loads == 0) {
            "The token store should have never been queried"
        }
        assert(fakeTokenService.calls.isEmpty()) {
            "No calls to the backend should have been made"
        }
    }

    @Test
    fun `getLatestTokens returns tokens from storage if possible`() = runTest {
        // given
        val credentials = makeCredentials(
            userId = "valid",
            isExpired = false,
        )
        val tokens = Tokens(
            credentials,
            "refreshToken",
        )
        createTokenRepository(
            FakeTokenService(),
            FakeTokensStore(authConfig.credentialsKey, tokens),
            networkingJobHandler = NetworkingJobHandler(this),
        )

        // when
        val result = tokenRepository.getLatestTokens()

        // then
        assert(result == tokens) {
            "The returned credentials should be the same as the ones held in the token store"
        }
        assert(fakeTokensStore.gets == 1) {
            "The token store should have been queried once"
        }
        assert(fakeTokensStore.loads == 1) {
            "The token store should have been queried once"
        }
        assert(fakeTokenService.calls.isEmpty()) {
            "No calls to the backend should have been made"
        }
    }

    @Test
    fun `there can only be one refresh call even when requested from multiple threads`() = runTest {
        val credentials = makeCredentials(
            userId = "valid",
            isExpired = true,
        )
        val tokens = Tokens(
            credentials,
            "refreshToken",
        )
        createTokenRepository(
            FakeTokenService(),
            FakeTokensStore(authConfig.credentialsKey, tokens),
            networkingJobHandler = NetworkingJobHandler(this),
        )

        val numberOfThreads = 100
        val jobs = List(numberOfThreads) {
            launch {
                tokenRepository.getCredentials(null)
            }
        }
        jobs.forEach { it.join() }

        val numberOfRefreshCalls = fakeTokenService.calls.filter { it == CallType.Refresh }.size
        assertEquals(1, numberOfRefreshCalls)
    }

    @Test
    fun `credentials are not saved to the store again if they didn't change`() = runTest {
        // given
        val clientId = "someClientId"
        val credentials = makeCredentials(
            userId = "999",
            isExpired = false,
            token = "credentials",
            clientId = clientId,
        )
        val tokens = Tokens(
            credentials,
            "refreshToken",
        )
        val tokenService = FakeTokenService()
        createAuthConfig(clientId = clientId)
        fakeTokensStore = FakeTokensStore(authConfig.credentialsKey).apply {
            saveTokens(tokens)
        }
        createTokenRepository(
            tokenService,
            fakeTokensStore,
            networkingJobHandler = NetworkingJobHandler(this),
        )

        // when
        tokenRepository.getCredentials(null)

        // then
        assert(fakeTokensStore.saves == 1) {
            "No additional save to the store should have been made"
        }
    }
}
