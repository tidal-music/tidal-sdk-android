package com.tidal.sdk.player.playbackengine.mediasource.streamingsession

import com.tidal.sdk.player.common.Configuration
import com.tidal.sdk.player.common.UUIDWrapper

internal class ExplicitStreamingSessionFactoryTest : StreamingSessionFactoryTest() {

    override val streamingSessionFactoryF =
        { uuidWrapper: UUIDWrapper, configuration: Configuration ->
            StreamingSession.Factory.Explicit(uuidWrapper, configuration)
        }
}
