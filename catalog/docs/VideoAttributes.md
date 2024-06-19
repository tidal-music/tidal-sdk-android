
# VideoAttributes

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**title** | **kotlin.String** | Album item&#39;s title | 
**isrc** | **kotlin.String** | ISRC code | 
**duration** | **kotlin.String** | Duration expressed in accordance with ISO 8601 | 
**explicit** | **kotlin.Boolean** | Indicates whether a catalog item consist of any explicit content | 
**popularity** | **kotlin.Double** | Track or video popularity (ranged in 0.00 ... 1.00). Conditionally visible | 
**version** | **kotlin.String** | Version of the album&#39;s item; complements title |  [optional]
**copyright** | **kotlin.String** | Copyright information |  [optional]
**releaseDate** | [**java.time.LocalDate**](java.time.LocalDate.md) | Release date (ISO-8601) |  [optional]
**availability** | [**inline**](#kotlin.collections.List&lt;Availability&gt;) | Defines a catalog item availability e.g. for streaming, DJs, stems |  [optional]
**imageLinks** | [**kotlin.collections.List&lt;ImageLink&gt;**](ImageLink.md) | Represents available links to, and metadata about, an album item images |  [optional]
**externalLinks** | [**kotlin.collections.List&lt;ExternalLink&gt;**](ExternalLink.md) | Represents available links to something that is related to a catalog item, but external to the TIDAL API |  [optional]


<a id="kotlin.collections.List<Availability>"></a>
## Enum: availability
Name | Value
---- | -----
availability | STREAM, DJ, STEM



