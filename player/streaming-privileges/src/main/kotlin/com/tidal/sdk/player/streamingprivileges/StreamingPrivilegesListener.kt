package com.tidal.sdk.player.streamingprivileges

/**
 * Describes interactions that instances of [StreamingPrivilegesListener] may want to have with
 * listeners (typically their hosts).
 */
interface StreamingPrivilegesListener {

    /**
     * Notifies successful establishment of a connection with the backend behind the parameter, if
     * any. This might be called multiple times during the lifetime of a [StreamingPrivileges]
     * depending on factors such as network conditions if the implementation requires network
     * communication and other implementation details.
     */
    fun onConnectionEstablished()

    /**
     * Notifies that current streaming privileges for this client have been revoked.
     *
     * @param privilegedClientDisplayName A name representative of the client that has caused this
     * client to lose streaming privileges.
     */
    fun onStreamingPrivilegesRevoked(privilegedClientDisplayName: String)
}
