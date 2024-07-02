package com.tidal.sdk.demo

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
import com.tidal.sdk.auth.model.AuthConfig
import com.tidal.sdk.auth.model.Credentials
import com.tidal.sdk.auth.model.CredentialsUpdatedMessage
import com.tidal.sdk.auth.network.NetworkLogLevel
import com.tidal.sdk.auth.util.isLoggedIn
import com.tidal.sdk.catalogue.Catalogue
import com.tidal.sdk.catalogue.demo.BuildConfig
import com.tidal.sdk.common.d
import com.tidal.sdk.common.logger
import com.tidal.sdk.demo.ui.NavigationHost
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Suppress("MagicNumber")
class MainActivity : ComponentActivity() {

    lateinit var auth: Auth
    lateinit var credentialsProvider: CredentialsProvider
    lateinit var catalogue: Catalogue

    private lateinit var navController: NavController

    private val authConfig = AuthConfig(
        clientId = BuildConfig.TIDAL_CLIENT_ID,
        clientSecret = BuildConfig.TIDAL_CLIENT_SECRET,
        scopes = BuildConfig.TIDAL_CLIENT_SCOPES.split(" ").toSet(),
        credentialsKey = "catalogueDemo",
        enableCertificatePinning = true,
        logLevel = NetworkLogLevel.BODY,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSdk()

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    val navController = rememberNavController().also {
                        logger.d { "NavController initialized" }
                        this@MainActivity.navController = it
                    }
                    NavigationHost(navController = navController)
                }
            }
        }
        listenToAuth()
    }

    private fun initSdk() {
        with(
            TidalAuth.getInstance(authConfig, this),
        ) {
            this@MainActivity.auth = this.auth
            this@MainActivity.credentialsProvider = this.credentialsProvider
            this@MainActivity.catalogue = Catalogue(this.credentialsProvider)
        }
    }

    private fun listenToAuth() {
        lifecycleScope.launch {
            credentialsProvider.bus.collectLatest { message ->
                this@MainActivity.logger.d { "Auth bus message: $message" }
                with(message as? CredentialsUpdatedMessage) {
                    this?.credentials?.let { analyzeCredentials(it) }
                }
            }
        }
        lifecycleScope.launch {
            val credentialsResult = credentialsProvider.getCredentials()
            credentialsResult.successData?.let { token ->
                analyzeCredentials(token)
            }
        }
    }

    private fun analyzeCredentials(credentials: Credentials) {
        if (credentials.isLoggedIn()) {
            logger.d { "User is logged in" }
        } else {
            logger.d { "User is not logged in" }
            navController.navigate("login")
        }
    }

    @Suppress("UnusedPrivateMember")
    fun onRedirectUriReceived(uri: Uri) {
        lifecycleScope.launch {
            auth.finalizeLogin(uri.toString())
        }.invokeOnCompletion {
            if (navController.currentBackStackEntry.toString().contains("login")) {
                navController.popBackStack()
            }
        }
    }

    fun logout() {
        lifecycleScope.launch {
            auth.logout()
        }
    }
}
