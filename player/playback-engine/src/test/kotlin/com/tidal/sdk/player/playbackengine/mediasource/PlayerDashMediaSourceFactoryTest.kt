package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.dash.manifest.DashManifest
import androidx.media3.exoplayer.drm.DrmSessionManagerProvider
import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.sdk.player.playbackengine.dash.DashManifestFactory
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

private const val ENCODED_MANIFEST = "encodedManifest"

internal class PlayerDashMediaSourceFactoryTest {

    private val dashMediaSourceFactory = mock<DashMediaSource.Factory>()
    private val dashManifestFactory = mock<DashManifestFactory>()
    private val playerDashMediaSourceFactory =
        PlayerDashMediaSourceFactory(dashMediaSourceFactory, dashManifestFactory)

    @Test
    fun create() {
        val mediaItem = mock<MediaItem>()
        val dashManifest = mock<DashManifest>()
        val drmSessionManagerProvider = mock<DrmSessionManagerProvider>()
        val expectedDashMediaSource = mock<DashMediaSource>()
        whenever(dashManifestFactory.create(ENCODED_MANIFEST)).thenReturn(dashManifest)
        whenever(dashMediaSourceFactory.setDrmSessionManagerProvider(drmSessionManagerProvider))
            .thenReturn(dashMediaSourceFactory)
        whenever(dashMediaSourceFactory.createMediaSource(dashManifest, mediaItem))
            .thenReturn(expectedDashMediaSource)

        val actualDashMediaSource =
            playerDashMediaSourceFactory.create(
                mediaItem,
                ENCODED_MANIFEST,
                drmSessionManagerProvider,
            )

        verify(dashMediaSourceFactory).setDrmSessionManagerProvider(drmSessionManagerProvider)
        assertThat(actualDashMediaSource).isSameAs(expectedDashMediaSource)
    }
}
