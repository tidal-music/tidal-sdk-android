package com.tidal.sdk.player.streamingapi.drm.model

import androidx.annotation.Keep

/**
 * Drm license of a given track or video. This will reflect the response from our backend and it
 * will be used for decrypting secured media products.
 *
 * @property[streamingSessionId] Loop back of the streaming session id sent in the request.
 * @property[payload] Base64 encoded payload we are supposed to pass to the CDM.
 */
@Keep
data class DrmLicense(val streamingSessionId: String, val payload: String)
