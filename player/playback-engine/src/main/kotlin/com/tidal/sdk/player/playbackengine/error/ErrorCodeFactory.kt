package com.tidal.sdk.player.playbackengine.error

internal class ErrorCodeFactory {

    fun createForOther(extra: Extra, errorCode: Int? = -1) =
        "${Type.Other.value}:${extra.value}:$errorCode"

    fun createForApiError(status: Int?, subStatus: Int) =
        "${Type.ApiError.value}:$status:$subStatus"

    fun createForTimeout(extra: Extra, errorCode: Int? = -1) =
        "${Type.Timeout.value}:${extra.value}:$errorCode"

    fun createForNetwork(extra: Extra, errorCode: Int? = -1) =
        "${Type.Network.value}:${extra.value}:$errorCode"

    fun createForInvalidResponseCode(responseCode: Int, errorCode: Int?) =
        "${Type.InvalidResponseCode.value}:$responseCode:$errorCode"

    fun createForHttpDataSource(type: Int, errorCode: Int?) =
        "${Type.HttpDataSource.value}:$type:$errorCode"

    fun createForDrmSession(extra: Extra, errorCode: Int?) =
        "${Type.DrmSession.value}:${extra.value}:$errorCode"

    fun createForUnexpectedLoader(extra: Extra, errorCode: Int?) =
        "${Type.UnexpectedLoader.value}:${extra.value}:$errorCode"

    fun createForBehindLiveWindow(extra: Extra, errorCode: Int?) =
        "${Type.BehindLiveWindow.value}:${extra.value}:$errorCode"

    fun createForAudioSinkInitialization(extra: Extra, errorCode: Int?) =
        "${Type.AudioSinkInitialization.value}:${extra.value}:$errorCode"

    /**
     * Describes what type of error we are dealing with.
     */
    @Suppress("MagicNumber")
    private sealed class Type(val value: Int) {
        object Other : Type(-1)
        object ApiError : Type(0)
        object Timeout : Type(1)
        object Network : Type(2)
        object InvalidResponseCode : Type(3)
        object HttpDataSource : Type(4)
        object DrmSession : Type(5)
        object UnexpectedLoader : Type(6)
        object BehindLiveWindow : Type(7)
        object AudioSinkInitialization : Type(8)
    }

    /**
     * Describes the where or some extra details of the error,
     * so that we can easily identify exactly what has happened.
     */
    @Suppress("MagicNumber")
    sealed class Extra(val value: Int) {
        object Other : Extra(-1)
        object PlaybackInfoFetch : Extra(0)
        object DrmLicenseFetch : Extra(1)
        object PlayerSourceError : Extra(2)
        object PlayerRendererError : Extra(3)
        object PlayerUnexpectedError : Extra(4)
        object PlayerOtherError : Extra(5)
        object Broadcast : Extra(6)
        object Storage : Extra(7)
    }
}
