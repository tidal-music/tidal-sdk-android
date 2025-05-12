package com.tidal.sdk.player.auth.weblogin

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi

@Composable
@SuppressLint("SetJavaScriptEnabled")
@UnstableApi
internal fun ComposeWebView(
    modifier: Modifier = Modifier,
    dispatchSetSnackbarMessage: (String) -> Unit,
    dispatchFinalizeWebLogin: (Context, Uri) -> Unit,
    url: String,
) {
    val javaScriptInterface = JavaScriptInterface()
    val extendedWebViewClient =
        ExtendedWebClient(
            LocalContext.current,
            dispatchSetSnackbarMessage,
            dispatchFinalizeWebLogin,
        )

    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView.setWebContentsDebuggingEnabled(true)
            WebView(context).apply {
                setBackgroundColor(Color.TRANSPARENT)
                settings.apply {
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    javaScriptEnabled = true
                    cacheMode = WebSettings.LOAD_NO_CACHE
                    isScrollbarFadingEnabled = false
                }

                webChromeClient = ExtendedChromeClient()
                webViewClient = extendedWebViewClient
                addJavascriptInterface(javaScriptInterface, "javascriptObject")
                loadUrl(url)
            }
        },
    )
}
