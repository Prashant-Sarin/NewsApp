package com.sample.newsapp.ui.news

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sample.newsapp.BR
import com.sample.newsapp.R
import com.sample.newsapp.database.entities.Article
import com.sample.newsapp.databinding.ListItemNewsBinding
import com.sample.newsapp.listeners.CustomItemClickListener


class NewsAdapter(
    var context: Context,
    var articleList: ArrayList<Article>,
    val listener: (View, Article) -> Unit
) :
    RecyclerView.Adapter<NewsAdapter.NewsHolder>(), CustomItemClickListener {

    private val TAG = NewsAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        return NewsHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_news,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = articleList.size

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val article = articleList[position]
        holder.bindArticle(article)
        Log.d(TAG, "total articles = ${articleList.size}")
        if (article.urlToImage.isNullOrBlank()) {
            holder.listItemNewsBinding.ivNews.visibility = View.GONE
        } else {
            Glide.with(context)
                .load(article.urlToImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(context.getDrawable(R.drawable.ic_image_64))
                .into(holder.listItemNewsBinding.ivNews)
        }


//        addLinks(holder, feed)
    }

    inner class NewsHolder(var listItemNewsBinding: ListItemNewsBinding) :
        RecyclerView.ViewHolder(listItemNewsBinding.root) {
        fun bindArticle(articleModel: Article) {
            listItemNewsBinding.setVariable(BR.articleModel, articleModel)
            listItemNewsBinding.setVariable(BR.itemClickListener, this@NewsAdapter)
            listItemNewsBinding.executePendingBindings()
        }
    }

    override fun <T> onItemClick(view: View, t: T) {
        val article = t as Article
        listener(view, article)
    }

    /**
     * This method adds links to text under news article object
     */
    /* private fun addLinks(holder: NewsHolder, feed: ArticleModel) {
         val filter = TransformFilter { match, url -> match.group() }

         val mentionPattern: Pattern = Pattern.compile("@([A-Za-z0-9_-]+)")
         val mentionScheme = "http://www.twitter.com/"
         Linkify.addLinks(
             holder.listItemFeedBinding.tvText,
             mentionPattern,
             mentionScheme,
             null,
             filter
         )

         val hashtagPattern: Pattern = Pattern.compile("#([A-Za-z0-9_-]+)")
         val hashtagScheme = "http://www.twitter.com/search/"
         Linkify.addLinks(
             holder.listItemFeedBinding.tvText,
             hashtagPattern,
             hashtagScheme,
             null,
             filter
         )

         val urlPattern: Pattern = Patterns.WEB_URL
         Linkify.addLinks(holder.listItemFeedBinding.tvText, urlPattern, null, null, filter)
     }
 */
}