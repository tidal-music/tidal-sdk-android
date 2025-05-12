package com.tidal.sdk.player.playbackengine.datasource

import androidx.media3.datasource.FileDataSource

internal class DecryptedHeaderFileDataSourceFactoryFactory(
    private val upstream: FileDataSource.Factory
) {

    fun create(decryptedHeader: ByteArray) =
        DecryptedHeaderFileDataSourceFactory(decryptedHeader, upstream)
}
