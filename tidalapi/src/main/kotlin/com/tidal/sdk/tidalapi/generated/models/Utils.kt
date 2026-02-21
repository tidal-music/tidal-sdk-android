package com.tidal.sdk.tidalapi.generated.models

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlinx.serialization.modules.polymorphic

fun getOneOfSerializer() = SerializersModule {
    polymorphic(IncludedInner::class) {
        subclass(AlbumStatisticsResourceObject::class, AlbumStatisticsResourceObject.serializer())
        subclass(AlbumsResourceObject::class, AlbumsResourceObject.serializer())
        subclass(AppreciationsResourceObject::class, AppreciationsResourceObject.serializer())
        subclass(
            ArtistBiographiesResourceObject::class,
            ArtistBiographiesResourceObject.serializer(),
        )
        subclass(ArtistClaimsResourceObject::class, ArtistClaimsResourceObject.serializer())
        subclass(ArtistRolesResourceObject::class, ArtistRolesResourceObject.serializer())
        subclass(ArtistsResourceObject::class, ArtistsResourceObject.serializer())
        subclass(ArtworksResourceObject::class, ArtworksResourceObject.serializer())
        subclass(ContentClaimsResourceObject::class, ContentClaimsResourceObject.serializer())
        subclass(CreditsResourceObject::class, CreditsResourceObject.serializer())
        subclass(DownloadsResourceObject::class, DownloadsResourceObject.serializer())
        subclass(DspSharingLinksResourceObject::class, DspSharingLinksResourceObject.serializer())
        subclass(DynamicModulesResourceObject::class, DynamicModulesResourceObject.serializer())
        subclass(DynamicPagesResourceObject::class, DynamicPagesResourceObject.serializer())
        subclass(GenresResourceObject::class, GenresResourceObject.serializer())
        subclass(InstallationsResourceObject::class, InstallationsResourceObject.serializer())
        subclass(LyricsResourceObject::class, LyricsResourceObject.serializer())
        subclass(
            ManualArtistClaimsResourceObject::class,
            ManualArtistClaimsResourceObject.serializer(),
        )
        subclass(OfflineTasksResourceObject::class, OfflineTasksResourceObject.serializer())
        subclass(PlayQueuesResourceObject::class, PlayQueuesResourceObject.serializer())
        subclass(PlaylistsResourceObject::class, PlaylistsResourceObject.serializer())
        subclass(
            PriceConfigurationsResourceObject::class,
            PriceConfigurationsResourceObject.serializer(),
        )
        subclass(ProvidersResourceObject::class, ProvidersResourceObject.serializer())
        subclass(ReactionsResourceObject::class, ReactionsResourceObject.serializer())
        subclass(SavedSharesResourceObject::class, SavedSharesResourceObject.serializer())
        subclass(SearchResultsResourceObject::class, SearchResultsResourceObject.serializer())
        subclass(
            SearchSuggestionsResourceObject::class,
            SearchSuggestionsResourceObject.serializer(),
        )
        subclass(SharesResourceObject::class, SharesResourceObject.serializer())
        subclass(
            StripeConnectionsResourceObject::class,
            StripeConnectionsResourceObject.serializer(),
        )
        subclass(
            StripeDashboardLinksResourceObject::class,
            StripeDashboardLinksResourceObject.serializer(),
        )
        subclass(TrackFilesResourceObject::class, TrackFilesResourceObject.serializer())
        subclass(TrackManifestsResourceObject::class, TrackManifestsResourceObject.serializer())
        subclass(TrackSourceFilesResourceObject::class, TrackSourceFilesResourceObject.serializer())
        subclass(TrackStatisticsResourceObject::class, TrackStatisticsResourceObject.serializer())
        subclass(
            TracksMetadataStatusResourceObject::class,
            TracksMetadataStatusResourceObject.serializer(),
        )
        subclass(TracksResourceObject::class, TracksResourceObject.serializer())
        subclass(UsageRulesResourceObject::class, UsageRulesResourceObject.serializer())
        subclass(
            UserCollectionAlbumsResourceObject::class,
            UserCollectionAlbumsResourceObject.serializer(),
        )
        subclass(
            UserCollectionArtistsResourceObject::class,
            UserCollectionArtistsResourceObject.serializer(),
        )
        subclass(
            UserCollectionFoldersResourceObject::class,
            UserCollectionFoldersResourceObject.serializer(),
        )
        subclass(
            UserCollectionPlaylistsResourceObject::class,
            UserCollectionPlaylistsResourceObject.serializer(),
        )
        subclass(
            UserCollectionTracksResourceObject::class,
            UserCollectionTracksResourceObject.serializer(),
        )
        subclass(
            UserCollectionVideosResourceObject::class,
            UserCollectionVideosResourceObject.serializer(),
        )
        subclass(UserCollectionsResourceObject::class, UserCollectionsResourceObject.serializer())
        subclass(UserEntitlementsResourceObject::class, UserEntitlementsResourceObject.serializer())
        subclass(
            UserRecommendationsResourceObject::class,
            UserRecommendationsResourceObject.serializer(),
        )
        subclass(UserReportsResourceObject::class, UserReportsResourceObject.serializer())
        subclass(UsersResourceObject::class, UsersResourceObject.serializer())
        subclass(VideosResourceObject::class, VideosResourceObject.serializer())
    }
    contextual(LyricsAttributesProvider::class, LyricsAttributesProviderSerializer)
}
