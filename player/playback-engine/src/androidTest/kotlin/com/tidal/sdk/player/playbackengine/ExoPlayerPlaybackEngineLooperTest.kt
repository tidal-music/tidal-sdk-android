package com.tidal.sdk.player.playbackengine

import androidx.test.platform.app.InstrumentationRegistry
import assertk.assertThat
import assertk.assertions.isSameAs
import com.google.gson.Gson
import com.tidal.sdk.player.common.Configuration
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.commonandroid.Base64Codec
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.playbackengine.model.AssetTimeoutConfig
import com.tidal.sdk.player.playbackengine.model.BufferConfiguration
import com.tidal.sdk.player.playbackengine.player.CacheProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import okhttp3.OkHttpClient
import org.junit.Test
import org.mockito.kotlin.mock

internal class ExoPlayerPlaybackEngineLooperTest {

    private val singleHandlerPlaybackEngine =
        PlaybackEngineModuleRoot(
                InstrumentationRegistry.getInstrumentation().targetContext,
                mock(),
                true,
                MutableSharedFlow(),
                BufferConfiguration(),
                AssetTimeoutConfig(),
                CacheProvider.Internal(),
                Configuration(false),
                InstrumentationRegistry.getInstrumentation().targetContext.cacheDir,
                mock(),
                OkHttpClient(),
                OkHttpClient(),
                Gson(),
                mock(),
                mock(),
                UUIDWrapper(),
                TrueTimeWrapper(),
                mock(),
                mock(),
                mock(),
                Base64Codec(),
                Dispatchers.IO,
                CoroutineScope(Dispatchers.IO),
            )
            .playbackEngine as SingleHandlerPlaybackEngine

    private val exoPlayerPlaybackEngine =
        singleHandlerPlaybackEngine.reflectionDelegate as ExoPlayerPlaybackEngine

    @Test
    fun applicationLooperIsSameAsExoPlayerPlaybackEngineLooper() {
        val extendedExoPlayer = exoPlayerPlaybackEngine.reflectionExtendedExoPlayer
        val handler = exoPlayerPlaybackEngine.reflectionInternalHandler

        assertThat(extendedExoPlayer.applicationLooper).isSameAs(handler.looper)
    }
}
