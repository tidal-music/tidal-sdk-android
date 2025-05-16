package com.tidal.sdk.tidalapi.generated

import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.tidalapi.generated.apis.Albums
import com.tidal.sdk.tidalapi.generated.apis.ArtistRoles
import com.tidal.sdk.tidalapi.generated.apis.Artists
import com.tidal.sdk.tidalapi.generated.apis.Artworks
import com.tidal.sdk.tidalapi.generated.apis.Playlists
import com.tidal.sdk.tidalapi.generated.apis.Providers
import com.tidal.sdk.tidalapi.generated.apis.SearchResults
import com.tidal.sdk.tidalapi.generated.apis.SearchSuggestions
import com.tidal.sdk.tidalapi.generated.apis.Searchresults
import com.tidal.sdk.tidalapi.generated.apis.TrackFiles
import com.tidal.sdk.tidalapi.generated.apis.TrackManifests
import com.tidal.sdk.tidalapi.generated.apis.Tracks
import com.tidal.sdk.tidalapi.generated.apis.UserCollections
import com.tidal.sdk.tidalapi.generated.apis.UserEntitlements
import com.tidal.sdk.tidalapi.generated.apis.UserRecommendations
import com.tidal.sdk.tidalapi.generated.apis.Users
import com.tidal.sdk.tidalapi.generated.apis.Videos
import com.tidal.sdk.tidalapi.networking.RetrofitProvider

class TidalApiClient(credentialsProvider: CredentialsProvider, baseUrl: String = DEFAULT_BASE_URL) {

    private val retrofit by lazy {
        RetrofitProvider().provideRetrofit(baseUrl, credentialsProvider)
    }

    /** Returns an instance of the [Albums] which can be used to make API calls to the */
    fun createAlbums(): Albums {
        return retrofit.create(Albums::class.java)
    }

    /** Returns an instance of the [ArtistRoles] which can be used to make API calls to the */
    fun createArtistRoles(): ArtistRoles {
        return retrofit.create(ArtistRoles::class.java)
    }

    /** Returns an instance of the [Artists] which can be used to make API calls to the */
    fun createArtists(): Artists {
        return retrofit.create(Artists::class.java)
    }

    /** Returns an instance of the [Artworks] which can be used to make API calls to the */
    fun createArtworks(): Artworks {
        return retrofit.create(Artworks::class.java)
    }

    /** Returns an instance of the [Playlists] which can be used to make API calls to the */
    fun createPlaylists(): Playlists {
        return retrofit.create(Playlists::class.java)
    }

    /** Returns an instance of the [Providers] which can be used to make API calls to the */
    fun createProviders(): Providers {
        return retrofit.create(Providers::class.java)
    }

    /** Returns an instance of the [SearchResults] which can be used to make API calls to the */
    fun createSearchResults(): SearchResults {
        return retrofit.create(SearchResults::class.java)
    }

    /** Returns an instance of the [SearchSuggestions] which can be used to make API calls to the */
    fun createSearchSuggestions(): SearchSuggestions {
        return retrofit.create(SearchSuggestions::class.java)
    }

    /** Returns an instance of the [Searchresults] which can be used to make API calls to the */
    fun createSearchresults(): Searchresults {
        return retrofit.create(Searchresults::class.java)
    }

    /** Returns an instance of the [TrackFiles] which can be used to make API calls to the */
    fun createTrackFiles(): TrackFiles {
        return retrofit.create(TrackFiles::class.java)
    }

    /** Returns an instance of the [TrackManifests] which can be used to make API calls to the */
    fun createTrackManifests(): TrackManifests {
        return retrofit.create(TrackManifests::class.java)
    }

    /** Returns an instance of the [Tracks] which can be used to make API calls to the */
    fun createTracks(): Tracks {
        return retrofit.create(Tracks::class.java)
    }

    /** Returns an instance of the [UserCollections] which can be used to make API calls to the */
    fun createUserCollections(): UserCollections {
        return retrofit.create(UserCollections::class.java)
    }

    /** Returns an instance of the [UserEntitlements] which can be used to make API calls to the */
    fun createUserEntitlements(): UserEntitlements {
        return retrofit.create(UserEntitlements::class.java)
    }

    /**
     * Returns an instance of the [UserRecommendations] which can be used to make API calls to the
     */
    fun createUserRecommendations(): UserRecommendations {
        return retrofit.create(UserRecommendations::class.java)
    }

    /** Returns an instance of the [Users] which can be used to make API calls to the */
    fun createUsers(): Users {
        return retrofit.create(Users::class.java)
    }

    /** Returns an instance of the [Videos] which can be used to make API calls to the */
    fun createVideos(): Videos {
        return retrofit.create(Videos::class.java)
    }

    companion object {
        const val DEFAULT_BASE_URL = "https://openapi.tidal.com/v2/"
    }
}
