package com.example.newsappexample.domain

import androidx.paging.PagingData
import com.example.newsappexample.data.model.news.ArticlesItem
import com.example.newsappexample.data.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(category: String): Flow<PagingData<ArticlesItem>> =
        newsRepository.getAllNews(category)
}