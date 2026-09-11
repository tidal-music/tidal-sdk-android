package com.simple.musicplayer

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.auth.TidalAuth
import com.tidal.sdk.auth.model.AuthConfig
import com.tidal.sdk.auth.model.LoginConfig
import com.tidal.sdk.eventproducer.EventProducer
import com.tidal.sdk.eventproducer.model.EventsConfig
import com.tidal.sdk.player.Player
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.playbackengine.model.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * UI States for the app
 */
sealed class UiState {
    object Loading : UiState()
    data class NotLoggedIn(val message: String? = null) : UiState()
    data class LoggedIn(val currentStatus: String = "") : UiState()
    data class Error(val message: String) : UiState()
}

/**
 * ViewModel that manages authentication and music playback
 */
class MusicPlayerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var context: Context? = null
    private var tidalAuth: TidalAuth? = null
    private var credentialsProvider: CredentialsProvider? = null
    private var player: Player? = null

    private val TAG = "MusicPlayerViewModel"

    /**
     * Initialize the ViewModel with Android context
     */
    fun initialize(activityContext: Context?) {
        activityContext?.let { context = it.applicationContext }

        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading

                // Check if credentials are configured
                if (BuildConfig.TIDAL_CLIENT_ID.isEmpty() || BuildConfig.TIDAL_CLIENT_SECRET.isEmpty()) {
                    _uiState.value = UiState.Error(
                        "Missing TIDAL credentials. Please add them to local.properties file."
                    )
                    return@launch
                }

                // Initialize TIDAL Auth
                initializeAuth()

                // Check if user is already logged in
                checkLoginStatus()

            } catch (e: Exception) {
                Log.e(TAG, "Initialization error", e)
                _uiState.value = UiState.Error("Initialization failed: ${e.message}")
            }
        }
    }

    /**
     * Initialize TIDAL Authentication
     */
    private fun initializeAuth() {
        val ctx = context ?: return

        tidalAuth = TidalAuth.getInstance(
            AuthConfig(
                clientId = BuildConfig.TIDAL_CLIENT_ID,
                clientSecret = BuildConfig.TIDAL_CLIENT_SECRET,
                scopes = setOf("r_usr", "w_usr"), // Read user data, write user data
                credentialsKey = "com.simple.musicplayer",
                enableCertificatePinning = false // Disable for easier development
            ),
            ctx
        )

        credentialsProvider = tidalAuth?.credentialsProvider
        Log.d(TAG, "TIDAL Auth initialized")
    }

    /**
     * Check if user is already logged in
     */
    private suspend fun checkLoginStatus() {
        val provider = credentialsProvider ?: return

        if (provider.isUserLoggedIn) {
            // User is logged in, initialize player
            initializePlayer()
            _uiState.value = UiState.LoggedIn("Ready to play music")
        } else {
            _uiState.value = UiState.NotLoggedIn()
        }
    }

    /**
     * Start the OAuth login flow
     * Opens browser/webview for user to login
     */
    fun startLogin() {
        val auth = tidalAuth ?: return
        val ctx = context ?: return

        try {
            // Get the login URL from TIDAL
            val loginUri = auth.auth.initializeLogin(
                BuildConfig.TIDAL_REDIRECT_URI,
                LoginConfig()
            )

            Log.d(TAG, "Opening login URL: $loginUri")

            // Open the login URL in the browser
            val intent = Intent(Intent.ACTION_VIEW, loginUri).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            ctx.startActivity(intent)

            _uiState.value = UiState.NotLoggedIn("Opening browser for login...")

        } catch (e: Exception) {
            Log.e(TAG, "Login start failed", e)
            _uiState.value = UiState.NotLoggedIn("Login failed: ${e.message}")
        }
    }

    /**
     * Handle the callback from OAuth login
     * Called when app receives the redirect URI: simplemusicplayer://auth/callback?code=...
     */
    fun handleLoginCallback(uri: Uri) {
        val auth = tidalAuth ?: return

        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading

                Log.d(TAG, "Finalizing login with URI: $uri")

                // Complete the login process
                auth.auth.finalizeLogin(uri.toString())

                // Initialize player now that we're logged in
                initializePlayer()

                _uiState.value = UiState.LoggedIn("Login successful!")

            } catch (e: Exception) {
                Log.e(TAG, "Login finalization failed", e)
                _uiState.value = UiState.NotLoggedIn("Login failed: ${e.message}")
            }
        }
    }

    /**
     * Initialize the TIDAL Player
     */
    private suspend fun initializePlayer() {
        val ctx = context ?: return
        val provider = credentialsProvider ?: return

        try {
            if (player != null) {
                Log.d(TAG, "Player already initialized")
                return
            }

            // Create Event Producer for analytics
            val eventProducer = EventProducer(
                credentialsProvider = provider,
                config = EventsConfig()
            )

            // Create the Player
            player = Player(
                application = ctx as android.app.Application,
                credentialsProvider = provider,
                eventSender = eventProducer.eventSender
            )

            // Listen to player events
            viewModelScope.launch {
                player?.playbackEngine?.events?.collect { event ->
                    handlePlayerEvent(event)
                }
            }

            Log.d(TAG, "Player initialized successfully")

        } catch (e: Exception) {
            Log.e(TAG, "Player initialization failed", e)
            _uiState.value = UiState.Error("Player init failed: ${e.message}")
        }
    }

    /**
     * Play a TIDAL track by ID
     */
    fun playTrack(trackId: String) {
        val playerEngine = player?.playbackEngine

        if (playerEngine == null) {
            updateStatus("Player not initialized")
            return
        }

        if (trackId.isBlank()) {
            updateStatus("Please enter a track ID")
            return
        }

        viewModelScope.launch {
            try {
                updateStatus("Loading track $trackId...")

                // Create a media product for the track
                val mediaProduct = MediaProduct(
                    productType = ProductType.TRACK,
                    productId = trackId
                )

                // Load and play the track
                playerEngine.load(mediaProduct)
                playerEngine.play()

                updateStatus("Playing track $trackId")

            } catch (e: Exception) {
                Log.e(TAG, "Play track failed", e)
                updateStatus("Error: ${e.message}")
            }
        }
    }

    /**
     * Pause playback
     */
    fun pause() {
        player?.playbackEngine?.pause()
        updateStatus("Paused")
    }

    /**
     * Logout the user
     */
    fun logout() {
        viewModelScope.launch {
            try {
                // Release player resources
                player?.release()
                player = null

                // Logout from TIDAL
                tidalAuth?.auth?.logout()

                _uiState.value = UiState.NotLoggedIn("Logged out successfully")

            } catch (e: Exception) {
                Log.e(TAG, "Logout failed", e)
            }
        }
    }

    /**
     * Handle player events
     */
    private fun handlePlayerEvent(event: Event) {
        Log.d(TAG, "Player Event: $event")

        when (event) {
            is Event.MediaProductTransition -> {
                event.mediaProduct?.let {
                    updateStatus("Now playing: ${it.productId}")
                }
            }
            is Event.PlaybackStateChange -> {
                updateStatus("Playback state: ${event.playbackState}")
            }
            else -> {
                // Handle other events as needed
            }
        }
    }

    /**
     * Update the status message in UI
     */
    private fun updateStatus(message: String) {
        Log.d(TAG, "Status: $message")
        val current = _uiState.value
        if (current is UiState.LoggedIn) {
            _uiState.value = current.copy(currentStatus = message)
        }
    }

    override fun onCleared() {
        super.onCleared()
        player?.release()
    }
}
