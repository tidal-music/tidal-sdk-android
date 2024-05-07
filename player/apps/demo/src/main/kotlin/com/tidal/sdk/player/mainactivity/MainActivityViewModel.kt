package com.tidal.sdk.player.mainactivity

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.auth.TidalAuth
import com.tidal.sdk.auth.model.AuthConfig
import com.tidal.sdk.auth.model.CredentialsUpdatedMessage
import com.tidal.sdk.auth.model.LoginConfig
import com.tidal.sdk.auth.model.QueryParameter
import com.tidal.sdk.eventproducer.EventProducer
import com.tidal.sdk.eventproducer.model.EventsConfig
import com.tidal.sdk.player.BuildConfig
import com.tidal.sdk.player.Player
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.LoudnessNormalizationMode
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.mainactivity.MainActivityViewModelState.AwaitingLoginFlowChoice
import com.tidal.sdk.player.mainactivity.MainActivityViewModelState.LoggingIn
import com.tidal.sdk.player.mainactivity.MainActivityViewModelState.PlayerInitialized
import com.tidal.sdk.player.mainactivity.MainActivityViewModelState.PlayerInitializing
import com.tidal.sdk.player.mainactivity.MainActivityViewModelState.PlayerNotInitialized
import com.tidal.sdk.player.mainactivity.MainActivityViewModelState.PlayerReleasing
import com.tidal.sdk.player.playbackengine.PlaybackEngine
import com.tidal.sdk.player.playbackengine.model.ByteAmount.Companion.megabytes
import com.tidal.sdk.player.playbackengine.model.Event
import com.tidal.sdk.player.playbackengine.player.CacheProvider
import com.tidal.sdk.player.playbackengine.view.AspectRatioAdjustingSurfaceView
import java.io.File
import java.net.URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@UnstableApi
@Suppress("MagicNumber")
internal class MainActivityViewModel(context: Context) : ViewModel() {

    private val tidalAuth = TidalAuth.getInstance(
        AuthConfig(
            clientId = BuildConfig.TIDAL_CLIENT_ID,
            clientSecret = BuildConfig.TIDAL_CLIENT_SECRET,
            credentialsKey = "com.tidal.sdk.player",
            enableCertificatePinning = false,
        ),
        context.applicationContext,
    )
    val credentialsProvider by tidalAuth::credentialsProvider
    val uiState by lazy { _uiState.asStateFlow() }
    private val deriveUiState = DeriveUiState()
    private val _uiState by lazy {
        MutableStateFlow(deriveUiState(state))
    }
    private val authLoginUri: Uri
        get() = tidalAuth.auth.initializeLogin(
            LOGIN_URI,
            LoginConfig(
                customParams = setOf(
                    QueryParameter(
                        key = "appMode",
                        value = "android",
                    ),
                ),
            ),
        )
    private var state: MainActivityViewModelState = AwaitingLoginFlowChoice(
        null,
        credentialsProvider.isUserLoggedIn(),
        authLoginUri,
    )
        set(value) {
            synchronized(this@MainActivityViewModel) {
                if (field === value) {
                    return
                }
                field = value
                _uiState.update { deriveUiState(state) }
            }
        }

    init {
        viewModelScope.launch {
            credentialsProvider.bus.collect {
                Log.d(MainActivityViewModel::class.java.name, it.toString())
                if (it !is CredentialsUpdatedMessage) return@collect
                if (it.credentials == null) {
                    dispatch(
                        Operation.Impure.LogOut(
                            this@MainActivityViewModel,
                            credentialsProvider,
                        ),
                    )
                } else {
                    state = state.castAndCopy()
                }
            }
        }
    }

    fun <T : MainActivityViewModelState> dispatch(
        operation: Operation<T, MainActivityViewModelState>,
    ) {
        viewModelScope.launch(Dispatchers.IO) { state = operation(state as T) }
    }

    sealed class Operation<
        T : MainActivityViewModelState,
        out V : MainActivityViewModelState,
        > private constructor() {

        abstract suspend operator fun invoke(state: T): V

        sealed class Pure<T : MainActivityViewModelState> : Operation<T, T>() {

            override suspend operator fun invoke(state: T): T {
                invokePure(state)
                return state
            }

            abstract suspend fun invokePure(state: T)

            sealed class Seek : Pure<PlayerInitialized>() {

                override suspend fun invokePure(state: PlayerInitialized) {
                    state.player.playbackEngine.apply {
                        seek(targetPositionMs(this))
                    }
                }

                abstract fun targetPositionMs(playbackEngine: PlaybackEngine): Float

                data object Rewind : Seek() {

                    override fun targetPositionMs(playbackEngine: PlaybackEngine) =
                        (playbackEngine.assetPosition - 10) * 1_000
                }

                data object FastForward : Seek() {

                    override fun targetPositionMs(playbackEngine: PlaybackEngine) =
                        (playbackEngine.assetPosition + 10) * 1_000
                }

                data object SeekToNearEnd : Seek() {

                    override fun targetPositionMs(playbackEngine: PlaybackEngine) =
                        (playbackEngine.playbackContext!!.duration - 10) * 1_000
                }
            }

            data object Play : Pure<PlayerInitialized>() {

                override suspend fun invokePure(state: PlayerInitialized) {
                    state.player.playbackEngine.play()
                }
            }

            data object Pause : Pure<PlayerInitialized>() {

                override suspend fun invokePure(state: PlayerInitialized) {
                    state.player.playbackEngine.pause()
                }
            }

            data object Skip : Pure<PlayerInitialized>() {

                override suspend fun invokePure(state: PlayerInitialized) {
                    state.player.playbackEngine.skipToNext()
                }
            }
        }

        sealed class Impure<T : MainActivityViewModelState, V : MainActivityViewModelState> :
            Operation<T, V>() {

            sealed class CreatePlayer(
                private val context: Context,
                private val mainActivityViewModel: MainActivityViewModel,
            ) : Impure<MainActivityViewModelState, PlayerInitializing>() {

                abstract val cacheProviderLazy: Lazy<CacheProvider>
                abstract val isOfflineMode: Boolean

                @Suppress("LongMethod", "MaxLineLength")
                override suspend operator fun invoke(state: MainActivityViewModelState): PlayerInitializing { // ktlint-disable max-line-length
                    mainActivityViewModel.viewModelScope
                        .launch(Dispatchers.IO) {
                            val player = Player(
                                context.applicationContext as Application,
                                mainActivityViewModel.credentialsProvider,
                                EventProducer.getInstance(
                                    URL("https://event-collector.obelix-staging-use1.tidalhi.fi/"),
                                    mainActivityViewModel.credentialsProvider,
                                    EventsConfig(
                                        Int.MAX_VALUE,
                                        emptySet(),
                                        "player-sample-${BuildConfig.VERSION_NAME}",
                                    ),
                                    context,
                                    CoroutineScope(Dispatchers.IO),
                                ).eventSender,
                                userClientIdSupplier = { 1 },
                                version = "player-sample-${BuildConfig.VERSION_NAME}",
                                isOfflineMode = isOfflineMode,
                                isDebuggable = BuildConfig.DEBUG,
                                cacheProvider = cacheProviderLazy.value,
                            )

                            mainActivityViewModel.state = PlayerInitialized(
                                player = player,
                                eventCollectionJob = mainActivityViewModel.viewModelScope.launch {
                                    player.playbackEngine
                                        .events
                                        .collect(
                                            PlaybackEngineEventCollector(
                                                mainActivityViewModel,
                                            ),
                                        )
                                },
                                itemPositionPollingJob = mainActivityViewModel.viewModelScope
                                    .launch {
                                        while (true) {
                                            mainActivityViewModel.state =
                                                mainActivityViewModel.state.castAndCopy()
                                            delay(200)
                                        }
                                    },
                                cacheProvider = cacheProviderLazy.value,
                                current = player.playbackEngine.mediaProduct,
                                next = null,
                                isRepeatOneEnabled = false,
                                snackbarMessage = null,
                                draggedPositionSeconds = null,
                                streamingAudioQualityCellular =
                                player.playbackEngine.streamingCellularAudioQuality,
                                streamingAudioQualityWifi =
                                player.playbackEngine.streamingWifiAudioQuality,
                                loudnessNormalizationMode = player.playbackEngine
                                    .loudnessNormalizationMode,
                            )
                        }

                    return PlayerInitializing(state.snackbarMessage)
                }

                class WithInternalCache(
                    context: Context,
                    mainActivityViewModel: MainActivityViewModel,
                    override val isOfflineMode: Boolean,
                ) : CreatePlayer(context, mainActivityViewModel) {

                    override val cacheProviderLazy = lazyOf(CacheProvider.Internal())
                }

                class WithExternalCache(
                    context: Context,
                    mainActivityViewModel: MainActivityViewModel,
                    override val isOfflineMode: Boolean,
                ) : CreatePlayer(context, mainActivityViewModel) {

                    override val cacheProviderLazy = lazy {
                        val cacheDir = File(context.cacheDir, CACHE_DIR)
                        val cacheEvictor = LeastRecentlyUsedCacheEvictor(CACHE_MAX_SIZE_BYTES)
                        val simpleCache =
                            SimpleCache(cacheDir, cacheEvictor, StandaloneDatabaseProvider(context))
                        CacheProvider.External(simpleCache)
                    }
                }

                companion object {
                    private const val CACHE_DIR = "exoplayer-cache"
                    private val CACHE_MAX_SIZE_BYTES = 50.megabytes.value
                }
            }

            class Load(private val mediaProduct: MediaProduct) :
                Impure<PlayerInitialized, PlayerInitialized>() {

                override suspend operator fun invoke(state: PlayerInitialized): PlayerInitialized {
                    state.player.playbackEngine.load(mediaProduct)
                    return state.copy(current = mediaProduct)
                }
            }

            data object Reset : Impure<PlayerInitialized, PlayerInitialized>() {

                override suspend operator fun invoke(state: PlayerInitialized): PlayerInitialized {
                    state.player.playbackEngine.reset()
                    return state.copy(
                        current = null,
                        next = null,
                        isRepeatOneEnabled = false,
                    )
                }
            }

            class SetRepeatOne(private val repeatOne: Boolean) :
                Impure<PlayerInitialized, PlayerInitialized>() {

                override suspend operator fun invoke(state: PlayerInitialized): PlayerInitialized {
                    state.player.playbackEngine.setRepeatOne(repeatOne)
                    return state.copy(isRepeatOneEnabled = repeatOne)
                }
            }

            class SetOfflineMode(private val isOfflineMode: Boolean) :
                Impure<PlayerInitialized, PlayerInitialized>() {

                override suspend operator fun invoke(state: PlayerInitialized): PlayerInitialized {
                    state.player.configuration.isOfflineMode = isOfflineMode
                    return state.copy()
                }
            }

            class SetSnackbarMessage(private val snackbarMessage: String?) :
                Impure<MainActivityViewModelState, MainActivityViewModelState>() {

                override suspend operator fun invoke(state: MainActivityViewModelState) =
                    state.castAndCopy(snackbarMessage = snackbarMessage)
            }

            class SetDraggedPosition(private val positionSeconds: Float?) :
                Impure<PlayerInitialized, PlayerInitialized>() {

                override suspend operator fun invoke(state: PlayerInitialized): PlayerInitialized {
                    if (state.draggedPositionSeconds != null && positionSeconds != null) {
                        state.player.playbackEngine.seek(positionSeconds * 1_000)
                    }
                    return state.copy(draggedPositionSeconds = positionSeconds)
                }
            }

            class SetAudioQualityOnWifi(private val audioQuality: AudioQuality) :
                Impure<PlayerInitialized, PlayerInitialized>() {

                override suspend operator fun invoke(state: PlayerInitialized): PlayerInitialized {
                    state.player.playbackEngine.streamingWifiAudioQuality = audioQuality
                    return state.copy(streamingAudioQualityWifi = audioQuality)
                }
            }

            class SetAudioQualityOnCell(private val audioQuality: AudioQuality) :
                Impure<PlayerInitialized, PlayerInitialized>() {

                override suspend operator fun invoke(state: PlayerInitialized): PlayerInitialized {
                    state.player.playbackEngine.streamingCellularAudioQuality = audioQuality
                    return state.copy(streamingAudioQualityCellular = audioQuality)
                }
            }

            class SetLoudnessNormalizationMode(
                private val loudnessNormalizationMode: LoudnessNormalizationMode,
            ) : Impure<PlayerInitialized, PlayerInitialized>() {

                override suspend operator fun invoke(state: PlayerInitialized): PlayerInitialized {
                    state.player.playbackEngine.loudnessNormalizationMode =
                        loudnessNormalizationMode
                    return state.copy(loudnessNormalizationMode = loudnessNormalizationMode)
                }
            }

            class SetNext(private val mediaProduct: MediaProduct?) :
                Impure<PlayerInitialized, PlayerInitialized>() {

                override suspend operator fun invoke(state: PlayerInitialized): PlayerInitialized {
                    state.player.playbackEngine.setNext(mediaProduct)
                    return state.copy(next = mediaProduct)
                }
            }

            class SetVideoSurfaceView(
                private val aspectRatioAdjustingSurfaceView: AspectRatioAdjustingSurfaceView?,
            ) : Impure<PlayerInitialized, PlayerInitialized>() {

                override suspend operator fun invoke(state: PlayerInitialized): PlayerInitialized {
                    state.player.playbackEngine.videoSurfaceView = aspectRatioAdjustingSurfaceView
                    return state.copy()
                }
            }

            data object Release : Impure<PlayerInitialized, PlayerReleasing.FromRequest>() {

                override suspend operator fun invoke(state: PlayerInitialized) = state.run {
                    itemPositionPollingJob.cancel()
                    player.release()
                    PlayerReleasing.FromRequest(null, eventCollectionJob, cacheProvider)
                }
            }

            sealed class FinalizeLoginFlow(
                private val context: Context,
                private val mainActivityViewModel: MainActivityViewModel,
            ) : Impure<AwaitingLoginFlowChoice, LoggingIn>() {

                override suspend fun invoke(state: AwaitingLoginFlowChoice): LoggingIn {
                    mainActivityViewModel.viewModelScope.launch(Dispatchers.IO) {
                        finalize()
                        mainActivityViewModel.state =
                            CreatePlayer.WithInternalCache(context, mainActivityViewModel, false)(
                                mainActivityViewModel.state,
                            )
                    }
                    return LoggingIn(state.snackbarMessage)
                }

                abstract suspend fun finalize()

                class Web(
                    context: Context,
                    private val mainActivityViewModel: MainActivityViewModel,
                    private val loginResponseUri: Uri,
                ) : FinalizeLoginFlow(context, mainActivityViewModel) {
                    override suspend fun finalize() {
                        mainActivityViewModel.tidalAuth.auth.finalizeLogin(
                            loginResponseUri.toString()
                        )
                    }
                }

                class Implicit(
                    context: Context,
                    private val mainActivityViewModel: MainActivityViewModel,
                ) : FinalizeLoginFlow(context, mainActivityViewModel) {
                    override suspend fun finalize() {
                        mainActivityViewModel.credentialsProvider.getCredentials(null)
                    }
                }
            }

            class LogOut(
                private val viewModel: MainActivityViewModel,
                private val credentialsProvider: CredentialsProvider,
            ) : Impure<MainActivityViewModelState, MainActivityViewModelState>() {

                @Suppress("MaxLineLength")
                override suspend fun invoke(state: MainActivityViewModelState): MainActivityViewModelState { // ktlint-disable max-line-length
                    return if (state is PlayerInitialized) {
                        Release(state).run {
                            PlayerReleasing.FromLogOut(
                                snackbarMessage,
                                eventCollectionJob,
                                cacheProvider,
                            )
                        }
                    } else {
                        AwaitingLoginFlowChoice(
                            state.snackbarMessage,
                            credentialsProvider.isUserLoggedIn(),
                            viewModel.authLoginUri,
                        )
                    }
                }
            }
        }
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            check(modelClass === MainActivityViewModel::class.java)
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(context) as T
        }
    }

    class PlaybackEngineEventCollector(private val mainActivityViewModel: MainActivityViewModel) :
        FlowCollector<Event> {

        override suspend fun emit(value: Event) {
            when (value) {
                is Event.MediaProductTransition -> {
                    val state = mainActivityViewModel.state as PlayerInitialized
                    if (value.mediaProduct.referenceId != state.current?.referenceId) {
                        mainActivityViewModel.state = state.copy(
                            current = value.mediaProduct,
                            next = null,
                        )
                    }
                }

                is Event.MediaProductEnded -> {
                    val state = mainActivityViewModel.state as PlayerInitialized
                    mainActivityViewModel.state = state.copy(current = null)
                }

                is Event.Release -> {
                    val state = mainActivityViewModel.state as PlayerReleasing
                    state.eventCollectionJob.cancel()
                    (state.cacheProvider as? CacheProvider.External)?.cache?.release()
                    mainActivityViewModel.state = when (state) {
                        is PlayerReleasing.FromRequest ->
                            PlayerNotInitialized(state.snackbarMessage)

                        is PlayerReleasing.FromLogOut -> AwaitingLoginFlowChoice(
                            state.snackbarMessage,
                            mainActivityViewModel.credentialsProvider.isUserLoggedIn(),
                            mainActivityViewModel.authLoginUri,
                        )
                    }
                }

                is Event.Error -> {
                    val state = mainActivityViewModel.state as PlayerInitialized
                    mainActivityViewModel.state = state.copy(
                        snackbarMessage =
                        "${value.javaClass.simpleName}(${value::errorCode.name}=" +
                            "${value.errorCode})",
                    )
                }

                is Event.StreamingPrivilegesRevoked,
                is Event.DjSessionUpdate,
                -> {
                    val state = mainActivityViewModel.state as PlayerInitialized
                    mainActivityViewModel.state = state.copy(snackbarMessage = value.toString())
                }

                else ->
                    mainActivityViewModel.state = (mainActivityViewModel.state as PlayerInitialized)
                        .copy()
            }
        }
    }

    companion object {
        const val LOGIN_URI = "https://tidal.com/android/login/auth"
    }
}

private fun <T : MainActivityViewModelState> T.castAndCopy(
    snackbarMessage: String? = this.snackbarMessage,
) = (this as MainActivityViewModelState).run {
    when (this) {
        is AwaitingLoginFlowChoice -> copy(snackbarMessage = snackbarMessage)
        is LoggingIn -> copy(snackbarMessage = snackbarMessage)
        is PlayerReleasing.FromRequest -> copy(snackbarMessage = snackbarMessage)
        is PlayerReleasing.FromLogOut -> copy(snackbarMessage = snackbarMessage)
        is PlayerNotInitialized -> copy(snackbarMessage = snackbarMessage)
        is PlayerInitialized -> copy(snackbarMessage = snackbarMessage)
        is PlayerInitializing -> copy(snackbarMessage = snackbarMessage)
    } as T
}
