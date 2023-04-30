package com.goodboards.app.analyzer

import kotlinx.serialization.Serializable

class News(val id: String, val gameId: String, val title: String, val description: String, val url:String)

@Serializable
data class NewsUnit(val gameID: String,
                    val title: String,
                    val description: String,
                    val url: String)

