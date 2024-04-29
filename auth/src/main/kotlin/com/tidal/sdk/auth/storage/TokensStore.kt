package com.tidal.sdk.auth.storage

import com.tidal.sdk.auth.model.Tokens

internal interface TokensStore {

    fun getLatestTokens(key: String): Tokens?
    fun saveTokens(tokens: Tokens)
    fun eraseTokens()
}
