package com.tidal.sdk.player.playbackengine.model

import androidx.annotation.RestrictTo
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.playbackengine.PlaybackEngine
import com.tidal.sdk.player.playbackengine.dj.DjSessionStatus
import com.tidal.sdk.player.playbackengine.outputdevice.OutputDevice

/**
 * Events that can be used to track happenings related to Player's functionality.
 *
 * @see PlaybackEngine.events
 */
sealed interface Event {

    /**
     * A transition to playing a new mediaProduct. This may or may not be the same one as reflected
     * by the previously reported [MediaProductTransition] event (or previous ones) and will be
     * reported as a cause of both implicit and explicit transitions.
     */
    data class MediaProductTransition(
        val mediaProduct: MediaProduct,
        val playbackContext: PlaybackContext,
    ) : Event

    /** A playing mediaProduct has ended. */
    data class MediaProductEnded(
        val mediaProduct: MediaProduct,
        val playbackContext: PlaybackContext,
    ) : Event

    /**
     * A change in [PlaybackEngine.playbackState]. Updates to state using the same value are not
     * considered changes and will therefore not trigger [PlaybackStateChange] events.
     */
    data class PlaybackStateChange(val playbackState: PlaybackState) : Event

    /**
     * A loss of streaming permission. This event will cause playback to pause if possible, and
     * resuming/starting it may cause the equivalent loss for other instances instead.
     *
     * @param privilegedClientDisplayName A human-readable description of the device causing the
     *   streaming privilege loss for this instance.
     */
    data class StreamingPrivilegesRevoked(val privilegedClientDisplayName: String?) : Event

    /** A DJ session product has been updated. */
    data class DjSessionUpdate(val productId: String, val status: DjSessionStatus) : Event

    /** The output device has been updated. */
    data class OutputDeviceUpdated(val outputDevice: OutputDevice) : Event

    /** Playback quality has been updated. */
    data class PlaybackQualityChanged(val playbackContext: PlaybackContext) : Event

    /** Indicates that the PlaybackEngine has been released. */
    object Release : Event

    sealed class Error private constructor(val errorCode: String, cause: Throwable?) :
        Event, Throwable(errorCode, cause) {

        class Unexpected
        @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
        constructor(errorCode: String, throwable: Throwable?) : Error(errorCode, throwable)

        class ContentNotAvailableInLocation
        @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
        constructor(errorCode: String, throwable: Throwable?) : Error(errorCode, throwable)

        class ContentNotAvailableForSubscription
        @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
        constructor(errorCode: String, throwable: Throwable?) : Error(errorCode, throwable)

        class MonthlyStreamQuotaExceeded
        @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
        constructor(errorCode: String, throwable: Throwable?) : Error(errorCode, throwable)

        class NotAllowed
        @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
        constructor(errorCode: String, throwable: Throwable?) : Error(errorCode, throwable)

        class Retryable
        @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
        constructor(errorCode: String, throwable: Throwable?) : Error(errorCode, throwable)

        class Network
        @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
        constructor(errorCode: String, throwable: Throwable?) : Error(errorCode, throwable)
    }
}
