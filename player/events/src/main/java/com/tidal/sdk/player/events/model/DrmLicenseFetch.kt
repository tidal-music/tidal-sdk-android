package com.tidal.sdk.player.events.model

import androidx.annotation.Keep
import com.tidal.sdk.player.common.model.Extras
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.UUID

@Keep
@Suppress("UnusedPrivateMember")
data class DrmLicenseFetch
@AssistedInject
internal constructor(
    @Assisted override val ts: Long,
    @Assisted override val uuid: UUID,
    @Assisted override val user: User,
    @Assisted override val client: Client,
    @Assisted override val payload: Payload,
    @Assisted override val extras: Extras?,
) : StreamingMetrics<DrmLicenseFetch.Payload>("drm_license_fetch") {

    @Keep
    data class Payload(
        override val streamingSessionId: String,
        private val startTimestamp: Long,
        private val endTimestamp: Long,
        private val endReason: EndReason,
        private val errorMessage: String?,
        private val errorCode: String?,
    ) : StreamingMetrics.Payload

    @AssistedFactory
    interface Factory {

        @Suppress("LongParameterList")
        fun create(
            ts: Long,
            uuid: UUID,
            user: User,
            client: Client,
            payload: Payload,
            extras: Extras?,
        ): DrmLicenseFetch
    }
}
