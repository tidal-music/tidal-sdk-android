package com.tidal.sdk.auth

import com.tidal.sdk.auth.model.Credentials
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

internal class SetCredentialsRunnable(
    @Assisted
    private val newToken: Credentials,
    @Assisted
    private val callback: (Credentials) -> Unit,
    private val depsYouNeedToSetTheToken,
) : Runnable {
    override fun run() {
        // Here you move what you had in LoginRepository.setCredentials. The only suspend stuff you
        // had there was was bus.emit, but you can use bus.tryEmit instead.
        depsYouNeedToSetTheToken.set(newToken)
        callback(this)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            newToken: Credentials,
            callback: (Runnable) -> Unit
        ): SetCredentialsRunnable
    }
}
