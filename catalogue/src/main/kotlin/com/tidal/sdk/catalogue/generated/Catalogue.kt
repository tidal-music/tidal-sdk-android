package com.tidal.sdk.catalogue.generated

import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.catalogue.networking.RetrofitProvider

import com.tidal.sdk.catalogue.generated.apis.AlbumJSONAPI
import com.tidal.sdk.catalogue.generated.apis.ArtistJSONAPI
import com.tidal.sdk.catalogue.generated.apis.ProviderJSONAPI
import com.tidal.sdk.catalogue.generated.apis.TrackJSONAPI
import com.tidal.sdk.catalogue.generated.apis.VideoJSONAPI

class Catalogue(credentialsProvider: CredentialsProvider, baseUrl: String = DEFAULT_BASE_URL) {

    private val retrofit by lazy {
        RetrofitProvider().provideRetrofit(
            baseUrl,
            credentialsProvider,
        )
    }

    /**
    * Returns an instance of the [AlbumJSONAPI] which can be used
    * to make API calls to the 
    */
    fun createAlbumJSONAPI(): AlbumJSONAPI {
        return retrofit.create(AlbumJSONAPI::class.java)
    }
    /**
    * Returns an instance of the [ArtistJSONAPI] which can be used
    * to make API calls to the 
    */
    fun createArtistJSONAPI(): ArtistJSONAPI {
        return retrofit.create(ArtistJSONAPI::class.java)
    }
    /**
    * Returns an instance of the [ProviderJSONAPI] which can be used
    * to make API calls to the 
    */
    fun createProviderJSONAPI(): ProviderJSONAPI {
        return retrofit.create(ProviderJSONAPI::class.java)
    }
    /**
    * Returns an instance of the [TrackJSONAPI] which can be used
    * to make API calls to the 
    */
    fun createTrackJSONAPI(): TrackJSONAPI {
        return retrofit.create(TrackJSONAPI::class.java)
    }
    /**
    * Returns an instance of the [VideoJSONAPI] which can be used
    * to make API calls to the 
    */
    fun createVideoJSONAPI(): VideoJSONAPI {
        return retrofit.create(VideoJSONAPI::class.java)
    }

    companion object {
        const val DEFAULT_BASE_URL = "https://openapi.tidal.com/v2/"
    }

}
