package com.tidal.sdk.player.playbackengine.model

/** A helper type for byte multiples. */
@JvmInline
value class ByteAmount(val value: Long) {
    companion object {
        inline val Number.bytes
            get() = ByteAmount(toLong())

        inline val Number.kilobytes
            get() = ByteAmount(toLong() * FACTOR_FROM_KILOBYTES)

        inline val Number.megabytes
            get() = ByteAmount(toLong() * FACTOR_FROM_MEGABYTES)

        inline val Number.gigabytes
            get() = ByteAmount(toLong() * FACTOR_FROM_GIGABYTES)

        const val FACTOR_FROM_KILOBYTES = 1024
        const val FACTOR_FROM_MEGABYTES = 1024 * 1024
        const val FACTOR_FROM_GIGABYTES = 1024 * 1024 * 1024
    }
}
