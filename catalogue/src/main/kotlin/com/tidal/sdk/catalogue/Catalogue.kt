package com.tidal.sdk.catalogue

import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.catalogue.generated.apis.AlbumJSONAPI
import com.tidal.sdk.catalogue.generated.apis.ArtistJSONAPI
import com.tidal.sdk.catalogue.generated.apis.ProviderJSONAPI
import com.tidal.sdk.catalogue.generated.apis.TrackJSONAPI
import com.tidal.sdk.catalogue.generated.apis.VideoJSONAPI
import com.tidal.sdk.catalogue.networking.RetrofitProvider

class Catalogue(credentialsProvider: CredentialsProvider) {

    private val retrofit by lazy {
        RetrofitProvider().provideRetrofit(
            baseUrl,
            credentialsProvider,
        )
    }

    /**
     * Returns an instance of the [AlbumJSONAPI] which can be used
     * to make API calls to the Album API
     */
    fun createAlbumAPI(): AlbumJSONAPI {
        return retrofit.create(AlbumJSONAPI::class.java)
    }

    /**
     * Returns an instance of the [ArtistJSONAPI] which can be used
     * to make API calls to the Artist
     * API
     */
    fun createArtistAPI(): ArtistJSONAPI {
        return retrofit.create(ArtistJSONAPI::class.java)
    }

    /**
     * Returns an instance of the [ProviderJSONAPI] which can be used
     * to make API calls to the Provider API
     */
    fun createProviderAPI(): ProviderJSONAPI {
        return retrofit.create(ProviderJSONAPI::class.java)
    }

    /**
     * Returns an instance of the [TrackJSONAPI] which can be used
     * to make API calls to the Track API
     */
    fun createTrackAPI(): TrackJSONAPI {
        return retrofit.create(TrackJSONAPI::class.java)
    }

    /**
     * Returns an instance of the [VideoJSONAPI] which can be used
     * to make API calls to the Video API
     */
    fun createVideoAPI(): VideoJSONAPI {
        return retrofit.create(VideoJSONAPI::class.java)
    }
}
