package com.tidal.sdk.player.auth.weblogin

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView

internal class ExtendedChromeClient : WebChromeClient() {

    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?,
    ): Boolean {
        return if (filePathCallback != null && fileChooserParams != null) {
            true
        } else {
            super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
        }
    }
}
