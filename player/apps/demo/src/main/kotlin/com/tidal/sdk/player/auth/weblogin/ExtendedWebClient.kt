package com.tidal.sdk.player.auth.weblogin

import android.annotation.TargetApi
import android.content.Context
import android.net.Uri
import android.os.Build
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.media3.common.util.UnstableApi
import com.tidal.sdk.player.mainactivity.MainActivityViewModel

@UnstableApi
internal class ExtendedWebClient(
    private val context: Context,
    private val onErrorReceived: (String) -> Unit,
    private val onRedirectUriReceived: (Context, Uri) -> Unit,
) : WebViewClient() {

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
        return if (uri.toString().startsWith(MainActivityViewModel.LOGIN_URI)) {
            onRedirectUriReceived(context, uri)
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
        onReceivedError(
            view,
            error.errorCode,
            error.description.toString(),
            request.url.toString(),
        )
    }

    @SuppressWarnings("deprecation")
    override fun onReceivedError(
        view: WebView,
        errorCode: Int,
        description: String,
        failingUrl: String,
    ) {
        onErrorReceived(
            "Error errorCode=$errorCode description=$description failingUrl=$failingUrl",
        )
    }
}
