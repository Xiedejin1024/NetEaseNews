package com.example.neteasenews.ui

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.neteasenews.R
import com.example.neteasenews.ui.common.ui.BaseActivity
import com.example.neteasenews.utils.LogUtil
import com.gyf.immersionbar.ImmersionBar


class WebViewActivity : BaseActivity() {

    private lateinit var mWebview: WebView
    private lateinit var mLoading: RelativeLayout
    private lateinit var mLoadingError: RelativeLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        setStatusBarBackground(R.color.black_50)
        mWebview = findViewById<WebView>(R.id.webview)
        mLoading = findViewById<RelativeLayout>(R.id.rl_loading)
        mLoadingError = findViewById<RelativeLayout>(R.id.rl_loading_error)


        findViewById<ImageView>(R.id.image_back).setOnClickListener {
            gotoMain()
            finish()
        }

        val actionUrl: String = intent.getStringExtra(ACTION_URL).toString()

        mWebview.loadUrl(actionUrl)

        mWebview.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                LogUtil.e("it520com", "shouldOverrideUrlLoading=")
                view.loadUrl(url)
                return true
            }
        }
        mWebview.settings.javaScriptEnabled = true
    }

    override fun onBackPressed() {
        if (mWebview.canGoBack()) {
            mWebview.goBack()
            return
        }
        gotoMain()
        finish()
    }

    private fun gotoMain() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    companion object {
        const val ACTION_URL = "action_url"
    }
}