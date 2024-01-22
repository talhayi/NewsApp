package com.example.newsappexample.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsappexample.data.model.ArticlesItem
import com.example.newsappexample.domain.FavoriteUseCase
import com.example.newsappexample.domain.NewsUseCase
import com.example.newsappexample.domain.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase,
    private val searchUseCase: SearchUseCase,
    private val favoriteUseCase: FavoriteUseCase,
): ViewModel() {

    private val _allNews: MutableLiveData<PagingData<ArticlesItem>> = MutableLiveData()
    val allNews: LiveData<PagingData<ArticlesItem>> = _allNews

    private val _searchNews: MutableLiveData<PagingData<ArticlesItem>> = MutableLiveData()
    val searchNews: LiveData<PagingData<ArticlesItem>> = _searchNews

    fun getAllNews(category: String){
        viewModelScope.launch {
            newsUseCase(category).cachedIn(this).collectLatest {
                _allNews.value = it
            }
        }
    }

    fun getSearchNews(searchQuery: String){
        viewModelScope.launch {
            searchUseCase(searchQuery).cachedIn(this).collectLatest {
                _searchNews.value = it
            }
        }
    }

    fun favorite (articlesItem: ArticlesItem){
        viewModelScope.launch {
            favoriteUseCase.favorite(articlesItem)
        }
    }
    fun getFavoriteNews() =
        favoriteUseCase.getFavoriteNews()

    fun deleteFavoriteNews(articlesItem: ArticlesItem){
        viewModelScope.launch {
            favoriteUseCase.deleteFavoriteNews(articlesItem)
        }
    }
}