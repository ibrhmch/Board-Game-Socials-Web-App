package com.goodboards.app.analyzer

import com.goodboards.db.DBConnection
import com.goodboards.db.DBInterface
import com.goodboards.redis.RedisInterface
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

object Wrapper {

    const val NEWS_TABLE_NAME: String = "goodboards.news"

    fun getRedisInterface(): RedisInterface = RedisInterface()

    fun getConnection(): Connection {
        val dbCredentials = DBConnection.getCredentials()
        return DriverManager.getConnection(dbCredentials.url, dbCredentials.username, dbCredentials.password)
    }

    fun getDBInterface(): DBInterface{
        return DBInterface(DBConnection)
    }
    fun getRandomUUID(): String = UUID.randomUUID().toString()

}