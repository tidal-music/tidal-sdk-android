package com.tidal.sdk.auth.di

import android.content.Context
import com.tidal.sdk.auth.TidalAuth
import com.tidal.sdk.auth.model.AuthConfig
import com.tidal.sdk.auth.network.NetworkingJobHandler
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AuthModule::class,
        StorageModule::class,
        LoginModule::class,
        NetworkModule::class,
        CredentialsModule::class,
    ],
)
interface AuthComponent {

    fun inject(tidalAuth: TidalAuth)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context,
            @BindsInstance config: AuthConfig,
            @BindsInstance jobHandler: NetworkingJobHandler = NetworkingJobHandler(),
        ): AuthComponent
    }
}
