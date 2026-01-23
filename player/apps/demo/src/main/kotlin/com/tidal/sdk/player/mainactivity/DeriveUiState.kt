package com.tidal.sdk.player.mainactivity

internal class DeriveUiState {

    operator fun invoke(mainActivityViewModelState: MainActivityViewModelState) =
        mainActivityViewModelState.run {
            when (this) {
                is MainActivityViewModelState.AwaitingLoginFlowChoice ->
                    MainActivityState.AwaitingLoginFlowChoice(
                        snackbarMessage,
                        isUserLoggedIn,
                        webLoginUri,
                    )

                is MainActivityViewModelState.LoggingIn,
                is MainActivityViewModelState.PlayerInitializing,
                is MainActivityViewModelState.PlayerReleasing ->
                    MainActivityState.Loading(snackbarMessage)

                is MainActivityViewModelState.PlayerNotInitialized ->
                    MainActivityState.PlayerNotInitialized(snackbarMessage)

                is MainActivityViewModelState.PlayerInitialized ->
                    MainActivityState.PlayerInitialized(
                        snackbarMessage,
                        streamingAudioQualityWifi,
                        streamingAudioQualityCellular,
                        loudnessNormalizationMode,
                        immersiveAudio,
                        enableAdaptive,
                        current,
                        next,
                        player.playbackEngine.playbackState,
                        player.playbackEngine.outputDevice,
                        isRepeatOneEnabled,
                        player.configuration.isOfflineMode,
                        player.playbackEngine.playbackContext?.duration,
                        draggedPositionSeconds ?: player.playbackEngine.assetPosition,
                    )
            }
        }
}
