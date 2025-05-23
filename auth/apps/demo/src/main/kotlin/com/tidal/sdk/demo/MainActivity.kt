package com.tidal.sdk.demo

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tidal.sdk.auth.Auth
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.auth.TidalAuth
import com.tidal.sdk.auth.demo.BuildConfig
import com.tidal.sdk.auth.model.AuthConfig
import com.tidal.sdk.auth.model.AuthResult
import com.tidal.sdk.auth.model.DeviceAuthorizationResponse
import com.tidal.sdk.auth.model.LoginConfig
import com.tidal.sdk.auth.model.QueryParameter
import com.tidal.sdk.auth.network.NetworkLogLevel
import com.tidal.sdk.common.d
import com.tidal.sdk.common.logger
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavController

    lateinit var auth: Auth
    lateinit var credentialsProvider: CredentialsProvider

    val loginConfig =
        LoginConfig(
            customParams =
                setOf(
                    QueryParameter(key = "appMode", value = "android")
                ) // Client has to inform the module about the appMode
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * This test app expects the following values in 'local.properties' to be set:
         * TIDAL_CLIENT_ID: The id of the app created by you in the TIDAL developer portal
         * TIDAL_CLIENT_SECRET: The secret generated for this id TIDAL_CLIENT_SCOPES: The scopes you
         * selected in the portal, entered as a single string, scopes separated by a space
         */
        val authConfig =
            AuthConfig(
                clientId = BuildConfig.TIDAL_CLIENT_ID,
                clientSecret = BuildConfig.TIDAL_CLIENT_SECRET,
                scopes = BuildConfig.TIDAL_CLIENT_SCOPES.split(" ").toSet(),
                credentialsKey = STORAGE_KEY,
                enableCertificatePinning = true,
                logLevel = NetworkLogLevel.BODY,
            )
        initAuthModule(authConfig)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    val navController =
                        rememberNavController().also { this@MainActivity.navController = it }
                    NavigationHost(navController = navController)
                }
            }
        }
    }

    private fun initAuthModule(authConfig: AuthConfig) {
        with(TidalAuth.getInstance(authConfig, this)) {
            this@MainActivity.auth = this.auth
            this@MainActivity.credentialsProvider = this.credentialsProvider

            // This watches the bus and prints all received messages to our log
            lifecycleScope.launch {
                credentialsProvider.bus.collectLatest { logger.d { it.toString() } }
            }
            lifecycleScope.launch {
                val token = credentialsProvider.getCredentials()
                logger.d { "token: $token" }
            }
        }
    }

    @Suppress("UnusedPrivateMember")
    fun onRedirectUriReceived(uri: Uri) {
        lifecycleScope
            .launch { auth.finalizeLogin(requireNotNull(uri.query)) }
            .invokeOnCompletion {
                if (navController.currentBackStackEntry.toString().contains("login")) {
                    navController.popBackStack()
                }
            }
    }

    suspend fun initializeDeviceLogin(): AuthResult<DeviceAuthorizationResponse> {
        return auth.initializeDeviceLogin()
    }

    suspend fun finalizeDeviceLogin(deviceCode: String) {
        with(auth.finalizeDeviceLogin(deviceCode)) {
            if (this.isSuccess) {
                navController.popBackStack()
            }
        }
    }

    fun logout() {
        lifecycleScope.launch { auth.logout() }
    }

    companion object {

        private const val STORAGE_KEY = "storage"
    }
}

internal fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    error("Permissions should be called in the context of an Activity")
}
