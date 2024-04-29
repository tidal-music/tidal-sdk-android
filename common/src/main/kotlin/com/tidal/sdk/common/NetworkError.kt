package com.tidal.sdk.common

class NetworkError(override val code: String, val throwable: Throwable? = null) : TidalError
