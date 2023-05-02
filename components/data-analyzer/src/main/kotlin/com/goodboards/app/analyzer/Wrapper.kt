package com.goodboards.app.analyzer

import com.goodboards.db.DBConnection
import com.goodboards.redis.RedisInterface
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

object Wrapper {

    const val NEWS_TABLE_NAME: String = "goodboards.news"

    fun getRedisInterface(): RedisInterface = RedisInterface()

    fun getTableName(): String = NEWS_TABLE_NAME

    fun getDBConnection(): Connection {
        val dbCredentials = DBConnection.getCredentials()
        return DriverManager.getConnection(dbCredentials.url, dbCredentials.username, dbCredentials.password)
    }

    fun getInsertNewsStatement(id: String, news: NewsUnit): String{
        return "INSERT INTO ${NEWS_TABLE_NAME}(id, gameId, title, description, url) VALUES ('${id}', '${news.gameID}', '${news.title}', '${news.description}', '${news.url}');"
    }

    fun setDoubleApostrophe(str: String): String{
        var temp = ""
        for(character in str.iterator()){
            if(character == '\''){
                temp = temp + character.toString() + character.toString()
            }else {
                temp += character.toString()
            }
        }
        return temp
    }

    fun getRandomUUID(): String = UUID.randomUUID().toString()!!

}