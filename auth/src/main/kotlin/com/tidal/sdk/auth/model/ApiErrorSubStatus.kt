package com.tidal.sdk.auth.model

/**
 * Represents a TIDAL-specific error condition that can be used to trigger specific behavior.
 *
 * @param value The error code returned by the API.
 * @param shouldTriggerRefresh Whether the error should trigger a backend call to refresh the user's
 *   credentials.
 */
internal enum class ApiErrorSubStatus(val value: String, val shouldTriggerRefresh: Boolean) {
    AuthorizationPending("1002", false),
    SessionDoesNotExist("6001", true),
    TemporaryAuthServerError("11001", true),
    InvalidAccessToken("11002", true),
    ExpiredAccessToken("11003", true),
}
