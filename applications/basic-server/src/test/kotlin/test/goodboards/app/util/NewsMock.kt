package test.goodboards.app.util

import com.goodboards.app.news.News
import com.goodboards.app.news.NewsHelper
import io.mockk.every
import io.mockk.mockkObject

object NewsMock {
    fun makeNews(): List<News> {
        return mutableListOf(News("1", "game", "url", "desc"))
    }

    fun mockNews() : List<News> {
        val news = makeNews()
        mockkObject(NewsHelper)
        every { NewsHelper.getNewsForGame("name") }  returns news
        return news
    }
}