package com.tidal.sdk.auth.login

import org.junit.jupiter.api.Test

class RedirectDataTest {

    @Test
    fun `instance creation with no code in the submitted string should return Error subclass`() {
        // given
        val wrongQuery = "state=na&appMode=android&lang=en"

        // when
        val redirectData = RedirectData.fromQueryString(wrongQuery)

        // then
        assert(redirectData is RedirectData.Failure) {
            "Creating a RedirectData from a string missing a 'code' parameter should result in a RedirectData.Failure type"
        }
    }

    @Test
    fun `instance creation with error in the submitted string should return Error subclass`() {
        // given
        val wrongQuery = "?error=someMadeUpError?error_description=helloWorld&lang=en"

        // when
        val redirectData = RedirectData.fromQueryString(wrongQuery)

        // then
        assert(redirectData is RedirectData.Failure) {
            "Creating a RedirectData from a string with an error parameter should result in a RedirectData.Failure type"
        }
        assert((redirectData as RedirectData.Failure).errorMessage == "helloWorld") {
            "A RedirectData created from a string with an error parameter should have its 'errorMessage' field set correctly"
        }
    }

    @Test
    fun `instance creation satisfying the requirements should return a correct Success subclass`() {
        // given
        val correctQuery = "?state=na&appMode=android&lang=en&code=HERE_BE_CODE"

        // when
        val redirectData = RedirectData.fromQueryString(correctQuery)

        // then
        assert(redirectData is RedirectData.Success) {
            "Creating a RedirectData from a correct uri string should result in a RedirectData.Success type"
        }
        assert((redirectData as RedirectData.Success).code == "HERE_BE_CODE") {
            "A RedirectData.Success should have the correct value in its 'code' property"
        }
    }
}
