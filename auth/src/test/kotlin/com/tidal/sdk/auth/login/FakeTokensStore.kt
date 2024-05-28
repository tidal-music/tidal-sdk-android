package com.tidal.sdk.auth.login

import com.tidal.sdk.auth.model.Tokens
import com.tidal.sdk.auth.storage.TokensStore

internal class FakeTokensStore(
    val credentialsKey: String,
    val storedTokens: Tokens? = null,
) : TokensStore {

    var saves = 0
    var gets = 0
    var loads = 0

    var tokensList = mutableListOf<Tokens>()

    /**
     * Please be aware that this mimicks the behaviour of [DefaultTokensStore] without
     * having an actual database. So if the implementation changes, this should be updated.
     * We aim to improve this in the near future though. Until then, you are aware now.
     */
    override fun getLatestTokens(key: String): Tokens? {
        gets += 1
        if (key != credentialsKey) return null
        val lastTokens = last()
        return if (lastTokens != null) {
            lastTokens
        } else {
            loads += 1
            storedTokens
        }
    }

    override fun saveTokens(tokens: Tokens) {
        saves += 1
        tokensList.add(tokens)
    }

    override fun eraseTokens() {
        tokensList.clear()
    }

    fun last(): Tokens? {
        return tokensList.lastOrNull()
    }
}
