package com.sample.newsapp.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.sample.newsapp.model.SourceModel

@Entity(indices = [Index(value = ["title"], unique = true)])
data class Article(
    @PrimaryKey(autoGenerate = true) @Expose(serialize = false, deserialize = false)
    var article_id: Int = 0,
    @Embedded
    var source: SourceModel?,
    var author: String?,
    var title: String?,
    var description: String?,
    var url: String?,
    var urlToImage: String?,
    var publishedAt: String?,
    var content: String?
) {
    fun getTrimmedContent(): String {
        return content?.substringBeforeLast("[") ?: (content ?: "")
    }

    fun getTrimmedTitle(): String {
        return title?.substringBeforeLast("-") ?: (title ?: "")
    }

    fun getSourceName(): String {
        return "- ${source?.name}"
    }
}