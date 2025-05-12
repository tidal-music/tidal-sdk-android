package com.tidal.sdk.demo.webview

import android.graphics.Color
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.tidal.sdk.demo.MainActivity

@Composable
fun ComposeWebView(activity: MainActivity, url: String) {
    val javaScriptInterface = JavaScriptInterface()
    val extendedWebViewClient = ExtendedWebClient { activity.onRedirectUriReceived(it) }

    AndroidView(
        factory = { context ->
            WebView.setWebContentsDebuggingEnabled(true)
            WebView(context).apply {
                setBackgroundColor(Color.TRANSPARENT)
                settings.apply {
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    javaScriptEnabled = true
                    cacheMode = WebSettings.LOAD_NO_CACHE
                }

                webChromeClient = ExtendedChromeClient()
                webViewClient = extendedWebViewClient
                addJavascriptInterface(javaScriptInterface, "javascriptObject")
                loadUrl(url)
            }
        }
    )
}
