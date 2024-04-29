package com.tidal.sdk.player.mainactivity

import com.tidal.sdk.auth.model.Credentials
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.common.model.ProductType
import java.util.UUID

internal data class DemoPlayableItem(
    val name: String,
    val productType: ProductType,
    val mediaProductId: String,
    val allowedCredentialLevels: Set<Credentials.Level>,
) {

    fun createMediaProduct(referenceId: String = UUID.randomUUID().toString()) =
        MediaProduct(productType, mediaProductId, referenceId = referenceId)

    companion object {
        val HARDCODED = arrayOf(
            DemoPlayableItem(
                "Wolves",
                ProductType.TRACK,
                "57273420",
                setOf(Credentials.Level.USER, Credentials.Level.CLIENT),
            ),
            DemoPlayableItem(
                "Empire State Of Mind",
                ProductType.TRACK,
                "37704290",
                setOf(Credentials.Level.USER, Credentials.Level.CLIENT),
            ),
            DemoPlayableItem(
                "Izzo",
                ProductType.TRACK,
                "35738577",
                setOf(Credentials.Level.USER, Credentials.Level.CLIENT),
            ),
            DemoPlayableItem(
                "The heart part 5",
                ProductType.VIDEO,
                "228097594",
                setOf(Credentials.Level.USER),
            ),
            DemoPlayableItem(
                "Cost of livin'",
                ProductType.VIDEO,
                "26004828",
                setOf(Credentials.Level.USER),
            ),
            DemoPlayableItem(
                "Ronnie",
                ProductType.VIDEO,
                "63520295",
                setOf(Credentials.Level.USER),
            ),
            DemoPlayableItem(
                "Rush (Dolby Atmos)",
                ProductType.TRACK,
                "313178984",
                setOf(Credentials.Level.USER, Credentials.Level.CLIENT),
            ),
        )
    }
}
