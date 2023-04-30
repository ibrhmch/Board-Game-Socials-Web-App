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
        val dbNews = DBMock.makeGames()
        every { dbInterface.getNewsForGame("Uno") } returns dbNews
        val news: List<News> = NewsHelper.getNewsForGame("Uno");
        verify (exactly = 1) { dbInterface.getNewsForGame("Uno") }
        assertTrue { dbNews.size == news.size }
    }
}