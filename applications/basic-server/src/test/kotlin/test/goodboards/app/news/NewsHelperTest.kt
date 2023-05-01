package test.goodboards.app.news

import com.goodboards.app.news.News
import com.goodboards.app.news.NewsHelper
import io.mockk.every
import io.mockk.verify
import org.junit.Test
import test.goodboards.app.util.DBMock
import kotlin.test.assertTrue

class NewsHelperTest {
    @Test
    fun testGetNewsForGame() {
        DBMock.mockDBConnection()
        val dbInterface = DBMock.mockDBInterface()
        val dbNews = DBMock.mockNews()
        every { dbInterface.getNewsForGame("c1ce3f0d-53be-4b47-9228-c668f9f0f503") } returns dbNews
        val news: List<News> = NewsHelper.getNewsForGame("c1ce3f0d-53be-4b47-9228-c668f9f0f503");
        verify (exactly = 1) { dbInterface.getNewsForGame("c1ce3f0d-53be-4b47-9228-c668f9f0f503") }
        assertTrue { dbNews.size == news.size }
    }
}