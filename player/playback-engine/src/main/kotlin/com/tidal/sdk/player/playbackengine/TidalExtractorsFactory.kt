package com.tidal.sdk.player.playbackengine

import androidx.media3.decoder.flac.FlacExtractor
import androidx.media3.extractor.Extractor
import androidx.media3.extractor.ExtractorsFactory
import androidx.media3.extractor.mp3.Mp3Extractor
import androidx.media3.extractor.mp4.Mp4Extractor

internal class TidalExtractorsFactory : ExtractorsFactory {

    override fun createExtractors(): Array<Extractor> {
        val mp4Extractor = Mp4Extractor()
        val flacExtractor = FlacExtractor()
        val mp3Extractor = Mp3Extractor()
        return arrayOf(mp4Extractor, flacExtractor, mp3Extractor)
    }
}
