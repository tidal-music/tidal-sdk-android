package com.tidal.sdk.player.playbackengine.mediasource.streamingsession

import com.tidal.sdk.player.events.model.StreamingSessionStart
import org.mockito.kotlin.mock

internal class ExplicitStreamingSessionCreatorTest :
    StreamingSessionCreatorTest<StreamingSession.Factory.Explicit>() {

    override val startReason = StreamingSessionStart.StartReason.EXPLICIT
    override val factory = mock<StreamingSession.Factory.Explicit>()
    override val streamingSessionCreator = StreamingSession.Creator.Explicit(
        factory,
        sntpClient,
        eventReporter,
    )
}
