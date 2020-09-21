package com.sample.newsapp.ui.news

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.newsapp.database.NewsDataBase
import com.sample.newsapp.database.entities.Article
import com.sample.newsapp.listeners.RestCallback
import com.sample.newsapp.model.NewsModel
import com.sample.newsapp.repository.NewsRepository
import com.sample.newsapp.util.Coroutines

class NewsViewModel : ViewModel() {

    private val TAG = NewsViewModel::class.java.simpleName

    var newsApiSuccess = MutableLiveData<Boolean>()
    var newsModel: NewsModel? = null
    var articleList = MutableLiveData<List<Article>>()

    /**
     * This function fetches news and updates newsApiSuccess(MutableLiveData) based on success/failure
     */
    fun getNews(context: Context) {

        val callback = object : RestCallback<NewsModel> {
            override fun onSuccess(t: NewsModel?) {
                Log.d(TAG, "getNews success value = $t")
                Log.d(TAG, "getNews success news size = ${t?.totalResults}")
                t?.let {
                    newsModel = it
                }
                if (!newsModel?.articles.isNullOrEmpty()) {
                    Coroutines.io {
                        val titleList = ArrayList<String>()
                        newsModel?.articles?.forEach {
                            titleList.add(it.title ?: "")
                        }
                        NewsDataBase.getInstance(context).getArticleDao()
                            .updateArticles(newsModel?.articles!!, titleList)
                    }
                }
                newsApiSuccess.postValue(true)
            }

            override fun onFailure(error: String) {
                Log.e(TAG, "getNews failed")
                newsApiSuccess.postValue(false)
            }
        }
        NewsRepository().getNewsFromApi("IN", callback)
    }

    fun getArticlesFromDB(context: Context) {
        Coroutines.io {
            val articles = NewsRepository().getNewsFromDB(context)
            articleList.postValue(articles)
        }
    }
}