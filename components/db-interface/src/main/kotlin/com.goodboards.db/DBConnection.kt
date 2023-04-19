package com.goodboards.db

import java.sql.Connection
import java.sql.DriverManager

// TODO: decide if DBConnection.setConnection() prior to all uses is good?
object DBConnection {
    private val jdbcUrl = "jdbc:" + System.getenv("DATABASE_URL")
    private val username = System.getenv("DATABASE_USERNAME")
    private val password = System.getenv("DATABASE_PASSWORD")
    private var connection: Connection? = null

    // TODO: find a better way to write this
    fun isConnected(): Boolean {
        if(connection == null) {
            return false
        }
        return true
    }

    fun getConnection(): Connection {
        return connection!!
    }

    fun setConnection() {
        connection = DriverManager
            .getConnection(jdbcUrl, username, password)
    }
}