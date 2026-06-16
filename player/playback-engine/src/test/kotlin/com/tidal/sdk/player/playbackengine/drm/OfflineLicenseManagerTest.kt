package com.tidal.sdk.player.playbackengine.drm

import android.util.Log
import androidx.media3.exoplayer.drm.DefaultDrmSessionManager
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.playbackengine.dash.DashManifestFactory
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class OfflineLicenseManagerTest {

    private lateinit var logMock: MockedStatic<Log>

    @BeforeEach
    fun setUp() {
        logMock = Mockito.mockStatic(Log::class.java)
    }

    @AfterEach
    fun tearDown() {
        logMock.close()
    }

    private val defaultDrmSessionManagerBuilder = mock<DefaultDrmSessionManager.Builder>()
    private val tidalMediaDrmCallbackFactory = mock<TidalMediaDrmCallbackFactory>()
    private val dashManifestFactory = mock<DashManifestFactory>()
    private val drmKeySetStore = mock<DrmKeySetStore>()
    private val trueTimeWrapper = mock<TrueTimeWrapper>()
    private val offlineLicenseManager =
        OfflineLicenseManager(
            defaultDrmSessionManagerBuilder,
            tidalMediaDrmCallbackFactory,
            dashManifestFactory,
            drmKeySetStore,
            trueTimeWrapper,
            CoroutineScope(Dispatchers.Unconfined),
        )

    @Test
    fun getValidKeySetIdReturnsNullWhenNothingStored() {
        val playbackInfo = mock<PlaybackInfo.Track> { on { manifestHash } doReturn "hash" }
        whenever(drmKeySetStore.get("hash")).thenReturn(null)

        assertThat(offlineLicenseManager.getValidKeySetId(playbackInfo)).isNull()
    }

    @Test
    fun getValidKeySetIdReturnsKeySetIdWhenNotExpired() {
        val playbackInfo = mock<PlaybackInfo.Track> { on { manifestHash } doReturn "hash" }
        val keySetId = byteArrayOf(9, 8, 7)
        whenever(trueTimeWrapper.currentTimeMillis).thenReturn(10_000L)
        whenever(drmKeySetStore.get("hash"))
            .thenReturn(
                DrmKeySetStore.Entry(
                    keySetId = keySetId,
                    acquiredAtMs = 0L,
                    licenseDurationSec = 3600L,
                )
            )

        assertThat(offlineLicenseManager.getValidKeySetId(playbackInfo)).isEqualTo(keySetId)
    }

    @Test
    fun getValidKeySetIdRemovesAndReturnsNullWhenExpired() {
        val playbackInfo = mock<PlaybackInfo.Track> { on { manifestHash } doReturn "hash" }
        whenever(trueTimeWrapper.currentTimeMillis).thenReturn(3_600_000L)
        whenever(drmKeySetStore.get("hash"))
            .thenReturn(
                DrmKeySetStore.Entry(
                    keySetId = byteArrayOf(1),
                    acquiredAtMs = 0L,
                    licenseDurationSec = 100L,
                )
            )

        assertThat(offlineLicenseManager.getValidKeySetId(playbackInfo)).isNull()
        verify(drmKeySetStore).remove("hash")
    }

    @Test
    fun getValidKeySetIdReturnsNullForBroadcastWithoutContentKey() {
        val playbackInfo = mock<PlaybackInfo.Broadcast>()

        assertThat(offlineLicenseManager.getValidKeySetId(playbackInfo)).isNull()
        verify(drmKeySetStore, never()).get(org.mockito.kotlin.any())
    }
}
