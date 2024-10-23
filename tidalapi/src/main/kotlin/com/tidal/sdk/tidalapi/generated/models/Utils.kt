package com.tidal.sdk.tidalapi.generated.models

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

fun getOneOfSerializer() = SerializersModule {
    polymorphic(AlbumsItemsRelationshipDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(AlbumsMultiDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(AlbumsRelationshipDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(ArtistsMultiDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(UsersResource::class, UsersResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(ArtistsRelationshipDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(PlaylistsMultiDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(UserEntitlementsResource::class, UserEntitlementsResource.serializer())
        subclass(UserPublicProfilesResource::class, UserPublicProfilesResource.serializer())
        subclass(UserRecommendationsResource::class, UserRecommendationsResource.serializer())
        subclass(UsersResource::class, UsersResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(PlaylistsOwnersRelationshipDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(UserEntitlementsResource::class, UserEntitlementsResource.serializer())
        subclass(UserPublicProfilesResource::class, UserPublicProfilesResource.serializer())
        subclass(UserRecommendationsResource::class, UserRecommendationsResource.serializer())
        subclass(UsersResource::class, UsersResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(ProvidersRelationshipDocumentIncludedInner::class) {
        subclass(ProvidersResource::class, ProvidersResource.serializer())
    }
    polymorphic(SearchResultsTopHitsRelationshipDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(UsersResource::class, UsersResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(TracksMultiDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(UsersResource::class, UsersResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(TracksRelationshipsDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
    }
    polymorphic(UserEntitlementsRelationshipDocumentIncludedInner::class) {
        subclass(UserEntitlementsResource::class, UserEntitlementsResource.serializer())
    }
    polymorphic(UserPublicProfilePicksItemRelationshipDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(UserPublicProfilePicksMultiDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(UserPublicProfilePicksResource::class, UserPublicProfilePicksResource.serializer())
        subclass(UserPublicProfilesResource::class, UserPublicProfilesResource.serializer())
        subclass(UsersResource::class, UsersResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(UserPublicProfilePicksRelationshipDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(UserPublicProfilePicksResource::class, UserPublicProfilePicksResource.serializer())
        subclass(UserPublicProfilesResource::class, UserPublicProfilesResource.serializer())
    }
    polymorphic(UserPublicProfilesMultiDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(UserEntitlementsResource::class, UserEntitlementsResource.serializer())
        subclass(UserPublicProfilePicksResource::class, UserPublicProfilePicksResource.serializer())
        subclass(UserPublicProfilesResource::class, UserPublicProfilesResource.serializer())
        subclass(UserRecommendationsResource::class, UserRecommendationsResource.serializer())
        subclass(UsersResource::class, UsersResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(UserPublicProfilesRelationshipDocumentIncludedInner::class) {
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(UserPublicProfilePicksResource::class, UserPublicProfilePicksResource.serializer())
        subclass(UserPublicProfilesResource::class, UserPublicProfilesResource.serializer())
        subclass(UsersResource::class, UsersResource.serializer())
    }
    polymorphic(UserRecommendationsMultiDataDocumentIncludedInner::class) {
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(UsersResource::class, UsersResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(UsersMultiDataDocumentIncludedInner::class) {
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(UserEntitlementsResource::class, UserEntitlementsResource.serializer())
        subclass(UserPublicProfilePicksResource::class, UserPublicProfilePicksResource.serializer())
        subclass(UserPublicProfilesResource::class, UserPublicProfilesResource.serializer())
        subclass(UserRecommendationsResource::class, UserRecommendationsResource.serializer())
        subclass(UsersResource::class, UsersResource.serializer())
    }
    polymorphic(UsersRecommendationsRelationshipDocumentIncludedInner::class) {
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(UserRecommendationsResource::class, UserRecommendationsResource.serializer())
    }
    polymorphic(UsersRelationshipDocumentIncludedInner::class) {
        subclass(UserEntitlementsResource::class, UserEntitlementsResource.serializer())
        subclass(UserPublicProfilesResource::class, UserPublicProfilesResource.serializer())
        subclass(UserRecommendationsResource::class, UserRecommendationsResource.serializer())
        subclass(UsersResource::class, UsersResource.serializer())
    }
    polymorphic(VideosMultiDataDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(PlaylistsResource::class, PlaylistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(TracksResource::class, TracksResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
    polymorphic(VideosRelationshipsDocumentIncludedInner::class) {
        subclass(AlbumsResource::class, AlbumsResource.serializer())
        subclass(ArtistsResource::class, ArtistsResource.serializer())
        subclass(ProvidersResource::class, ProvidersResource.serializer())
        subclass(VideosResource::class, VideosResource.serializer())
    }
}
