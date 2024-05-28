package com.tidal.sdk.player.auth.weblogin

import android.webkit.JavascriptInterface
import androidx.annotation.Keep

@Suppress("EmptyFunctionBlock")
internal class JavaScriptInterface {

    @Keep
    @Suppress("unused")
    @JavascriptInterface
    fun triggerFacebookSDKLogin() {
    }

    @Keep
    @Suppress("unused")
    @JavascriptInterface
    fun triggerTwitterSDKLogin() {
    }

    @Keep
    @Suppress("unused")
    @JavascriptInterface
    fun triggerResetPassword(url: String) {
    }

    @Keep
    @Suppress("unused")
    @JavascriptInterface
    fun openInExternalBrowser(url: String, closeWebView: Boolean) {
    }
}
