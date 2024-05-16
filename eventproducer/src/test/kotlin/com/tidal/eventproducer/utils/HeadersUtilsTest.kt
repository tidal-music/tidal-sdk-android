package com.tidal.eventproducer.utils

import assertk.assertThat
import com.tidal.sdk.eventproducer.auth.AuthProvider
import com.tidal.sdk.eventproducer.utils.APP_VERSION_KEY
import com.tidal.sdk.eventproducer.utils.CLIENT_ID_KEY
import com.tidal.sdk.eventproducer.utils.HeadersUtils
import com.tidal.sdk.eventproducer.utils.OS_NAME_KEY
import okhttp3.Response
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HeadersUtilsTest {

    private val headerUtils = HeadersUtils(
        "",
        object : AuthProvider {
            override val token: String = ""
            override val clientId: String? = null
            override suspend fun handleAuthError(response: Response): Boolean {
                return false
            }
        },
    )

    @Test
    fun `verify that event headers contain both default and supplied headers`() {
        val header3 = Pair(APP_VERSION_KEY, "1.0")
        val header1 = Pair(CLIENT_ID_KEY, "1")
        val header2 = Pair(OS_NAME_KEY, "Android")
        val suppliedHeaders = mapOf(header1)
        val defaultHeaders = mapOf(header2, header3)
        val headers = headerUtils.getEventHeaders(defaultHeaders, suppliedHeaders)
        assertThat { headers.contains(CLIENT_ID_KEY) }
        assertThat { headers.contains(OS_NAME_KEY) }
        assertThat { headers.contains(APP_VERSION_KEY) }
        assertEquals(3, headers.size)
    }

    @Test
    fun `verify that supplied header override default one`() {
        val defaultClientId = "1"
        val suppliedClientId = "2"
        val suppliedHeaders = mapOf(Pair(CLIENT_ID_KEY, suppliedClientId))
        val defaultHeaders = mapOf(Pair(CLIENT_ID_KEY, defaultClientId))
        val headers = headerUtils.getEventHeaders(defaultHeaders, suppliedHeaders)
        assertEquals(headers[CLIENT_ID_KEY], suppliedClientId)
    }
}
