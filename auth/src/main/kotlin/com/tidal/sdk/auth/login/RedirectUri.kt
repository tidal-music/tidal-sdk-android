package com.tidal.sdk.auth.login

internal sealed class RedirectUri {

    companion object {
        private const val CODE_PARAMETER_KEY = "code"
        private const val ERROR_MESSAGE_PARAMETER_KEY = "error_description"
        private const val ERROR_PARAMETER_KEY = "error"

        fun fromUriString(uri: String): RedirectUri {
            return with(uri) {
                if (hasError() || getCode().isBlank()) {
                    Failure(getErrorMessage())
                } else {
                    Success(getUrl(), getCode())
                }
            }
        }

        private fun String.hasError(): Boolean {
            val errorRegex = "(&|\\?)$ERROR_PARAMETER_KEY=(.+)".toRegex()
            return errorRegex.containsMatchIn(this)
        }

        private fun String.getUrl(): String {
            return substringBefore("?")
        }

        private fun String.getCode(): String {
            return substringAfter("$CODE_PARAMETER_KEY=", "").substringBefore("&")
        }

        private fun String.getErrorMessage(): String {
            return substringAfter("$ERROR_MESSAGE_PARAMETER_KEY=").substringBefore("&")
        }
    }

    data class Success(
        val url: String,
        val code: String,
    ) : RedirectUri()

    data class Failure(
        val errorMessage: String?,
    ) : RedirectUri()
}
