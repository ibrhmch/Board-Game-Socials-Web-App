package com.goodboards.app.analyzer

import com.goodboards.db.DBConnection
import com.goodboards.db.DBInterface

// TODO refactor DB Connection management
object DBHelper {

        var dbInterface : DBInterface? = null
        data class DatabaseCredential(val url: String, val username: String, val password: String)

        private fun initDBInterface() : DBInterface{
            val dbCredentials = getDatabaseCredentials()
            val dbConnection = DBConnection
            dbConnection.setConnection(dbCredentials.url, dbCredentials.username, dbCredentials.password)
            return DBInterface(dbConnection)
        }

        fun getDBInterface() : DBInterface {
            if (dbInterface == null) {
                dbInterface = initDBInterface()
            }
            return dbInterface!!
        }

        fun getDatabaseCredentials() : DatabaseCredential {
            val DB_URL: String = Wrapper.getenv("JDBC_DATABASE_URL")
            val DB_USENAME : String =  Wrapper.getenv("DATABASE_USERNAME")
            val DB_PASSWORD : String = Wrapper.getenv("DATABASE_PASSWORD")
            return DatabaseCredential(DB_URL, DB_USENAME, DB_PASSWORD)
        }

}