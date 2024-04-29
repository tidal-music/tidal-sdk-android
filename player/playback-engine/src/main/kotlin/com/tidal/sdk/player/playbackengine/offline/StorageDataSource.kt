package com.tidal.sdk.player.playbackengine.offline

import android.net.Uri
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.TransferListener

/**
 * Dummy data source to be used as upstream when playing back offlined files.
 *
 * When playing offlined files fails, it will use the upstream to go online and get the file.
 * We don't want that, we instead want to be able to throw our custom [StorageException] so we can
 * give the user appropriate feedback.
 *
 * Situations where offline files fail to play:
 * - It can't find the files because they have been moved or deleted
 * - The SD card is not mounted
 * - A different SD card is mounted(it can obviously not find the original files)
 *
 * Note: If we don't set this as upstream, it will use the [DummyDataSource] which just sends a
 * default [IOException], but we want to control this and use our own [StorageException].
 */
internal class StorageDataSource : DataSource {

    class Factory(private val storageDataSource: StorageDataSource) : DataSource.Factory {
        override fun createDataSource() = storageDataSource
    }

    override fun addTransferListener(transferListener: TransferListener) {
        // NOOP
    }

    override fun open(dataSpec: DataSpec): Long {
        throw StorageException()
    }

    override fun getUri(): Uri? {
        return null
    }

    override fun close() {
        // NOOP
    }

    override fun read(target: ByteArray, offset: Int, length: Int): Int {
        throw UnsupportedOperationException()
    }
}
