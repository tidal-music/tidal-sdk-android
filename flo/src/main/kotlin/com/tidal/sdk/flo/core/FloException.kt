package com.tidal.sdk.flo.core

sealed class FloException(cause: Throwable) : Exception(cause) {

    class ConnectionLost internal constructor(cause: Throwable) : FloException(cause)

    class NotAuthorized internal constructor(cause: Throwable) : FloException(cause)
}
