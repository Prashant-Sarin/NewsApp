package com.sample.newsapp.database.dao

import androidx.room.*
import com.sample.newsapp.database.entities.Article

@Dao
interface ArticleDao {


    @Query("SELECT * FROM Article")
    suspend fun getAllArticles(): List<Article>

    @Query("SELECT * FROM Article WHERE article_id = :articleId")
    suspend fun getArticle(articleId: Int): Article

    @Transaction
    suspend fun updateArticles(articles: List<Article>?, listTitles: List<String?>?) {
        bulkInsert(articles)
        deleteOldArticles(listTitles)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun bulkInsert(articles: List<Article>?)

    @Query("DELETE FROM Article WHERE title NOT IN(:listTitles)")
    suspend fun deleteOldArticles(listTitles: List<String?>?)

}