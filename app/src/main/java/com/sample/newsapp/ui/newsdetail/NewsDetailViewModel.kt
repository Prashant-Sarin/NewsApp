package com.sample.newsapp.ui.newsdetail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.newsapp.database.entities.Article
import com.sample.newsapp.repository.NewsRepository
import com.sample.newsapp.util.Coroutines

class NewsDetailViewModel : ViewModel() {

    var article: Article? = null
    var articleUpdated  = MutableLiveData<Boolean>()
    var visitSource = MutableLiveData<Boolean>()

    fun visitPressed() {
        visitSource.postValue(true)
    }

    fun fetchArticle(context: Context, articleId: Int) {
        Coroutines.io {
            article = NewsRepository().getArticleFromDB(context, articleId)
            articleUpdated.postValue(true)
        }
    }

}