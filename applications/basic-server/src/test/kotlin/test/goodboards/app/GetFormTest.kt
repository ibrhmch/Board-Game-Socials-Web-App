package test.goodboards.app

import io.ktor.http.*
import io.ktor.server.testing.*
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
}