package com.tidal.sdk.player.playbackengine.player.di

import android.os.Handler
import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayer
import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayerStateUpdateRunnable
import dagger.Subcomponent
import javax.inject.Qualifier
import javax.inject.Scope

@Subcomponent(
    modules = [
        ExtendedExoPlayerModule::class,
        MediaSourcererModule::class,
        RendererModule::class,
        ProgressiveModule::class,
    ],
)
@ExtendedExoPlayerComponent.Scoped
internal interface ExtendedExoPlayerComponent {

    val extendedExoPlayer: ExtendedExoPlayer
    val handler: Handler
    val stateUpdateRunnable: ExtendedExoPlayerStateUpdateRunnable

    @Subcomponent.Factory
    interface Factory {

        fun create(): ExtendedExoPlayerComponent
    }

    @Scope
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Scoped

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Local
}
