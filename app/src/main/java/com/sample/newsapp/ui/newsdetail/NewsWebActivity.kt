package com.sample.newsapp.ui.newsdetail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.sample.newsapp.R
import com.sample.newsapp.newsapplication.AppConstants
import com.sample.newsapp.util.CustomWebViewClient
import kotlinx.android.synthetic.main.activity_news_web.*

class NewsWebActivity : AppCompatActivity() {

    private val TAG = NewsWebActivity::class.java.simpleName

    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_web)
        url = intent?.getStringExtra(AppConstants.INTENT_EXTRA_URL)
        initUI()
        if (!url.isNullOrBlank()){
            setWebView()
        }else{
            finish()
        }

    }

    /**
     * Init UI in this function
     */
    private fun initUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.news_source)
    }

    private fun setWebView() {
        wv_news?.settings?.javaScriptEnabled = true
        wv_news?.settings?.loadWithOverviewMode = true
        wv_news?.settings?.useWideViewPort = true
        wv_news?.settings?.displayZoomControls = true
        wv_news?.webViewClient = CustomWebViewClient()
        wv_news?.settings?.builtInZoomControls = true
        wv_news.loadUrl(url)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}