package com.example.newsappexample.data.datasource


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsappexample.data.model.ArticlesItem
import com.example.newsappexample.data.model.NewsResponse
import com.example.newsappexample.retrofit.NewsApi
import com.example.newsappexample.room.NewsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response

class NewsDataSource(
    private var newsDao: NewsDao,
    private var newsApi: NewsApi
) {
    fun getAllNews(category: String): Flow<PagingData<ArticlesItem>>{
        return Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {NewsPagingSource(category, null, newsApi)}
        ).flow
    }

    fun searchNews(searchQuery: String): Flow<PagingData<ArticlesItem>>{
        return Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {NewsPagingSource(null, searchQuery, newsApi)}
        ).flow
    }

    suspend fun favorite(articlesItem: ArticlesItem) = newsDao.insert(articlesItem)

    fun getFavoriteNews() = newsDao.getAllNews()

    suspend fun deleteFavoriteNews(articlesItem: ArticlesItem) = newsDao.deleteNews(articlesItem)
}