package com.example.newsappexample.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsappexample.data.model.ArticlesItem
import com.example.newsappexample.retrofit.NewsApi

class NewsPagingSource(
    private val category: String?,
    private val searchQuery: String?,
    private val newsApi: NewsApi
) : PagingSource<Int, ArticlesItem>() {
    override fun getRefreshKey(state: PagingState<Int, ArticlesItem>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1)?:anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItem> {
        val page = params.key?:1
        return try {
            val data = if (category != null){
                newsApi.getAllNews(category, page)
            }else{
                newsApi.searchNews(searchQuery!!, page)
            }
            val articles: List<ArticlesItem> = data.body()?.articles!!.filterNotNull()
                //.map { it.copy(isFavorite = true) }
            LoadResult.Page(
                data = articles,
                prevKey = if (page==1) null else page -1,
                nextKey = if (articles.isEmpty()) null else page +1
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}