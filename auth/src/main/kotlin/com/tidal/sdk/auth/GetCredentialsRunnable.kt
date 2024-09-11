package com.tidal.sdk.auth

import com.tidal.sdk.auth.model.Credentials
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

internal class GetCredentialsRunnable(
    @Assisted
    private val apiErrorSubStatus: String?,
    @Assisted
    private val callback: (Credentials) -> Unit,
    private val depsYouNeedToGetTheToken,
) : Runnable {

    override fun run() {
        // Here you move what you had in TokenRepository.getCredentials, but simpler because it has
        // to be synchronous, so you get rid of all the suspend stuff (e.g. the network calls).
        // Asynchronicity is built via the Handler's thread and exposed as a suspend function
        // thanks to suspendCancellableCoroutine
        callback(otherDepsYouNeedToGetTheToken.getCredentials)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            apiErrorSubStatus: String?,
            callback: (Credentials) -> Unit,
        ): GetCredentialsRunnable
    }
}
