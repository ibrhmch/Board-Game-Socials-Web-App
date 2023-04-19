package test.goodboards.app

import com.goodboards.app.module
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class AppTest : BaseAppTest() {

    @Test
    fun testHomePage() = testApp {
        handleRequest(HttpMethod.Get, "/").apply {
            assertEquals(200, response.status()?.value)

            assertTrue(response.content!!.contains("GOODBOARDS"))
        }
    }

    @Test
    fun testContactPage() = testApp {
        handleRequest(HttpMethod.Get, "/").apply {
            assertEquals(200, response.status()?.value)
            assertTrue(response.content!!.contains("GOODBOARDS"))
        }
    }

    @Test
    fun singleGame() = testApp {
        handleRequest(HttpMethod.Get, "/game/0").apply {
            assertEquals(200, response.status()?.value)
            // make sure news is part of the game display
            assertTrue(response.content!!.contains("News"))
            assertTrue(response.content!!.contains("news"))

            assertTrue(response.content!!.contains("Contact"))
        }
    }
}
