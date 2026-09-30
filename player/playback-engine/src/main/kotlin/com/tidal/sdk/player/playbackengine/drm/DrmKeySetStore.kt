package com.tidal.sdk.player.playbackengine.drm

import android.content.SharedPreferences
import com.tidal.sdk.player.commonandroid.Base64Codec

/**
 * Persists Widevine offline key-set IDs so that previously streamed (and disk-cached) content can
 * be decrypted again without a network license request, until the license expires.
 *
 * Each entry is keyed by a stable content identifier (the manifest hash) and stores the base64
 * encoded key-set ID together with the time it was acquired and the license duration reported by
 * the CDM. Validity is computed from those two values at lookup time.
 *
 * @param[sharedPreferences] Backing store, dedicated to DRM key sets.
 * @param[base64Codec] Used to (de)serialize the binary key-set ID to a string.
 */
internal class DrmKeySetStore(
    private val sharedPreferences: SharedPreferences,
    private val base64Codec: Base64Codec,
) {

    /**
     * @param[keySetId] The offline Widevine key-set ID returned by the CDM.
     * @param[acquiredAtMs] Wall-clock time (ms) the license was acquired.
     * @param[licenseDurationSec] License duration remaining (s) reported at acquisition time.
     */
    data class Entry(
        val keySetId: ByteArray,
        val acquiredAtMs: Long,
        val licenseDurationSec: Long,
    ) {
        override fun equals(other: Any?) =
            this === other ||
                (other is Entry &&
                    keySetId.contentEquals(other.keySetId) &&
                    acquiredAtMs == other.acquiredAtMs &&
                    licenseDurationSec == other.licenseDurationSec)

        override fun hashCode() =
            (keySetId.contentHashCode() * 31 + acquiredAtMs.hashCode()) * 31 +
                licenseDurationSec.hashCode()
    }

    fun get(contentKey: String): Entry? {
        val raw = sharedPreferences.getString(contentKey, null) ?: return null
        val parts = raw.split(DELIMITER)
        if (parts.size != FIELD_COUNT) {
            return null
        }
        return try {
            Entry(
                keySetId = base64Codec.decode(parts[0].toByteArray(CHARSET)),
                acquiredAtMs = parts[1].toLong(),
                licenseDurationSec = parts[2].toLong(),
            )
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    fun put(contentKey: String, entry: Entry) {
        val encodedKeySetId = String(base64Codec.encode(entry.keySetId), CHARSET)
        val raw =
            listOf(encodedKeySetId, entry.acquiredAtMs, entry.licenseDurationSec)
                .joinToString(DELIMITER)
        sharedPreferences.edit().putString(contentKey, raw).apply()
    }

    fun remove(contentKey: String) {
        sharedPreferences.edit().remove(contentKey).apply()
    }

    private companion object {
        const val DELIMITER = "|"
        const val FIELD_COUNT = 3
        val CHARSET = Charsets.UTF_8
    }
}
