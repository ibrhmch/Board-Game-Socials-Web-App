package com.goodboards.app.analyzer

import java.sql.Connection
import java.sql.ResultSet

object AnalyzerWorkerHelper {

        fun setDoubleApostrophe(title: String): String {
            var temp = ""
            for(character in title.iterator()){
                if(character == '\''){
                    temp = temp + character.toString() + character.toString()
                }else {
                    temp += character.toString()
                }
            }
            return temp
        }

        fun getQuery(newsItem: NewsUnit, conn: Connection): ResultSet? {
            val query = conn.prepareStatement("SELECT * FROM ${Wrapper.NEWS_TABLE_NAME} where title = '${newsItem.title}';")
            return query.executeQuery()
        }
}