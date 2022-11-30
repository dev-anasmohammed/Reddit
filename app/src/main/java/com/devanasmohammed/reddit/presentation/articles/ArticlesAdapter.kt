package com.devanasmohammed.reddit.presentation.articles

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.devanasmohammed.reddit.R
import com.devanasmohammed.reddit.data.model.Article

class ArticlesAdapter(
    private val context: Context,
) : RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder>() {

    inner class ArticlesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        val author: TextView
        val publishedIcon: ImageView
        val publishedSince: TextView
        val thumbnail: ImageView
        val linear: View

        init {
            title = view.findViewById(R.id.title_tv)
            author = view.findViewById(R.id.author_tv)
            publishedIcon = view.findViewById(R.id.published_icon_iv)
            publishedSince = view.findViewById(R.id.published_since_tv)
            thumbnail = view.findViewById(R.id.thumbnail_iv)
            linear = view.findViewById(R.id.linear_v)
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, differCallback)

    fun submitList(list: List<Article>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        return ArticlesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_article, parent, false)
        )

    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.apply {
            //set data to views
            title.text = article.title
            author.text = article.author
            publishedSince.text = article.publishedSince

            if (article.thumbnailUrl != "") {
                handleIfImage(holder, article.thumbnailUrl.toString(), thumbnail)
            }
        }
    }


    override fun getItemCount() = differ.currentList.size

    private fun handleIfImage(holder: ArticlesViewHolder, url: String, thumbnail: ImageView) {
        val whiteColor = ResourcesCompat.getColor(context.resources, R.color.white, null)

        holder.apply {
            title.setTextColor(whiteColor)
            author.setTextColor(whiteColor)
            publishedSince.setTextColor(whiteColor)
            publishedIcon.setImageResource(R.drawable.ic_time_white)

            thumbnail.visibility = View.VISIBLE
            linear.visibility = View.VISIBLE
        }
        setupGlide(url, thumbnail)
    }

    private fun setupGlide(url: String, thumbnail: ImageView) {
        Glide.with(context)
            .load(url)
//            .placeholder(getShimmerDrawable())
//            .error(R.drawable.ic_no_photo)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
//                    holder.shimmer.hideShimmer()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
//                    holder.shimmer.hideShimmer()
                    return false
                }

            })
            .into(thumbnail)
    }

}