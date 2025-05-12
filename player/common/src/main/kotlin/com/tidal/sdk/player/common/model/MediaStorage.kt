package com.tidal.sdk.player.common.model

import androidx.annotation.Keep
import com.tidal.sdk.player.common.model.MediaStorage.DEVICE_EXTERNAL
import com.tidal.sdk.player.common.model.MediaStorage.DEVICE_INTERNAL
import com.tidal.sdk.player.common.model.MediaStorage.INTERNET

/**
 * Source of the asset for a given product.
 *
 * [INTERNET] tells us that the asset source is from the Internet. [DEVICE_INTERNAL] tells us that
 * the asset is stored on an internal storage on the device. [DEVICE_EXTERNAL] tells us that the
 * asset is stored on an external storage device.
 */
@Keep
enum class MediaStorage {
    INTERNET,
    DEVICE_INTERNAL,
    DEVICE_EXTERNAL,
}
