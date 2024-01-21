package com.example.newsappexample.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    fun getAllNews(category: String){
        CoroutineScope(Dispatchers.Main).launch {
            allNews.value = Result.Loading
            val response = newsRepository.getAllNews(category, newsPage)
            allNews.value = handleNewsResponse(response)
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
}