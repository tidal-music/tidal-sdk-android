package com.tidal.sdk.common

data class RetryableError(
    override val code: String,
    val subStatus: Int? = null,
    val throwable: Throwable? = null,
) : TidalError
