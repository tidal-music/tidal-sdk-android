package com.tidal.sdk.auth.login

import org.junit.jupiter.api.Test

class RedirectUriTest {

    @Test
    fun `instance creation with no code in the submitted string should return Error subclass`() {
        // given
        val wrongUri = "https://tidal.com/android/login/auth?state=na&appMode=android&lang=en"

        // when
        val redirectUri = RedirectUri.fromUriString(wrongUri)

        // then
        assert(redirectUri is RedirectUri.Failure) {
            "Creating a RedirectUri from a string missing a 'code' parameter should result in a RedirectUri.Failure type"
        }
    }

    @Test
    fun `instance creation with error in the submitted string should return Error subclass`() {
        // given
        val wrongUri =
            "https://tidal.com/android/login/auth?error=someMadeUpError?error_description=helloWorld&lang=en"

        // when
        val redirectUri = RedirectUri.fromUriString(wrongUri)

        // then
        assert(redirectUri is RedirectUri.Failure) {
            "Creating a RedirectUri from a string with an error parameter should result in a RedirectUri.Failure type"
        }
        assert((redirectUri as RedirectUri.Failure).errorMessage == "helloWorld") {
            "A RedirectUri created from a string with an error parameter should have its 'errorMessage' field set correctly"
        }
    }

    @Test
    fun `instance creation satisfying the requirements should return a correct Correct subclass`() {
        // given
        val correctUri =
            "https://tidal.com/android/login/auth?state=na&appMode=android&lang=en&code=HERE_BE_CODE"

        // when
        val redirectUri = RedirectUri.fromUriString(correctUri)

        // then
        assert(redirectUri is RedirectUri.Success) {
            "Creating a RedirectUri from a correct uri string should result in a RedirectUri.Success type"
        }
        assert((redirectUri as RedirectUri.Success).code == "HERE_BE_CODE") {
            "A RedirectUri.Success should have the correct value in its 'code' property"
        }
    }
}
