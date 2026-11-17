package com.tidal.sdk.player.common.model

/**
 * A media product. The [productType] and the [productId] together determine what media product this
 * is. Source information is only used as extra information used for tracking purposes and may be
 * omitted.
 *
 * @param[productType] The type of the media product as [ProductType].
 * @param[productId] The id of a media product as [String].
 * @param[sourceType] The type of source as [String]. May be null or empty.
 * @param[sourceId] The id of source as [String]. May be null or empty.
 * @param[referenceId] A unique id as [String]. Typically used to differentiate on an otherwise
 *   identical media product. May be null or empty.
 * @param[shareCode] The share code of an upload shared by link, sent with the track manifest
 *   request so that a recipient can play it before saving it. Only used for [ProductType.TRACK].
 */
data class MediaProduct(
    override val productType: ProductType,
    override val productId: String,
    override val sourceType: String? = null,
    override val sourceId: String? = null,
    override val extras: Extras? = null,
    val referenceId: String? = null,
    override val shareCode: String? = null,
) : BaseMediaProduct
