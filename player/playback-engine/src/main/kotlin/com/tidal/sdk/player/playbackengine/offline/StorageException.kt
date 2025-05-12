package com.tidal.sdk.player.playbackengine.offline

import java.io.IOException

/** Exception that is thrown when we can't find the file we are trying to play. */
internal class StorageException :
    IOException(
        "Can't open file. Has it been moved/deleted or maybe you have not mounted your SD card?"
    )
