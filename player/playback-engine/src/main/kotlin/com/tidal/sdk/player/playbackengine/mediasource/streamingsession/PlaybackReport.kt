package com.tidal.sdk.player.playbackengine.mediasource.streamingsession

import android.content.SharedPreferences
import androidx.annotation.Keep
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.events.EventReporter
import com.tidal.sdk.player.events.model.Progress
import java.util.Locale
import kotlin.properties.Delegates

@Keep
internal data class PlaybackReport(
    @SerializedName("itemId")
    val actualProductId: String,
    val itemType: String,
    val duration: Int,
    val progressStop: Int = 0,
    val sourceInfo: Map<String, String?>,
) {

    class Handler(
        private val gson: Gson,
        private val sharedPreferences: SharedPreferences,
        private val eventReporter: EventReporter,
    ) {
        var currentPlaybackReport: PlaybackReport? by Delegates.observable(
            gson.fromJson(
                sharedPreferences.getString(KEY_PLAYBACK_REPORT, null),
                PlaybackReport::class.java,
            ),
        ) { _, _, newValue ->
            with(sharedPreferences.edit()) {
                if (newValue == null) {
                    remove(KEY_PLAYBACK_REPORT)
                } else {
                    putString(KEY_PLAYBACK_REPORT, gson.toJson(newValue))
                }
                apply()
            }
        }

        init {
            reportAndClearCurrent()
        }

        fun reportAndClearCurrent() {
            currentPlaybackReport?.run {
                eventReporter.report(
                    Progress.Payload(
                        Progress.Payload.Playback(
                            actualProductId,
                            progressStop,
                            duration,
                            ProductType.valueOf(itemType.uppercase(Locale.ENGLISH)),
                            sourceInfo.run {
                                Progress.Payload.Playback.Source(
                                    get(KEY_SOURCE_INFO_TYPE),
                                    get(KEY_SOURCE_INFO_ID),
                                )
                            },
                        ),
                    ),
                )
                currentPlaybackReport = null
            }
        }

        companion object {
            private const val KEY_PLAYBACK_REPORT = "playback_report"
            const val KEY_SOURCE_INFO_TYPE = "type"
            const val KEY_SOURCE_INFO_ID = "id"
        }
    }
}
