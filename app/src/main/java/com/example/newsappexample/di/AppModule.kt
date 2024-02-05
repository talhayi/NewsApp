package com.example.newsappexample.di

import android.content.Context
import androidx.room.Room
import com.example.newsappexample.data.datasource.NewsDataSource
import com.example.newsappexample.data.repository.NewsRepository
import com.example.newsappexample.retrofit.ApiUtils
import com.example.newsappexample.retrofit.NewsApi
import com.example.newsappexample.room.NewsDao
import com.example.newsappexample.room.NewsDatabase
import com.example.newsappexample.ui.adapter.NewsAdapter
import com.example.newsappexample.ui.adapter.NewsPagingAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNewsDataSource(newsDao: NewsDao, newsApi: NewsApi): NewsDataSource {
        return NewsDataSource(newsDao, newsApi)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsDataSource: NewsDataSource): NewsRepository{
        return NewsRepository(newsDataSource)
    }

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi{
        return ApiUtils.getAllNews()
    }

    @Provides
    @Singleton
    fun provideNewsDao(@ApplicationContext context: Context): NewsDao{
        val db = Room.databaseBuilder(context, NewsDatabase::class.java,"news.sqlite").fallbackToDestructiveMigration().build()
        return db.getNewsDao()
    }

    @Provides
    @Singleton
    fun provideNewsAdapter(): NewsAdapter{
        return NewsAdapter()
    }

    @Provides
    @Singleton
    fun provideNewsPagingAdapter(): NewsPagingAdapter {
        return NewsPagingAdapter()
    }
}