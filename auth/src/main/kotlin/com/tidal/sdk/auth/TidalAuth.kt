package com.tidal.sdk.auth

import android.content.Context
import com.tidal.sdk.auth.di.AuthComponent
import com.tidal.sdk.auth.di.DaggerAuthComponent
import com.tidal.sdk.auth.model.AuthConfig
import javax.inject.Inject

/**
 * The `TidalAuth` class encapsulates the authentication and authorization logic, providing a
 * streamlined interface for managing user sessions and handling OAuth flows.
 *
 * This class provides instances of both [Auth] (for login) and [CredentialsProvider] (providing
 * OAuth tokens). It is designed as a singleton to ensure a unified access point for authentication
 * operations with a consistent state.
 *
 * @constructor Private to prevent direct instantiation. Use [getInstance] to obtain the singleton
 * instance.
 */
class TidalAuth private constructor() {

    @Inject
    lateinit var auth: Auth

    @Inject
    lateinit var credentialsProvider: CredentialsProvider

    companion object {

        const val AUTH_SERVICE_BASE_HOSTNAME = "auth.tidal.com"
        const val AUTH_SERVICE_BASE_URL = "$AUTH_SERVICE_BASE_HOSTNAME/v1/"
        const val DEFAULT_PROTOCOL = "https://"

        @Volatile
        private var instance: TidalAuth? = null

        /**
         * Provides a global access point to the [TidalAuth] instance, ensuring that only one
         * instance is created and used throughout the application lifecycle.
         * If the instance has not been created yet, it initializes the [TidalAuth] instance
         * with the provided configuration parameters.

         * @return The single instance of [TidalAuth] that can be used to perform authentication
         * and authorization operations.
         */
        fun getInstance(
            config: AuthConfig,
            context: Context,
            authComponent: AuthComponent? = null,
        ): TidalAuth {
            return instance ?: synchronized(this) {
                TidalAuth().also {
                    val component = authComponent ?: DaggerAuthComponent.factory().create(
                        context = context,
                        config = config,
                    )
                    component.inject(it)
                    instance = it
                }
            }
        }
    }
}
