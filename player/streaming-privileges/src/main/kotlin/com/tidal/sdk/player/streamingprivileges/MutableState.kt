package com.tidal.sdk.player.streamingprivileges

import com.tidal.sdk.player.streamingprivileges.connection.ConnectionMutableState

internal class MutableState {

    var connectionMutableState: ConnectionMutableState? = null
    var keepAlive = false
    var isNetworkConnectivityCallbackCurrentlyRegistered = false
    var streamingPrivilegesListener: StreamingPrivilegesListener? = null
}
