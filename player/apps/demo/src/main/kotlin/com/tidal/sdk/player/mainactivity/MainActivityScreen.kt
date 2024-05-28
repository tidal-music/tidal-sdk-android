package com.tidal.sdk.player.mainactivity

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.media3.common.util.UnstableApi
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.LoudnessNormalizationMode
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.playbackengine.view.AspectRatioAdjustingSurfaceView

@Composable
@Suppress("LongParameterList")
@UnstableApi
internal fun MainActivityScreen(
    state: MainActivityState,
    snackbarHostState: SnackbarHostState,
    paddingValues: PaddingValues = PaddingValues(),
    dispatchLoad: (MediaProduct) -> Unit,
    dispatchSetNext: (MediaProduct?) -> Unit,
    dispatchPlay: () -> Unit,
    dispatchSkip: () -> Unit,
    dispatchSetVideoSurfaceView: (AspectRatioAdjustingSurfaceView?) -> Unit,
    dispatchSetDraggedPosition: (Float?) -> Unit,
    dispatchPause: () -> Unit,
    dispatchReset: () -> Unit,
    dispatchRewind: () -> Unit,
    dispatchFastForward: () -> Unit,
    dispatchSeekToNearEnd: () -> Unit,
    dispatchSetRepeatOne: (Boolean) -> Unit,
    dispatchSetOfflineMode: (Boolean) -> Unit,
    dispatchSetAudioQualityOnWifi: (AudioQuality) -> Unit,
    dispatchSetAudioQualityOnCell: (AudioQuality) -> Unit,
    dispatchSetLoudnessNormalizationMode: (LoudnessNormalizationMode) -> Unit,
    dispatchRelease: () -> Unit,
    dispatchSetSnackbarMessage: (String?) -> Unit,
    dispatchCreatePlayerWithExternalCache: (Context, Boolean) -> Unit,
    dispatchCreatePlayerWithInternalCache: (Context, Boolean) -> Unit,
    dispatchFinalizeWebLoginFlow: (Context, Uri) -> Unit,
    dispatchFinalizeImplicitLoginFlow: (Context) -> Unit,
) {
    when (state) {
        is MainActivityState.AwaitingLoginFlowChoice ->
            LoginScreen(
                snackbarHostState,
                state,
                paddingValues,
                dispatchSetSnackbarMessage,
                dispatchFinalizeWebLoginFlow,
                dispatchFinalizeImplicitLoginFlow,
            )

        is MainActivityState.PlayerInitialized ->
            PlayerInitializedScreen(
                state,
                paddingValues,
                dispatchLoad,
                dispatchSetNext,
                dispatchPlay,
                dispatchSkip,
                dispatchSetVideoSurfaceView,
                dispatchSetDraggedPosition,
                dispatchPause,
                dispatchReset,
                dispatchRewind,
                dispatchFastForward,
                dispatchSeekToNearEnd,
                dispatchSetRepeatOne,
                dispatchSetOfflineMode,
                dispatchSetAudioQualityOnWifi,
                dispatchSetAudioQualityOnCell,
                dispatchSetLoudnessNormalizationMode,
                dispatchRelease,
            )

        is MainActivityState.PlayerNotInitialized ->
            PlayerNotInitializedScreen(
                paddingValues,
                dispatchCreatePlayerWithExternalCache,
                dispatchCreatePlayerWithInternalCache,
            )

        is MainActivityState.Loading -> LoadingScreen()
    }
    val snackbarMessage = state.snackbarMessage
    if (snackbarMessage == null) {
        snackbarHostState.currentSnackbarData?.dismiss()
    } else {
        LaunchedEffect(snackbarHostState) {
            check(
                snackbarHostState.showSnackbar(
                    message = snackbarMessage,
                    withDismissAction = true,
                    duration = SnackbarDuration.Indefinite,
                ) == SnackbarResult.Dismissed,
            )
            dispatchSetSnackbarMessage(null)
        }
    }
}
