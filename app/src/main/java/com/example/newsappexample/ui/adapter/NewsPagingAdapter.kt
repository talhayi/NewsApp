package com.example.newsappexample.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsappexample.R
import com.example.newsappexample.data.model.news.ArticlesItem
import com.example.newsappexample.databinding.NewsItemLayoutBinding

class NewsPagingAdapter: PagingDataAdapter<ArticlesItem, NewsPagingAdapter.NewsPagingViewHolder>(differCallBack)  {
    private var onItemClickListener: ((ArticlesItem) -> Unit)? = null
    private var onFavoriteButtonClickListener: ((ArticlesItem) -> Unit)? = null
    val emptyPagingData = PagingData.empty<ArticlesItem>()
    inner class NewsPagingViewHolder(var binding: NewsItemLayoutBinding): RecyclerView.ViewHolder(binding.root)
    companion object{
        val differCallBack = object : DiffUtil.ItemCallback<ArticlesItem>(){
            override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: NewsPagingViewHolder, position: Int) {
        val news = getItem(position)?:return
        holder.binding.apply {
            Glide.with(root).load(news.urlToImage).placeholder(R.drawable.no_image).override(1000, 500) .into(newsImageView)
            authorTextView.text = news.author
            titleTextView.text = news.title
            descriptionTextView.text = news.description
            root.setOnClickListener{
                onItemClickListener?.let { it(news) }
            }
            favoriteButton.setOnClickListener {
                onFavoriteButtonClickListener?.let {
                    it(news)
                    favoriteButton.setColorFilter(root.context.getColor(R.color.red))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsPagingViewHolder {
        val binding = NewsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsPagingViewHolder(binding)
    }

    fun setOnItemClickListener(listener: (ArticlesItem) ->Unit, ){
        onItemClickListener = listener
    }
    fun setOnFavoriteButtonClickListener(listener: (ArticlesItem) -> Unit) {
        onFavoriteButtonClickListener = listener
    }
}
