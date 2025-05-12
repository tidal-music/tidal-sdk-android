package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.common.MediaItem
import androidx.media3.datasource.DataSource
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.dash.manifest.DashManifest
import androidx.media3.exoplayer.drm.DrmSessionManager
import androidx.media3.exoplayer.drm.DrmSessionManagerProvider
import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.sdk.player.playbackengine.dash.DashManifestFactory
import com.tidal.sdk.player.playbackengine.offline.OfflineDrmHelper
import com.tidal.sdk.player.playbackengine.offline.OfflineStorageProvider
import com.tidal.sdk.player.streamingapi.offline.Storage
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.Answers
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

private const val ENCODED_MANIFEST = "encodedManifest"

internal class PlayerDashOfflineMediaSourceFactoryTest {

    private val dashMediaSourceFactoryFactory = mock<DashMediaSourceFactoryFactory>()
    private val dashManifestFactory = mock<DashManifestFactory>()
    private val offlineStorageProvider = mock<OfflineStorageProvider>()
    private val offlineDrmHelper = mock<OfflineDrmHelper>()
    private val playerDashOfflineMediaSourceFactory =
        PlayerDashOfflineMediaSourceFactory(
            dashMediaSourceFactoryFactory,
            dashManifestFactory,
            offlineStorageProvider,
            offlineDrmHelper,
        )

    @AfterEach
    fun afterEach() =
        verifyNoMoreInteractions(
            dashMediaSourceFactoryFactory,
            dashManifestFactory,
            offlineStorageProvider,
            offlineDrmHelper,
        )

    @Test
    fun createDrmProtected() {
        val mediaItem = mock<MediaItem>()
        val storage = mock<Storage>()
        val offlineLicense = "1a2b3c4d"
        val isDrmProtected = true
        val dashManifest = mock<DashManifest>()
        val drmSessionManager = mock<DrmSessionManager>()
        val drmSessionManagerProvider =
            mock<DrmSessionManagerProvider> { on { it.get(mediaItem) } doReturn drmSessionManager }
        val expectedDashMediaSource = mock<DashMediaSource>()
        val dataSourceFactoryForOfflinePlay = mock<DataSource.Factory>()
        val dashMediaSourceFactory = mock<DashMediaSource.Factory>()
        whenever(dashManifestFactory.create(ENCODED_MANIFEST)).thenReturn(dashManifest)
        whenever(offlineStorageProvider.getDataSourceFactoryForOfflinePlay(storage, isDrmProtected))
            .thenReturn(dataSourceFactoryForOfflinePlay)
        whenever(dashMediaSourceFactoryFactory.create(dataSourceFactoryForOfflinePlay))
            .thenReturn(dashMediaSourceFactory)
        whenever(dashMediaSourceFactory.setDrmSessionManagerProvider(drmSessionManagerProvider))
            .thenReturn(dashMediaSourceFactory)
        whenever(dashMediaSourceFactory.createMediaSource(dashManifest, mediaItem))
            .thenReturn(expectedDashMediaSource)

        val actualDashMediaSource =
            playerDashOfflineMediaSourceFactory.create(
                mediaItem,
                ENCODED_MANIFEST,
                offlineLicense,
                storage,
                drmSessionManagerProvider,
            )

        assertThat(actualDashMediaSource).isSameAs(expectedDashMediaSource)
        verify(dashManifestFactory).create(ENCODED_MANIFEST)
        verify(offlineStorageProvider).getDataSourceFactoryForOfflinePlay(storage, isDrmProtected)
        verify(drmSessionManagerProvider).get(mediaItem)
        verify(offlineDrmHelper).setOfflineLicense(offlineLicense, drmSessionManager)
        verify(dashMediaSourceFactoryFactory).create(dataSourceFactoryForOfflinePlay)
        verify(dashMediaSourceFactory).setDrmSessionManagerProvider(drmSessionManagerProvider)
        verify(dashMediaSourceFactory).createMediaSource(dashManifest, mediaItem)
        verifyNoMoreInteractions(
            mediaItem,
            storage,
            dashManifest,
            drmSessionManager,
            drmSessionManagerProvider,
            expectedDashMediaSource,
            dataSourceFactoryForOfflinePlay,
            dashMediaSourceFactory,
        )
    }

    @Test
    fun createNotProtected() {
        val mediaItem = mock<MediaItem>()
        val storage = mock<Storage>()
        val offlineLicense = ""
        val isDrmProtected = false
        val builtMediaItem = mock<MediaItem>()
        val dashManifest = mock<DashManifest>()
        val drmSessionManager = mock<DrmSessionManager>()
        val drmSessionManagerProvider = mock<DrmSessionManagerProvider>()
        val expectedDashMediaSource = mock<DashMediaSource>()
        val dataSourceFactoryForOfflinePlay = mock<DataSource.Factory>()
        val dashMediaSourceFactory = mock<DashMediaSource.Factory>()
        whenever(dashManifestFactory.create(ENCODED_MANIFEST)).thenReturn(dashManifest)
        val mediaItemBuilder = mock<MediaItem.Builder>(defaultAnswer = Answers.RETURNS_SELF)
        whenever(mediaItem.buildUpon()) doReturn mediaItemBuilder
        whenever(mediaItemBuilder.build()) doReturn builtMediaItem
        whenever(offlineStorageProvider.getDataSourceFactoryForOfflinePlay(storage, isDrmProtected))
            .thenReturn(dataSourceFactoryForOfflinePlay)
        whenever(dashMediaSourceFactoryFactory.create(dataSourceFactoryForOfflinePlay))
            .thenReturn(dashMediaSourceFactory)
        whenever(dashMediaSourceFactory.setDrmSessionManagerProvider(drmSessionManagerProvider))
            .thenReturn(dashMediaSourceFactory)
        whenever(dashMediaSourceFactory.createMediaSource(dashManifest, mediaItem))
            .thenReturn(expectedDashMediaSource)

        val actualDashMediaSource =
            playerDashOfflineMediaSourceFactory.create(
                mediaItem,
                ENCODED_MANIFEST,
                offlineLicense,
                storage,
                drmSessionManagerProvider,
            )

        assertThat(actualDashMediaSource).isSameAs(expectedDashMediaSource)
        verify(dashManifestFactory).create(ENCODED_MANIFEST)
        verify(offlineStorageProvider).getDataSourceFactoryForOfflinePlay(storage, isDrmProtected)
        verify(dashMediaSourceFactoryFactory).create(dataSourceFactoryForOfflinePlay)
        verify(dashMediaSourceFactory).setDrmSessionManagerProvider(drmSessionManagerProvider)
        verify(dashMediaSourceFactory).createMediaSource(dashManifest, mediaItem)
        verifyNoMoreInteractions(
            mediaItem,
            storage,
            builtMediaItem,
            dashManifest,
            drmSessionManager,
            drmSessionManagerProvider,
            expectedDashMediaSource,
            dataSourceFactoryForOfflinePlay,
            dashMediaSourceFactory,
        )
    }
}
