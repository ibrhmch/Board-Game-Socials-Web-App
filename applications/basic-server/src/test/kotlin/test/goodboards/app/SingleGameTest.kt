package test.goodboards.app

import com.goodboards.app.game.Game
import com.goodboards.app.news.News
import io.ktor.http.*
import io.ktor.server.testing.*
import junit.framework.TestCase.assertNotNull
import org.jsoup.Jsoup
import org.junit.Ignore
import org.junit.Test
import test.goodboards.app.util.GamesMock
import test.goodboards.app.util.NewsMock
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SingleGameTest : BaseAppTest() {
    @Test
    fun testSingleGameResponse() = testApp {
        val game: Game = GamesMock.mockSingleGame()
        val news: News = NewsMock.mockNews()[0]
        handleRequest(HttpMethod.Get, "/game/${game.id}").apply {
            assertEquals(200, response.status()?.value)
            assertNotNull(response.content)
            val htmlResponse = Jsoup.parse(response.content)
            assertNotNull(htmlResponse.body())
            assertNotNull(htmlResponse.body().select("h1.game-name"))
            assertTrue(htmlResponse.body().select("h1.game-name").text().contains(game.name))
            assertNotNull(htmlResponse.body().select("div.game-description"))
            assertTrue(htmlResponse.body().select("div.game-description").text().contains(game.description))
            assertTrue(htmlResponse.body().select("div.news-title").text().contains(news.title))
            assertTrue(htmlResponse.body().select("p.news-desc").text().contains(news.description))
        }
    }
}