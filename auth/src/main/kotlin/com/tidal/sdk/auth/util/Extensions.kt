package com.tidal.sdk.auth.util

import com.tidal.sdk.auth.model.Credentials

/** Convenience function to check if the user is logged in. */
fun Credentials.isLoggedIn(): Boolean {
    return level == Credentials.Level.USER
}
