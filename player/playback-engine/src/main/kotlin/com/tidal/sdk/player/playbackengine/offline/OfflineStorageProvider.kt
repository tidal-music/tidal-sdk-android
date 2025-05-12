package com.tidal.sdk.player.playbackengine.offline

import androidx.media3.datasource.DataSource
import androidx.media3.datasource.cache.SimpleCache
import com.tidal.sdk.player.streamingapi.offline.Storage

/**
 * This helps get the correct [DataSource.Factory] used for playing back the downloaded content.
 *
 * ExoPlayer does not have a solution for downloading to different paths(including a potential SD
 * card) selectable by the user, so we need to keep track of this by ourselves by using two
 * different instances of [SimpleCache] (handled by [OfflineCacheProvider]) and provide the correct
 * [DataSource.Factory] on demand.
 *
 * The cache instances MUST be the same for downloading and playback for the same piece of content,
 * otherwise it won't find the offlined files and will throw a [StorageException].
 *
 * @param[offlinePlayDataSourceFactoryHelper] An instance of [OfflinePlayDataSourceFactoryHelper].
 *   Used to get the correct [DataSource.Factory] for offline playback without protection.
 * @param[offlinePlayDrmDataSourceFactoryHelper] An instance of
 *   [OfflinePlayDrmDataSourceFactoryHelper]. Used to get the correct [DataSource.Factory] for drm
 *   protected offline playback.
 */
internal class OfflineStorageProvider(
    private val offlinePlayDataSourceFactoryHelper: OfflinePlayDataSourceFactoryHelper,
    private val offlinePlayDrmDataSourceFactoryHelper: OfflinePlayDrmDataSourceFactoryHelper,
) {

    fun getDataSourceFactoryForOfflinePlay(
        storage: Storage,
        isDrmProtected: Boolean = false,
    ): DataSource.Factory {
        return if (isDrmProtected) {
            if (storage.externalStorage) {
                offlinePlayDrmDataSourceFactoryHelper.getExternal(storage.path)
            } else {
                offlinePlayDrmDataSourceFactoryHelper.getInternal(storage.path)
            }
        } else {
            if (storage.externalStorage) {
                offlinePlayDataSourceFactoryHelper.getExternal(storage.path)
            } else {
                offlinePlayDataSourceFactoryHelper.getInternal(storage.path)
            }
        }
    }
}
