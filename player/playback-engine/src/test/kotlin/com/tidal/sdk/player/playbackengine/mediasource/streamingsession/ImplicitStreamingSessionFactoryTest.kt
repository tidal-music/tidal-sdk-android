package com.tidal.sdk.player.playbackengine.mediasource.streamingsession

import com.tidal.sdk.player.common.Configuration
import com.tidal.sdk.player.common.UUIDWrapper

internal class ImplicitStreamingSessionFactoryTest : StreamingSessionFactoryTest() {

    override val streamingSessionFactoryF =
        { uuidWrapper: UUIDWrapper, configuration: Configuration ->
            StreamingSession.Factory.Implicit(uuidWrapper, configuration)
        }
}
