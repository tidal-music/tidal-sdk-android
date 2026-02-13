package com.tidal.sdk.player.mainactivity

import com.tidal.sdk.player.demo.BuildConfig
import android.text.format.DateUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.tidal.sdk.auth.model.Credentials
import com.tidal.sdk.player.BuildConfig
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.LoudnessNormalizationMode
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.playbackengine.model.PlaybackState
import com.tidal.sdk.player.playbackengine.view.AspectRatioAdjustingSurfaceView

@Composable
@Suppress("MagicNumber", "ComplexMethod", "LongParameterList")
internal fun PlayerInitializedScreen(
    state: MainActivityState.PlayerInitialized,
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
    dispatchSetImmersiveAudio: (Boolean) -> Unit,
    dispatchSetEnableAdaptive: (Boolean) -> Unit,
    dispatchRelease: () -> Unit,
) {
    Column(Modifier.padding(paddingValues)) {
        var demoPlayableItemsExpanded by rememberSaveable { mutableStateOf(true) }
        Button(
            onClick = { demoPlayableItemsExpanded = !demoPlayableItemsExpanded },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text =
                    "SHOW ${
                    if (demoPlayableItemsExpanded) {
                        "PLAYBACK CONTROLS"
                    } else {
                        "DEMO PLAYABLE ITEMS"
                    }
                }"
            )
        }

        if (demoPlayableItemsExpanded) {
            DemoPlayableItemsList(state, dispatchLoad, dispatchSetNext, dispatchPlay, dispatchSkip)
        } else {
            PlaybackControls(
                state,
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
                dispatchSetImmersiveAudio,
                dispatchSetEnableAdaptive,
                dispatchRelease,
            )
        }
    }
}

@Composable
private fun DemoPlayableItemsList(
    state: MainActivityState.PlayerInitialized,
    dispatchLoad: (MediaProduct) -> Unit,
    dispatchSetNext: (MediaProduct?) -> Unit,
    dispatchPlay: () -> Unit,
    dispatchSkip: () -> Unit,
) {
    val itemListState = rememberLazyListState()
    val selectedDemoPlayableItems =
        DemoPlayableItem.HARDCODED.filter { CREDENTIAL_LEVEL in it.allowedCredentialLevels }
    val selectedIndex =
        if (state.currentMediaProduct != null) {
            selectedDemoPlayableItems.run {
                indexOf(
                    single { it.mediaProductId.contentEquals(state.currentMediaProduct.productId) }
                )
            }
        } else {
            null
        }
    selectedIndex?.let { LaunchedEffect("ScrollToCurrent") { itemListState.scrollToItem(it) } }
    LazyColumn(state = itemListState) {
        itemsIndexed(selectedDemoPlayableItems) { i, item ->
            DemoPlayableItemComposable(
                item = item,
                isCurrent = item.mediaProductId.contentEquals(state.currentMediaProduct?.productId),
                isNext = item.mediaProductId.contentEquals(state.nextMediaProduct?.productId),
                dispatchLoad,
                {
                    if (i < selectedDemoPlayableItems.size - 1) {
                        selectedDemoPlayableItems[i + 1].createMediaProduct()
                    } else {
                        null
                    }
                },
                dispatchSetNext,
                dispatchPlay,
                dispatchSkip,
            )
        }
    }
}

@Suppress("LongParameterList")
@Composable
private fun PlaybackControls(
    state: MainActivityState.PlayerInitialized,
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
    dispatchSetImmersiveAudioOnCell: (Boolean) -> Unit,
    dispatchSetEnableAdaptive: (Boolean) -> Unit,
    dispatchRelease: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollState)) {
        val playbackState = state.playbackState
        OutlinedTextField(
            value = playbackState.name,
            readOnly = true,
            enabled = false,
            label = { Text(text = "Playback state") },
            onValueChange = { throw UnsupportedOperationException() },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            colors =
                TextFieldDefaults.outlinedTextFieldColors(
                    disabledLabelColor = LocalContentColor.current,
                    disabledBorderColor = LocalContentColor.current,
                    disabledTextColor = LocalContentColor.current,
                ),
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        )
        val outputDevice = state.outputDevice
        OutlinedTextField(
            value = outputDevice.name,
            readOnly = true,
            enabled = false,
            label = { Text(text = "Output Device") },
            onValueChange = { throw UnsupportedOperationException() },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            colors =
                TextFieldDefaults.outlinedTextFieldColors(
                    disabledLabelColor = LocalContentColor.current,
                    disabledBorderColor = LocalContentColor.current,
                    disabledTextColor = LocalContentColor.current,
                ),
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        )
        if (
            playbackState != PlaybackState.IDLE &&
                state.currentMediaProduct?.productType == ProductType.VIDEO
        ) {
            AndroidView(
                factory = { AspectRatioAdjustingSurfaceView(it) },
                modifier = Modifier.fillMaxWidth(),
                update = { dispatchSetVideoSurfaceView(it) },
            )
        } else {
            dispatchSetVideoSurfaceView(null)
        }

        Slider(
            enabled = playbackState != PlaybackState.IDLE,
            value = state.positionSeconds,
            onValueChange = { dispatchSetDraggedPosition(it) },
            valueRange = 0f..(state.durationSeconds?.coerceAtLeast(0F) ?: 0f),
            onValueChangeFinished = { dispatchSetDraggedPosition(null) },
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(0.dp, 0.dp, 0.dp, 16.dp),
        ) {
            Text(text = DateUtils.formatElapsedTime(state.positionSeconds.toLong()))
            Text(text = DateUtils.formatElapsedTime(state.durationSeconds?.toLong() ?: 0L))
        }

        FlowRow(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(enabled = playbackState == PlaybackState.NOT_PLAYING, onClick = dispatchPlay) {
                Text(text = "PLAY")
            }
            Button(
                enabled =
                    playbackState == PlaybackState.PLAYING ||
                        playbackState == PlaybackState.STALLED,
                onClick = dispatchPause,
            ) {
                Text(text = "PAUSE")
            }
            Button(
                enabled = playbackState != PlaybackState.IDLE && state.nextMediaProduct != null,
                onClick = dispatchSkip,
            ) {
                Text(text = "SKIP")
            }
            Button(onClick = dispatchReset) { Text(text = "RESET") }
            Button(enabled = playbackState != PlaybackState.IDLE, onClick = dispatchRewind) {
                Text(text = "RW 10s")
            }
            Button(enabled = playbackState != PlaybackState.IDLE, onClick = dispatchFastForward) {
                Text(text = "FF 10s")
            }
            Button(enabled = playbackState != PlaybackState.IDLE, onClick = dispatchSeekToNearEnd) {
                Text(text = "SEEK TO NEAR END")
            }
            Button(onClick = { dispatchSetRepeatOne(!state.isRepeatOneEnabled) }) {
                Text(text = "SET REPEAT ONE (IS ${if (state.isRepeatOneEnabled) "ON" else "OFF"})")
            }
            Button(onClick = { dispatchSetOfflineMode(!state.isOfflineModeEnabled) }) {
                Text(
                    text =
                        "SET OFFLINE MODE (IS ${if (state.isOfflineModeEnabled) "ON" else "OFF"})"
                )
            }
        }
        Selector(
            title = "Audio quality wifi",
            selectedValue = state.streamingAudioQualityOnWifi,
            possibleValues = AudioQuality.values(),
        ) {
            dispatchSetAudioQualityOnWifi(it)
        }
        Selector(
            title = "Audio quality cellular",
            selectedValue = state.streamingAudioQualityOnCell,
            possibleValues = AudioQuality.values(),
        ) {
            dispatchSetAudioQualityOnCell(it)
        }
        Selector(
            title = "Loudness normalization mode",
            selectedValue = state.loudnessNormalizationMode,
            possibleValues = LoudnessNormalizationMode.values(),
        ) {
            dispatchSetLoudnessNormalizationMode(it)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        ) {
            Text(
                text = "Immersive Audio",
                modifier = Modifier.padding(PaddingValues(end = 8F.dp)).weight(1F, fill = false),
            )
            Switch(
                checked = state.immersiveAudio,
                onCheckedChange = { dispatchSetImmersiveAudioOnCell(it) },
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        ) {
            Text(
                text = "Adaptive",
                modifier = Modifier.padding(PaddingValues(end = 8F.dp)).weight(1F, fill = false),
            )
            Switch(
                checked = state.enableAdaptive,
                onCheckedChange = { dispatchSetEnableAdaptive(it) },
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Text(
                text = "PREVIEW REASON",
                modifier = Modifier.padding(PaddingValues(end = 8F.dp)).weight(1F, fill = false),
            )
            Text(text = state.previewReason?.name ?: "N/A")
        }
        Button(onClick = dispatchRelease, modifier = Modifier.fillMaxWidth()) {
            Text(text = "RELEASE PLAYER")
        }
    }
}

private val CREDENTIAL_LEVEL =
    when {
        BuildConfig.TIDAL_CLIENT_SECRET.isNullOrBlank() -> Credentials.Level.USER
        else -> Credentials.Level.CLIENT
    }
