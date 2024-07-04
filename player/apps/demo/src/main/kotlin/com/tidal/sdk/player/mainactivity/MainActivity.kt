package com.tidal.sdk.player.mainactivity

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.util.UnstableApi
import com.tidal.sdk.player.mainactivity.MainActivityViewModel.Operation.Impure
import com.tidal.sdk.player.ui.theme.PlayerTheme

@UnstableApi
internal class MainActivity : ComponentActivity() {

    @Suppress("LongMethod")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            PlayerTheme {
                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Player",
                                )
                            },
                        )
                    },
                ) { paddingValues ->
                    Column {
                        ViewModelProvider(
                            this@MainActivity,
                            MainActivityViewModel.Factory(this@MainActivity),
                        )[MainActivityViewModel::class.java].run {
                            val addedNonTopPadding = 16.dp
                            MainActivityScreen(
                                uiState.collectAsState().value,
                                snackbarHostState,
                                PaddingValues(
                                    start = paddingValues.calculateStartPadding(
                                        LocalLayoutDirection.current,
                                    ) + addedNonTopPadding,
                                    top = paddingValues.calculateTopPadding(),
                                    end = paddingValues.calculateEndPadding(
                                        LocalLayoutDirection.current,
                                    ) + addedNonTopPadding,
                                    bottom = paddingValues.calculateBottomPadding() +
                                        addedNonTopPadding,
                                ),
                                dispatchLoad = {
                                    dispatch(MainActivityViewModel.Operation.Pure.Load(it))
                                },
                                dispatchSetNext = {
                                    dispatch(Impure.SetNext(it))
                                },
                                dispatchPlay = {
                                    dispatch(MainActivityViewModel.Operation.Pure.Play)
                                },
                                dispatchSkip = {
                                    dispatch(MainActivityViewModel.Operation.Pure.Skip)
                                },
                                dispatchSetVideoSurfaceView = {
                                    dispatch(Impure.SetVideoSurfaceView(it))
                                },
                                dispatchSetDraggedPosition = {
                                    dispatch(Impure.SetDraggedPosition(it))
                                },
                                dispatchPause = {
                                    dispatch(MainActivityViewModel.Operation.Pure.Pause)
                                },
                                dispatchReset = {
                                    dispatch(Impure.Reset)
                                },
                                dispatchRewind = {
                                    dispatch(MainActivityViewModel.Operation.Pure.Seek.Rewind)
                                },
                                dispatchFastForward = {
                                    dispatch(MainActivityViewModel.Operation.Pure.Seek.FastForward)
                                },
                                dispatchSeekToNearEnd = {
                                    dispatch(
                                        MainActivityViewModel.Operation.Pure.Seek.SeekToNearEnd,
                                    )
                                },
                                dispatchSetRepeatOne = {
                                    dispatch(Impure.SetRepeatOne(it))
                                },
                                dispatchSetOfflineMode = {
                                    dispatch(Impure.SetOfflineMode(it))
                                },
                                dispatchSetAudioQualityOnWifi = {
                                    dispatch(Impure.SetAudioQualityOnWifi(it))
                                },
                                dispatchSetAudioQualityOnCell = {
                                    dispatch(Impure.SetAudioQualityOnCell(it))
                                },
                                dispatchSetLoudnessNormalizationMode = {
                                    dispatch(
                                        Impure.SetLoudnessNormalizationMode(it),
                                    )
                                },
                                dispatchSetImmersiveAudio = {
                                    dispatch(Impure.SetImmersiveAudio(it))
                                },
                                dispatchRelease = { dispatch(Impure.Release) },
                                dispatchSetSnackbarMessage = {
                                    dispatch(Impure.SetSnackbarMessage(it))
                                },
                                dispatchCreatePlayerWithExternalCache = { context, startOffline ->
                                    dispatch(
                                        Impure.CreatePlayer.WithExternalCache(
                                            context,
                                            this,
                                            startOffline,
                                        ),
                                    )
                                },
                                dispatchCreatePlayerWithInternalCache = { context, startOffline ->
                                    dispatch(
                                        Impure.CreatePlayer.WithInternalCache(
                                            context,
                                            this,
                                            startOffline,
                                        ),
                                    )
                                },
                                dispatchFinalizeWebLoginFlow = { context: Context, uri: Uri ->
                                    dispatch(Impure.FinalizeLoginFlow.Web(context, this, uri))
                                },
                                dispatchFinalizeImplicitLoginFlow = { context: Context ->
                                    dispatch(Impure.FinalizeLoginFlow.Implicit(context, this))
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}
