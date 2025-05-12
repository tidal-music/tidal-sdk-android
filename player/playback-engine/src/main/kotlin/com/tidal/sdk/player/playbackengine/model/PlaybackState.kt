package com.tidal.sdk.player.playbackengine.model

/** States the playback engine can be in. */
enum class PlaybackState {

    /**
     * The default state where no active or next media product are set. This is the initial state,
     * and also the state reached after the last requested media product finishes playing or after
     * the playback engine is reset.
     */
    IDLE,

    /** We are currently playing the active media product. */
    PLAYING,

    /**
     * We have an active media product, but we are not playing it, nor do we try to play it. This
     * can be because of a user initiated pause, some error occurred or similar actions.
     */
    NOT_PLAYING,

    /**
     * Playback is currently stalled due to some unexpected/unwanted reason, but we are still trying
     * to play. This can happen because of buffering, waiting for resources etc.
     *
     * If we manage to resume playback automatically, the state will change to [PLAYING]. If we
     * don't manage to resume playback automatically, the state will change to [NOT_PLAYING] and a
     * message will be sent to describe why.
     */
    STALLED,
}
