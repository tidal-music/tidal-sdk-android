package com.tidal.sdk.tidalapi.generated

import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.tidalapi.generated.apis.AcceptedTermsApi
import com.tidal.sdk.tidalapi.generated.apis.AlbumStatisticsApi
import com.tidal.sdk.tidalapi.generated.apis.AlbumsApi
import com.tidal.sdk.tidalapi.generated.apis.AppreciationsApi
import com.tidal.sdk.tidalapi.generated.apis.ArtistBiographiesApi
import com.tidal.sdk.tidalapi.generated.apis.ArtistClaimStatusesApi
import com.tidal.sdk.tidalapi.generated.apis.ArtistClaimsApi
import com.tidal.sdk.tidalapi.generated.apis.ArtistRolesApi
import com.tidal.sdk.tidalapi.generated.apis.ArtistsApi
import com.tidal.sdk.tidalapi.generated.apis.ArtworksApi
import com.tidal.sdk.tidalapi.generated.apis.ClientsApi
import com.tidal.sdk.tidalapi.generated.apis.CollaborationInviteRedemptionsApi
import com.tidal.sdk.tidalapi.generated.apis.CollaborationInvitesApi
import com.tidal.sdk.tidalapi.generated.apis.CommentsApi
import com.tidal.sdk.tidalapi.generated.apis.ContentClaimsApi
import com.tidal.sdk.tidalapi.generated.apis.CreditsApi
import com.tidal.sdk.tidalapi.generated.apis.DownloadsApi
import com.tidal.sdk.tidalapi.generated.apis.DspSharingLinksApi
import com.tidal.sdk.tidalapi.generated.apis.DynamicModulesApi
import com.tidal.sdk.tidalapi.generated.apis.DynamicPagesApi
import com.tidal.sdk.tidalapi.generated.apis.GenresApi
import com.tidal.sdk.tidalapi.generated.apis.InstallationsApi
import com.tidal.sdk.tidalapi.generated.apis.LyricsApi
import com.tidal.sdk.tidalapi.generated.apis.ManualArtistClaimsApi
import com.tidal.sdk.tidalapi.generated.apis.OfflineTasksApi
import com.tidal.sdk.tidalapi.generated.apis.PlayQueuesApi
import com.tidal.sdk.tidalapi.generated.apis.PlaylistsApi
import com.tidal.sdk.tidalapi.generated.apis.PriceConfigurationsApi
import com.tidal.sdk.tidalapi.generated.apis.ProviderOwnersApi
import com.tidal.sdk.tidalapi.generated.apis.ProviderProductInfosApi
import com.tidal.sdk.tidalapi.generated.apis.ProvidersApi
import com.tidal.sdk.tidalapi.generated.apis.PurchasesApi
import com.tidal.sdk.tidalapi.generated.apis.ReactionsApi
import com.tidal.sdk.tidalapi.generated.apis.SavedSharesApi
import com.tidal.sdk.tidalapi.generated.apis.ScopesApi
import com.tidal.sdk.tidalapi.generated.apis.SearchHistoryEntriesApi
import com.tidal.sdk.tidalapi.generated.apis.SearchResultsApi
import com.tidal.sdk.tidalapi.generated.apis.SearchSuggestionsApi
import com.tidal.sdk.tidalapi.generated.apis.SharesApi
import com.tidal.sdk.tidalapi.generated.apis.SquareConnectionsApi
import com.tidal.sdk.tidalapi.generated.apis.StripeConnectionsApi
import com.tidal.sdk.tidalapi.generated.apis.StripeDashboardLinksApi
import com.tidal.sdk.tidalapi.generated.apis.TemporaryUserTokensApi
import com.tidal.sdk.tidalapi.generated.apis.TermsApi
import com.tidal.sdk.tidalapi.generated.apis.TrackFilesApi
import com.tidal.sdk.tidalapi.generated.apis.TrackManifestsApi
import com.tidal.sdk.tidalapi.generated.apis.TrackSourceFilesApi
import com.tidal.sdk.tidalapi.generated.apis.TrackStatisticsApi
import com.tidal.sdk.tidalapi.generated.apis.TracksApi
import com.tidal.sdk.tidalapi.generated.apis.TracksMetadataStatusApi
import com.tidal.sdk.tidalapi.generated.apis.UsageRulesApi
import com.tidal.sdk.tidalapi.generated.apis.UserCollectionAlbumsApi
import com.tidal.sdk.tidalapi.generated.apis.UserCollectionArtistsApi
import com.tidal.sdk.tidalapi.generated.apis.UserCollectionFoldersApi
import com.tidal.sdk.tidalapi.generated.apis.UserCollectionPlaylistsApi
import com.tidal.sdk.tidalapi.generated.apis.UserCollectionSaveForLatersApi
import com.tidal.sdk.tidalapi.generated.apis.UserCollectionTracksApi
import com.tidal.sdk.tidalapi.generated.apis.UserCollectionVideosApi
import com.tidal.sdk.tidalapi.generated.apis.UserCollectionsApi
import com.tidal.sdk.tidalapi.generated.apis.UserDailyMixesApi
import com.tidal.sdk.tidalapi.generated.apis.UserDataExportRequestsApi
import com.tidal.sdk.tidalapi.generated.apis.UserDiscoveryMixesApi
import com.tidal.sdk.tidalapi.generated.apis.UserNewReleaseMixesApi
import com.tidal.sdk.tidalapi.generated.apis.UserOfflineMixesApi
import com.tidal.sdk.tidalapi.generated.apis.UserRecommendationBlocksApi
import com.tidal.sdk.tidalapi.generated.apis.UserRecommendationsApi
import com.tidal.sdk.tidalapi.generated.apis.UserReportsApi
import com.tidal.sdk.tidalapi.generated.apis.UsersApi
import com.tidal.sdk.tidalapi.generated.apis.VideoManifestsApi
import com.tidal.sdk.tidalapi.generated.apis.VideosApi
import com.tidal.sdk.tidalapi.networking.RetrofitProvider

class TidalApiClient(
    credentialsProvider: CredentialsProvider,
    baseUrl: String = DEFAULT_BASE_URL,
    retrofitProvider: RetrofitProvider = RetrofitProvider(),
) {

    private val retrofit by lazy { retrofitProvider.provideRetrofit(baseUrl, credentialsProvider) }

    /** Returns an instance of the [AcceptedTermsApi] which can be used to make API calls to the */
    fun createAcceptedTermsApi(): AcceptedTermsApi {
        return retrofit.create(AcceptedTermsApi::class.java)
    }

    /**
     * Returns an instance of the [AlbumStatisticsApi] which can be used to make API calls to the
     */
    fun createAlbumStatisticsApi(): AlbumStatisticsApi {
        return retrofit.create(AlbumStatisticsApi::class.java)
    }

    /** Returns an instance of the [AlbumsApi] which can be used to make API calls to the */
    fun createAlbumsApi(): AlbumsApi {
        return retrofit.create(AlbumsApi::class.java)
    }

    /** Returns an instance of the [AppreciationsApi] which can be used to make API calls to the */
    fun createAppreciationsApi(): AppreciationsApi {
        return retrofit.create(AppreciationsApi::class.java)
    }

    /**
     * Returns an instance of the [ArtistBiographiesApi] which can be used to make API calls to the
     */
    fun createArtistBiographiesApi(): ArtistBiographiesApi {
        return retrofit.create(ArtistBiographiesApi::class.java)
    }

    /**
     * Returns an instance of the [ArtistClaimStatusesApi] which can be used to make API calls to
     * the
     */
    fun createArtistClaimStatusesApi(): ArtistClaimStatusesApi {
        return retrofit.create(ArtistClaimStatusesApi::class.java)
    }

    /** Returns an instance of the [ArtistClaimsApi] which can be used to make API calls to the */
    fun createArtistClaimsApi(): ArtistClaimsApi {
        return retrofit.create(ArtistClaimsApi::class.java)
    }

    /** Returns an instance of the [ArtistRolesApi] which can be used to make API calls to the */
    fun createArtistRolesApi(): ArtistRolesApi {
        return retrofit.create(ArtistRolesApi::class.java)
    }

    /** Returns an instance of the [ArtistsApi] which can be used to make API calls to the */
    fun createArtistsApi(): ArtistsApi {
        return retrofit.create(ArtistsApi::class.java)
    }

    /** Returns an instance of the [ArtworksApi] which can be used to make API calls to the */
    fun createArtworksApi(): ArtworksApi {
        return retrofit.create(ArtworksApi::class.java)
    }

    /** Returns an instance of the [ClientsApi] which can be used to make API calls to the */
    fun createClientsApi(): ClientsApi {
        return retrofit.create(ClientsApi::class.java)
    }

    /**
     * Returns an instance of the [CollaborationInviteRedemptionsApi] which can be used to make API
     * calls to the
     */
    fun createCollaborationInviteRedemptionsApi(): CollaborationInviteRedemptionsApi {
        return retrofit.create(CollaborationInviteRedemptionsApi::class.java)
    }

    /**
     * Returns an instance of the [CollaborationInvitesApi] which can be used to make API calls to
     * the
     */
    fun createCollaborationInvitesApi(): CollaborationInvitesApi {
        return retrofit.create(CollaborationInvitesApi::class.java)
    }

    /** Returns an instance of the [CommentsApi] which can be used to make API calls to the */
    fun createCommentsApi(): CommentsApi {
        return retrofit.create(CommentsApi::class.java)
    }

    /** Returns an instance of the [ContentClaimsApi] which can be used to make API calls to the */
    fun createContentClaimsApi(): ContentClaimsApi {
        return retrofit.create(ContentClaimsApi::class.java)
    }

    /** Returns an instance of the [CreditsApi] which can be used to make API calls to the */
    fun createCreditsApi(): CreditsApi {
        return retrofit.create(CreditsApi::class.java)
    }

    /** Returns an instance of the [DownloadsApi] which can be used to make API calls to the */
    fun createDownloadsApi(): DownloadsApi {
        return retrofit.create(DownloadsApi::class.java)
    }

    /**
     * Returns an instance of the [DspSharingLinksApi] which can be used to make API calls to the
     */
    fun createDspSharingLinksApi(): DspSharingLinksApi {
        return retrofit.create(DspSharingLinksApi::class.java)
    }

    /** Returns an instance of the [DynamicModulesApi] which can be used to make API calls to the */
    fun createDynamicModulesApi(): DynamicModulesApi {
        return retrofit.create(DynamicModulesApi::class.java)
    }

    /** Returns an instance of the [DynamicPagesApi] which can be used to make API calls to the */
    fun createDynamicPagesApi(): DynamicPagesApi {
        return retrofit.create(DynamicPagesApi::class.java)
    }

    /** Returns an instance of the [GenresApi] which can be used to make API calls to the */
    fun createGenresApi(): GenresApi {
        return retrofit.create(GenresApi::class.java)
    }

    /** Returns an instance of the [InstallationsApi] which can be used to make API calls to the */
    fun createInstallationsApi(): InstallationsApi {
        return retrofit.create(InstallationsApi::class.java)
    }

    /** Returns an instance of the [LyricsApi] which can be used to make API calls to the */
    fun createLyricsApi(): LyricsApi {
        return retrofit.create(LyricsApi::class.java)
    }

    /**
     * Returns an instance of the [ManualArtistClaimsApi] which can be used to make API calls to the
     */
    fun createManualArtistClaimsApi(): ManualArtistClaimsApi {
        return retrofit.create(ManualArtistClaimsApi::class.java)
    }

    /** Returns an instance of the [OfflineTasksApi] which can be used to make API calls to the */
    fun createOfflineTasksApi(): OfflineTasksApi {
        return retrofit.create(OfflineTasksApi::class.java)
    }

    /** Returns an instance of the [PlayQueuesApi] which can be used to make API calls to the */
    fun createPlayQueuesApi(): PlayQueuesApi {
        return retrofit.create(PlayQueuesApi::class.java)
    }

    /** Returns an instance of the [PlaylistsApi] which can be used to make API calls to the */
    fun createPlaylistsApi(): PlaylistsApi {
        return retrofit.create(PlaylistsApi::class.java)
    }

    /**
     * Returns an instance of the [PriceConfigurationsApi] which can be used to make API calls to
     * the
     */
    fun createPriceConfigurationsApi(): PriceConfigurationsApi {
        return retrofit.create(PriceConfigurationsApi::class.java)
    }

    /** Returns an instance of the [ProviderOwnersApi] which can be used to make API calls to the */
    fun createProviderOwnersApi(): ProviderOwnersApi {
        return retrofit.create(ProviderOwnersApi::class.java)
    }

    /**
     * Returns an instance of the [ProviderProductInfosApi] which can be used to make API calls to
     * the
     */
    fun createProviderProductInfosApi(): ProviderProductInfosApi {
        return retrofit.create(ProviderProductInfosApi::class.java)
    }

    /** Returns an instance of the [ProvidersApi] which can be used to make API calls to the */
    fun createProvidersApi(): ProvidersApi {
        return retrofit.create(ProvidersApi::class.java)
    }

    /** Returns an instance of the [PurchasesApi] which can be used to make API calls to the */
    fun createPurchasesApi(): PurchasesApi {
        return retrofit.create(PurchasesApi::class.java)
    }

    /** Returns an instance of the [ReactionsApi] which can be used to make API calls to the */
    fun createReactionsApi(): ReactionsApi {
        return retrofit.create(ReactionsApi::class.java)
    }

    /** Returns an instance of the [SavedSharesApi] which can be used to make API calls to the */
    fun createSavedSharesApi(): SavedSharesApi {
        return retrofit.create(SavedSharesApi::class.java)
    }

    /** Returns an instance of the [ScopesApi] which can be used to make API calls to the */
    fun createScopesApi(): ScopesApi {
        return retrofit.create(ScopesApi::class.java)
    }

    /**
     * Returns an instance of the [SearchHistoryEntriesApi] which can be used to make API calls to
     * the
     */
    fun createSearchHistoryEntriesApi(): SearchHistoryEntriesApi {
        return retrofit.create(SearchHistoryEntriesApi::class.java)
    }

    /** Returns an instance of the [SearchResultsApi] which can be used to make API calls to the */
    fun createSearchResultsApi(): SearchResultsApi {
        return retrofit.create(SearchResultsApi::class.java)
    }

    /**
     * Returns an instance of the [SearchSuggestionsApi] which can be used to make API calls to the
     */
    fun createSearchSuggestionsApi(): SearchSuggestionsApi {
        return retrofit.create(SearchSuggestionsApi::class.java)
    }

    /** Returns an instance of the [SharesApi] which can be used to make API calls to the */
    fun createSharesApi(): SharesApi {
        return retrofit.create(SharesApi::class.java)
    }

    /**
     * Returns an instance of the [SquareConnectionsApi] which can be used to make API calls to the
     */
    fun createSquareConnectionsApi(): SquareConnectionsApi {
        return retrofit.create(SquareConnectionsApi::class.java)
    }

    /**
     * Returns an instance of the [StripeConnectionsApi] which can be used to make API calls to the
     */
    fun createStripeConnectionsApi(): StripeConnectionsApi {
        return retrofit.create(StripeConnectionsApi::class.java)
    }

    /**
     * Returns an instance of the [StripeDashboardLinksApi] which can be used to make API calls to
     * the
     */
    fun createStripeDashboardLinksApi(): StripeDashboardLinksApi {
        return retrofit.create(StripeDashboardLinksApi::class.java)
    }

    /**
     * Returns an instance of the [TemporaryUserTokensApi] which can be used to make API calls to
     * the
     */
    fun createTemporaryUserTokensApi(): TemporaryUserTokensApi {
        return retrofit.create(TemporaryUserTokensApi::class.java)
    }

    /** Returns an instance of the [TermsApi] which can be used to make API calls to the */
    fun createTermsApi(): TermsApi {
        return retrofit.create(TermsApi::class.java)
    }

    /** Returns an instance of the [TrackFilesApi] which can be used to make API calls to the */
    fun createTrackFilesApi(): TrackFilesApi {
        return retrofit.create(TrackFilesApi::class.java)
    }

    /** Returns an instance of the [TrackManifestsApi] which can be used to make API calls to the */
    fun createTrackManifestsApi(): TrackManifestsApi {
        return retrofit.create(TrackManifestsApi::class.java)
    }

    /**
     * Returns an instance of the [TrackSourceFilesApi] which can be used to make API calls to the
     */
    fun createTrackSourceFilesApi(): TrackSourceFilesApi {
        return retrofit.create(TrackSourceFilesApi::class.java)
    }

    /**
     * Returns an instance of the [TrackStatisticsApi] which can be used to make API calls to the
     */
    fun createTrackStatisticsApi(): TrackStatisticsApi {
        return retrofit.create(TrackStatisticsApi::class.java)
    }

    /** Returns an instance of the [TracksApi] which can be used to make API calls to the */
    fun createTracksApi(): TracksApi {
        return retrofit.create(TracksApi::class.java)
    }

    /**
     * Returns an instance of the [TracksMetadataStatusApi] which can be used to make API calls to
     * the
     */
    fun createTracksMetadataStatusApi(): TracksMetadataStatusApi {
        return retrofit.create(TracksMetadataStatusApi::class.java)
    }

    /** Returns an instance of the [UsageRulesApi] which can be used to make API calls to the */
    fun createUsageRulesApi(): UsageRulesApi {
        return retrofit.create(UsageRulesApi::class.java)
    }

    /**
     * Returns an instance of the [UserCollectionAlbumsApi] which can be used to make API calls to
     * the
     */
    fun createUserCollectionAlbumsApi(): UserCollectionAlbumsApi {
        return retrofit.create(UserCollectionAlbumsApi::class.java)
    }

    /**
     * Returns an instance of the [UserCollectionArtistsApi] which can be used to make API calls to
     * the
     */
    fun createUserCollectionArtistsApi(): UserCollectionArtistsApi {
        return retrofit.create(UserCollectionArtistsApi::class.java)
    }

    /**
     * Returns an instance of the [UserCollectionFoldersApi] which can be used to make API calls to
     * the
     */
    fun createUserCollectionFoldersApi(): UserCollectionFoldersApi {
        return retrofit.create(UserCollectionFoldersApi::class.java)
    }

    /**
     * Returns an instance of the [UserCollectionPlaylistsApi] which can be used to make API calls
     * to the
     */
    fun createUserCollectionPlaylistsApi(): UserCollectionPlaylistsApi {
        return retrofit.create(UserCollectionPlaylistsApi::class.java)
    }

    /**
     * Returns an instance of the [UserCollectionSaveForLatersApi] which can be used to make API
     * calls to the
     */
    fun createUserCollectionSaveForLatersApi(): UserCollectionSaveForLatersApi {
        return retrofit.create(UserCollectionSaveForLatersApi::class.java)
    }

    /**
     * Returns an instance of the [UserCollectionTracksApi] which can be used to make API calls to
     * the
     */
    fun createUserCollectionTracksApi(): UserCollectionTracksApi {
        return retrofit.create(UserCollectionTracksApi::class.java)
    }

    /**
     * Returns an instance of the [UserCollectionVideosApi] which can be used to make API calls to
     * the
     */
    fun createUserCollectionVideosApi(): UserCollectionVideosApi {
        return retrofit.create(UserCollectionVideosApi::class.java)
    }

    /**
     * Returns an instance of the [UserCollectionsApi] which can be used to make API calls to the
     */
    fun createUserCollectionsApi(): UserCollectionsApi {
        return retrofit.create(UserCollectionsApi::class.java)
    }

    /** Returns an instance of the [UserDailyMixesApi] which can be used to make API calls to the */
    fun createUserDailyMixesApi(): UserDailyMixesApi {
        return retrofit.create(UserDailyMixesApi::class.java)
    }

    /**
     * Returns an instance of the [UserDataExportRequestsApi] which can be used to make API calls to
     * the
     */
    fun createUserDataExportRequestsApi(): UserDataExportRequestsApi {
        return retrofit.create(UserDataExportRequestsApi::class.java)
    }

    /**
     * Returns an instance of the [UserDiscoveryMixesApi] which can be used to make API calls to the
     */
    fun createUserDiscoveryMixesApi(): UserDiscoveryMixesApi {
        return retrofit.create(UserDiscoveryMixesApi::class.java)
    }

    /**
     * Returns an instance of the [UserNewReleaseMixesApi] which can be used to make API calls to
     * the
     */
    fun createUserNewReleaseMixesApi(): UserNewReleaseMixesApi {
        return retrofit.create(UserNewReleaseMixesApi::class.java)
    }

    /**
     * Returns an instance of the [UserOfflineMixesApi] which can be used to make API calls to the
     */
    fun createUserOfflineMixesApi(): UserOfflineMixesApi {
        return retrofit.create(UserOfflineMixesApi::class.java)
    }

    /**
     * Returns an instance of the [UserRecommendationBlocksApi] which can be used to make API calls
     * to the
     */
    fun createUserRecommendationBlocksApi(): UserRecommendationBlocksApi {
        return retrofit.create(UserRecommendationBlocksApi::class.java)
    }

    /**
     * Returns an instance of the [UserRecommendationsApi] which can be used to make API calls to
     * the
     */
    fun createUserRecommendationsApi(): UserRecommendationsApi {
        return retrofit.create(UserRecommendationsApi::class.java)
    }

    /** Returns an instance of the [UserReportsApi] which can be used to make API calls to the */
    fun createUserReportsApi(): UserReportsApi {
        return retrofit.create(UserReportsApi::class.java)
    }

    /** Returns an instance of the [UsersApi] which can be used to make API calls to the */
    fun createUsersApi(): UsersApi {
        return retrofit.create(UsersApi::class.java)
    }

    /** Returns an instance of the [VideoManifestsApi] which can be used to make API calls to the */
    fun createVideoManifestsApi(): VideoManifestsApi {
        return retrofit.create(VideoManifestsApi::class.java)
    }

    /** Returns an instance of the [VideosApi] which can be used to make API calls to the */
    fun createVideosApi(): VideosApi {
        return retrofit.create(VideosApi::class.java)
    }

    companion object {
        const val DEFAULT_BASE_URL = "https://openapi.tidal.com/v2/"
    }
}
