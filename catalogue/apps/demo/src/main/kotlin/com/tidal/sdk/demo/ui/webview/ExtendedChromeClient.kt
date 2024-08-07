package com.tidal.sdk.demo.ui.webview

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView

class ExtendedChromeClient : WebChromeClient() {

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
