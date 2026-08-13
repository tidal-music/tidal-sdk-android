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

    /**
     * Builds a [DashMediaSource] for offline playback.
     *
     * The [manifest] argument is interpreted in one of two ways, to keep backwards compatibility
     * with content that was downloaded before the MPD-URI migration:
     * - If it is an http(s) MPD URI (the current scheme), media3 loads and parses the MPD from the
     *   offline cache by its `uri.path` via the cache-backed data source factory.
     * - Otherwise it is treated as a legacy base64-encoded MPD, which is decoded and parsed
     *   in-process via [dashManifestFactory] and side-loaded as a pre-parsed manifest.
     */
    fun create(
        mediaItem: MediaItem,
        manifest: String,
        offlineLicense: String,
        storage: Storage,
        drmSessionManagerProvider: DrmSessionManagerProvider,
    ): DashMediaSource {
        val isDrmProtected = offlineLicense.isNotEmpty()
        val dataSourceFactory =
            offlineStorageProvider!!.getDataSourceFactoryForOfflinePlay(storage, isDrmProtected)

        if (isDrmProtected) {
            offlineDrmHelper!!.setOfflineLicense(
                offlineLicense,
                drmSessionManagerProvider[mediaItem],
            )
        }

        val dashMediaSourceFactory =
            dashMediaSourceFactoryFactory
                .create(dataSourceFactory)
                .setDrmSessionManagerProvider(drmSessionManagerProvider)

        return if (manifest.isManifestUri()) {
            val mediaItemWithManifestUri = mediaItem.buildUpon().setUri(manifest).build()
            dashMediaSourceFactory.createMediaSource(mediaItemWithManifestUri)
        } else {
            dashMediaSourceFactory.createMediaSource(
                dashManifestFactory.create(manifest),
                mediaItem,
            )
        }
    }

    private fun String.isManifestUri() = startsWith("http://") || startsWith("https://")
}
