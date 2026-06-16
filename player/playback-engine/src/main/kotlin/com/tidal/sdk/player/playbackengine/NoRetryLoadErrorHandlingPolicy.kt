package com.tidal.sdk.player.playbackengine

import androidx.media3.common.C
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy.LoadErrorInfo

/**
 * A [LoadErrorHandlingPolicy] that never retries the load it governs: [getRetryDelayMsFor] always
 * returns [C.TIME_UNSET], so PlaybackInfoLoadableLoaderCallback.onLoadError treats every load error
 * as fatal. Used for TIDAL API-backed (TRACK/VIDEO) PlaybackInfo fetches, whose retry is owned by
 * tidalapi's built-in retry. All other policy methods delegate to [loadErrorHandlingPolicy].
 */
internal class NoRetryLoadErrorHandlingPolicy(
    private val loadErrorHandlingPolicy: LoadErrorHandlingPolicy
) : LoadErrorHandlingPolicy by loadErrorHandlingPolicy {

    override fun getRetryDelayMsFor(loadErrorInfo: LoadErrorInfo) = C.TIME_UNSET
}
