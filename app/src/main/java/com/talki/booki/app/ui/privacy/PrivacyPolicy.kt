package com.talki.booki.app.ui.privacy

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.talki.booki.app.R
import com.talki.booki.app.databinding.ActivityPrivacyPolicyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrivacyPolicy : AppCompatActivity() {

    private lateinit var binding : ActivityPrivacyPolicyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_privacy_policy)

        val sUrl = "file:///android_asset/privacy.html"
        binding.webview.setWebViewClient(MyBrowser())
        binding.webview.getSettings().setLoadsImagesAutomatically(true)
        binding.webview.getSettings().setJavaScriptEnabled(true)
        binding.webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)
        binding.webview.loadUrl(sUrl)

        binding.toolbarHeaderContent.ivBack.setOnClickListener {
            finish()
        }
    }

    private class MyBrowser : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }
}