package com.goodboards.app.analyzer

import com.goodboards.db.News
import com.goodboards.workflow.Worker
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


class AnalyzerWorker(override val name: String = "data-analyzer") : Worker<AnalyzerTask> {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun execute(task: AnalyzerTask) {
        runBlocking {
            logger.info("starting data analysis.")

            val dbInterface = Wrapper.getDBInterface()
            val conn = Wrapper.getConnection()
            val redisInterface = Wrapper.getRedisInterface()
            val redisQueue = redisInterface.getFromList(key = "news:collect-analyze", 10)
            for (item in redisQueue) {
                val newsItem = Json.decodeFromString<NewsUnit>(item)
                newsItem.title = AnalyzerWorkerHelper.setDoubleApostrophe(newsItem.title)
                newsItem.description = AnalyzerWorkerHelper.setDoubleApostrophe(newsItem.description)
                val result = AnalyzerWorkerHelper.getQuery(newsItem, conn)
                if (result != null) {
                    if (!result.next()) {
                        dbInterface.addNews(newsItem.title,newsItem.description,newsItem.url, newsItem.gameID, Wrapper.getRandomUUID())
                    }
                }
                logger.info("completed data analysis.")
            }
        }
    }
}