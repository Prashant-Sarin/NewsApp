package com.sample.newsapp.database.dao

import androidx.room.*
import com.sample.newsapp.database.entities.Article

@Dao
interface ArticleDao {


    @Query("SELECT * FROM Article ORDER BY article_id DESC")
    fun getAllArticles(): List<Article>

    @Query("SELECT * FROM Article WHERE article_id = :articleId")
    fun getArticle(articleId: Int): Article

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun bulkInsert(articles: List<Article>?)

}