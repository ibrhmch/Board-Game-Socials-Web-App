package test.goodboards.app.analyzer

import com.goodboards.app.analyzer.*
import com.goodboards.db.DBInterface
import com.goodboards.redis.RedisInterface
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import org.junit.Test
import java.sql.Connection
import java.sql.ResultSet



class AnalyzerWorkerTest{
    // test if the news has not been included in db
    @Test
    fun testNewsNotIncludedinDB(){
        // mock the news unit that will be added
        val newsUnits = listOf<String>(
            """{
                "gameID": "Fake gameId",
                "title": "Fake title",
                "description": "Fake description",
                "url": "Fake URL"
            }""".trimIndent()
        )

        mockkObject(Wrapper)
        val mockedDBConnection: Connection = mockk(relaxed = true)
        val mockedRedisInterface: RedisInterface = mockk(relaxed = true)
        val mockedDBInterface: DBInterface = mockk(relaxed = true)
        val mockedAnalyzerWorkerHelper: AnalyzerWorkerHelper = mockk(relaxed = true)

        every { Wrapper.getConnection()} returns mockedDBConnection
        every { Wrapper.getRedisInterface() } returns mockedRedisInterface
        every { Wrapper.getRandomUUID()} returns "FakeUUID1"
        every { Wrapper.getDBInterface() } returns mockedDBInterface
        every { mockedRedisInterface.getFromList("news:collect-analyze", 10)} returns newsUnits


        val mockedResultSet: ResultSet = mockk(relaxed = true)
        val newsItem = NewsUnit("Fake gameId", "Fake title", "Fake description", "Fake URL")
        every{ mockedAnalyzerWorkerHelper.getQuery(newsItem, mockedDBConnection)} returns mockedResultSet
        every { mockedResultSet.next() } returns false
        every { mockedDBInterface.addNews(newsItem.title, newsItem.description, newsItem.url, newsItem.gameID, Wrapper.getRandomUUID()) } returns Unit

        val task = AnalyzerTask("Analyzer Task test")
        val worker = AnalyzerWorker()
        worker.execute(task)
    }

    @Test
    fun testNewsAlreadyinDB(){
        val newsUnits = listOf<String>("""{
                "gameID": "Fake gameId",
                "title": "Fake title",
                "description": "Fake description",
                "url": "Fake URL"
            }""")
        mockkObject(Wrapper)
        val mockedDBConnection: Connection = mockk(relaxed = true)
        val mockedRedisInterface: RedisInterface = mockk(relaxed = true)
        val mockedDBInterface: DBInterface = mockk(relaxed = true)
        val mockedAnalyzerWorkerHelper: AnalyzerWorkerHelper = mockk(relaxed = true)

        every { Wrapper.getConnection()} returns mockedDBConnection
        every { Wrapper.getRedisInterface() } returns mockedRedisInterface
        every { Wrapper.getRandomUUID()} returns "FakeUUID1"
        every { Wrapper.getDBInterface() } returns mockedDBInterface
        every { mockedRedisInterface.getFromList("news:collect-analyze", 10)} returns newsUnits


        val mockedResultSet: ResultSet = mockk(relaxed = true)
        val newsItem = NewsUnit("Fake gameId", "Fake title", "Fake description", "Fake URL")
        every{ mockedAnalyzerWorkerHelper.getQuery(newsItem, mockedDBConnection)} returns mockedResultSet
        every { mockedResultSet.next() } returns true

        val task = AnalyzerTask("Analyzer Task test")
        val worker = AnalyzerWorker()
        worker.execute(task)
    }

}