package com.tidal.sdk.player.events.util

import java.util.Locale

internal class HardwarePlatform(model: CharSequence, manufacturer: CharSequence) {
    val value =
        if (model.contains(manufacturer, ignoreCase = true)) {
                model
            } else {
                FORMAT_HARDWARE_PLATFORM_FORCED_MANUFACTURER.format(
                    Locale.ENGLISH,
                    manufacturer,
                    model,
                )
            }
            .toString()
}

private const val FORMAT_HARDWARE_PLATFORM_FORCED_MANUFACTURER = "%1\$s %2\$s"
