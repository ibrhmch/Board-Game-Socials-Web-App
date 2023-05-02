package com.goodboards.app.analyzer

import com.goodboards.app.database.DBHelper
import com.goodboards.db.DBConnection
import com.goodboards.db.DBInterface
import com.goodboards.redis.RedisInterface
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

object Wrapper {
    fun getRedisInterface(): RedisInterface = RedisInterface()

    fun getDBInterface(): DBInterface{
        return DBHelper.getDBInterface()
    }
    fun getRandomUUID(): String = UUID.randomUUID().toString()

}