package com.example.newsappexample.data.datasource

import com.example.newsappexample.data.model.NewsResponse
import com.example.newsappexample.retrofit.NewsApi
import com.example.newsappexample.room.NewsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class NewsDataSource(
    private var newsDao: NewsDao,
    private var newsApi: NewsApi
) {

    suspend fun getAllNews(category: String, pageNumber: Int): Response<NewsResponse> =
        withContext(Dispatchers.IO){
            return@withContext newsApi.getAllNews(category, pageNumber)
        }

    suspend fun searchNews(searchQuery: String, pageNumber: Int): Response<NewsResponse> =
        withContext(Dispatchers.IO){
            return@withContext newsApi.searchNews(searchQuery, pageNumber)
        }
}