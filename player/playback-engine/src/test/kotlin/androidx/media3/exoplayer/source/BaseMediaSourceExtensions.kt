package androidx.media3.exoplayer.source

import androidx.media3.common.Timeline

internal fun BaseMediaSource.realRefreshSourceInfo(timeline: Timeline) {
    refreshSourceInfo(timeline)
}
