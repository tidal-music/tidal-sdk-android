package com.tidal.sdk.auth.login

internal sealed class RedirectData {

    companion object {
        private const val CODE_PARAMETER_KEY = "code"
        private const val ERROR_MESSAGE_PARAMETER_KEY = "error_description"
        private const val ERROR_PARAMETER_KEY = "error"

        fun fromQueryString(uri: String): RedirectData = with(uri) {
            if (hasError() || getCode().isBlank()) {
                Failure(getErrorMessage())
            } else {
                Success(getCode())
            }
        }

        private fun String.hasError(): Boolean {
            val errorRegex = "(&|\\?)$ERROR_PARAMETER_KEY=(.+)".toRegex()
            return errorRegex.containsMatchIn(this)
        }

        private fun String.getCode(): String = substringAfter(
            "$CODE_PARAMETER_KEY=",
            ""
        ).substringBefore("&")

        private fun String.getErrorMessage(): String = substringAfter(
            "$ERROR_MESSAGE_PARAMETER_KEY="
        ).substringBefore("&")
    }

    data class Success(val code: String) : RedirectData()

    data class Failure(val errorMessage: String?) : RedirectData()
}
