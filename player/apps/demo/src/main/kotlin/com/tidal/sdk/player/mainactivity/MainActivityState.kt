package com.tidal.sdk.player.mainactivity

import android.net.Uri
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.LoudnessNormalizationMode
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.playbackengine.model.PlaybackState
import com.tidal.sdk.player.playbackengine.outputdevice.OutputDevice

internal sealed class MainActivityState private constructor() {

    abstract val snackbarMessage: String?

    data class AwaitingLoginFlowChoice(
        override val snackbarMessage: String? = null,
        val isUserLoggedIn: Boolean,
        val webLoginUri: Uri,
    ) : MainActivityState()

    data class Loading(override val snackbarMessage: String?) : MainActivityState()

    data class PlayerNotInitialized(override val snackbarMessage: String?) : MainActivityState()

    data class PlayerInitialized(
        override val snackbarMessage: String?,
        val streamingAudioQualityOnWifi: AudioQuality,
        val streamingAudioQualityOnCell: AudioQuality,
        val loudnessNormalizationMode: LoudnessNormalizationMode,
        val immersiveAudio: Boolean,
        val currentMediaProduct: MediaProduct?,
        val nextMediaProduct: MediaProduct?,
        val playbackState: PlaybackState,
        val outputDevice: OutputDevice,
        val isRepeatOneEnabled: Boolean,
        val isOfflineModeEnabled: Boolean,
        val durationSeconds: Float?,
        val positionSeconds: Float,
    ) : MainActivityState()
}
