package com.tidal.sdk.player.streamingprivileges

/**
 * Describes interactions with streaming privilege-related functionalities.
 */
interface StreamingPrivileges {

    /**
     * Dictates whether to keep this instance connected whenever possible.
     *
     * @return true if the command can be deemed successful; false otherwise.
     */
    fun setKeepAlive(newValue: Boolean): Boolean

    /**
     * Sets a [StreamingPrivilegesListener] for StreamingPrivileges-related events.
     *
     * @return true if the command can be deemed successful; false otherwise.
     */
    fun setStreamingPrivilegesListener(
        streamingPrivilegesListener: StreamingPrivilegesListener?,
    ): Boolean

    /**
     * Publish a demand for streaming privileges for playback. This may cause other clients to lose
     * their playback privileges.
     *
     * @return true if the acquisition can be deemed successful; false otherwise.
     */
    fun acquireStreamingPrivileges(): Boolean

    /**
     * Releases this instance. It will no longer be usable moving forward.
     *
     * @return true if the release can be deemed successful; false otherwise.
     */
    fun release(): Boolean
}
