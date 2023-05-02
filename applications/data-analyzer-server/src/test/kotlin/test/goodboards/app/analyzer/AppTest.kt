package test.goodboards.app.analyzer

import com.goodboards.app.analyzer.module
import com.goodboards.app.analyzer.tasks.AnalyzerNewsTaskHelper
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.every
import io.mockk.mockkObject
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class AppTest {

    @Test
    fun testEmptyHome() = testApp {
        handleRequest(HttpMethod.Get, "/").apply {
            assertEquals(200, response.status()?.value)
            assertTrue(response.content!!.contains("hi!"))
        }
    }
    
    private fun testApp(callback: TestApplicationEngine.() -> Unit) {
        withTestApplication({
            mockkObject(AnalyzerNewsTaskHelper)
            every{ AnalyzerNewsTaskHelper.scheduleAnalyzerNewsTask()} returns Unit
            module()
        }) { callback() }
    }
}