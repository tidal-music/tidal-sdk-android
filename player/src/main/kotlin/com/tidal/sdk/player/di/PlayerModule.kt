package com.tidal.sdk.player.di

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.common.model.BaseMediaProduct
import com.tidal.sdk.player.commonandroid.Base64Codec
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.serialization.ExtrasSerializationAdapter
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
internal object PlayerModule {

    @Provides
    @Reusable
    fun connectivityManager(context: Context): ConnectivityManager =
        context.getSystemService(ConnectivityManager::class.java)

    @Provides
    @Reusable
    fun gson(): Gson {
        return GsonBuilder()
            .registerTypeHierarchyAdapter(
                BaseMediaProduct.Extras::class.java,
                ExtrasSerializationAdapter(),
            )
            .disableHtmlEscaping()
            .create()
    }

    @Provides
    @Reusable
    fun uuidWrapper() = UUIDWrapper()

    @Provides
    @Reusable
    fun trueTimeWrapper() = TrueTimeWrapper()

    @Provides
    @Reusable
    fun base64Codec() = Base64Codec()
}
