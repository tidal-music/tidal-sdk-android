package com.tidal.sdk.auth.util

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import retrofit2.HttpException

class RetryWithPolicyTest {

    @Test
    fun `Retry the defined number of times when receiving server errors`() = runTest {
        // given
        val testPolicy =
            object : RetryPolicy {
                override val numberOfRetries = 5
                override val delayMillis = 1
                override val delayFactor = 1
            }
        val exception = buildTestHttpException(503)
        var attempts = 0
        fun throwIt(): Int {
            attempts += 1
            throw exception
        }

        // when
        retryWithPolicy(testPolicy) { throwIt() }

        // then
        assert(attempts == 6) { "The function should have made 6 attempts (1 + 5 retries)" }
    }

    @Test
    fun `Stop retrying on non-server errors`() = runTest {
        // given
        val testPolicy =
            object : RetryPolicy {
                override val numberOfRetries = 5
                override val delayMillis = 1
                override val delayFactor = 1
            }

        val exception = buildTestHttpException(401)
        var attempts = 0
        fun throwIt(): Int {
            attempts += 1
            throw exception
        }

        // when
        retryWithPolicy(testPolicy) { throwIt() }

        // then
        assert(attempts == 1) { "The function should have made just 1 attempt" }
    }

    @Test
    fun `Retry the defined number of times while custom shouldRetry function is true`() = runTest {
        // given
        val errorCode = 999
        val testPolicy =
            object : RetryPolicy {
                override val numberOfRetries = 5
                override val delayMillis = 1
                override val delayFactor = 1

                override fun shouldRetryOnException(throwable: Throwable): Boolean {
                    return (throwable as? HttpException)?.code() == errorCode
                }
            }

        val exception = buildTestHttpException(errorCode)
        var attempts = 0
        fun throwIt(): Int {
            attempts += 1
            throw exception
        }

        // when
        retryWithPolicy(testPolicy) { throwIt() }

        // then
        assert(attempts == 6) { "The function should have made 6 attempts (1 + 5 retries)" }
    }
}
