package com.simple.musicplayer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Main Activity - handles login and displays the music player
 */
class MainActivity : ComponentActivity() {

    private val viewModel: MusicPlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the ViewModel with this activity context
        viewModel.initialize(this)

        // Check if we're coming back from OAuth login
        handleDeepLink(intent)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MusicPlayerScreen(viewModel)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleDeepLink(intent)
    }

    /**
     * Handle deep link from OAuth redirect
     * When user logs in, TIDAL redirects to: simplemusicplayer://auth/callback?code=...
     */
    private fun handleDeepLink(intent: Intent?) {
        val uri = intent?.data
        if (uri != null && uri.toString().startsWith(BuildConfig.TIDAL_REDIRECT_URI)) {
            Log.d("MainActivity", "Received OAuth redirect: $uri")
            viewModel.handleLoginCallback(uri)
        }
    }
}

@Composable
fun MusicPlayerScreen(viewModel: MusicPlayerViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Simple Music Player") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (uiState) {
                is UiState.Loading -> {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Initializing...")
                }

                is UiState.NotLoggedIn -> {
                    LoginScreen(
                        onLoginClick = { viewModel.startLogin() },
                        message = (uiState as UiState.NotLoggedIn).message
                    )
                }

                is UiState.LoggedIn -> {
                    PlayerScreen(
                        viewModel = viewModel,
                        state = uiState as UiState.LoggedIn
                    )
                }

                is UiState.Error -> {
                    ErrorScreen(
                        error = (uiState as UiState.Error).message,
                        onRetry = { viewModel.initialize(null) }
                    )
                }
            }
        }
    }
}

@Composable
fun LoginScreen(onLoginClick: () -> Unit, message: String?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = androidx.compose.material.icons.Icons.Default.MusicNote,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Welcome to Simple Music Player",
            style = MaterialTheme.typography.headlineSmall
        )

        if (message != null) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Login with TIDAL")
        }

        Text(
            text = "You need a TIDAL account to use this app",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun PlayerScreen(viewModel: MusicPlayerViewModel, state: UiState.LoggedIn) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        // User info
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Logged In",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Ready to play music!",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Track input
        var trackId by remember { mutableStateOf("251380837") }
        OutlinedTextField(
            value = trackId,
            onValueChange = { trackId = it },
            label = { Text("TIDAL Track ID") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Playback controls
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { viewModel.playTrack(trackId) },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.PlayArrow,
                    contentDescription = "Play"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Play")
            }

            Button(
                onClick = { viewModel.pause() },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Pause,
                    contentDescription = "Pause"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Pause")
            }
        }

        // Status
        if (state.currentStatus.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Text(
                    text = state.currentStatus,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Logout button
        Spacer(modifier = Modifier.weight(1f))
        OutlinedButton(
            onClick = { viewModel.logout() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
    }
}

@Composable
fun ErrorScreen(error: String, onRetry: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = androidx.compose.material.icons.Icons.Default.Error,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.error
        )

        Text(
            text = "Error",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.error
        )

        Text(
            text = error,
            style = MaterialTheme.typography.bodyMedium
        )

        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}
