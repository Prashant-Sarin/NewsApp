package com.sample.newsapp.ui.newsdetail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sample.newsapp.BR
import com.sample.newsapp.R
import com.sample.newsapp.databinding.ActivityNewsDetailBinding
import com.sample.newsapp.newsapplication.AppConstants
import com.sample.newsapp.util.NetworkUtil
import com.sample.twitterapiapp.ui.base.BaseActivity


class NewsDetailActivity : BaseActivity() {

    private val TAG = NewsDetailActivity::class.java.simpleName

    private var binding: ActivityNewsDetailBinding? = null
    private var viewModel: NewsDetailViewModel? = null
    var articleId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_detail)
        viewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(NewsDetailViewModel::class.java)
        binding?.setVariable(BR.viewModel, viewModel)
        binding?.executePendingBindings()
        initUI()
        initObservers()
        articleId = intent.extras?.getInt(AppConstants.INTENT_EXTRA_ARTICLE_ID)
        articleId?.let {
            viewModel?.fetchArticle(this, it)
        }

    }

    /**
     * Init UI in this function
     */
    private fun initUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.news_detail)
    }

    private fun initObservers() {
        viewModel?.visitSource?.observe(this, Observer {
            openExternalLink()
        })

        viewModel?.articleUpdated?.observe(this, Observer {
            binding?.setVariable(BR.viewModel, viewModel)
            binding?.executePendingBindings()
            showImage()
        })
    }

    private fun showImage() {
        if (viewModel?.article?.urlToImage.isNullOrBlank()) {
            binding?.ivNewsDetail?.visibility = View.GONE
        } else {
            Glide.with(this)
                .load(viewModel?.article?.urlToImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(getDrawable(R.drawable.ic_image_64))
                .into(binding?.ivNewsDetail!!)
        }

    }

    private fun openExternalLink() {
        val link = viewModel?.article?.url ?: ""
        if (link.isNotBlank()) {
            if (NetworkUtil.isNetworkAvailable(this)) {
                val browserIntent = Intent(this, NewsWebActivity::class.java)
                browserIntent.putExtra(AppConstants.INTENT_EXTRA_URL, link)
                startActivity(browserIntent)
            } else {
                Toast.makeText(this, getString(R.string.no_connectivity), Toast.LENGTH_SHORT).show()
            }
        }
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