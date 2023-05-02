package test.goodboards.db

import com.goodboards.db.Game
import com.goodboards.db.News
import io.mockk.every
import io.mockk.mockk
import java.sql.PreparedStatement
import java.sql.ResultSet

class DBInterfaceTestHelpers() {

    val ids = mutableListOf<String>()
    val names = mutableListOf<String>()
    val descriptions = mutableListOf<String>()
    val iterations = mutableListOf<Boolean>()

    val newsId = mutableListOf<String>()
    val newsName = mutableListOf<String>()
    val newsDescription = mutableListOf<String>()
    val newsUrl = mutableListOf<String>()
    val newsGameId = mutableListOf<String>()
    val newsIterator = mutableListOf<Boolean>()

    fun parseGames (games: List<Game>) {
        for(game in games) {
            ids.add(game.uuid)
            names.add(game.name)
            descriptions.add(game.description)
            iterations.add(true)
        }
        iterations.add(false)
    }

    fun parseNews (news: List<News>){
        for(article in news){
            newsId.add(article.id)
            newsName.add(article.title)
            newsDescription.add(article.description)
            newsUrl.add(article.url)
            newsGameId.add(article.gameId)
            newsIterator.add(true)
        }
        newsIterator.add(false)
    }

    fun getMockedResultSetForNews(): ResultSet{
        val mockedResultSet: ResultSet = mockk(relaxed = true)
        every { mockedResultSet.getString("id") } returnsMany newsId
        every { mockedResultSet.getString("title") } returnsMany newsName
        every { mockedResultSet.getString("description") } returnsMany newsDescription
        every { mockedResultSet.getString("url") } returnsMany newsUrl
        every { mockedResultSet.getString("gameid") } returnsMany newsGameId
        every { mockedResultSet.next() } returnsMany newsIterator
        return mockedResultSet
    }

    fun getMockedResultSet() : ResultSet {
        val mockedResultSet: ResultSet = mockk(relaxed = true)
        every { mockedResultSet.getString("id") } returnsMany ids
        every { mockedResultSet.getString("name") } returnsMany names
        every { mockedResultSet.getString("description") } returnsMany descriptions
        every { mockedResultSet.next() } returnsMany iterations
        return mockedResultSet
    }

    fun getPreparedStatement(mockedResultSet: ResultSet) : PreparedStatement {
        val mockedStatement: PreparedStatement = mockk(relaxed = true)
        every { mockedStatement.executeQuery() } returns mockedResultSet
        return mockedStatement
    }

}