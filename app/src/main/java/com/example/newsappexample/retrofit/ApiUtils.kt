package com.example.newsappexample.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class ApiUtils {

    companion object {
        private const val BASE_URL = "https://newsapi.org/"
        const val API_KEY = "a82260a85ac34754b3630308d6e75626"
        private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        fun getAllNews(): NewsApi =
            RetrofitClient.getClient(BASE_URL).create(NewsApi::class.java)
    }
}