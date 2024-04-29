package com.tidal.sdk.player.playbackengine.dash

import com.tidal.sdk.player.reflectionSetInstanceMemberProperty
import java.io.ByteArrayInputStream

internal fun DashManifestFactory.reflectionSetByteArrayInputStreamF(
    newByteArrayInputStreamF: (ByteArray) -> ByteArrayInputStream,
) = reflectionSetInstanceMemberProperty("byteArrayInputStreamF", newByteArrayInputStreamF)
