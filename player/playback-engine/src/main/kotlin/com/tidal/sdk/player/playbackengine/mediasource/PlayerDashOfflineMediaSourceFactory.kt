package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.drm.DrmSessionManagerProvider
import com.tidal.sdk.player.playbackengine.dash.DashManifestFactory
import com.tidal.sdk.player.playbackengine.offline.OfflineDrmHelper
import com.tidal.sdk.player.playbackengine.offline.OfflineStorageProvider
import com.tidal.sdk.player.streamingapi.offline.Storage

internal class PlayerDashOfflineMediaSourceFactory(
    private val dashMediaSourceFactoryFactory: DashMediaSourceFactoryFactory,
    private val dashManifestFactory: DashManifestFactory,
    private val offlineStorageProvider: OfflineStorageProvider?,
    private val offlineDrmHelper: OfflineDrmHelper?,
) {

    fun create(
        mediaItem: MediaItem,
        encodedManifest: String,
        offlineLicense: String,
        storage: Storage,
        drmSessionManagerProvider: DrmSessionManagerProvider,
    ): DashMediaSource {
        val dashManifest = dashManifestFactory.create(encodedManifest)
        val isDrmProtected = offlineLicense.isNotEmpty()
        val dataSourceFactory =
            offlineStorageProvider!!.getDataSourceFactoryForOfflinePlay(storage, isDrmProtected)

        if (isDrmProtected) {
            offlineDrmHelper!!.setOfflineLicense(
                offlineLicense,
                drmSessionManagerProvider[mediaItem],
            )
        }

        return dashMediaSourceFactoryFactory
            .create(dataSourceFactory)
            .setDrmSessionManagerProvider(drmSessionManagerProvider)
            .createMediaSource(dashManifest, mediaItem)
    }
}
