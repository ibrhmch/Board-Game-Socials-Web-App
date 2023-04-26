package com.goodboards.app.kt

import kotlinx.serialization.Serializable

@Serializable
data class Articles(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)
@Serializable
data class Source(
    val id: String,
    val name: String
)

@Serializable
data class NewsResponse(val status: String, val totalResults: Int, val articles: List<Articles>)

class News
private constructor(articles: List<Articles>?)
