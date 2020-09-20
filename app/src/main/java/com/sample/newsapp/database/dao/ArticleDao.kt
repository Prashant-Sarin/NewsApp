package com.sample.newsapp.database.dao

import androidx.room.*
import com.sample.newsapp.database.entities.Article

@Dao
interface ArticleDao {
   /* @Transaction
    fun updateArticles(articles: List<Article>) {
        deleteAllArticles()
//        insertAllArticles(articles)
        bulkInsert(articles)
    }*/

    @Query("SELECT * FROM Article ORDER BY article_id DESC")
    fun getAllArticles(): List<Article>

    @Query("SELECT * FROM Article WHERE article_id = :articleId")
    fun getArticle(articleId: Int): Article

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    fun insertAllArticles(articles: List<Article>)

//    @Query("DELETE FROM Article")
//    fun deleteAllArticles()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun bulkInsert(articles: List<Article>?)

}