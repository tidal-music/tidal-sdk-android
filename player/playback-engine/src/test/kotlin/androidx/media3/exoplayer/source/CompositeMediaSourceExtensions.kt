package androidx.media3.exoplayer.source

import androidx.media3.common.Timeline

internal fun <T> CompositeMediaSource<T>.realOnChildSourceInfoRefreshed(
    id: T,
    mediaSource: MediaSource,
    timeline: Timeline,
) {
    onChildSourceInfoRefreshed(id, mediaSource, timeline)
}

internal fun CompositeMediaSource<*>.realReleaseSourceInternal() {
    releaseSourceInternal()
}
