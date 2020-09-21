package com.sample.newsapp.repository

import android.content.Context
import android.util.Log
import com.sample.newsapp.database.NewsDataBase
import com.sample.newsapp.database.entities.Article
import com.sample.newsapp.model.NewsModel
import com.sample.newsapp.network.Api
import com.sample.newsapp.network.ApiRequest
import com.sample.newsapp.newsapplication.AppConstants

class NewsRepository : ApiRequest() {

    private val TAG = NewsRepository::class.java.simpleName

    private val api = Api.create()

    suspend fun getNewsFromApi(country: String): NewsModel {
       return apiRequest {
           api.getHeadlines(country, AppConstants.NewsApiKey)
       }
    }

    suspend fun getNewsFromDB(context: Context): List<Article>{
        val articles = NewsDataBase.getInstance(context).getArticleDao().getAllArticles()
        Log.d(TAG,"db articles = $articles")
        return articles
    }

    suspend fun getArticleFromDB(context: Context, articleId: Int): Article{
        Log.d(TAG,"articleId = $articleId")
        val article = NewsDataBase.getInstance(context).getArticleDao().getArticle(articleId)
        Log.d(TAG,"db article = $article")
        return article
    }

}