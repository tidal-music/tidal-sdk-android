package com.tidal.sdk.player.playbackengine.di

import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayerFactory
import com.tidal.sdk.player.playbackengine.player.di.ExtendedExoPlayerComponent
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module(subcomponents = [ExtendedExoPlayerComponent::class])
internal object ExtendedExoPlayerFactoryModule {

    @Provides
    @Reusable
    fun extendedExoPlayerFactory(
        extendedExoPlayerComponentFactory: ExtendedExoPlayerComponent.Factory
    ) = ExtendedExoPlayerFactory(extendedExoPlayerComponentFactory)
}
