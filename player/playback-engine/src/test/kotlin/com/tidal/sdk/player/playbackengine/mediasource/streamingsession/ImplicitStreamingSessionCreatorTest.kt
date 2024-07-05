package com.tidal.sdk.player.playbackengine.mediasource.streamingsession

import com.tidal.sdk.player.events.model.StreamingSessionStart
import org.mockito.kotlin.mock

internal class ImplicitStreamingSessionCreatorTest :
    StreamingSessionCreatorTest<StreamingSession.Factory.Implicit>() {

    override val startReason = StreamingSessionStart.StartReason.IMPLICIT
    override val factory = mock<StreamingSession.Factory.Implicit>()
    override val streamingSessionCreator = StreamingSession.Creator.Implicit(
        factory,
        sntpClient,
        eventReporter,
    )
}
