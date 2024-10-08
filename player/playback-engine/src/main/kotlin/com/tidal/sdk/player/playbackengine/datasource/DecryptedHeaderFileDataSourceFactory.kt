package com.tidal.sdk.player.playbackengine.datasource

import androidx.media3.datasource.DataSource
import androidx.media3.datasource.FileDataSource

@Suppress("UnsafeOptInUsageError")
internal class DecryptedHeaderFileDataSourceFactory(
    private val decryptedHeader: ByteArray,
    private val upstream: FileDataSource.Factory,
) : DataSource.Factory {

    override fun createDataSource(): DataSource {
        return DecryptedHeaderFileDataSource(decryptedHeader, upstream.createDataSource())
    }
}
