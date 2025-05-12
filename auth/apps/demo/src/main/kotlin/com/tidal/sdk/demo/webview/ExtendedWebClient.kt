package com.tidal.sdk.demo.webview

import android.annotation.TargetApi
import android.net.Uri
import android.os.Build
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.tidal.sdk.auth.demo.BuildConfig

class ExtendedWebClient(private val onRedirectUriReceived: (Uri) -> Unit) : WebViewClient() {

    override fun onPageCommitVisible(view: WebView?, url: String?) {
        super.onPageCommitVisible(view, url)
    }

    @TargetApi(Build.VERSION_CODES.N)
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        return hasRedirectUri(request.url)
    }

    @SuppressWarnings("deprecation")
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        val uri = Uri.parse(url)
        return hasRedirectUri(uri)
    }

    private fun hasRedirectUri(uri: Uri): Boolean {
        return if (uri.toString().startsWith(BuildConfig.TIDAL_CLIENT_REDIRECT_URI)) {
            onRedirectUriReceived(uri)
            true
        } else {
            false
        }
    }

    override fun onReceivedError(
        view: WebView,
        request: WebResourceRequest,
        error: WebResourceError,
    ) {
        onReceivedError(view, error.errorCode, error.description.toString(), request.url.toString())
    }

    @SuppressWarnings("deprecation")
    override fun onReceivedError(
        view: WebView,
        errorCode: Int,
        description: String,
        failingUrl: String,
    ) {
        // TODO handle error
    }
}
