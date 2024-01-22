package com.example.newsappexample.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsappexample.data.model.ArticlesItem
import com.example.newsappexample.data.model.NewsResponse
import com.example.newsappexample.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.example.newsappexample.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel() {

    val allNews: MutableLiveData<Result<NewsResponse>> = MutableLiveData()
    var newsPage = 1

    val searchNews: MutableLiveData<Result<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1

    fun getAllNews(category: String){
        CoroutineScope(Dispatchers.Main).launch {
            allNews.value = Result.Loading
            val response = newsRepository.getAllNews(category, newsPage)
            allNews.value = handleNewsResponse(response)
        }
    }

    fun getSearchNews(searchQuery: String){
        CoroutineScope(Dispatchers.Main).launch {
            searchNews.value = Result.Loading
            val response = newsRepository.searchNews(searchQuery, newsPage)
            searchNews.value = handleSearchNewsResponse(response)
        }
    }

    private fun handleNewsResponse(response: Response<NewsResponse>): Result<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let {resultResponse->

                return Result.Success(resultResponse)
            }
        }
        return Result.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Result<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let {resultResponse->

                return Result.Success(resultResponse)
            }
        }
        return Result.Error(response.message())
    }

    fun favorite (articlesItem: ArticlesItem){
        CoroutineScope(Dispatchers.Main).launch {
            newsRepository.favorite(articlesItem)
        }
    }

    fun getFavoriteNews () =  newsRepository.getFavoriteNews()

    fun deleteFavoriteNews(articlesItem: ArticlesItem){
        CoroutineScope(Dispatchers.Main).launch {
            newsRepository.deleteFavoriteNews(articlesItem)
        }
    }
}