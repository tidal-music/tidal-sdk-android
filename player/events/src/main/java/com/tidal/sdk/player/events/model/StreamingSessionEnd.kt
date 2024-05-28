package com.tidal.sdk.player.events.model

import androidx.annotation.Keep
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.UUID

@Keep
@Suppress("UnusedPrivateMember")
data class StreamingSessionEnd @AssistedInject internal constructor(
    @Assisted override val ts: Long,
    @Assisted override val uuid: UUID,
    @Assisted override val user: User,
    @Assisted override val client: Client,
    @Assisted override val payload: Payload,
) : StreamingMetrics<StreamingSessionEnd.Payload>("streaming_session_end") {

    @Keep
    data class Payload(override val streamingSessionId: String, private val timestamp: Long) :
        StreamingMetrics.Payload

    @AssistedFactory
    internal interface Factory {

        fun create(
            ts: Long,
            uuid: UUID,
            user: User,
            client: Client,
            payload: Payload,
        ): StreamingSessionEnd
    }
}
