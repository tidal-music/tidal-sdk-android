package com.tidal.sdk.player.playbackengine.drm

import androidx.media3.common.util.Util
import com.tidal.sdk.player.common.model.Extras
import com.tidal.sdk.player.playbackengine.StreamingApiRepository
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

internal class TidalMediaDrmCallbackFactory(
    private val streamingApiRepository: StreamingApiRepository,
    private val okHttpClient: OkHttpClient,
) {

    fun create(licenseUrl: String, streamingSessionId: String, mode: DrmMode, extras: Extras?) =
        TidalMediaDrmCallback(
            streamingApiRepository,
            streamingSessionId,
            licenseUrl,
            mode,
            okHttpClient,
            lazy(LazyThreadSafetyMode.NONE) { Request.Builder() },
            lazy(LazyThreadSafetyMode.NONE) { RequestBody.create(null, Util.EMPTY_BYTE_ARRAY) },
            extras,
        )
}
