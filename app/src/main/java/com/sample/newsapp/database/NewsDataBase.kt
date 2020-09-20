package com.sample.newsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sample.newsapp.database.dao.ArticleDao
import com.sample.newsapp.database.entities.Article
import com.sample.newsapp.newsapplication.AppConstants

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class NewsDataBase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {

        private var instance: NewsDataBase? = null
        private val lock = Any()

        fun getInstance(context: Context): NewsDataBase {
            if (instance == null) {
                synchronized(lock) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NewsDataBase::class.java,
                        AppConstants.DATABASE_NAME
                    ).build()
                }
            }
            return instance!!
        }

    }
}