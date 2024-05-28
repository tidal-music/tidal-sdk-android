package com.tidal.sdk.player.streamingapi.offline

/**
 * Simple data class to keep track of external vs internal storage and the path of the storage.
 *
 * @param[externalStorage] A [Boolean] that tells if this is stored externally or not.
 * @param[path] A [String] representing the path of the storage.
 */
data class Storage(val externalStorage: Boolean, val path: String)
