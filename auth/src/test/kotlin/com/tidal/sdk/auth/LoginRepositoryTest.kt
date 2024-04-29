package com.tidal.sdk.auth

import app.cash.turbine.test
import com.tidal.sdk.auth.FakeLoginService.CallType
import com.tidal.sdk.auth.login.CodeChallengeBuilder
import com.tidal.sdk.auth.login.FakeTokensStore
import com.tidal.sdk.auth.login.LoginRepository
import com.tidal.sdk.auth.login.LoginUriBuilder
import com.tidal.sdk.auth.login.LoginUriBuilder.QueryKeys.CLIENT_ID_KEY
import com.tidal.sdk.auth.login.LoginUriBuilder.QueryKeys.CLIENT_UNIQUE_KEY
import com.tidal.sdk.auth.login.LoginUriBuilder.QueryKeys.CODE_CHALLENGE_KEY
import com.tidal.sdk.auth.login.LoginUriBuilder.QueryKeys.CODE_CHALLENGE_METHOD_KEY
import com.tidal.sdk.auth.login.LoginUriBuilder.QueryKeys.LANGUAGE_KEY
import com.tidal.sdk.auth.login.LoginUriBuilder.QueryKeys.REDIRECT_URI_KEY
import com.tidal.sdk.auth.model.AuthConfig
import com.tidal.sdk.auth.model.AuthResult
import com.tidal.sdk.auth.model.AuthorizationError
import com.tidal.sdk.auth.model.CredentialsUpdatedMessage
import com.tidal.sdk.auth.model.ErrorResponse
import com.tidal.sdk.auth.model.LoginConfig
import com.tidal.sdk.auth.model.LoginResponse
import com.tidal.sdk.auth.model.QueryParameter
import com.tidal.sdk.auth.model.Scopes
import com.tidal.sdk.auth.model.TokenResponseError
import com.tidal.sdk.auth.model.Tokens
import com.tidal.sdk.auth.model.UnexpectedError
import com.tidal.sdk.auth.util.RetryPolicy
import com.tidal.sdk.auth.util.TimeProvider
import com.tidal.sdk.common.NetworkError
import com.tidal.sdk.common.RetryableError
import com.tidal.sdk.common.TidalMessage
import com.tidal.sdk.util.CoroutineTestTimeProvider
import com.tidal.sdk.util.TEST_CLIENT_ID
import com.tidal.sdk.util.TEST_CLIENT_UNIQUE_KEY
import com.tidal.sdk.util.TEST_TIME_PROVIDER
import com.tidal.sdk.util.makeCredentials
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import java.util.Locale
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginRepositoryTest {

    private lateinit var timeProvider: TimeProvider
    private val authConfig = AuthConfig(
        clientId = TEST_CLIENT_ID,
        clientUniqueKey = TEST_CLIENT_UNIQUE_KEY,
        credentialsKey = "credentialsKey",
        scopes = Scopes(setOf()),
        enableCertificatePinning = false,
    )
    private val dummyEncodedString = "encoded_string"
    private val loginUri = "https://tidal.com/android/login/auth"
    private val messageBus: MutableSharedFlow<TidalMessage> = MutableSharedFlow()
    private lateinit var fakeTokensStore: FakeTokensStore
    private lateinit var fakeLoginService: FakeLoginService
    private lateinit var loginRepository: LoginRepository
    private val testRetryPolicy: RetryPolicy = object : RetryPolicy {
        override val numberOfRetries = 3
        override val delayMillis = 1
        override val delayFactor = 1
    }

    private fun createLoginRepository(
        loginService: FakeLoginService,
        tokensStore: FakeTokensStore = FakeTokensStore(""),
        retryPolicy: RetryPolicy = testRetryPolicy,
        bus: MutableSharedFlow<TidalMessage> = messageBus,
        loginBaseUrl: String = "login.tidal.com",
    ) {
        fakeLoginService = loginService
        fakeTokensStore = tokensStore
        loginRepository = LoginRepository(
            authConfig,
            timeProvider,
            CodeChallengeBuilder(),
            LoginUriBuilder(TEST_CLIENT_ID, TEST_CLIENT_UNIQUE_KEY, loginBaseUrl),
            loginService,
            tokensStore,
            retryPolicy,
            bus,
        )
    }

    @BeforeAll
    fun setup() {
        mockkStatic(android.util.Base64::class)
        mockk<android.util.Base64> {
            every {
                android.util.Base64.encodeToString(any(), any())
            } answers { dummyEncodedString }
        }
        timeProvider = TEST_TIME_PROVIDER
    }

    @Test
    fun `getLoginUri uses the correct base url`() {
        // given
        val loginBaseUrl = "imaginary.address.tidal.com"
        val protocol = TidalAuth.DEFAULT_PROTOCOL
        createLoginRepository(loginService = FakeLoginService(), loginBaseUrl = loginBaseUrl)
        // when
        val actualUrl = loginRepository.getLoginUri(loginUri, null)

        // then
        assert(actualUrl.startsWith("$protocol$loginBaseUrl")) {
            "getLoginUri() should use the correct base url"
        }
    }

    @Test
    fun `getLoginUri appends the submitted base query arguments`() {
        // given
        timeProvider = TEST_TIME_PROVIDER
        createLoginRepository(FakeLoginService())
        val clientIdMatcher = "$QUERY_PREFIX$CLIENT_ID_KEY=$TEST_CLIENT_ID".toRegex()
        val clientUniqueKeyMatcher =
            "$QUERY_PREFIX$CLIENT_UNIQUE_KEY=$TEST_CLIENT_UNIQUE_KEY".toRegex()
        val codeChallengeMethodMatcher = "$QUERY_PREFIX$CODE_CHALLENGE_METHOD_KEY=S256".toRegex()
        val codeChallengeMatcher = "$QUERY_PREFIX$CODE_CHALLENGE_KEY=$dummyEncodedString".toRegex()

        // when
        val generatedUri = loginRepository.getLoginUri(loginUri, null)

        // then
        assert(clientIdMatcher.containsMatchIn(generatedUri)) {
            "getLoginUri() should append the $CLIENT_ID_KEY"
        }
        assert(clientUniqueKeyMatcher.containsMatchIn(generatedUri)) {
            "getLoginUri() should append the $CLIENT_UNIQUE_KEY"
        }
        assert(codeChallengeMethodMatcher.containsMatchIn(generatedUri)) {
            "getLoginUri() should append the $$CODE_CHALLENGE_METHOD_KEY"
        }
        assert(codeChallengeMatcher.containsMatchIn(generatedUri)) {
            "getLoginUri() should append the $$CODE_CHALLENGE_KEY"
        }
    }

    @Test
    fun `getLoginUri appends the RedirectUri properly`() {
        // given
        createLoginRepository(FakeLoginService())
        val fakeUri = "fakeUri"

        val redirectUriMatcher = "$QUERY_PREFIX$REDIRECT_URI_KEY=$fakeUri".toRegex()

        // when
        val generatedUri = loginRepository.getLoginUri(fakeUri, null)

        // then
        assert(redirectUriMatcher.containsMatchIn(generatedUri)) {
            "getLoginUri() should append the $$REDIRECT_URI_KEY"
        }
    }

    @Test
    fun `getLoginUri returns a uri containing all custom query arguments`() {
        // given
        createLoginRepository(FakeLoginService())
        val loginConfig = LoginConfig(
            customParams = setOf(
                QueryParameter("key1", "value1"),
                QueryParameter("key2", "value2"),
                QueryParameter("key3", "value3"),
            ),
        )

        // when
        val generatedUri = loginRepository.getLoginUri(loginUri, loginConfig)

        // then
        loginConfig.customParams.forEach {
            val matcher = "$QUERY_PREFIX${it.key}=${it.value}".toRegex()
            assert(matcher.containsMatchIn(generatedUri)) {
                "getLoginUrl() should append all custom query parameters"
            }
        }
    }

    @Test
    fun `getLoginUri appends the locale properly`() {
        // given
        createLoginRepository(FakeLoginService())
        val locale = Locale.ENGLISH
        val localeString = locale.toString()
        val loginConfig = LoginConfig(
            locale = Locale.ENGLISH,
        )

        // when
        val generatedUri = loginRepository.getLoginUri(loginUri, loginConfig)

        // then
        val localeMatcher = "$QUERY_PREFIX$LANGUAGE_KEY=$localeString".toRegex()
        assert(localeMatcher.containsMatchIn(generatedUri)) {
            "getLoginUrl() should append the locale properly"
        }
    }

    @Test
    fun `do not get a token with an incorrect login response`() = runTest {
        // given
        createLoginRepository(FakeLoginService())
        val incorrectUriString = "https://www.google.com"

        // when
        loginRepository.getLoginUri(loginUri, null)
        val result = loginRepository.getCredentialsFromLoginCode(incorrectUriString)
        // then
        assert(
            fakeLoginService.calls.none {
                it == CallType.GET_TOKEN_WITH_CODE_VERIFIER
            },
        ) {
            "If the submitted uri is incorrect, the service should not get called"
        }
        assert((result as AuthResult.Failure).message is AuthorizationError) {
            "If the submitted uri is incorrect, a Failure containing an AuthorizationError should be returned."
        }
    }

    @Test
    fun `get a token with a correct login response`() = runTest {
        // given
        createLoginRepository(FakeLoginService())
        val correctUriString = "https://tidal.com/android/login/auth&code=123&appMode=android"

        // when
        loginRepository.getLoginUri(loginUri, null)
        val result = loginRepository.getCredentialsFromLoginCode(correctUriString)

        // then
        assert(
            fakeLoginService.calls.filter {
                it == CallType.GET_TOKEN_WITH_CODE_VERIFIER
            }.size == 1,
        ) {
            "If the submitted uri is correct, the service should get called"
        }
        assert(result.successData is LoginResponse) {
            "If the submitted uri is correct, a Failure containing an AuthorizationError should be returned."
        }
    }

    @Test
    fun `getTokenFromLoginCode fails with an AuthorizationError on wrong redirect uris`() =
        runTest {
            // given
            createLoginRepository(FakeLoginService())
            val firstWrongUri = "https://tidal.org/my-nonexistent-service/code=abcde"
            val secondWrongUri =
                "https://tidal.com/android/login/auth?error=someError&code=somecode"
            val thirdWrongUri = "https://tidal.com/android/login/auth?someotherArg=someValue"

            // when
            val firstResult =
                loginRepository.getCredentialsFromLoginCode(firstWrongUri) as AuthResult.Failure
            val secondResult =
                loginRepository.getCredentialsFromLoginCode(secondWrongUri) as AuthResult.Failure
            val thirdResult =
                loginRepository.getCredentialsFromLoginCode(thirdWrongUri) as AuthResult.Failure

            // then
            assert(firstResult.message is AuthorizationError) {
                "A Uri pointing to the wrong domain should trigger the emission of an AuthorizationError"
            }
            assert(secondResult.message is AuthorizationError) {
                "A Uri with an error parameter should trigger the emission of an AuthorizationError"
            }
            assert(thirdResult.message is AuthorizationError) {
                "A Uri with no code parameter should trigger the emission of an AuthorizationError"
            }
        }

    @Test
    fun `getTokenFromLoginCode returns an AuthResult Success if service returns a TokenResponse`() =
        runTest {
            // given
            createLoginRepository(FakeLoginService())

            // when
            loginRepository.getLoginUri(loginUri, null)
            val result = loginRepository.getCredentialsFromLoginCode(VALID_URI)

            // then
            assert(result is AuthResult.Success) {
                "If a LoginResponse is returned from LoginService, getTokenFromLoginCode should return a successful AuthResult"
            }
        }

    @Test
    fun `getTokenFromLoginCode retries as many times as specified in policy on 5xx HttpError`() =
        runTest {
            // given
            val retryPolicy = object : RetryPolicy {
                override val numberOfRetries = 3
                override val delayMillis = 5
                override val delayFactor = 1
            }
            val fakeErrorResponse = ErrorResponse(503, "message", 0, "")

            createLoginRepository(
                loginService = FakeLoginService(fakeErrorResponse),
                retryPolicy = retryPolicy,
            )

            // when
            loginRepository.getLoginUri(loginUri, null)
            loginRepository.getCredentialsFromLoginCode(VALID_URI)

            // then
            assert(
                fakeLoginService.calls.filter {
                    it == CallType.GET_TOKEN_WITH_CODE_VERIFIER
                }.size == retryPolicy.numberOfRetries + 1,
            ) {
                "getTokenFromLoginCode should retry as many times as specified, resulting in a total number of attempts that is equal to the policy's specified retries + 1"
            }
        }

    @Test
    fun `getTokenFromLoginCode returns a Failure with UnexpectedError on Http 4xx Error`() =
        runTest {
            // given
            val retryPolicy = object : RetryPolicy {
                override val numberOfRetries = 3
                override val delayMillis = 5
                override val delayFactor = 1
            }
            val exceptionCode = 400
            val fakeErrorResponse = ErrorResponse(exceptionCode, "message", 0, "")

            createLoginRepository(
                loginService = FakeLoginService(fakeErrorResponse),
                retryPolicy = retryPolicy,
            )

            // when
            loginRepository.getLoginUri(loginUri, null)
            val result = loginRepository.getCredentialsFromLoginCode(VALID_URI)

            // then
            assert(
                fakeLoginService.calls.filter {
                    it == CallType.GET_TOKEN_WITH_CODE_VERIFIER
                }.size == 1,
            ) {
                "getTokenFromLoginCode should not retry on 4xx errors"
            }
            assert((result as AuthResult.Failure).message is UnexpectedError) {
                "Http 4xx errors should result in a Failure containing an UnexpectedError"
            }
            assert(((result).message as UnexpectedError).code == exceptionCode.toString()) {
                "The returned result should contain the exception's code"
            }
        }

    @Test
    fun `getTokenFromLoginCode retries as specified and returns a Failure with RetryableError on Http 5xx Error`() =
        runTest {
            // given
            val retryPolicy = object : RetryPolicy {
                override val numberOfRetries = 3
                override val delayMillis = 5
                override val delayFactor = 1
            }
            val exceptionCode = 503
            val fakeErrorResponse = ErrorResponse(exceptionCode, "message", 0, "")

            createLoginRepository(
                loginService = FakeLoginService(fakeErrorResponse),
                retryPolicy = retryPolicy,
            )

            // when
            loginRepository.getLoginUri(loginUri, null)
            val result = loginRepository.getCredentialsFromLoginCode(VALID_URI)

            // then
            assert(
                fakeLoginService.calls.filter {
                    it == CallType.GET_TOKEN_WITH_CODE_VERIFIER
                }.size == retryPolicy.numberOfRetries + 1,
            ) {
                "The network call should be retried the specified number of times"
            }
            assert((result as AuthResult.Failure).message is RetryableError) {
                "Http 4xx errors should result in a Failure containing an RetryableError"
            }
            assert(((result).message as RetryableError).code == exceptionCode.toString()) {
                "The returned result should contain the exception's code"
            }
        }

    @Test
    fun `getCredentialsFromLoginCode does not retry and returns a Failure with NetworkError on any non-Http Exception`() =
        runTest {
            // given
            val dispatcher = StandardTestDispatcher()
            val coroutineTimeProvider = CoroutineTestTimeProvider(dispatcher).also {
                timeProvider = it
            }

            val retryPolicy = object : RetryPolicy {
                override val numberOfRetries = 3
                override val delayMillis = 5
                override val delayFactor = 1
            }
            createLoginRepository(
                loginService = FakeLoginService(shouldThrowUnknownHostException = true),
                retryPolicy = retryPolicy,
            )
            fakeLoginService.deviceLoginBehaviour = DeviceLoginBehaviour(
                authorizationResponseExpirationSeconds = 10,
                loginPendingSeconds = 9,
                timeProvider = coroutineTimeProvider,
            )

            // when
            coroutineTimeProvider.startTimeFor(this, 10)
            loginRepository.getLoginUri(loginUri, null)
            val result = loginRepository.getCredentialsFromLoginCode(VALID_URI)

            // then
            assert((result as AuthResult.Failure).message is NetworkError) {
                "Http 4xx errors should result in a Failure containing an NetworkError"
            }
            assert(
                fakeLoginService.calls.filter {
                    it == CallType.GET_TOKEN_WITH_CODE_VERIFIER
                }.size == 1,
            ) {
                "getTokenFromLoginCode should not retry on non-5xx errors"
            }
        }

    @Test
    fun `credentials are saved upon successful regular login token retrieval`() = runTest {
        // given
        createLoginRepository(FakeLoginService())

        // when
        loginRepository.getLoginUri(loginUri, null)
        loginRepository.getCredentialsFromLoginCode(VALID_URI)

        // then
        assert(fakeTokensStore.saves == 1) {
            "TokensStore should have been called once to save after successful token retrieval"
        }
    }

    @Test
    fun `credentials are saved upon successful device login token retrieval`() = runTest {
        // given
        val dispatcher = StandardTestDispatcher()
        val coroutineTimeProvider = CoroutineTestTimeProvider(dispatcher).also {
            timeProvider = it
        }

        createLoginRepository(FakeLoginService())
        fakeLoginService.deviceLoginBehaviour = DeviceLoginBehaviour(
            authorizationResponseExpirationSeconds = 10,
            loginPendingSeconds = 1,
            timeProvider = coroutineTimeProvider,
        )

        // when
        coroutineTimeProvider.startTimeFor(this, 2)
        val authorizationResponse = loginRepository.initializeDeviceLogin()
        loginRepository.pollForDeviceLoginResponse(
            authorizationResponse.successData!!.deviceCode,
        )
        // then
        assert(fakeTokensStore.saves == 1) {
            "TokensStore should have been called once to save after successful token retrieval"
        }
    }

    @Test
    fun `initializeDeviceLogin retries the specified number of times on 5xx errors and returns a RetryableError`() =
        runTest {
            // given
            val testErrorCode = 503
            createLoginRepository(FakeLoginService(ErrorResponse(testErrorCode, "message", 0, "")))

            // when
            val result = loginRepository.initializeDeviceLogin()
            val expectedNumberOfCalls = testRetryPolicy.numberOfRetries + 1

            // then

            assert(
                fakeLoginService.calls.filter {
                    it == CallType.GET_DEVICE_AUTHORIZATION
                }.size == expectedNumberOfCalls,
            ) {
                "In case of 5xx returns, initializeDeviceLogin should trigger retries as defined by the retryPolicy"
            }
            assert(
                ((result as AuthResult.Failure).message as RetryableError).code == testErrorCode.toString(),
            ) {
                "When finished retrying, a RetryableError should be returned that cointains the correct error code."
            }
        }

    @Test
    fun `pollForDeviceLoginResponse retries the specified number of times on 5xx errors`() =
        runTest {
            // given
            val dispatcher = StandardTestDispatcher()
            val coroutineTimeProvider = CoroutineTestTimeProvider(dispatcher).also {
                timeProvider = it
            }
            createLoginRepository(FakeLoginService())
            fakeLoginService.deviceLoginBehaviour = DeviceLoginBehaviour(
                authorizationResponseExpirationSeconds = 10,
                loginPendingSeconds = 9,
                errorResponseWhilePending = ErrorResponse(503, "message", 0, ""),
                timeProvider = coroutineTimeProvider,
            )

            // when
            coroutineTimeProvider.startTimeFor(this, 60)
            val authorizationResponse = loginRepository.initializeDeviceLogin()
            val expectedPollCalls =
                fakeLoginService.deviceLoginBehaviour.authorizationResponseExpirationSeconds /
                    authorizationResponse.successData!!.interval
            val result = loginRepository.pollForDeviceLoginResponse(
                authorizationResponse.successData!!.deviceCode,
            )

            // then

            // the number of calls to expect are defined by the expiration and the interval
            // in the DeviceAuthorizationResponse as well as the retryPolicy's numberOfRetries
            val expectedTotalCalls =
                expectedPollCalls + expectedPollCalls * testRetryPolicy.numberOfRetries

            assert(
                fakeLoginService.calls.filter {
                    it == CallType.GET_TOKEN_FROM_DEVICE_CODE
                }.size == expectedTotalCalls,
            ) {
                "In case of 5xx returns, each poll attempt should trigger retries as defined by the retryPolicy"
            }
            assert((result as AuthResult.Failure).message is RetryableError) {
                "When finished retrying, a RetryableError should be returned"
            }
        }

    @Test
    fun `pollForDeviceLoginResponse returns a success if a response is available before the authorization expires`() =
        runTest {
            // given
            val dispatcher = StandardTestDispatcher()
            val coroutineTimeProvider = CoroutineTestTimeProvider(dispatcher).also {
                timeProvider = it
            }
            createLoginRepository(FakeLoginService())
            fakeLoginService.deviceLoginBehaviour = DeviceLoginBehaviour(
                authorizationResponseExpirationSeconds = 12,
                loginPendingSeconds = 9,
                timeProvider = coroutineTimeProvider,
            )

            // when
            coroutineTimeProvider.startTimeFor(this, 60)
            val authorizationResponse = loginRepository.initializeDeviceLogin()
            val result = loginRepository.pollForDeviceLoginResponse(
                authorizationResponse.successData!!.deviceCode,
            )

            // then
            assert(result.isSuccess) {
                "If a response is returned before the expiration time, the call to finalizeDeviceLogin() should succeed"
            }
        }

    @Test
    fun `pollForDeviceLoginResponse should keep polling on 400 and 401 errors, but not do individual retries`() =
        runTest {
            // given
            val dispatcher = StandardTestDispatcher()
            val coroutineTimeProvider = CoroutineTestTimeProvider(dispatcher).also {
                timeProvider = it
            }
            createLoginRepository(FakeLoginService())
            fakeLoginService.deviceLoginBehaviour = DeviceLoginBehaviour(
                authorizationResponseExpirationSeconds = 10,
                loginPendingSeconds = 9,
                timeProvider = coroutineTimeProvider,
            )
            val expectedPollCalls =
                fakeLoginService.deviceLoginBehaviour.authorizationResponseExpirationSeconds / 2

            // when
            coroutineTimeProvider.startTimeFor(this, 60)
            val authorizationResponse = loginRepository.initializeDeviceLogin()
            loginRepository.pollForDeviceLoginResponse(
                authorizationResponse.successData!!.deviceCode,
            )
            // then

            assert(
                fakeLoginService.calls.filter {
                    it == CallType.GET_TOKEN_FROM_DEVICE_CODE
                }.size == expectedPollCalls,
            ) {
                "Given an expiration of ${fakeLoginService.deviceLoginBehaviour.authorizationResponseExpirationSeconds} and a poll interval of ${authorizationResponse.successData!!.interval}, login service should have been called $expectedPollCalls"
            }
        }

    @Test
    fun `pollForDeviceLoginResponse should return a TokenResponseError on 4xx that are not 400 or 401`() =
        runTest {
            // given
            val dispatcher = StandardTestDispatcher()
            val coroutineTimeProvider = CoroutineTestTimeProvider(dispatcher).also {
                timeProvider = it
            }
            createLoginRepository(FakeLoginService())
            fakeLoginService.deviceLoginBehaviour = DeviceLoginBehaviour(
                authorizationResponseExpirationSeconds = 10,
                loginPendingSeconds = 9,
                errorResponseWhilePending = ErrorResponse(403, "message", 0, ""),
                timeProvider = coroutineTimeProvider,
            )

            // when
            coroutineTimeProvider.startTimeFor(this, 60)
            val authorizationResponse = loginRepository.initializeDeviceLogin()
            val result = loginRepository.pollForDeviceLoginResponse(
                authorizationResponse.successData!!.deviceCode,
            )
            // then
            assert((result as AuthResult.Failure).message!! is TokenResponseError) {
                "On 4xx errors that aren't 400 or 401, a TokenResponseError should be returned"
            }
        }

    @Test
    fun `pollForDeviceLogin should return a TokenResponseError if the token used for polling has expired`() {
        runTest {
            // given
            val dispatcher = StandardTestDispatcher()
            val coroutineTimeProvider = CoroutineTestTimeProvider(dispatcher).also {
                timeProvider = it
            }
            createLoginRepository(FakeLoginService())
            fakeLoginService.deviceLoginBehaviour = DeviceLoginBehaviour(
                authorizationResponseExpirationSeconds = 10,
                loginPendingSeconds = 5,
                errorResponseToFinishWith = ErrorResponse(401, "token expired", 11003, ""),
                timeProvider = coroutineTimeProvider,
            )

            // when
            coroutineTimeProvider.startTimeFor(this, 60)
            val authorizationResponse = loginRepository.initializeDeviceLogin()
            val result = loginRepository.pollForDeviceLoginResponse(
                authorizationResponse.successData!!.deviceCode,
            )

            // then
            assert((result as AuthResult.Failure).message!! is TokenResponseError) {
                "If the token used for polling has expired, a TokenResponseError should be returned"
            }
        }
    }

    @Test
    fun `pollForDeviceLoginResponse should return a RetryableError after failing with a 5xx Http error`() =
        runTest {
            // given
            val dispatcher = StandardTestDispatcher()
            val coroutineTimeProvider = CoroutineTestTimeProvider(dispatcher).also {
                timeProvider = it
            }
            createLoginRepository(FakeLoginService())
            fakeLoginService.deviceLoginBehaviour = DeviceLoginBehaviour(
                authorizationResponseExpirationSeconds = 10,
                loginPendingSeconds = 9,
                errorResponseWhilePending = ErrorResponse(503, "message", 0, ""),
                timeProvider = coroutineTimeProvider,
            )

            // when
            coroutineTimeProvider.startTimeFor(this, 60)
            val authorizationResponse = loginRepository.initializeDeviceLogin()
            val result = loginRepository.pollForDeviceLoginResponse(
                authorizationResponse.successData!!.deviceCode,
            )
            // then
            assert((result as AuthResult.Failure).message!! is RetryableError) {
                "On 5xx errors, a RetryableError should be returned"
            }
        }

    @Test
    fun `logout clears stored credentials`() = runTest {
        // given
        createLoginRepository(FakeLoginService())

        // when
        loginRepository.getLoginUri(loginUri, null)
        loginRepository.getCredentialsFromLoginCode(VALID_URI)

        // then
        assert(fakeTokensStore.last() != null) {
            "After retrieving a token we should have a saved one"
        }
        // when
        loginRepository.logout()

        // then
        assert(fakeTokensStore.last() == null) {
            "After logout, there should not be stored credentials"
        }
        assert(!loginRepository.isLoggedIn()) {
            "After logout, isLoggedIn() should return false"
        }
    }

    @Test
    fun `after login and logout, a CredentialsUpdatedMessage is sent`() = runTest {
        messageBus.test {
            // given
            createLoginRepository(FakeLoginService())

            // when
            loginRepository.getLoginUri(loginUri, null)
            loginRepository.getCredentialsFromLoginCode(VALID_URI)

            // then
            assert(awaitItem() is CredentialsUpdatedMessage) {
                "After saving a token, a CredentialsUpdatedMessage should be sent"
            }

            // when
            loginRepository.logout()

            // then
            assert(awaitItem() is CredentialsUpdatedMessage) {
                "After saving a token, a CredentialsUpdatedMessage should be sent"
            }
        }
    }

    @Test
    fun `setCredentials stores the submitted values in the TokenStore and overwrites any existing ones`() =
        runTest {
            // given
            val oldUserId = "old"
            val newUserId = "new"
            val oldRefreshToken = "oldRefreshToken"
            val newRefreshToken = "newRefreshToken"

            val oldTokens = Tokens(
                makeCredentials(
                    userId = oldUserId,
                    isExpired = true,
                ),
                oldRefreshToken,
            )
            val newTokens = Tokens(
                makeCredentials(
                    userId = newUserId,
                    isExpired = true,
                ),
                newRefreshToken,
            )
            createLoginRepository(FakeLoginService())
            fakeTokensStore.saveTokens(oldTokens)

            // when
            loginRepository.setCredentials(newTokens.credentials, newRefreshToken)

            // then
            assert(fakeTokensStore.saves == 2) {
                "Two tokens should have been saved"
            }
            assert(fakeTokensStore.last()!!.credentials.userId == newUserId) {
                "The last saved token should have the new userId"
            }
            assert(fakeTokensStore.last()!!.refreshToken == newRefreshToken) {
                "The last saved token should have the new refreshToken"
            }
        }

    @Test
    fun `credentials sent via setCredentials trigger a CredentialsUpdatedMessage`() =
        runTest {
            messageBus.test {
                // given
                val oldUserId = "old"
                val newUserId = "new"
                val oldRefreshToken = "oldRefreshToken"
                val newRefreshToken = "newRefreshToken"

                val oldTokens = Tokens(
                    makeCredentials(
                        userId = oldUserId,
                        isExpired = true,
                    ),
                    oldRefreshToken,
                )
                val newTokens = Tokens(
                    makeCredentials(
                        userId = newUserId,
                        isExpired = true,
                    ),
                    newRefreshToken,
                )
                createLoginRepository(FakeLoginService())
                fakeTokensStore.saveTokens(oldTokens)

                // when
                loginRepository.setCredentials(newTokens.credentials, newRefreshToken)

                // then
                assert(awaitItem() is CredentialsUpdatedMessage) {
                    "A CredentialsUpdatedMessage should have been sent after saving"
                }
            }
        }

    companion object {
        private const val QUERY_PREFIX = "(&|\\?)"
        private const val VALID_URI =
            "https://tidal.com/android/login/auth&code=123&appMode=android"
    }
}
