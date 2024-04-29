package com.tidal.sdk.player.streamingprivileges.connection

import okhttp3.WebSocket

internal enum class CloseReason(val code: Int, val description: String) {

    REASON_REQUESTED_BY_SELF(RFC_6455_SECTION_7_4_STATUS_CODE_NORMAL_CLOSURE, "Requested by self"),
    REASON_REQUESTED_BY_PEER(RFC_6455_SECTION_7_4_STATUS_CODE_GOING_AWAY, "Requested by peer"),
}

internal fun WebSocket.close(closeReason: CloseReason) = closeReason.run {
    close(code, description)
}

private const val RFC_6455_SECTION_7_4_STATUS_CODE_NORMAL_CLOSURE = 1000
private const val RFC_6455_SECTION_7_4_STATUS_CODE_GOING_AWAY = 1001
