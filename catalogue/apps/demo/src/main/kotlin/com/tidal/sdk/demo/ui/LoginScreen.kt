package com.tidal.sdk.demo.ui

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.tidal.sdk.auth.model.LoginConfig
import com.tidal.sdk.auth.model.QueryParameter
import com.tidal.sdk.catalogue.demo.BuildConfig
import com.tidal.sdk.demo.MainActivity
import com.tidal.sdk.demo.ui.webview.ComposeWebView

@Composable
fun LoginScreen() {
    val activity = LocalContext.current.findActivity() as MainActivity
    val loginConfig = LoginConfig(
        customParams = setOf(
            QueryParameter(
                key = "appMode",
                value = "android",
            ),
        ), // Client has to inform the module about the appMode
    )

    @Composable
    fun getLoginUri(): Uri {
        return activity.auth.initializeLogin(
            BuildConfig.TIDAL_CLIENT_REDIRECT_URI,
            loginConfig,
        )
    }

    val url = getLoginUri().toString()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        ComposeWebView(activity = activity, url = url)
    }
}
