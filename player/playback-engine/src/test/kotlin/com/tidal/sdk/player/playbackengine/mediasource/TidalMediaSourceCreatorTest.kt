package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.drm.DrmSessionManager
import androidx.media3.exoplayer.drm.DrmSessionManagerProvider
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.sdk.player.playbackengine.drm.DrmSessionManagerFactory
import com.tidal.sdk.player.playbackengine.drm.DrmSessionManagerProviderFactory
import com.tidal.sdk.player.streamingapi.offline.Storage
import com.tidal.sdk.player.streamingapi.playbackinfo.model.ManifestMimeType
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class TidalMediaSourceCreatorTest {

    private val playerProgressiveMediaSourceFactory = mock<PlayerProgressiveMediaSourceFactory>()
    private val playerDashMediaSourceFactory = mock<PlayerDashMediaSourceFactory>()
    private val playerHlsMediaSourceFactory = mock<PlayerHlsMediaSourceFactory>()
    private val playerDecryptedHeaderProgressiveOfflineMediaSourceFactory =
        mock<PlayerDecryptedHeaderProgressiveOfflineMediaSourceFactory>()
    private val playerProgressiveOfflineMediaSourceFactory =
        mock<PlayerProgressiveOfflineMediaSourceFactory>()
    private val playerDashOfflineMediaSourceFactory = mock<PlayerDashOfflineMediaSourceFactory>()
    private val drmSessionManagerFactory = mock<DrmSessionManagerFactory>()
    private val drmSessionManagerProviderFactory = mock<DrmSessionManagerProviderFactory>()
    private val tidalMediaSourceCreator = TidalMediaSourceCreator(
        playerProgressiveMediaSourceFactory,
        playerDashMediaSourceFactory,
        playerHlsMediaSourceFactory,
        playerDecryptedHeaderProgressiveOfflineMediaSourceFactory,
        playerProgressiveOfflineMediaSourceFactory,
        playerDashOfflineMediaSourceFactory,
        drmSessionManagerFactory,
        drmSessionManagerProviderFactory,
    )

    @Test
    fun invokeWithManifestMimeTypeDash() {
        val mediaItem = mock<MediaItem>()
        val playbackInfo = mock<PlaybackInfo.Track> {
            on { manifest } doReturn "dashManifest"
            on { manifestMimeType } doReturn ManifestMimeType.DASH
        }
        val expectedDashMediaSource = mock<DashMediaSource>()
        val drmSessionManager = mock<DrmSessionManager>()
        val drmSessionManagerProvider = mock<DrmSessionManagerProvider>()
        whenever(drmSessionManagerFactory.createDrmSessionManagerForOnlinePlay(playbackInfo))
            .thenReturn(drmSessionManager)
        whenever(
            playerDashMediaSourceFactory.create(
                mediaItem,
                playbackInfo.manifest,
                drmSessionManagerProvider,
            ),
        )
            .thenReturn(expectedDashMediaSource)
        whenever(drmSessionManagerProviderFactory.create(drmSessionManager))
            .thenReturn(drmSessionManagerProvider)

        val actual = tidalMediaSourceCreator.invoke(mediaItem, playbackInfo)

        assertThat(actual).isSameAs(expectedDashMediaSource)
    }

    @Test
    fun invokeWithManifestMimeTypeEmu() {
        val mediaItem = mock<MediaItem>()
        val playbackInfo = mock<PlaybackInfo.Track> {
            on { manifest } doReturn "emuManifest"
            on { manifestMimeType } doReturn ManifestMimeType.EMU
        }
        val expectedHlsMediaSource = mock<HlsMediaSource>()
        val drmSessionManager = mock<DrmSessionManager>()
        val drmSessionManagerProvider = mock<DrmSessionManagerProvider>()
        whenever(drmSessionManagerFactory.createDrmSessionManagerForOnlinePlay(playbackInfo))
            .thenReturn(drmSessionManager)
        whenever(
            playerHlsMediaSourceFactory.create(
                mediaItem,
                playbackInfo.manifest,
                drmSessionManagerProvider,
            ),
        )
            .thenReturn(expectedHlsMediaSource)
        whenever(drmSessionManagerProviderFactory.create(drmSessionManager))
            .thenReturn(drmSessionManagerProvider)

        val actual = tidalMediaSourceCreator.invoke(mediaItem, playbackInfo)

        assertThat(actual).isSameAs(expectedHlsMediaSource)
    }

    @Test
    fun invokeWithManifestMimeTypeEmuForUC() {
        val mediaItem = mock<MediaItem>()
        val playbackInfo = mock<PlaybackInfo.UC> {
            on { manifestMimeType } doReturn ManifestMimeType.EMU
        }
        val expectedHlsMediaSource = mock<HlsMediaSource>()
        val drmSessionManager = mock<DrmSessionManager>()
        val drmSessionManagerProvider = mock<DrmSessionManagerProvider>()
        whenever(drmSessionManagerFactory.createDrmSessionManagerForOnlinePlay(playbackInfo))
            .thenReturn(drmSessionManager)
        whenever(
            playerHlsMediaSourceFactory.createWithUrl(
                mediaItem,
                playbackInfo.url,
                drmSessionManagerProvider,
            ),
        )
            .thenReturn(expectedHlsMediaSource)
        whenever(drmSessionManagerProviderFactory.create(drmSessionManager))
            .thenReturn(drmSessionManagerProvider)

        val actual = tidalMediaSourceCreator.invoke(mediaItem, playbackInfo)

        assertThat(actual).isSameAs(expectedHlsMediaSource)
    }

    @Test
    fun invokeWithManifestMimeTypeBts() {
        val mediaItem = mock<MediaItem>()
        val playbackInfo = mock<PlaybackInfo.Track> {
            on { manifest } doReturn "btsManifest"
            on { manifestMimeType } doReturn ManifestMimeType.BTS
        }
        val expectedProgressiveMediaSource = mock<ProgressiveMediaSource>()
        whenever(playerProgressiveMediaSourceFactory.create(mediaItem, playbackInfo.manifest))
            .thenReturn(expectedProgressiveMediaSource)

        val actual = tidalMediaSourceCreator.invoke(mediaItem, playbackInfo)

        assertThat(actual).isSameAs(expectedProgressiveMediaSource)
    }

    @Test
    fun invokeWithManifestMimeTypeBtsForOffline() {
        val mediaItem = mock<MediaItem>()
        val playbackInfoTrack = mock<PlaybackInfo.Track> {
            on { it.trackId } doReturn 123
        }
        val playbackInfoOfflineTrack = mock<PlaybackInfo.Offline.Track> {
            on { it.track } doReturn playbackInfoTrack
            on { it.manifest } doReturn "btsManifest"
            on { it.manifestMimeType } doReturn ManifestMimeType.BTS
            on { it.partiallyEncrypted } doReturn true
        }
        val expectedProgressiveMediaSource = mock<ProgressiveMediaSource>()
        whenever(
            playerDecryptedHeaderProgressiveOfflineMediaSourceFactory.create(
                mediaItem,
                playbackInfoOfflineTrack.manifest,
                playbackInfoOfflineTrack.track.trackId.toString(),
            ),
        ).thenReturn(expectedProgressiveMediaSource)

        val actual = tidalMediaSourceCreator.invoke(mediaItem, playbackInfoOfflineTrack)

        assertThat(actual).isSameAs(expectedProgressiveMediaSource)
    }

    @Test
    fun invokeWithManifestMimeTypeBtsForOfflineWithFullEncryption() {
        val mediaItem = mock<MediaItem>()
        val storage = mock<Storage>()
        val playbackInfoOfflineTrack = mock<PlaybackInfo.Offline.Track> {
            on { it.manifest } doReturn "btsManifest"
            on { it.manifestMimeType } doReturn ManifestMimeType.BTS
            on { it.partiallyEncrypted } doReturn false
            on { it.storage } doReturn storage
        }
        val expectedProgressiveMediaSource = mock<ProgressiveMediaSource>()
        whenever(
            playerProgressiveOfflineMediaSourceFactory.create(
                mediaItem,
                playbackInfoOfflineTrack.manifest,
                playbackInfoOfflineTrack.storage!!,
            ),
        ).thenReturn(expectedProgressiveMediaSource)

        val actual = tidalMediaSourceCreator.invoke(mediaItem, playbackInfoOfflineTrack)

        assertThat(actual).isSameAs(expectedProgressiveMediaSource)
    }

    @Test
    fun invokeWithManifestMimeTypeEmuForOffline() {
        val mediaItem = mock<MediaItem>()
        val playbackInfoOfflineTrack = mock<PlaybackInfo.Offline.Track> {
            on { it.manifestMimeType } doReturn ManifestMimeType.EMU
        }

        assertThrows<IllegalArgumentException> {
            tidalMediaSourceCreator.invoke(mediaItem, playbackInfoOfflineTrack)
        }
    }

    @Test
    fun invokeWithManifestMimeTypeDashForOffline() {
        val mediaItem = mock<MediaItem>()
        val offlineLicense = "offlineLicense"
        val storage = mock<Storage>()
        val playbackInfoOfflineTrack = mock<PlaybackInfo.Offline.Track> {
            on { it.manifest } doReturn "dashManifest"
            on { it.manifestMimeType } doReturn ManifestMimeType.DASH
            on { it.partiallyEncrypted } doReturn false
            on { it.storage } doReturn storage
            on { it.offlineLicense } doReturn offlineLicense
        }
        val drmSessionManager = mock<DrmSessionManager>()
        val drmSessionManagerProvider = mock<DrmSessionManagerProvider>()
        val expectedDashMediaSource = mock<DashMediaSource>()
        whenever(
            drmSessionManagerFactory.createDrmSessionManagerForOfflinePlay(
                playbackInfoOfflineTrack,
            ),
        ).thenReturn(drmSessionManager)
        whenever(
            drmSessionManagerProviderFactory.create(drmSessionManager),
        ).thenReturn(drmSessionManagerProvider)
        whenever(
            playerDashOfflineMediaSourceFactory.create(
                mediaItem,
                playbackInfoOfflineTrack.manifest,
                playbackInfoOfflineTrack.offlineLicense!!,
                playbackInfoOfflineTrack.storage!!,
                drmSessionManagerProvider,
            ),
        ).thenReturn(expectedDashMediaSource)

        val actual = tidalMediaSourceCreator.invoke(mediaItem, playbackInfoOfflineTrack)

        assertThat(actual).isSameAs(expectedDashMediaSource)
    }
}
