package com.tidal.sdk.player.playbackengine.drm

import androidx.media3.exoplayer.drm.DefaultDrmSessionManager
import androidx.media3.exoplayer.drm.DrmSessionManager
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isSameAs
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class DrmSessionManagerFactoryTest {

    private val tidalMediaDrmCallbackFactory = mock<TidalMediaDrmCallbackFactory>()
    private val defaultDrmSessionManagerBuilder = mock<DefaultDrmSessionManager.Builder>()
    private val drmSessionManagerFactory = DrmSessionManagerFactory(
        defaultDrmSessionManagerBuilder,
        tidalMediaDrmCallbackFactory,
    )

    @Test
    fun createDrmSessionManagerForOnlinePlayWithNullLicenseSecurityToken() {
        val actual = drmSessionManagerFactory.createDrmSessionManagerForOnlinePlay(
            mock<PlaybackInfo.Track>(),
        )

        assertThat(actual).isEqualTo(DrmSessionManager.DRM_UNSUPPORTED)
    }

    @Test
    fun createDrmSessionManagerForOnlinePlayWithEmptyLicenseSecurityToken() {
        val playbackInfo = mock<PlaybackInfo.Track> {
            on { licenseSecurityToken } doReturn ""
        }

        val actual = drmSessionManagerFactory.createDrmSessionManagerForOnlinePlay(playbackInfo)

        assertThat(actual).isEqualTo(DrmSessionManager.DRM_UNSUPPORTED)
    }

    @Test
    fun createDrmSessionManagerForOnlinePlayWithDrmModeStreaming() {
        val playbackInfo = mock<PlaybackInfo.Track> {
            on { licenseSecurityToken } doReturn "licenseSecurityToken"
        }
        val drmMode = DrmMode.Streaming
        val tidalMediaDrmCallback = mock<TidalMediaDrmCallback>()
        val defaultDrmSessionManager = mock<DefaultDrmSessionManager>()
        whenever(tidalMediaDrmCallbackFactory.create(playbackInfo, drmMode))
            .thenReturn(tidalMediaDrmCallback)
        whenever(defaultDrmSessionManagerBuilder.build(tidalMediaDrmCallback))
            .thenReturn(defaultDrmSessionManager)

        val actual = drmSessionManagerFactory.createDrmSessionManagerForOnlinePlay(playbackInfo)

        assertThat(actual).isSameAs(defaultDrmSessionManager)
    }

    @Test
    fun createDrmSessionManagerForOfflinePlayWithEmptyOfflineLicense() {
        val playbackInfo = mock<PlaybackInfo.Offline> {
            on { offlineLicense } doReturn ""
        }

        val actual = drmSessionManagerFactory.createDrmSessionManagerForOfflinePlay(playbackInfo)

        assertThat(actual).isEqualTo(DrmSessionManager.DRM_UNSUPPORTED)
    }

    @Test
    fun createDrmSessionManagerForOfflinePlayWithDrmModeStreaming() {
        val playbackInfo = mock<PlaybackInfo.Offline.Track> {
            on { offlineLicense } doReturn "offlineLicense"
        }
        val drmMode = DrmMode.Streaming
        val tidalMediaDrmCallback = mock<TidalMediaDrmCallback>()
        val defaultDrmSessionManager = mock<DefaultDrmSessionManager>()
        whenever(tidalMediaDrmCallbackFactory.create(playbackInfo.track, drmMode))
            .thenReturn(tidalMediaDrmCallback)
        whenever(defaultDrmSessionManagerBuilder.build(tidalMediaDrmCallback))
            .thenReturn(defaultDrmSessionManager)

        val actual = drmSessionManagerFactory.createDrmSessionManagerForOfflinePlay(playbackInfo)

        assertThat(actual).isSameAs(defaultDrmSessionManager)
    }
}
