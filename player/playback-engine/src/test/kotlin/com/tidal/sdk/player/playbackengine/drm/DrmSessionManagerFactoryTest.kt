package com.tidal.sdk.player.playbackengine.drm

import androidx.media3.exoplayer.drm.DefaultDrmSessionManager
import androidx.media3.exoplayer.drm.DrmSessionManager
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isSameInstanceAs
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class DrmSessionManagerFactoryTest {

    private val tidalMediaDrmCallbackFactory = mock<TidalMediaDrmCallbackFactory>()
    private val defaultDrmSessionManagerBuilder = mock<DefaultDrmSessionManager.Builder>()
    private val offlineLicenseManager = mock<OfflineLicenseManager>()
    private val drmSessionManagerFactory =
        DrmSessionManagerFactory(
            defaultDrmSessionManagerBuilder,
            tidalMediaDrmCallbackFactory,
            offlineLicenseManager,
        )

    @Test
    fun createDrmSessionManagerForOnlinePlayWithNullLicenseSecurityToken() {
        val actual =
            drmSessionManagerFactory.createDrmSessionManagerForOnlinePlay(
                mock<PlaybackInfo.Track>(),
                emptyMap(),
            )

        assertThat(actual).isEqualTo(DrmSessionManager.DRM_UNSUPPORTED)
    }

    @Test
    fun createDrmSessionManagerForOnlinePlayWithEmptyLicenseUrl() {
        val playbackInfo = mock<PlaybackInfo.Track> { on { licenseUrl } doReturn "" }

        val actual =
            drmSessionManagerFactory.createDrmSessionManagerForOnlinePlay(playbackInfo, emptyMap())

        assertThat(actual).isEqualTo(DrmSessionManager.DRM_UNSUPPORTED)
    }

    @Test
    fun createDrmSessionManagerForOnlinePlayWithDrmModeStreaming() {
        val playbackInfo = mock<PlaybackInfo.Track> { on { licenseUrl } doReturn "licenseUrl" }
        val drmMode = DrmMode.Streaming
        val tidalMediaDrmCallback = mock<TidalMediaDrmCallback>()
        val defaultDrmSessionManager = mock<DefaultDrmSessionManager>()
        whenever(
                tidalMediaDrmCallbackFactory.create(
                    playbackInfo.licenseUrl!!,
                    playbackInfo.streamingSessionId,
                    drmMode,
                    emptyMap(),
                )
            )
            .thenReturn(tidalMediaDrmCallback)
        whenever(defaultDrmSessionManagerBuilder.build(tidalMediaDrmCallback))
            .thenReturn(defaultDrmSessionManager)

        val actual =
            drmSessionManagerFactory.createDrmSessionManagerForOnlinePlay(playbackInfo, emptyMap())

        assertThat(actual).isSameInstanceAs(defaultDrmSessionManager)
    }

    @Test
    fun createDrmSessionManagerForOnlinePlayWithoutCachedLicenseTriggersLazyAcquisition() {
        val playbackInfo = mock<PlaybackInfo.Track> { on { licenseUrl } doReturn "licenseUrl" }
        val tidalMediaDrmCallback = mock<TidalMediaDrmCallback>()
        val defaultDrmSessionManager = mock<DefaultDrmSessionManager>()
        whenever(
                tidalMediaDrmCallbackFactory.create(
                    playbackInfo.licenseUrl!!,
                    playbackInfo.streamingSessionId,
                    DrmMode.Streaming,
                    emptyMap(),
                )
            )
            .thenReturn(tidalMediaDrmCallback)
        whenever(defaultDrmSessionManagerBuilder.build(tidalMediaDrmCallback))
            .thenReturn(defaultDrmSessionManager)
        whenever(offlineLicenseManager.getValidKeySetId(playbackInfo)).thenReturn(null)

        drmSessionManagerFactory.createDrmSessionManagerForOnlinePlay(playbackInfo, emptyMap())

        verify(offlineLicenseManager).acquireAndStoreAsync(playbackInfo, emptyMap())
        verify(defaultDrmSessionManager, never()).setMode(any(), any())
    }

    @Test
    fun createDrmSessionManagerForOnlinePlayWithCachedLicenseSetsPlaybackMode() {
        val playbackInfo = mock<PlaybackInfo.Track> { on { licenseUrl } doReturn "licenseUrl" }
        val tidalMediaDrmCallback = mock<TidalMediaDrmCallback>()
        val defaultDrmSessionManager = mock<DefaultDrmSessionManager>()
        val cachedKeySetId = byteArrayOf(1, 2, 3)
        whenever(
                tidalMediaDrmCallbackFactory.create(
                    playbackInfo.licenseUrl!!,
                    playbackInfo.streamingSessionId,
                    DrmMode.Streaming,
                    emptyMap(),
                )
            )
            .thenReturn(tidalMediaDrmCallback)
        whenever(defaultDrmSessionManagerBuilder.build(tidalMediaDrmCallback))
            .thenReturn(defaultDrmSessionManager)
        whenever(offlineLicenseManager.getValidKeySetId(playbackInfo)).thenReturn(cachedKeySetId)

        drmSessionManagerFactory.createDrmSessionManagerForOnlinePlay(playbackInfo, emptyMap())

        verify(defaultDrmSessionManager)
            .setMode(eq(DefaultDrmSessionManager.MODE_PLAYBACK), eq(cachedKeySetId))
        verify(offlineLicenseManager, never()).acquireAndStoreAsync(any(), any())
    }

    @Test
    fun createDrmSessionManagerForOfflinePlayWithEmptyOfflineLicense() {
        val playbackInfo = mock<PlaybackInfo.Offline> { on { offlineLicense } doReturn "" }

        val actual =
            drmSessionManagerFactory.createDrmSessionManagerForOfflinePlay(playbackInfo, emptyMap())

        assertThat(actual).isEqualTo(DrmSessionManager.DRM_UNSUPPORTED)
    }

    @Test
    fun createDrmSessionManagerForOfflinePlayWithDrmModeStreaming() {
        val trackPlaybackInfo =
            mock<PlaybackInfo.Track> { on { streamingSessionId } doReturn "streamingSessionId" }
        val playbackInfo =
            mock<PlaybackInfo.Offline.Track> {
                on { offlineLicense } doReturn "offlineLicense"
                on { track } doReturn trackPlaybackInfo
            }
        val drmMode = DrmMode.Streaming
        val tidalMediaDrmCallback = mock<TidalMediaDrmCallback>()
        val defaultDrmSessionManager = mock<DefaultDrmSessionManager>()
        whenever(
                tidalMediaDrmCallbackFactory.create(
                    "",
                    trackPlaybackInfo.streamingSessionId,
                    drmMode,
                    emptyMap(),
                )
            )
            .thenReturn(tidalMediaDrmCallback)
        whenever(defaultDrmSessionManagerBuilder.build(tidalMediaDrmCallback))
            .thenReturn(defaultDrmSessionManager)

        val actual =
            drmSessionManagerFactory.createDrmSessionManagerForOfflinePlay(playbackInfo, emptyMap())

        assertThat(actual).isSameInstanceAs(defaultDrmSessionManager)
    }
}
