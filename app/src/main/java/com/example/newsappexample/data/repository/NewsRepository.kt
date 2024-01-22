package com.example.newsappexample.data.repository

import com.example.newsappexample.data.datasource.NewsDataSource
import com.example.newsappexample.data.model.ArticlesItem
import com.example.newsappexample.data.model.NewsResponse
import retrofit2.Response

class NewsRepository(
    private val newsDataSource : NewsDataSource
) {

    suspend fun getAllNews(category: String, pageNumber: Int): Response<NewsResponse> =
        newsDataSource.getAllNews(category, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int): Response<NewsResponse> =
        newsDataSource.searchNews(searchQuery, pageNumber)

    suspend fun favorite(articlesItem: ArticlesItem) = newsDataSource.favorite(articlesItem)

    fun getFavoriteNews() = newsDataSource.getFavoriteNews()

    suspend fun deleteFavoriteNews(articlesItem: ArticlesItem) = newsDataSource.deleteFavoriteNews(articlesItem)

}