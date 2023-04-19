package test.goodboards.app

import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Ignore
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
// The singleGameTest tests the components for a game detail page. Which consist of the game, its description, and relevant news.
class SingleGameTest : BaseAppTest() {
    // Testing the single game detail page to ensure that we get a 200 response.
    @Test
    fun testSingleGameResponse() = testApp {
        handleRequest(HttpMethod.Get, "/game/0").apply {
            assertEquals(200, response.status()?.value)
        }
    }

    // Testing the placeholder for game 0: Uno to ensure the correct game name and description is returned.
    @Test
    fun testGameDetails() = testApp{
        handleRequest ( HttpMethod.Get, "/game/0" ).apply{
            assertTrue(response.content!!.contains("Uno"))
            assertTrue(response.content!!.contains("typical friendship destroying game"))
        }
    }
    @Test
    fun testNewsHeader() = testApp {
        handleRequest (HttpMethod.Get, "game/0").apply{
            assertTrue(response.content!!.contains("News"))
        }
    }
    @Test
    fun testNewsDetails() = testApp {
        handleRequest (HttpMethod.Get, "game/0").apply{
            assertTrue(response.content!!.contains("temp news placeholder"))
        }
    }
}