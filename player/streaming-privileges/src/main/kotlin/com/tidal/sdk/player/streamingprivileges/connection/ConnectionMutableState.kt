package com.tidal.sdk.player.streamingprivileges.connection

import com.tidal.sdk.player.streamingprivileges.StreamingPrivilegesListener

internal class ConnectionMutableState(
    var streamingPrivilegesListener: StreamingPrivilegesListener?
) {

    var socketConnectionState: SocketConnectionState = SocketConnectionState.NotConnected
    var isConnectionRelevant = true
}
