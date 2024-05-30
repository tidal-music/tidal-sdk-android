package com.tidal.sdk.player.mainactivity

import android.net.Uri
import com.tidal.sdk.player.Player
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.LoudnessNormalizationMode
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.playbackengine.player.CacheProvider
import kotlinx.coroutines.Job

internal sealed class MainActivityViewModelState private constructor() {

    abstract val snackbarMessage: String?

    data class AwaitingLoginFlowChoice(
        override val snackbarMessage: String?,
        val isUserLoggedIn: Boolean,
        val webLoginUri: Uri,
    ) : MainActivityViewModelState()

    data class LoggingIn(override val snackbarMessage: String?) : MainActivityViewModelState()

    sealed class PlayerReleasing private constructor() : MainActivityViewModelState() {

        abstract val eventCollectionJob: Job
        abstract val cacheProvider: CacheProvider

        data class FromRequest(
            override val snackbarMessage: String?,
            override val eventCollectionJob: Job,
            override val cacheProvider: CacheProvider,
        ) : PlayerReleasing()

        data class FromLogOut(
            override val snackbarMessage: String?,
            override val eventCollectionJob: Job,
            override val cacheProvider: CacheProvider,
        ) : PlayerReleasing()
    }

    data class PlayerNotInitialized(override val snackbarMessage: String?) :
        MainActivityViewModelState()

    data class PlayerInitializing(override val snackbarMessage: String?) :
        MainActivityViewModelState()

    /**
     * TODO The non-Player fields should instead be appropriately exposed from Player to enable a
     * single source of truth approach.
     */
    data class PlayerInitialized(
        override val snackbarMessage: String?,
        val player: Player,
        val eventCollectionJob: Job,
        val itemPositionPollingJob: Job,
        val cacheProvider: CacheProvider,
        val current: MediaProduct?,
        val next: MediaProduct?,
        val isRepeatOneEnabled: Boolean,
        val draggedPositionSeconds: Float?,
        val streamingAudioQualityWifi: AudioQuality,
        val streamingAudioQualityCellular: AudioQuality,
        val loudnessNormalizationMode: LoudnessNormalizationMode,
        val immersiveAudio: Boolean,
    ) : MainActivityViewModelState()
}
