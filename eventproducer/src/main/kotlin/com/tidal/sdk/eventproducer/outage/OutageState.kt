package com.tidal.sdk.eventproducer.outage

import com.tidal.sdk.common.TidalError
import com.tidal.sdk.common.TidalMessage

sealed class OutageState {
    data class Outage(val outageStartError: TidalError = OutageStartError()) : OutageState()

    data class NoOutage(val outageEndMessage: TidalMessage = OutageEndMessage()) : OutageState()
}
