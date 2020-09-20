package com.sample.newsapp.model

import com.google.gson.annotations.SerializedName
import com.sample.newsapp.database.entities.Article

data class NewsModel (
    @SerializedName("status") var status: String? = null,
    @SerializedName("totalResults") var totalResults: Long? = null,
    @SerializedName("articles") var articles: List<Article>? = null
)