package com.example.newsappexample.domain

import com.example.newsappexample.data.model.ArticlesItem
import com.example.newsappexample.data.repository.NewsRepository
import javax.inject.Inject

class FavoriteUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend fun favorite(articlesItem: ArticlesItem, url: String) = newsRepository.favorite(articlesItem, url)

    fun getFavoriteNews() = newsRepository.getFavoriteNews()

    suspend fun deleteFavoriteNews(articlesItem: ArticlesItem) = newsRepository.deleteFavoriteNews(articlesItem)
}