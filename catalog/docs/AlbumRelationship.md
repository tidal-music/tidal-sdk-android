
# AlbumRelationship

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **kotlin.String** | Provider name. Conditionally visible. | 
**popularity** | **kotlin.Double** | Album popularity (ranged in 0.00 ... 1.00). Conditionally visible | 
**title** | **kotlin.String** | Original title | 
**isrc** | **kotlin.String** | ISRC code | 
**duration** | **kotlin.String** | Duration (ISO-8601) | 
**explicit** | **kotlin.Boolean** | Indicates whether an album consist of any explicit content | 
**mediaTags** | **kotlin.collections.List&lt;kotlin.String&gt;** |  | 
**barcodeId** | **kotlin.String** | Barcode id (EAN-13 or UPC-A) | 
**numberOfVolumes** | **kotlin.Int** | Number of volumes | 
**numberOfItems** | **kotlin.Int** | Number of album items | 
**imageLinks** | [**kotlin.collections.List&lt;ImageLink&gt;**](ImageLink.md) | Represents available links to, and metadata about, an album cover images |  [optional]
**externalLinks** | [**kotlin.collections.List&lt;ExternalLink&gt;**](ExternalLink.md) | Represents available links to something that is related to an album resource, but external to the TIDAL API |  [optional]
**version** | **kotlin.String** | Version of the album&#39;s item; complements title |  [optional]
**copyright** | **kotlin.String** | Copyright information |  [optional]
**availability** | [**inline**](#kotlin.collections.List&lt;Availability&gt;) | Defines an album availability e.g. for streaming, DJs, stems |  [optional]
**releaseDate** | [**java.time.LocalDate**](java.time.LocalDate.md) | Release date (ISO-8601) |  [optional]
**videoLinks** | [**kotlin.collections.List&lt;VideoLink&gt;**](VideoLink.md) | Represents available links to, and metadata about, an album cover videos |  [optional]


<a id="kotlin.collections.List<Availability>"></a>
## Enum: availability
Name | Value
---- | -----
availability | STREAM, DJ, STEM



