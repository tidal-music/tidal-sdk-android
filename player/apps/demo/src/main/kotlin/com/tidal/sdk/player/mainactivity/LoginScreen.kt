package com.tidal.sdk.player.mainactivity

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.util.UnstableApi
import com.tidal.sdk.player.auth.weblogin.ComposeWebView

@Composable
@Suppress("LongParameterList")
@UnstableApi
internal fun LoginScreen(
    snackbarHostState: SnackbarHostState,
    state: MainActivityState.AwaitingLoginFlowChoice,
    paddingValues: PaddingValues = PaddingValues(),
    dispatchSetSnackbarMessage: (String?) -> Unit,
    dispatchFinalizeWebLoginFlow: (Context, Uri) -> Unit,
    dispatchFinalizeImplicitLoginFlow: (Context) -> Unit,
) {
    if (state.snackbarMessage == null) {
        snackbarHostState.currentSnackbarData?.dismiss()
    } else {
        LaunchedEffect(snackbarHostState) {
            check(
                snackbarHostState.showSnackbar(
                    message = state.snackbarMessage,
                    withDismissAction = true,
                    duration = SnackbarDuration.Indefinite,
                ) == SnackbarResult.Dismissed,
            )
            dispatchSetSnackbarMessage(null)
        }
    }
    if (state.isUserLoggedIn) {
        LoadingScreen()
        val context = LocalContext.current
        LaunchedEffect(Unit) { dispatchFinalizeImplicitLoginFlow(context) }
        return
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding()),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
        ComposeWebView(
            Modifier.fillMaxSize(),
            dispatchSetSnackbarMessage,
            dispatchFinalizeWebLogin = dispatchFinalizeWebLoginFlow,
            state.webLoginUri.toString(),
        )
    }
}
