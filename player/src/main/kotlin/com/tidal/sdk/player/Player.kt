package com.tidal.sdk.player

import android.app.Application
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.eventproducer.EventSender
import com.tidal.sdk.player.common.Configuration
import com.tidal.sdk.player.common.ConfigurationListener
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.di.DaggerPlayerComponent
import com.tidal.sdk.player.offlineplay.OfflinePlayProvider
import com.tidal.sdk.player.playbackengine.model.AssetTimeoutConfig
import com.tidal.sdk.player.playbackengine.model.BufferConfiguration
import com.tidal.sdk.player.playbackengine.playbackprivilege.PlaybackPrivilege
import com.tidal.sdk.player.playbackengine.playbackprivilege.PlaybackPrivilegeProvider
import com.tidal.sdk.player.playbackengine.player.CacheProvider
import com.tidal.sdk.player.streamingapi.StreamingApiTimeoutConfig
import okhttp3.OkHttpClient

/**
 * This is the Player that will be used by apps.
 *
 * @param application An [Application] to use.
 * @param[credentialsProvider] A [CredentialsProvider] from the Auth SDK.
 * @param[eventSender] An [EventSender] from the EventProducer SDK.
 * @param userClientIdSupplier A function that supplies a userClientId used for event tracking. This
 *   value needs to be taken from the session. Internal use only.
 * @param bufferConfiguration The parameters to configure different values for player buffer
 *   functionalities.
 * @param assetTimeoutConfig The parameters to configure different values for player asset timeouts.
 * @param[cacheProvider] A [CacheProvider] that decides if Player should use internal or external
 *   cache.
 * @param isOfflineMode Sets initial offline mode property.
 * @param[isDebuggable] A [Boolean] that describes if this instance is debuggable, or not.
 * @param[okHttpClient] An [OkHttpClient] that internal logic will use as a reference to build upon.
 *   Use this if you want to share your application's [OkHttpClient] with us, which is recommended
 *   by [OkHttpClient].
 * @param[playbackPrivilegeProvider] An implementation to differentiate the way in which items can
 *   be streamed. Internal use only.
 * @param[offlinePlayProvider] A means of supporting offline streaming when appropriate. Internal
 *   use only.
 * @param version The version of the app, used for event tracking. Defaults to 1.0.0.
 */
@Suppress("LongParameterList")
class Player(
    application: Application,
    credentialsProvider: CredentialsProvider,
    eventSender: EventSender,
    useLibflacAudioRenderer: Boolean = true,
    userClientIdSupplier: (() -> Int)? = null,
    bufferConfiguration: BufferConfiguration = BufferConfiguration(),
    assetTimeoutConfig: AssetTimeoutConfig = AssetTimeoutConfig(),
    streamingApiTimeoutConfig: StreamingApiTimeoutConfig = StreamingApiTimeoutConfig(),
    cacheProvider: CacheProvider = CacheProvider.Internal(),
    isOfflineMode: Boolean = false,
    isDebuggable: Boolean = false,
    okHttpClient: OkHttpClient = OkHttpClient(),
    playbackPrivilegeProvider: PlaybackPrivilegeProvider =
        object : PlaybackPrivilegeProvider {
            override fun get(mediaProduct: MediaProduct) = PlaybackPrivilege.OK_ONLINE
        },
    offlinePlayProvider: OfflinePlayProvider? = null,
    version: String = "1.0.0",
) : ConfigurationListener {
    private val playerComponent =
        DaggerPlayerComponent.factory()
            .create(
                application,
                credentialsProvider,
                eventSender,
                useLibflacAudioRenderer,
                userClientIdSupplier,
                version,
                bufferConfiguration,
                assetTimeoutConfig,
                streamingApiTimeoutConfig,
                cacheProvider,
                isOfflineMode,
                okHttpClient,
                isDebuggable,
                playbackPrivilegeProvider,
                offlinePlayProvider,
            )
    val configuration = playerComponent.configuration
    val playbackEngine = playerComponent.playbackEngine
    private val streamingPrivileges = playerComponent.streamingPrivileges

    init {
        configuration.addListener(this)
    }

    /**
     * Releases any remaining resources held exclusively by this Player instance and stops any
     * running threads that are specific to it. This instance must not be used after calling this
     * method; instead, create any number of new instances if you need one again later on.
     */
    fun release() {
        configuration.removeListener(this)
        streamingPrivileges.release()
        playbackEngine.release()
    }

    override fun onConfigurationChanged(configuration: Configuration) {
        streamingPrivileges.setKeepAlive(!configuration.isOfflineMode)
    }
}
