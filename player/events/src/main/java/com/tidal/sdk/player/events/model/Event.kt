package com.tidal.sdk.player.events.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.tidal.sdk.eventproducer.model.ConsentCategory
import com.tidal.sdk.player.common.model.BaseMediaProduct.Extras
import java.util.UUID

@Suppress("UnusedPrivateMember")
sealed class Event<T : Event.Payload>(
    @Transient
    val name: String,
    private val group: Group,
    private val version: Version,
) {

    protected abstract val ts: Long
    protected abstract val uuid: UUID
    protected abstract val user: User
    protected abstract val client: Client
    protected abstract val extras: Extras?
    abstract val payload: T

    val consentCategory: ConsentCategory
        get() = when (group) {
            Group.STREAMING_METRICS -> ConsentCategory.PERFORMANCE
            Group.PLAY_LOG -> ConsentCategory.NECESSARY
        }

    @JvmInline
    internal value class Version(val value: Int)

    @Keep
    internal enum class Group {

        @SerializedName("streaming_metrics")
        STREAMING_METRICS,

        @SerializedName("play_log")
        PLAY_LOG,
    }

    sealed interface Payload
}
