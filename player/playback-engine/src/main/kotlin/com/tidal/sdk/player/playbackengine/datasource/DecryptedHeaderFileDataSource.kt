package com.tidal.sdk.player.playbackengine.datasource

import androidx.media3.datasource.ByteArrayDataSource
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DataSpec
import kotlin.math.max

@Suppress("UnsafeOptInUsageError")
internal class DecryptedHeaderFileDataSource(
    private val decryptedHeader: ByteArray,
    private val upstream: DataSource,
) : DataSource by upstream {

    private val decryptedHeaderUpstream = ByteArrayDataSource(decryptedHeader)

    private var readPosition = 0L

    override fun open(dataSpec: DataSpec): Long {
        readPosition = dataSpec.position
        val remainingHeader = decryptedHeader.size - readPosition

        return open(remainingHeader, dataSpec)
    }

    private fun open(remainingHeader: Long, dataSpec: DataSpec): Long {
        var bytesToRead = 0L
        if (remainingHeader > 0) {
            bytesToRead += decryptedHeaderUpstream.open(dataSpec)
        }
        bytesToRead += upstream.open(dataSpec.subrange(max(remainingHeader, 0)))

        return bytesToRead
    }

    override fun read(target: ByteArray, offset: Int, length: Int): Int {
        var read = 0
        if (readPosition < decryptedHeader.size) {
            read = decryptedHeaderUpstream.read(target, offset, length)
        }

        if (read <= 0) {
            read = upstream.read(target, offset, length)
        }
        readPosition += read
        return read
    }

    override fun close() {
        decryptedHeaderUpstream.close()
        upstream.close()
    }
}
