package com.tidal.sdk.tidalapi.generated.models

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

fun getOneOfSerializer() = SerializersModule {
    polymorphic(AlbumsSingleDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(ArtistsSingleDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistRolesResource::class, ArtistRolesResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(PlaylistsMultiDataRelationshipDocumentIncludedInner::class) {
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(UsersResource::class, UsersResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(PlaylistsSingleDataDocumentIncludedInner::class) {
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(UsersResource::class, UsersResource.serializer())
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
    polymorphic(SearchresultsSingleDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(TracksSingleDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
    }
    polymorphic(UserPublicProfilePicksSingleDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(UserPublicProfilesResource::class, UserPublicProfilesResource.serializer())
    }
    polymorphic(UserPublicProfilePicksSingletonDataRelationshipDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(UserPublicProfilePicksResource::class, UserPublicProfilePicksResource.serializer())
        subclass(UserPublicProfilesResource::class, UserPublicProfilesResource.serializer())
    }
    polymorphic(UserPublicProfilesMultiDataRelationshipDocumentIncludedInner::class) {
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(UserPublicProfilePicksResource::class, UserPublicProfilePicksResource.serializer())
        subclass(UserPublicProfilesResource::class, UserPublicProfilesResource.serializer())
        subclass(UsersResource::class, UsersResource.serializer())
    }
    polymorphic(UserPublicProfilesSingleDataDocumentIncludedInner::class) {
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(UserPublicProfilePicksResource::class, UserPublicProfilePicksResource.serializer())
        subclass(UsersResource::class, UsersResource.serializer())
    }
    polymorphic(UserRecommendationsMultiDataRelationshipDocumentIncludedInner::class) {
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(UserRecommendationsResource::class, UserRecommendationsResource.serializer())
    }
    polymorphic(UserRecommendationsSingleDataDocumentIncludedInner::class) {
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
    }
    polymorphic(UsersSingleDataDocumentIncludedInner::class) {
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
    polymorphic(VideosMultiDataRelationshipDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(VideosSingleDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
    }
}

