package test.goodboards.app.analyzer

import com.goodboards.app.analyzer.NewsUnit
import com.goodboards.app.analyzer.Wrapper
import com.goodboards.db.DBConnection
import com.goodboards.db.DBInterface
import com.goodboards.db.DriverManagerWrapper
import com.goodboards.db.SystemWrapper
import com.goodboards.redis.RedisInterface
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import org.junit.Test
import kotlinx.serialization.json.Json
import org.junit.Before
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import kotlin.test.assertEquals


@Suppress("PROVIDED_RUNTIME_TOO_LOW")
@Serializable
data class NewsUnit(val gameID: String, var title: String, var description: String, val url: String)

class AnalyzerWorkerTest{
    @Before
    fun mockDBConnection(): Unit {
        mockkObject(SystemWrapper)
        every { SystemWrapper.getenv("JDBC_DATABASE_URL") } returns "fakeURL"
        every { SystemWrapper.getenv("DATABASE_USERNAME") } returns "fakeUsername"
        every { SystemWrapper.getenv("DATABASE_PASSWORD") } returns "fakePassword"
        val mockedConnection: Connection = mockk(relaxed = true)
        mockkObject(DriverManagerWrapper)
        every {
            DriverManagerWrapper.getConnection(
                "fakeURL",
                "fakeUsername",
                "fakePassword"
            )
        } returns mockedConnection
    }
    @Test
    fun testDBConnection(){
        val credentials = DBConnection.getCredentials()
        assertEquals("fakeURL", credentials.url)
        assertEquals("fakeUsername", credentials.username)
        assertEquals("fakePassword", credentials.password)

    }

//    fun allmocks(){
//        val mockedWrapper: Wrapper = mockk(relaxed = true)
//        val mockedRedisInterface: RedisInterface = mockk(relaxed = true)
//        val mockedDBConnection: Connection = mockk(relaxed = true)
//        every { mockedWrapper.getTableName() } returns "FakeGoodboards.news"
//        every { mockedWrapper.getRedisInterface() } returns mockedRedisInterface
//        every { mockedWrapper.getDBConnection()} returns mockedDBConnection
//        every { mockedWrapper.getInsertNewsStatement("FakeUUID1", NewsUnit("Fake gameId", "Fake title", "Fake description", "Fake URL")) } returns "Fake Insert Statement"
//        every { mockedWrapper.setDoubleApostrophe("Fake title") } returns "Fake title"
//        every { mockedWrapper.setDoubleApostrophe("Fake description") } returns "Fake description"
//        every { mockedWrapper.getRandomUUID()} returns "FakeUUID1"
//        val newsUnits = arrayOf(
//            """{
//                "gameID": "Fake gameId",
//                "title": "Fake title",
//                "description": "Fake description",
//                "url": "Fake URL"
//            }""".trimIndent()
//        )
//        every { mockedRedisInterface.getFromList(key = "news:collector-analyze", 10) } returns newsUnits.toList()
//    }

    @Test
    fun testRedisInteraction(){

        val mockedWrapper: Wrapper = mockk(relaxed = true)
        val mockedRedisInterface: RedisInterface = mockk(relaxed = true)
        every { mockedWrapper.getRedisInterface() } returns mockedRedisInterface
        val newsUnits = arrayOf(
            """{
                "gameID": "Fake gameId",
                "title": "Fake title",
                "description": "Fake description",
                "url": "Fake URL"
            }""".trimIndent()
        )
        every { mockedRedisInterface.getFromList(key = "news:collector-analyze", 10) } returns newsUnits.toList()

        val redisInterface = mockedWrapper.getRedisInterface()
        val redisQueue = redisInterface.getFromList(key = "news:collector-analyze", 10)
        assert(redisQueue.size == 1)
    }

    @Test
    fun testParsing(){
        val mockedWrapper: Wrapper = mockk(relaxed = true)
        every { mockedWrapper.setDoubleApostrophe("Fake title") } returns "Fake title"
        every { mockedWrapper.setDoubleApostrophe("Fake description") } returns "Fake description"
        val newsUnits = arrayOf(
            """{
                "gameID": "Fake gameId",
                "title": "Fake title",
                "description": "Fake description",
                "url": "Fake URL"
            }""".trimIndent()
        )

        val mockedList = newsUnits.toList()
        val newsUnit = Json.decodeFromString<NewsUnit>(mockedList[0])
        assertEquals("Fake URL", newsUnit.url)
        assertEquals("Fake gameId", newsUnit.gameID)
        assertEquals("Fake title", newsUnit.title)
        assertEquals("Fake description", newsUnit.description)

        assertEquals("Fake title", mockedWrapper.setDoubleApostrophe("Fake title"))
        assertEquals("Fake description", mockedWrapper.setDoubleApostrophe("Fake description"))
    }

    @Test
    fun testQuery(){
        val mockedWrapper: Wrapper = mockk(relaxed = true)
        val mockedRedisInterface: RedisInterface = mockk(relaxed = true)
        val mockedDBConnection: Connection = mockk(relaxed = true)
        every { mockedWrapper.getTableName() } returns "FakeGoodboards.news"
        every { mockedWrapper.getRedisInterface() } returns mockedRedisInterface
        every { mockedWrapper.getDBConnection()} returns mockedDBConnection
        every { mockedWrapper.getInsertNewsStatement("FakeUUID1", NewsUnit("Fake gameId", "Fake title", "Fake description", "Fake URL")) } returns "Fake Insert Statement"
        every { mockedWrapper.setDoubleApostrophe("Fake title") } returns "Fake title"
        every { mockedWrapper.setDoubleApostrophe("Fake description") } returns "Fake description"
        every { mockedWrapper.getRandomUUID()} returns "FakeUUID1"

        val preparedStatement = mockk<PreparedStatement>()
        val mockResultSet = mockk<ResultSet>()
        val newsItem = NewsUnit("Fake gameId", "Fake title", "Fake description", "Fake URL")
        every{ mockedDBConnection.prepareStatement("SELECT * FROM ${mockedWrapper.getTableName()} where title = '${newsItem.title}'")} returns preparedStatement

        val mockedStatement = mockedDBConnection.prepareStatement("SELECT * FROM ${mockedWrapper.getTableName()} where title = '${newsItem.title}'")
        every { mockedStatement.executeQuery() } returns mockResultSet

        verify(exactly = 1) { preparedStatement.executeQuery() }


    }

}