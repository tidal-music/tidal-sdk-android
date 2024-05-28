package com.tidal.sdk.player.playbackengine.playbackprivilege

import com.tidal.sdk.player.common.model.MediaProduct

/**
 * Implementations should decide, based on the given input, what privileges are available.
 */
interface PlaybackPrivilegeProvider {

    fun get(mediaProduct: MediaProduct): PlaybackPrivilege
}
