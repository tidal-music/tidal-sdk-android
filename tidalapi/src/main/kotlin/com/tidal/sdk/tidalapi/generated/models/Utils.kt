package com.tidal.sdk.tidalapi.generated.models

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

fun getOneOfSerializer() = SerializersModule {
    polymorphic(AlbumsMultiDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(AlbumsMultiDataRelationshipDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(ArtistsMultiDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistRolesResource::class, ArtistRolesResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(ArtistsMultiDataRelationshipDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistRolesResource::class, ArtistRolesResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(PlaylistsMultiDataDocumentIncludedInner::class) {
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(UsersResource::class, UsersResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(PlaylistsMultiDataRelationshipDocumentIncludedInner::class) {
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(UsersResource::class, UsersResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(SearchresultsMultiDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(SearchresultsMultiDataRelationshipDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(SearchresultsResource::class, SearchresultsResource.serializer())
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
    polymorphic(UserCollectionsMultiDataRelationshipDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(UserCollectionsResource::class, UserCollectionsResource.serializer())
    }
    polymorphic(UserPublicProfilePicksMultiDataDocumentIncludedInner::class) {
        subclass(UserPublicProfilesResource::class, UserPublicProfilesResource.serializer())
    }
    polymorphic(UserPublicProfilePicksSingletonDataRelationshipDocumentIncludedInner::class) {
        subclass(UserPublicProfilePicksResource::class, UserPublicProfilePicksResource.serializer())
        subclass(UserPublicProfilesResource::class, UserPublicProfilesResource.serializer())
    }
    polymorphic(UserPublicProfilesMultiDataDocumentIncludedInner::class) {
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(UserPublicProfilePicksResource::class, UserPublicProfilePicksResource.serializer())
        subclass(UsersResource::class, UsersResource.serializer())
    }
    polymorphic(UserPublicProfilesMultiDataRelationshipDocumentIncludedInner::class) {
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(UserPublicProfilePicksResource::class, UserPublicProfilePicksResource.serializer())
        subclass(UserPublicProfilesResource::class, UserPublicProfilesResource.serializer())
        subclass(UsersResource::class, UsersResource.serializer())
    }
    polymorphic(UserRecommendationsMultiDataDocumentIncludedInner::class) {
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
    }
    polymorphic(UserRecommendationsMultiDataRelationshipDocumentIncludedInner::class) {
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(UserRecommendationsResource::class, UserRecommendationsResource.serializer())
    }
    polymorphic(UsersMultiDataDocumentIncludedInner::class) {
        subclass(UserEntitlementsResource::class, UserEntitlementsResource.serializer())
        subclass(UserPublicProfilesResource::class, UserPublicProfilesResource.serializer())
        subclass(UserRecommendationsResource::class, UserRecommendationsResource.serializer())
    }
    polymorphic(UsersSingletonDataRelationshipDocumentIncludedInner::class) {
        subclass(UserEntitlementsResource::class, UserEntitlementsResource.serializer())
        subclass(UserPublicProfilesResource::class, UserPublicProfilesResource.serializer())
        subclass(UserRecommendationsResource::class, UserRecommendationsResource.serializer())
        subclass(UsersResource::class, UsersResource.serializer())
    }
    polymorphic(VideosMultiDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
    }
    polymorphic(VideosMultiDataRelationshipDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
}
