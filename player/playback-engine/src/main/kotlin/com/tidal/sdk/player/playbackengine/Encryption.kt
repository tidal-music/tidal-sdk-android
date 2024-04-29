package com.tidal.sdk.player.playbackengine

import androidx.media3.datasource.DataSource

/**
 * Implement this to provide encryption details from a host app.
 */
interface Encryption {

    /**
     * A secretKey of [ByteArray]. Must be the same value for the same content when reading from a
     * [DataSource] and writing to a[androidx.media3.datasource.DataSink].
     * Only necessary when the host app has downloaded the content to ensure we are using the same
     * key when playing back that downloaded content.
     */
    val secretKey: ByteArray

    /**
     * A decryptedHeader of [ByteArray] if this was used as an offlining strategy in the host app.
     * Only necessary when the host app has downloaded the content in this specific way to ensure
     * we are getting the correctly decrypted header when playing back that downloaded content.
     */
    fun getDecryptedHeader(productId: String): ByteArray
}
