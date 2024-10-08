package com.tidal.sdk.player.playbackengine.drm

import androidx.media3.common.util.Util
import com.tidal.sdk.player.common.model.BaseMediaProduct
import com.tidal.sdk.player.commonandroid.Base64Codec
import com.tidal.sdk.player.playbackengine.StreamingApiRepository
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

internal class TidalMediaDrmCallbackFactory(
    private val streamingApiRepository: StreamingApiRepository,
    private val base64Codec: Base64Codec,
    private val okHttpClient: OkHttpClient,
) {

    fun create(playbackInfo: PlaybackInfo, mode: DrmMode, extras: BaseMediaProduct.Extras?) =
        TidalMediaDrmCallback(
            streamingApiRepository,
            base64Codec,
            DrmLicenseRequestFactory(playbackInfo),
            mode,
            okHttpClient,
            lazy(LazyThreadSafetyMode.NONE) { Request.Builder() },
            lazy(LazyThreadSafetyMode.NONE) { RequestBody.create(null, Util.EMPTY_BYTE_ARRAY) },
            extras,
        )
}
