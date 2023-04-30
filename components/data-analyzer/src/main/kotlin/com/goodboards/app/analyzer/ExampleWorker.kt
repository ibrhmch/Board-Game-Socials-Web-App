package com.goodboards.app.analyzer

import com.goodboards.db.News
import com.goodboards.workflow.Worker
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

object ConnectionHelper{
    fun getConnection(): Connection {
        val databaseCredential = DatabaseInit.getDatabaseCredentials()
        return DriverManager.getConnection(databaseCredential.url, databaseCredential.username, databaseCredential.password)
    }
}
object DatabaseInit{
    const val NEWS_TABLE_NAME: String = "goodboards.news"
    data class DatabaseCredential(val url: String, val username:String, val password: String)
    fun getDatabaseCredentials() : DatabaseCredential  {
        val DB_URL: String = EnvHelper.getEnv("JDBC_DATABASE_URL")
        val DB_USENAME : String =  EnvHelper.getEnv("DATABASE_USERNAME")
        val DB_PASSWORD : String = EnvHelper.getEnv("DATABASE_PASSWORD")
        return DatabaseCredential(DB_URL, DB_USENAME, DB_PASSWORD)
    }
    fun getInsertNewsStatement(id: String, news: NewsUnit): String {
        return "INSERT INTO $NEWS_TABLE_NAME(id, gameId, title, description, url) VALUES ('${id}', '${news.gameID}', '${news.title}', '${news.description}', '${news.url}');"
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
}

object EnvHelper {
    fun getEnv(key: String): String = System.getenv(key)!!
}

object UUIDHelper{
    fun randomUUID(): UUID = UUID.randomUUID()!!
}

class ExampleWorker(override val name: String = "data-analyzer") : Worker<ExampleTask> {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun execute(task: ExampleTask) {
        runBlocking {
            logger.info("starting data analysis.")

            // todo - data analysis happens here
            val conn = ConnectionHelper.getConnection()!!
            val redisInterface = RedisInterface()
            val redisQueue = redisInterface.getFromList(key = "news:collect-analyze", 10)
            for (item in redisQueue) {
                var newsItem = Json.decodeFromString<NewsUnit>(item)
                newsItem.title = DatabaseInit.setDoubleApostrophe(newsItem.title)
                newsItem.description = DatabaseInit.setDoubleApostrophe(newsItem.description)
                val query =
                    conn.prepareStatement("SELECT * FROM ${DatabaseInit.NEWS_TABLE_NAME} where title = '${newsItem.title}';")
                val result = query.executeQuery()
                if (!result.next()) {
                    val newsInsertStatement =
                        DatabaseInit.getInsertNewsStatement(UUIDHelper.randomUUID().toString(), newsItem)
                    conn.createStatement().use {
                        val preparedStatement = conn.prepareStatement(newsInsertStatement)
                        preparedStatement.executeUpdate()
                        preparedStatement.close()
                    }
                }
                logger.info("completed data analysis.")
            }
        }
    }
}