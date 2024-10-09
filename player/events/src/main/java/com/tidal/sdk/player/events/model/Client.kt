package com.tidal.sdk.player.events.model

import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.tidal.sdk.player.events.R

/**
 * Information about the client an event is tied to.
 */
@Keep
@Suppress("UnusedPrivateMember")
data class Client(
    private val token: String,
    private val deviceType: DeviceType,
    private val version: String,
) {

    private val platform = "android"

    @Keep
    enum class DeviceType {

        @SerializedName("androidAuto")
        ANDROID_AUTO,

        @SerializedName("tv")
        TV,

        @SerializedName("tablet")
        TABLET,

        @SerializedName("mobile")
        MOBILE,

        ;

        companion object {
            fun from(context: Context, uiModeManager: UiModeManager): DeviceType {
                return when {
                    uiModeManager.currentModeType == Configuration.UI_MODE_TYPE_TELEVISION -> TV
                    uiModeManager.currentModeType == Configuration.UI_MODE_TYPE_CAR -> ANDROID_AUTO
                    context.resources.getBoolean(R.bool.is_tablet) -> TABLET
                    else -> MOBILE
                }
            }
        }
    }
}
