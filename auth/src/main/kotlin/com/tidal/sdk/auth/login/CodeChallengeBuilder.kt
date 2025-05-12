package com.tidal.sdk.auth.login

import android.util.Base64
import java.security.MessageDigest
import java.security.SecureRandom

internal class CodeChallengeBuilder {

    fun createCodeVerifier(): String {
        val code = ByteArray(CODE_VERIFIER_BYTE_ARRAY_SIZE)
        SecureRandom().nextBytes(code)
        return encodeToBase64String(code)
    }

    fun createCodeChallenge(codeVerifier: String): String {
        val digest = MessageDigest.getInstance(DIGEST_ALGORITHM)
        val value = digest.digest(codeVerifier.toByteArray(Charsets.US_ASCII))
        return encodeToBase64String(value)
    }

    private fun encodeToBase64String(value: ByteArray): String {
        return Base64.encodeToString(value, Base64.NO_WRAP or Base64.URL_SAFE or Base64.NO_PADDING)
    }

    companion object {

        private const val DIGEST_ALGORITHM = "SHA-256"
        private const val CODE_VERIFIER_BYTE_ARRAY_SIZE = 32
    }
}
