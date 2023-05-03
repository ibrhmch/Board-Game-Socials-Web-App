package com.goodboards.app.analyzer

import com.goodboards.db.DBInterface
import com.goodboards.redis.RedisInterface
import java.util.*

object Wrapper {
    fun getRedisInterface(): RedisInterface = RedisInterface()

    fun getDBInterface(): DBInterface{
        return DBHelper.getDBInterface()
    }
    fun getRandomUUID(): String = UUID.randomUUID().toString()
    fun getenv(key: String): String = System.getenv(key)!!

}