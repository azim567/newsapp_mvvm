package com.azimsiddiqui.newsapp.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import com.azimsiddiqui.newsapp.databinding.ActivityNewsDetailBinding
import com.azimsiddiqui.newsapp.util.Constants


@AndroidEntryPoint
class NewsDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsDetailBinding
    private lateinit var url:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        url = intent.extras?.get(Constants.EXTRA_NEWS_URL) as String
        Log.i("xyz", "initComponents: $url")
        // Setup the WebView.
        setUpWebview(binding.createCustomerWebview)

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebview(webView: WebView) {
        with(webView.settings) {
            useWideViewPort = true
            layoutAlgorithm = android.webkit.WebSettings.LayoutAlgorithm.NORMAL
            javaScriptEnabled = true
            allowFileAccessFromFileURLs = true
            javaScriptCanOpenWindowsAutomatically = true
            databaseEnabled = true
            domStorageEnabled = true
        }
        webView.webViewClient = CustomerWebviewClient()

        webView.webChromeClient = object: WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)

                binding.progressBar?.isVisible = newProgress < 100
                binding.progressBar?.progress = newProgress
            }
        }
        webView.settings.loadWithOverviewMode = true
        webView.loadUrl(url)

    }

    private inner class CustomerWebviewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            val ab = actionBar
            if (ab != null) {
                ab.title = url
            }
            return false
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.createCustomerWebview.canGoBack()) {
            binding.createCustomerWebview.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, exit the activity)
        return super.onKeyDown(keyCode, event)
    }

}