package com.example.newsappexample.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class NewsResponse(
	val totalResults: Int? = null,
	val articles: List<ArticlesItem?>? = null,
	val status: String? = null
)
@Entity(tableName = "articles")
data class ArticlesItem(
	@PrimaryKey(autoGenerate = true)
	var id: Int? = null,
	val publishedAt: String? = null,
	val author: String? = null,
	val urlToImage: String? = null,
	val description: String? = null,
	val source: Source? = null,
	val title: String? = null,
	val url: String? = null,
	val content: String? = null
)

data class Source(
	val name: String? = null,
	val id: String? = null
)
