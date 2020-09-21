package com.sample.newsapp.ui.news

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.newsapp.database.NewsDataBase
import com.sample.newsapp.database.entities.Article
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
        Coroutines.io {
            try {
                newsModel = NewsRepository().getNewsFromApi("IN")
                Log.d(TAG, "getNews success articles size = ${newsModel?.articles?.size}")
                Log.d(TAG, "getNews success news totalSize = ${newsModel?.totalResults}")

                if (!newsModel?.articles.isNullOrEmpty()) {

                    val titleList = ArrayList<String>()
                    newsModel?.articles?.forEach {
                        titleList.add(it.title ?: "")
                    }
                    NewsDataBase.getInstance(context).getArticleDao()
                        .updateArticles(newsModel?.articles!!, titleList)
                }
                newsApiSuccess.postValue(true)
            } catch (ex: Exception) {
                Log.e(TAG, "getNews failed")
                newsApiSuccess.postValue(false)
            }
        }
    }

    fun getArticlesFromDB(context: Context) {
        Coroutines.io {
            val articles = NewsRepository().getNewsFromDB(context)
            articleList.postValue(articles)
        }
    }
}