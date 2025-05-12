package com.tidal.sdk.player.streamingapi.di

import com.tidal.sdk.player.streamingapi.drm.api.DrmLicenseService
import com.tidal.sdk.player.streamingapi.drm.repository.DrmLicenseRepository
import com.tidal.sdk.player.streamingapi.drm.repository.DrmLicenseRepositoryDefault
import com.tidal.sdk.player.streamingapi.playbackinfo.mapper.ApiErrorMapper
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
internal object DrmLicenseModule {

    @Provides
    @Reusable
    fun provideDrmLicenseRepository(
        drmLicenseService: DrmLicenseService,
        apiErrorMapperLazy: Lazy<ApiErrorMapper>,
    ): DrmLicenseRepository {
        return DrmLicenseRepositoryDefault(drmLicenseService, apiErrorMapperLazy)
    }
}
