package com.goodboards.app.news

import com.goodboards.app.database.DBHelper


class NewsHelper {
    companion object {
        fun getNewsForGame(gameId: String): List<News> {
            val dbNews = DBHelper.getDBInterface().getNewsForGame(gameId)
            return dbNews.map { dbnews-> News(dbnews.id, dbnews.gameId, dbnews.title, dbnews.url, dbnews.description) }
        }
    }
}