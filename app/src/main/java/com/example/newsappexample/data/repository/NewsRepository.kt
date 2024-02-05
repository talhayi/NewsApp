package com.example.newsappexample.data.repository

import androidx.paging.PagingData
import com.example.newsappexample.data.datasource.NewsDataSource
import com.example.newsappexample.data.model.news.ArticlesItem
import kotlinx.coroutines.flow.Flow

class NewsRepository(
    private val newsDataSource : NewsDataSource
) {

    fun getAllNews(category: String): Flow<PagingData<ArticlesItem>> =
        newsDataSource.getAllNews(category)

    fun searchNews(searchQuery: String):Flow<PagingData<ArticlesItem>> =
        newsDataSource.searchNews(searchQuery)

    suspend fun favorite(articlesItem: ArticlesItem, url: String) = newsDataSource.favorite(articlesItem, url)

    fun getFavoriteNews() = newsDataSource.getFavoriteNews()

    suspend fun deleteFavoriteNews(articlesItem: ArticlesItem) = newsDataSource.deleteFavoriteNews(articlesItem)

}