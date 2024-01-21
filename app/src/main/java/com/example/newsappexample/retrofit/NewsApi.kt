package com.example.newsappexample.retrofit

import com.example.newsappexample.retrofit.ApiUtils.Companion.API_KEY
import com.example.newsappexample.data.model.NewsResponse

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getAllNews(
        @Query("category") category: String,
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("country") country: String = "us"
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>
}