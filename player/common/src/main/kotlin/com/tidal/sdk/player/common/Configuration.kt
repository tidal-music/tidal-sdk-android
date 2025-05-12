package com.tidal.sdk.player.common

import kotlin.properties.Delegates

/**
 * Simple configuration class that sets and gets common configuration.
 *
 * @param[isOfflineMode] A [Boolean] that tells us if client is in offline mode or not. Must be
 *   updated each time the offline mode changes.
 */
class Configuration(isOfflineMode: Boolean) {

    var isOfflineMode by
        Delegates.observable(isOfflineMode) { _, oldValue, newValue ->
            if (oldValue != newValue) {
                listeners.onEach { it.onConfigurationChanged(this) }
            }
        }
    private val listeners = mutableListOf<ConfigurationListener>()

    fun addListener(configurationListener: ConfigurationListener) =
        listeners.add(configurationListener).also {
            configurationListener.onConfigurationChanged(this)
        }

    fun removeListener(configurationListener: ConfigurationListener) =
        listeners.remove(configurationListener)
}
