package com.example.newsappexample.data.repository

import androidx.paging.PagingData
import com.example.newsappexample.data.datasource.NewsDataSource
import com.example.newsappexample.data.model.ArticlesItem
import com.example.newsappexample.data.model.NewsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepository(
    private val newsDataSource : NewsDataSource
) {

    fun getAllNews(category: String): Flow<PagingData<ArticlesItem>> =
        newsDataSource.getAllNews(category)

    fun searchNews(searchQuery: String):Flow<PagingData<ArticlesItem>> =
        newsDataSource.searchNews(searchQuery)

    suspend fun favorite(articlesItem: ArticlesItem) = newsDataSource.favorite(articlesItem)

    fun getFavoriteNews() = newsDataSource.getFavoriteNews()

    suspend fun deleteFavoriteNews(articlesItem: ArticlesItem) = newsDataSource.deleteFavoriteNews(articlesItem)

}