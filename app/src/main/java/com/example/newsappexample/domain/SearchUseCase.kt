package com.example.newsappexample.domain

import androidx.paging.PagingData
import com.example.newsappexample.data.model.ArticlesItem
import com.example.newsappexample.data.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(searchQuery: String): Flow<PagingData<ArticlesItem>> =
        newsRepository.searchNews(searchQuery)
}