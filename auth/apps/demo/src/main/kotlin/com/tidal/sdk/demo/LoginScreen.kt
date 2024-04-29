package com.tidal.sdk.demo

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.tidal.sdk.demo.MainActivity.Companion.LOGIN_URI
import com.tidal.sdk.demo.webview.ComposeWebView

@Composable
fun LoginScreen() {
    val activity = LocalContext.current.findActivity() as MainActivity

    @Composable
    fun getLoginUri(): Uri {
        val context = LocalContext.current
        val activity = (context.findActivity() as MainActivity)
        return activity.auth.initializeLogin(
            LOGIN_URI,
            activity.loginConfig,
        )
    }

    val url = getLoginUri().toString()

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black),
    ) {
        ComposeWebView(activity = activity, url = url)
    }
}
