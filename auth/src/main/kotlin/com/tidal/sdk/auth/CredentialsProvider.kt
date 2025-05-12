package com.tidal.sdk.auth

import com.tidal.sdk.auth.model.AuthResult
import com.tidal.sdk.auth.model.Credentials
import com.tidal.sdk.auth.model.CredentialsUpdatedMessage
import com.tidal.sdk.common.IllegalConfigurationError
import com.tidal.sdk.common.RetryableError
import com.tidal.sdk.common.TidalMessage
import kotlinx.coroutines.flow.Flow

/**
 * Provides functionality to retrieve and manage user credentials. This interface defines the
 * contract for credential operations within the library.
 */
interface CredentialsProvider {

    /**
     * The default bus is used for all asynchronous communication by the Auth module. Subscribe to
     * this [Flow] to receive [CredentialsUpdatedMessage]
     */
    val bus: Flow<TidalMessage>

    /**
     * Retrieves the current user's credentials, ensuring they are valid and up to date.
     *
     * @param apiErrorSubStatus Optional parameter indicating a TIDAL-specific error condition.
     * @return [AuthResult] containing either [Credentials] or one of the following errors:
     *   [RetryableError]: If updating credentials fails but can be retried.
     *   [IllegalConfigurationError] If the configuration prevents creating valid credentials.
     */
    suspend fun getCredentials(apiErrorSubStatus: String? = null): AuthResult<Credentials>

    /**
     * Convenience function to quickly check if a user is logged in.
     *
     * @return `true` if a user is logged in, `false` otherwise.
     */
    fun isUserLoggedIn(): Boolean
}
