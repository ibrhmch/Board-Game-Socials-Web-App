package test.goodboards.app.util

import com.goodboards.app.news.News
import com.goodboards.app.news.NewsHelper
import io.mockk.every
import io.mockk.mockkObject

object NewsMock {
    fun makeNews(): List<News> {
        return mutableListOf(News("1", "c1ce3f0d-53be-4b47-9228-c668f9f0f503", "title", "desc", "1@2.com"))
    }

    fun mockNews() : List<News> {
        val news = makeNews()
        mockkObject(NewsHelper)
        every { NewsHelper.getNewsForGame("c1ce3f0d-53be-4b47-9228-c668f9f0f503") }  returns news
        return news
    }
}