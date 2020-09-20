package com.sample.newsapp.ui.news

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.newsapp.R
import com.sample.newsapp.database.entities.Article
import com.sample.newsapp.databinding.ActivityNewsBinding
import com.sample.newsapp.newsapplication.AppConstants
import com.sample.newsapp.ui.newsdetail.NewsDetailActivity
import com.sample.newsapp.util.NetworkUtil
import com.sample.twitterapiapp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : BaseActivity() {

    private val TAG = NewsActivity::class.java.simpleName

    var activityNewsBinding: ActivityNewsBinding? = null
    var viewModel: NewsViewModel? = null
    var newsAdapter: NewsAdapter? = null
    var articleList = ArrayList<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNewsBinding = DataBindingUtil.setContentView(this, R.layout.activity_news)
        viewModel =
            ViewModelProvider.AndroidViewModelFactory(application).create(NewsViewModel::class.java)
        initUI()
        initObservers()
        viewModel?.getArticlesFromDB(this)

    }

    override fun onResume() {
        super.onResume()
        if (NetworkUtil.isNetworkAvailable(this)) {
            showLoadingDialog(false)
            viewModel?.getNews(this)
        }

    }

    /**
     * Init UI in this function
     */
    private fun initUI() {
        supportActionBar?.title = getString(R.string.news_title)
    }

    /**
     * Init observers for viewModel liveData variables
     */
    private fun initObservers() {
        viewModel?.newsApiSuccess?.observe(this, Observer {
            if (it) {
                viewModel?.getArticlesFromDB(this)
            }
        })

        viewModel?.articleList?.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                articleList.clear()
                articleList.addAll(it as ArrayList<Article>)
                hideLoadingDialog()
                setupNews()
            }
        })
    }


    /**
     * This function handles creating/ updating adapter for loading news.
     */
    private fun setupNews() {
        Log.d(TAG, "setupNews called")
//        if (articleList != null) {
        if (newsAdapter == null) {
            newsAdapter = NewsAdapter(this, articleList) { _, article ->
                Log.d(TAG, "clicked news feed ${article.article_id}")
                navigateToDetailActivity(article.article_id)
            }
            rvNews.layoutManager = LinearLayoutManager(this)
            rvNews.adapter = newsAdapter
        } else {
            rvNews.adapter?.notifyDataSetChanged()
        }
//        }
    }

    private fun navigateToDetailActivity(articleId: Int) {
        val intent = Intent(this, NewsDetailActivity::class.java)
        intent.putExtra(AppConstants.INTENT_EXTRA_ARTICLE_ID, articleId)
        startActivity(intent)
    }
}