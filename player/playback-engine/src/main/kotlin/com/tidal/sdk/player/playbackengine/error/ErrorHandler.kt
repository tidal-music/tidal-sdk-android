package com.tidal.sdk.player.playbackengine.error

import androidx.media3.common.PlaybackException
import androidx.media3.datasource.HttpDataSource
import androidx.media3.exoplayer.ExoPlaybackException
import androidx.media3.exoplayer.audio.AudioSink
import androidx.media3.exoplayer.drm.DrmSession
import androidx.media3.exoplayer.source.BehindLiveWindowException
import androidx.media3.exoplayer.upstream.Loader
import com.tidal.sdk.player.common.model.ApiError
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.playbackengine.model.Event
import com.tidal.sdk.player.playbackengine.offline.OfflineExpiredException
import com.tidal.sdk.player.playbackengine.offline.StorageException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.HttpException

private const val TOO_MANY_REQUESTS_STATUS = 429

internal class ErrorHandler(private val errorCodeFactory: ErrorCodeFactory) {

    @Suppress("MagicNumber") private val responseCode5xxRange = (500..599)

    fun getErrorCode(throwable: Throwable?, extra: ErrorCodeFactory.Extra) =
        when (throwable) {
            is ApiError ->
                errorCodeFactory.createForApiError(throwable.status, throwable.subStatus.code)

            is SocketTimeoutException -> errorCodeFactory.createForTimeout(extra)

            is ConnectException,
            is UnknownHostException -> errorCodeFactory.createForNetwork(extra)

            else -> errorCodeFactory.createForOther(extra)
        }

    @Suppress("ComplexMethod", "LongMethod")
    fun getErrorEvent(throwable: Throwable?, productType: ProductType? = null): Event.Error {
        var crawler: Throwable? = throwable
        var errorEvent: Lazy<Event.Error?>? = null

        val playbackExceptionErrorCode = (throwable as? PlaybackException)?.errorCode
        val exoPlaybackExceptionType = (throwable as? ExoPlaybackException)?.type
        val exoPlaybackExceptionExtra = exoPlaybackExceptionType.toErrorCodeExtra()

        do {
            val cause = crawler
            errorEvent =
                when (cause) {
                    is ApiError -> lazy { handleApiError(cause) }

                    is OfflineExpiredException ->
                        lazy {
                            Event.Error.NotAllowed(
                                errorCodeFactory.createForOther(
                                    ErrorCodeFactory.Extra.OfflineExpired,
                                    playbackExceptionErrorCode,
                                ),
                                cause,
                            )
                        }

                    is StorageException ->
                        lazy {
                            Event.Error.NotAllowed(
                                errorCodeFactory.createForOther(
                                    ErrorCodeFactory.Extra.Storage,
                                    playbackExceptionErrorCode,
                                ),
                                cause,
                            )
                        }

                    is HttpDataSource.InvalidResponseCodeException ->
                        lazy {
                            Event.Error.Retryable(
                                errorCodeFactory.createForInvalidResponseCode(
                                    cause.responseCode,
                                    playbackExceptionErrorCode,
                                ),
                                cause,
                            )
                        }

                    is HttpDataSource.HttpDataSourceException ->
                        lazy {
                            Event.Error.Network(
                                errorCodeFactory.createForHttpDataSource(
                                    cause.type,
                                    playbackExceptionErrorCode,
                                ),
                                cause,
                            )
                        }

                    is DrmSession.DrmSessionException ->
                        lazy {
                            val errorCodeForDrmSession =
                                errorCodeFactory.createForDrmSession(
                                    exoPlaybackExceptionExtra,
                                    playbackExceptionErrorCode,
                                )
                            when (exoPlaybackExceptionType) {
                                ExoPlaybackException.TYPE_SOURCE ->
                                    Event.Error.Retryable(errorCodeForDrmSession, cause)

                                ExoPlaybackException.TYPE_RENDERER ->
                                    Event.Error.NotAllowed(errorCodeForDrmSession, cause)

                                else -> null
                            }
                        }

                    is Loader.UnexpectedLoaderException ->
                        lazy {
                            if (productType == ProductType.BROADCAST) {
                                Event.Error.NotAllowed(
                                    errorCodeFactory.createForUnexpectedLoader(
                                        ErrorCodeFactory.Extra.Broadcast,
                                        playbackExceptionErrorCode,
                                    ),
                                    cause,
                                )
                            } else {
                                Event.Error.Retryable(
                                    errorCodeFactory.createForUnexpectedLoader(
                                        ErrorCodeFactory.Extra.Other,
                                        playbackExceptionErrorCode,
                                    ),
                                    cause,
                                )
                            }
                        }

                    is BehindLiveWindowException ->
                        lazy {
                            Event.Error.Retryable(
                                errorCodeFactory.createForBehindLiveWindow(
                                    exoPlaybackExceptionExtra,
                                    playbackExceptionErrorCode,
                                ),
                                cause,
                            )
                        }

                    is SocketTimeoutException ->
                        lazy {
                            Event.Error.Network(
                                errorCodeFactory.createForTimeout(
                                    exoPlaybackExceptionExtra,
                                    playbackExceptionErrorCode,
                                ),
                                cause,
                            )
                        }

                    is ConnectException,
                    is UnknownHostException ->
                        lazy {
                            Event.Error.Network(
                                errorCodeFactory.createForNetwork(
                                    exoPlaybackExceptionExtra,
                                    playbackExceptionErrorCode,
                                ),
                                cause,
                            )
                        }

                    is AudioSink.InitializationException ->
                        lazy {
                            Event.Error.NotAllowed(
                                errorCodeFactory.createForAudioSinkInitialization(
                                    exoPlaybackExceptionExtra,
                                    playbackExceptionErrorCode,
                                ),
                                cause,
                            )
                        }

                    is ExoPlaybackException ->
                        lazy {
                            if (exoPlaybackExceptionType == ExoPlaybackException.TYPE_SOURCE) {
                                when (playbackExceptionErrorCode) {
                                    ExoPlaybackException.ERROR_CODE_IO_UNSPECIFIED ->
                                        Event.Error.Retryable(
                                            errorCodeFactory.createForOther(
                                                exoPlaybackExceptionExtra,
                                                playbackExceptionErrorCode,
                                            ),
                                            cause,
                                        )

                                    ExoPlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED ->
                                        Event.Error.Network(
                                            errorCodeFactory.createForNetwork(
                                                exoPlaybackExceptionExtra,
                                                playbackExceptionErrorCode,
                                            ),
                                            cause,
                                        )

                                    ExoPlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT ->
                                        Event.Error.Network(
                                            errorCodeFactory.createForTimeout(
                                                exoPlaybackExceptionExtra,
                                                playbackExceptionErrorCode,
                                            ),
                                            cause,
                                        )

                                    else -> null
                                }
                            } else {
                                null
                            }
                        }

                    else -> errorEvent
                }
            crawler = crawler?.cause
        } while (crawler != null)

        return errorEvent?.value
            ?: Event.Error.Unexpected(
                errorCodeFactory.createForOther(
                    exoPlaybackExceptionExtra,
                    playbackExceptionErrorCode,
                ),
                throwable,
            )
    }

    @Suppress("ComplexMethod", "LongMethod")
    private fun handleApiError(apiError: ApiError): Event.Error {
        val errorCode = errorCodeFactory.createForApiError(apiError.status, apiError.subStatus.code)

        return when (apiError.subStatus) {
            ApiError.SubStatus.GenericPlaybackError -> Event.Error.NotAllowed(errorCode, apiError)

            ApiError.SubStatus.NoStreamingPrivileges -> Event.Error.NotAllowed(errorCode, apiError)

            ApiError.SubStatus.UserClientNotAuthorizedForOffline ->
                Event.Error.NotAllowed(errorCode, apiError)

            ApiError.SubStatus.UserMonthlyStreamQuotaExceeded ->
                Event.Error.MonthlyStreamQuotaExceeded(errorCode, apiError)

            ApiError.SubStatus.SessionNotFound -> Event.Error.NotAllowed(errorCode, apiError)

            ApiError.SubStatus.UserNotFound -> Event.Error.NotAllowed(errorCode, apiError)

            ApiError.SubStatus.ClientNotFound -> Event.Error.NotAllowed(errorCode, apiError)

            ApiError.SubStatus.ProductNotFound -> Event.Error.NotAllowed(errorCode, apiError)

            ApiError.SubStatus.NoContentAvailableInProduct ->
                Event.Error.NotAllowed(errorCode, apiError)

            ApiError.SubStatus.NoContentMatchingRequest ->
                Event.Error.NotAllowed(errorCode, apiError)

            ApiError.SubStatus.NoContentMatchingSubscriptionLocation ->
                Event.Error.ContentNotAvailableInLocation(errorCode, apiError)

            ApiError.SubStatus.NoContentMatchingSubscriptionConfiguration ->
                Event.Error.ContentNotAvailableForSubscription(errorCode, apiError)

            ApiError.SubStatus.NoContentMatchingClient ->
                Event.Error.NotAllowed(errorCode, apiError)

            ApiError.SubStatus.NoContentMatchingPrePaywallLocation ->
                Event.Error.ContentNotAvailableInLocation(errorCode, apiError)

            is ApiError.SubStatus.Unknown ->
                if (
                    apiError.status in responseCode5xxRange ||
                        (apiError.cause as HttpException).code() == TOO_MANY_REQUESTS_STATUS
                ) {
                    Event.Error.Retryable(errorCode, apiError)
                } else {
                    Event.Error.Unexpected(errorCode, apiError)
                }
        }
    }

    private fun Int?.toErrorCodeExtra() =
        when (this) {
            ExoPlaybackException.TYPE_SOURCE -> ErrorCodeFactory.Extra.PlayerSourceError
            ExoPlaybackException.TYPE_RENDERER -> ErrorCodeFactory.Extra.PlayerRendererError
            ExoPlaybackException.TYPE_UNEXPECTED -> ErrorCodeFactory.Extra.PlayerUnexpectedError
            ExoPlaybackException.TYPE_REMOTE -> ErrorCodeFactory.Extra.PlayerOtherError
            else -> ErrorCodeFactory.Extra.Other
        }
}
