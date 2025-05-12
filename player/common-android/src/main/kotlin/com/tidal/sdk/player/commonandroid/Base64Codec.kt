package com.tidal.sdk.player.commonandroid

import android.util.Base64

/** This will decode a Base64-encoded byte array into a Base64-decoded byte array, or vice versa. */
class Base64Codec {

    fun decode(input: ByteArray, flags: Int = Base64.DEFAULT): ByteArray =
        Base64.decode(input, flags)

    fun encode(input: ByteArray, flags: Int = Base64.NO_WRAP): ByteArray =
        Base64.encode(input, flags)
}
