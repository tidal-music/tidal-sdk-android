package com.tidal.sdk.player.events.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.tidal.sdk.player.common.model.ProductType
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.UUID

@Keep
@Suppress("UnusedPrivateMember")
data class Progress @AssistedInject internal constructor(
    @Assisted override val ts: Long,
    @Assisted override val uuid: UUID,
    @Assisted override val user: User,
    @Assisted override val client: Client,
    @Assisted override val payload: Payload,
) : Playback(name = "progress") {

    @Keep
    data class Payload(private val playback: Playback) : Event.Payload {

        @Keep
        data class Playback(
            @SerializedName("id")
            private val mediaProductId: String,
            @SerializedName("playedMS")
            private val playedMs: Int,
            @SerializedName("durationMS")
            private val durationMs: Int,
            @SerializedName("type")
            private val productType: ProductType,
            private val source: Source,
        ) {

            @Keep
            data class Source(private val type: String?, private val id: String?)
        }
    }

    @AssistedFactory
    internal interface Factory {

        fun create(ts: Long, uuid: UUID, user: User, client: Client, payload: Payload): Progress
    }
}
