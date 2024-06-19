
# ArtistRelationship

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**title** | **kotlin.String** | Album item&#39;s title | 
**barcodeId** | **kotlin.String** | Barcode id (EAN-13 or UPC-A) | 
**numberOfVolumes** | **kotlin.Int** | Number of volumes | 
**numberOfItems** | **kotlin.Int** | Number of album items | 
**duration** | **kotlin.String** | Duration expressed in accordance with ISO 8601 | 
**explicit** | **kotlin.Boolean** | Indicates whether a catalog item consist of any explicit content | 
**popularity** | **kotlin.Double** | Artist popularity (ranged in 0.00 ... 1.00). Conditionally visible | 
**mediaTags** | **kotlin.collections.List&lt;kotlin.String&gt;** |  | 
**isrc** | **kotlin.String** | ISRC code | 
**name** | **kotlin.String** | Artist name | 
**releaseDate** | [**java.time.LocalDate**](java.time.LocalDate.md) | Release date (ISO-8601) |  [optional]
**copyright** | **kotlin.String** | Copyright information |  [optional]
**availability** | [**inline**](#kotlin.collections.List&lt;Availability&gt;) | Defines a catalog item availability e.g. for streaming, DJs, stems |  [optional]
**imageLinks** | [**kotlin.collections.List&lt;ImageLink&gt;**](ImageLink.md) | Represents available links to, and metadata about, an artist images |  [optional]
**videoLinks** | [**kotlin.collections.List&lt;VideoLink&gt;**](VideoLink.md) | Represents available links to, and metadata about, an album cover videos |  [optional]
**externalLinks** | [**kotlin.collections.List&lt;ExternalLink&gt;**](ExternalLink.md) | Represents available links to something that is related to an artist resource, but external to the TIDAL API |  [optional]
**version** | **kotlin.String** | Version of the album&#39;s item; complements title |  [optional]


<a id="kotlin.collections.List<Availability>"></a>
## Enum: availability
Name | Value
---- | -----
availability | STREAM, DJ, STEM



