package com.tidal.sdk.util

import com.tidal.sdk.auth.model.Credentials
import com.tidal.sdk.auth.util.TimeProvider
import kotlin.time.DurationUnit
import kotlin.time.toDuration

const val TEST_CLIENT_ID = "testClientId"
const val TEST_CLIENT_UNIQUE_KEY = "testClientUniqueKey"

internal val TEST_TIME_PROVIDER: TimeProvider by lazy { TestTimeProvider() }

fun makeCredentials(
    clientId: String = TEST_CLIENT_ID,
    clientUniqueKey: String = TEST_CLIENT_UNIQUE_KEY,
    scopes: Set<String> = setOf(),
    isExpired: Boolean,
    userId: String? = "userId",
    token: String = "token",
): Credentials {
    val expiry = if (isExpired) {
        TEST_TIME_PROVIDER.now.minus(5.toDuration(DurationUnit.MINUTES))
    } else {
        TEST_TIME_PROVIDER.now.plus(5.toDuration(DurationUnit.MINUTES))
    }
    return Credentials(
        clientId = clientId,
        requestedScopes = scopes,
        clientUniqueKey = clientUniqueKey,
        grantedScopes = setOf(),
        userId = userId,
        expires = expiry,
        token = token,
    )
}
