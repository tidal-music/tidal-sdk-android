package com.tidal.sdk.tidalapi.generated.models

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

fun getOneOfSerializer() = SerializersModule {
    polymorphic(IncludedInner::class) {
        subclass(AlbumsResourceObject::class, AlbumsResourceObject.serializer())
        subclass(
            ArtistBiographiesResourceObject::class,
            ArtistBiographiesResourceObject.serializer(),
        )
        subclass(ArtistClaimsResourceObject::class, ArtistClaimsResourceObject.serializer())
        subclass(ArtistRolesResourceObject::class, ArtistRolesResourceObject.serializer())
        subclass(ArtistsResourceObject::class, ArtistsResourceObject.serializer())
        subclass(ArtworksResourceObject::class, ArtworksResourceObject.serializer())
        subclass(GenresResourceObject::class, GenresResourceObject.serializer())
        subclass(LyricsResourceObject::class, LyricsResourceObject.serializer())
        subclass(PlaylistsResourceObject::class, PlaylistsResourceObject.serializer())
        subclass(ProvidersResourceObject::class, ProvidersResourceObject.serializer())
        subclass(SearchResultsResourceObject::class, SearchResultsResourceObject.serializer())
        subclass(
            SearchSuggestionsResourceObject::class,
            SearchSuggestionsResourceObject.serializer(),
        )
        subclass(TrackFilesResourceObject::class, TrackFilesResourceObject.serializer())
        subclass(TrackManifestsResourceObject::class, TrackManifestsResourceObject.serializer())
        subclass(TrackSourceFilesResourceObject::class, TrackSourceFilesResourceObject.serializer())
        subclass(TrackStatisticsResourceObject::class, TrackStatisticsResourceObject.serializer())
        subclass(TracksResourceObject::class, TracksResourceObject.serializer())
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
}
