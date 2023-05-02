package com.goodboards.tests

import com.goodboards.tests.client.ClientUtil
import com.goodboards.tests.client.TemplateUtil
import io.ktor.client.statement.*
import io.ktor.client.request.get
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GamesTest {
    companion object {

        fun checkGames(body: Element) {
            assertTrue(body.select("div.game").size == 2)
        }
    }

    @Test
    fun `get home returns page with games list`(): Unit = runBlocking {
        val response = ClientUtil.getClient().get(urlString = EnvironmentConfig.Staging.URL)
        val htmlResponse = Jsoup.parse(response.bodyAsText())
        assertNotNull(htmlResponse.head())
        TemplateUtil.checkHead(htmlResponse.head())
        assertNotNull(htmlResponse.body())
        TemplateUtil.checkNavBar(htmlResponse.body())
        checkGames(htmlResponse.body())
    }
}
