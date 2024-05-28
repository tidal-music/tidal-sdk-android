package com.tidal.sdk.eventproducer.outage

import com.tidal.sdk.common.TidalError

// this error code needs to be agreed with the rest of the platforms
class OutageStartError(override val code: String = "1") : TidalError
