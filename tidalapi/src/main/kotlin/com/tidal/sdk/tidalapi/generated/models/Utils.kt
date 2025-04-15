package com.tidal.sdk.tidalapi.generated.models

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

fun getOneOfSerializer() = SerializersModule {
    polymorphic(AlbumsMultiDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(ArtworksResource::class, ArtworksResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(AlbumsMultiDataRelationshipDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(ArtworksResource::class, ArtworksResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(ArtistsMultiDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistRolesResource::class, ArtistRolesResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(ArtworksResource::class, ArtworksResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(ArtistsMultiDataRelationshipDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistRolesResource::class, ArtistRolesResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(ArtworksResource::class, ArtworksResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(PlaylistsMultiDataDocumentIncludedInner::class) {
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(ArtworksResource::class, ArtworksResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(UsersResource::class, UsersResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(PlaylistsMultiDataRelationshipDocumentIncludedInner::class) {
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(ArtworksResource::class, ArtworksResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(UsersResource::class, UsersResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(SearchResultsMultiDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(SearchResultsMultiDataRelationshipDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(SearchResultsResource::class, SearchResultsResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(SearchSuggestionsMultiDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(SearchSuggestionsMultiDataRelationshipDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(SearchSuggestionsResource::class, SearchSuggestionsResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(SearchResultsMultiDataRelationshipDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(SearchResultsResource::class, SearchResultsResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(TracksMultiDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
    }
    polymorphic(UserCollectionsMultiDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
    }
    polymorphic(UserRecommendationsMultiDataDocumentIncludedInner::class) {
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
    }
    polymorphic(UserRecommendationsMultiDataRelationshipDocumentIncludedInner::class) {
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(UserRecommendationsResource::class, UserRecommendationsResource.serializer())
    }
    polymorphic(VideosMultiDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(ArtworksResource::class, ArtworksResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
    }
    polymorphic(VideosMultiDataRelationshipDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(ArtworksResource::class, ArtworksResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
}

