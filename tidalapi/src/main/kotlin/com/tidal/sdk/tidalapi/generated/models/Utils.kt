package com.tidal.sdk.tidalapi.generated.models

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

fun getOneOfSerializer() = SerializersModule {
    polymorphic(IncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistBiographiesResource::class, ArtistBiographiesResource.serializer())
        subclass(ArtistRolesResource::class, ArtistRolesResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(ArtworksResource::class, ArtworksResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(SearchResultsResource::class, SearchResultsResource.serializer())
        subclass(SearchSuggestionsResource::class, SearchSuggestionsResource.serializer())
        subclass(TrackFilesResource::class, TrackFilesResource.serializer())
        subclass(TrackManifestsResource::class, TrackManifestsResource.serializer())
        subclass(TrackStatisticsResource::class, TrackStatisticsResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(UserCollectionsResource::class, UserCollectionsResource.serializer())
        subclass(UserEntitlementsResource::class, UserEntitlementsResource.serializer())
        subclass(UserRecommendationsResource::class, UserRecommendationsResource.serializer())
        subclass(UserReportsResource::class, UserReportsResource.serializer())
        subclass(UsersResource::class, UsersResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
}
