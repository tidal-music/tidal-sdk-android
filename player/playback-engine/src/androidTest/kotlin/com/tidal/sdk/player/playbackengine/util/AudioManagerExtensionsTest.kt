package com.tidal.sdk.player.playbackengine.util

import android.content.Context
import android.media.AudioManager
import androidx.test.platform.app.InstrumentationRegistry
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNotEqualTo
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class AudioManagerExtensionsTest {

    private val audioManager =
        InstrumentationRegistry.getInstrumentation()
            .targetContext
            .getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private var musicVolume = 0

    @Before
    fun beforeEach() {
        musicVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    }

    @After fun afterEach() = audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, musicVolume, 0)

    @Test
    fun getScaledVolume() {
        assertThat(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)).isNotEqualTo(0)
        assertThat(audioManager.isVolumeFixed).isFalse()

        val maxMusicVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val setRelativeVolumeAndAssertScaled: (Float) -> Unit = {
            val actualRelativeVolumeToSet = (it * maxMusicVolume).toInt()
            audioManager.apply {
                setStreamVolume(AudioManager.STREAM_MUSIC, actualRelativeVolumeToSet, 0)
                assertThat(scaledVolume)
                    .isEqualTo(actualRelativeVolumeToSet.toFloat() / maxMusicVolume)
            }
        }

        sequenceOf(0.2F, 0.87F, 0F, 1F).forEach { setRelativeVolumeAndAssertScaled(it) }
    }
}
