package com.tidal.sdk.auth.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.tidal.sdk.auth.model.AuthConfig
import com.tidal.sdk.auth.storage.DefaultTokensStore
import com.tidal.sdk.auth.storage.TokensStore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class StorageModule {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    @Provides
    @Singleton
    fun provideCredentialsStore(
        authConfig: AuthConfig,
        sharedPreferences: SharedPreferences,
    ): TokensStore = DefaultTokensStore(authConfig.credentialsKey, sharedPreferences)

    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(
        context: Context,
        authConfig: AuthConfig,
    ): SharedPreferences {
        return EncryptedSharedPreferences.create(
            "${authConfig.credentialsKey}_$PREFS_FILE_NAME",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    companion object {

        private const val PREFS_FILE_NAME = "tidal_auth_prefs"
    }
}
