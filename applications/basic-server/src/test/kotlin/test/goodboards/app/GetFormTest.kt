package test.goodboards.app

import io.ktor.http.*
import io.ktor.server.testing.*
import models.Game
import org.junit.Ignore
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetFormTest : BaseAppTest() {

    @Test
    fun testGetFormRespondsWithForm() = testApp {
        handleRequest(HttpMethod.Get, "/games").apply {
            assertEquals(200, response.status()?.value)
            assertTrue(response.content!!.contains("GOODBOARDS"))
        }
    }

    @Test
    fun testGetData() = testApp {
        handleRequest(HttpMethod.Get, "/").apply {
            val content = response.content!!;
            val expectedNumberOfGames = 2;
            val pattern  = "(Uno)".toRegex()
            val found = pattern.find(content)!!.groupValues
            assertEquals(found.size, expectedNumberOfGames)
        }
    }


    @Test
    fun singleGame() = testApp {
        handleRequest(HttpMethod.Get, "/game/0").apply {
            assertEquals(200, response.status()?.value)
            assertTrue(response.content!!.contains("GOODBOARDS"))
            // make sure news is part of the game display
            assertTrue(response.content!!.contains("News"))
        }
    }
}
