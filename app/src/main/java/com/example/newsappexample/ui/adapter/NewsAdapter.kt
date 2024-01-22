package com.example.newsappexample.ui.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsappexample.R
import com.example.newsappexample.databinding.NewsItemLayoutBinding
import com.example.newsappexample.data.model.ArticlesItem

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var onItemClickListener: ((ArticlesItem) -> Unit)? = null
    private var onFavoriteButtonClickListener: ((ArticlesItem) -> Unit)? = null
    inner class NewsViewHolder(var binding: NewsItemLayoutBinding):RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<ArticlesItem>(){
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = differ.currentList[position]

        holder.binding.apply {
            Glide.with(root).load(news.urlToImage).placeholder(R.drawable.no_image).override(1000, 500) .into(newsImageView)
            authorTextView.text = news.author
            titleTextView.text = news.title
            descriptionTextView.text = news.description
            root.setOnClickListener{
               onItemClickListener?.let { it(news) }
            }
            favoriteButton.setOnClickListener {
                onFavoriteButtonClickListener?.let { it(news)}
            }
        }
    }

    fun setOnItemClickListener(listener: (ArticlesItem) ->Unit, ){
        onItemClickListener = listener
    }
    fun setOnFavoriteButtonClickListener(listener: (ArticlesItem) -> Unit) {
        onFavoriteButtonClickListener = listener
    }
}