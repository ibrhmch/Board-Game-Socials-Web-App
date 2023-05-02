package com.goodboards.app.analyzer

import com.goodboards.db.News
import com.goodboards.workflow.Worker
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


class ExampleWorker(override val name: String = "data-analyzer") : Worker<AnalyzerTask> {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun execute(task: AnalyzerTask) {
        runBlocking {
            logger.info("starting data analysis.")

            // todo - data analysis happens here
            val conn = Wrapper.getDBConnection()
            val redisInterface = Wrapper.getRedisInterface()
            val redisQueue = redisInterface.getFromList(key = "news:collect-analyze", 10)
            for (item in redisQueue) {
                var newsItem = Json.decodeFromString<NewsUnit>(item)
                newsItem.title = Wrapper.setDoubleApostrophe(newsItem.title)
                newsItem.description = Wrapper.setDoubleApostrophe(newsItem.description)
                val query =
                    conn.prepareStatement("SELECT * FROM ${Wrapper.getTableName()} where title = '${newsItem.title}';")
                val result = query.executeQuery()
                if (!result.next()) {
                    val newsInsertStatement =
                        Wrapper.getInsertNewsStatement(Wrapper.getRandomUUID(), newsItem)
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