package com.tidal.sdk.auth.model

import com.tidal.sdk.common.TidalMessage

/**
 * Message sent on the [CredentialsProvider.bus] when credentials are updated.
 *
 * @param credentials The updated credentials, or `null` if the credentials were removed.
 */
data class CredentialsUpdatedMessage(val credentials: Credentials? = null) : TidalMessage
