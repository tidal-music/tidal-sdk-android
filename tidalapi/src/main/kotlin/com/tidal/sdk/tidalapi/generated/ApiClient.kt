package com.tidal.sdk.tidalapi.generated

import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.tidalapi.generated.apis.Albums
import com.tidal.sdk.tidalapi.generated.apis.Appreciations
import com.tidal.sdk.tidalapi.generated.apis.ArtistBiographies
import com.tidal.sdk.tidalapi.generated.apis.ArtistClaims
import com.tidal.sdk.tidalapi.generated.apis.ArtistRoles
import com.tidal.sdk.tidalapi.generated.apis.Artists
import com.tidal.sdk.tidalapi.generated.apis.Artworks
import com.tidal.sdk.tidalapi.generated.apis.Credits
import com.tidal.sdk.tidalapi.generated.apis.DynamicPages
import com.tidal.sdk.tidalapi.generated.apis.Genres
import com.tidal.sdk.tidalapi.generated.apis.Lyrics
import com.tidal.sdk.tidalapi.generated.apis.ManualArtistClaims
import com.tidal.sdk.tidalapi.generated.apis.PlayQueues
import com.tidal.sdk.tidalapi.generated.apis.Playlists
import com.tidal.sdk.tidalapi.generated.apis.Providers
import com.tidal.sdk.tidalapi.generated.apis.Reactions
import com.tidal.sdk.tidalapi.generated.apis.SavedShares
import com.tidal.sdk.tidalapi.generated.apis.SearchResults
import com.tidal.sdk.tidalapi.generated.apis.SearchSuggestions
import com.tidal.sdk.tidalapi.generated.apis.Shares
import com.tidal.sdk.tidalapi.generated.apis.StripeConnections
import com.tidal.sdk.tidalapi.generated.apis.TrackFiles
import com.tidal.sdk.tidalapi.generated.apis.TrackManifests
import com.tidal.sdk.tidalapi.generated.apis.TrackSourceFiles
import com.tidal.sdk.tidalapi.generated.apis.TrackStatistics
import com.tidal.sdk.tidalapi.generated.apis.Tracks
import com.tidal.sdk.tidalapi.generated.apis.TracksMetadataStatus
import com.tidal.sdk.tidalapi.generated.apis.UserCollectionFolders
import com.tidal.sdk.tidalapi.generated.apis.UserCollections
import com.tidal.sdk.tidalapi.generated.apis.UserEntitlements
import com.tidal.sdk.tidalapi.generated.apis.UserRecommendations
import com.tidal.sdk.tidalapi.generated.apis.UserReports
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

    /** Returns an instance of the [Appreciations] which can be used to make API calls to the */
    fun createAppreciations(): Appreciations {
        return retrofit.create(Appreciations::class.java)
    }

    /** Returns an instance of the [ArtistBiographies] which can be used to make API calls to the */
    fun createArtistBiographies(): ArtistBiographies {
        return retrofit.create(ArtistBiographies::class.java)
    }

    /** Returns an instance of the [ArtistClaims] which can be used to make API calls to the */
    fun createArtistClaims(): ArtistClaims {
        return retrofit.create(ArtistClaims::class.java)
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

    /** Returns an instance of the [Credits] which can be used to make API calls to the */
    fun createCredits(): Credits {
        return retrofit.create(Credits::class.java)
    }

    /** Returns an instance of the [DynamicPages] which can be used to make API calls to the */
    fun createDynamicPages(): DynamicPages {
        return retrofit.create(DynamicPages::class.java)
    }

    /** Returns an instance of the [Genres] which can be used to make API calls to the */
    fun createGenres(): Genres {
        return retrofit.create(Genres::class.java)
    }

    /** Returns an instance of the [Lyrics] which can be used to make API calls to the */
    fun createLyrics(): Lyrics {
        return retrofit.create(Lyrics::class.java)
    }

    /**
     * Returns an instance of the [ManualArtistClaims] which can be used to make API calls to the
     */
    fun createManualArtistClaims(): ManualArtistClaims {
        return retrofit.create(ManualArtistClaims::class.java)
    }

    /** Returns an instance of the [PlayQueues] which can be used to make API calls to the */
    fun createPlayQueues(): PlayQueues {
        return retrofit.create(PlayQueues::class.java)
    }

    /** Returns an instance of the [Playlists] which can be used to make API calls to the */
    fun createPlaylists(): Playlists {
        return retrofit.create(Playlists::class.java)
    }

    /** Returns an instance of the [Providers] which can be used to make API calls to the */
    fun createProviders(): Providers {
        return retrofit.create(Providers::class.java)
    }

    /** Returns an instance of the [Reactions] which can be used to make API calls to the */
    fun createReactions(): Reactions {
        return retrofit.create(Reactions::class.java)
    }

    /** Returns an instance of the [SavedShares] which can be used to make API calls to the */
    fun createSavedShares(): SavedShares {
        return retrofit.create(SavedShares::class.java)
    }

    /** Returns an instance of the [SearchResults] which can be used to make API calls to the */
    fun createSearchResults(): SearchResults {
        return retrofit.create(SearchResults::class.java)
    }

    /** Returns an instance of the [SearchSuggestions] which can be used to make API calls to the */
    fun createSearchSuggestions(): SearchSuggestions {
        return retrofit.create(SearchSuggestions::class.java)
    }

    /** Returns an instance of the [Shares] which can be used to make API calls to the */
    fun createShares(): Shares {
        return retrofit.create(Shares::class.java)
    }

    /** Returns an instance of the [StripeConnections] which can be used to make API calls to the */
    fun createStripeConnections(): StripeConnections {
        return retrofit.create(StripeConnections::class.java)
    }

    /** Returns an instance of the [TrackFiles] which can be used to make API calls to the */
    fun createTrackFiles(): TrackFiles {
        return retrofit.create(TrackFiles::class.java)
    }

    /** Returns an instance of the [TrackManifests] which can be used to make API calls to the */
    fun createTrackManifests(): TrackManifests {
        return retrofit.create(TrackManifests::class.java)
    }

    /** Returns an instance of the [TrackSourceFiles] which can be used to make API calls to the */
    fun createTrackSourceFiles(): TrackSourceFiles {
        return retrofit.create(TrackSourceFiles::class.java)
    }

    /** Returns an instance of the [TrackStatistics] which can be used to make API calls to the */
    fun createTrackStatistics(): TrackStatistics {
        return retrofit.create(TrackStatistics::class.java)
    }

    /** Returns an instance of the [Tracks] which can be used to make API calls to the */
    fun createTracks(): Tracks {
        return retrofit.create(Tracks::class.java)
    }

    /**
     * Returns an instance of the [TracksMetadataStatus] which can be used to make API calls to the
     */
    fun createTracksMetadataStatus(): TracksMetadataStatus {
        return retrofit.create(TracksMetadataStatus::class.java)
    }

    /**
     * Returns an instance of the [UserCollectionFolders] which can be used to make API calls to the
     */
    fun createUserCollectionFolders(): UserCollectionFolders {
        return retrofit.create(UserCollectionFolders::class.java)
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

    /** Returns an instance of the [UserReports] which can be used to make API calls to the */
    fun createUserReports(): UserReports {
        return retrofit.create(UserReports::class.java)
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
