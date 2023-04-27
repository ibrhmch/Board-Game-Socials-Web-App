package com.goodboards.app.database

import com.goodboards.db.DBConnection
import com.goodboards.db.DBInterface
import com.goodboards.db.SystemWrapper

object DBHelper {

        var dbInterface : DBInterface? = null

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

        fun getDatabaseCredentials() : DatabaseInit.DatabaseCredential {
            val DB_URL: String = "postgresql://localhost:5432/goodboards"
            val DB_USENAME : String =  "postgres"
            val DB_PASSWORD : String = "asdfASDFasdf1!"
            return DatabaseInit.DatabaseCredential(DB_URL, DB_USENAME, DB_PASSWORD)
        }
}