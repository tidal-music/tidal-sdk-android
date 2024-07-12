package com.tidal.sdk.auth.model

import com.tidal.sdk.util.TestTimeProvider
import org.junit.jupiter.api.Test

class CredentialsTest {

    private val timeProvider = TestTimeProvider()

    @Test
    fun `An access token expiring in more than a minute is valid`() {
        // given
        val time = System.currentTimeMillis() / 1000
        val expiry = time + 120
        val token = Credentials(
            "",
            setOf(),
            "",
            setOf(),
            "",
            expiry,
            "",
        )

        // when
        val isValid = token.isExpired(timeProvider).not()

        // then
        assert(isValid) {
            "A token that expires in more than one minute should consider itself valid"
        }
    }

    @Test
    fun `An access token expiring in less than a minute is not valid`() {
        // given
        val time = System.currentTimeMillis() / 1000
        val expiry = time + 30
        val token = Credentials(
            "",
            setOf(),
            "",
            setOf(),
            "",
            expiry,
            "",
        )

        // when
        val isValid = token.isExpired(timeProvider).not()

        // then
        assert(!isValid) {
            "A token that expires in less than one minute should consider itself invalid"
        }
    }

    @Test
    fun `An access token with no expiry is not valid`() {
        // given
        val token = Credentials(
            "",
            setOf(),
            "",
            setOf(),
            "",
            null,
            "",
        )

        // when
        val isValid = token.isExpired(timeProvider).not()

        // then
        assert(!isValid) {
            "A token that has no set expiry should consider itself invalid"
        }
    }

    @Test
    fun `An access token with userId and token is level USER`() {
        // given
        val token = Credentials(
            "",
            setOf(),
            "",
            setOf(),
            "userId",
            null,
            "token",
        )

        // when
        val level = token.level

        // then
        assert(level == Credentials.Level.USER) {
            "The token should be Level.USER"
        }
    }

    @Test
    fun `An access token without userId,but with token is level CLIENT`() {
        // given
        val token = Credentials(
            "",
            setOf(),
            "",
            setOf(),
            null,
            null,
            "token",
        )

        // when
        val level = token.level

        // then
        assert(level == Credentials.Level.CLIENT) {
            "The token should be Level.Client"
        }
    }

    @Test
    fun `An access token without userId and without token is level BASIC`() {
        // given
        val token = Credentials(
            "",
            setOf(),
            "",
            setOf(),
            null,
            null,
            null,
        )

        // when
        val level = token.level

        // then
        assert(level == Credentials.Level.BASIC) {
            "The token should be Level.BASIC"
        }
    }
}
