package com.tidal.sdk.auth.login

import com.tidal.sdk.auth.model.LoginConfig
import com.tidal.sdk.auth.model.QueryParameter
import okhttp3.HttpUrl

internal class LoginUriBuilder(
    private val clientId: String,
    private val clientUniqueKey: String?,
    private val loginUri: String,
) {

    fun getLoginUri(redirectUri: String, loginConfig: LoginConfig?, codeChallenge: String): String {
        var builder = HttpUrl.Builder().scheme(SCHEME).host(loginUri).addPathSegment(
            AUTH_PATH,
        )

        with(builder) {
            addQueryParameter(QueryKeys.REDIRECT_URI_KEY, redirectUri)
            buildBaseParameters(clientId, clientUniqueKey, codeChallenge).forEach {
                addQueryParameter(
                    it.key,
                    it.value,
                )
            }
        }

        loginConfig?.let {
            builder = evaluateLoginConfig(builder, it)
        }
        return builder.build().toString()
    }

    private fun evaluateLoginConfig(
        builder: HttpUrl.Builder,
        config: LoginConfig,
    ): HttpUrl.Builder {
        with(builder) {
            addQueryParameter(QueryKeys.LANGUAGE_KEY, config.locale.toString())
            config.email?.let {
                addQueryParameter(QueryKeys.EMAIL_KEY, it)
            }
            config.customParams.forEach {
                addQueryParameter(it.key, it.value)
            }
        }
        return builder
    }

    private fun buildBaseParameters(
        clientId: String,
        clientUniqueKey: String?,
        codeChallenge: String,
    ): Set<QueryParameter> {
        return mutableSetOf(
            QueryParameter(QueryKeys.CLIENT_ID_KEY, clientId),

            QueryParameter(
                QueryKeys.CODE_CHALLENGE_METHOD_KEY,
                CODE_CHALLENGE_METHOD,
            ),
            QueryParameter(QueryKeys.CODE_CHALLENGE_KEY, codeChallenge),
        ).apply {
            clientUniqueKey?.let {
                this.add(QueryParameter(QueryKeys.CLIENT_UNIQUE_KEY, it))
            }
        }
    }

    object QueryKeys {

        const val CLIENT_ID_KEY = "client_id"
        const val CLIENT_UNIQUE_KEY = "client_unique_key"
        const val CODE_CHALLENGE_KEY = "code_challenge"
        const val CODE_CHALLENGE_METHOD_KEY = "code_challenge_method"
        const val LANGUAGE_KEY = "lang"
        const val EMAIL_KEY = "email"
        const val REDIRECT_URI_KEY = "redirect_uri"
    }

    companion object {

        private const val AUTH_PATH = "authorize"
        private const val CODE_CHALLENGE_METHOD = "S256"
        private const val SCHEME = "https"
    }
}
