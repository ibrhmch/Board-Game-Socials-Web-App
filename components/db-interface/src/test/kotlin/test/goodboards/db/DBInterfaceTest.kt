package test.goodboards.db

import com.goodboards.db.Game
import com.goodboards.db.DBInterface
import com.goodboards.db.DBConnection
import org.junit.Test
import kotlin.test.*
import io.mockk.*
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class DBInterfaceTest {

    @Test
    fun testIsConnected_ConnectionExists() {
        // given -> setup
        val mockedConnection: Connection = mockk(relaxed = true)
        every { mockedConnection.isValid(0) } returns true
        val mockedDBConnection: DBConnection = mockk(relaxed = true)
        every { mockedDBConnection.getConnection() } returns mockedConnection

        // when -> test action
        val dbInterface = DBInterface(mockedDBConnection)
        val status = dbInterface.isConnected()

        // then -> verify results
        assertTrue { status }
    }

    @Test
    fun testIsConnected_NoConnection() {
        // given -> setup
        val mockedConnection: Connection = mockk(relaxed = true)
        every { mockedConnection.isValid(0) } returns false
        val mockedDBConnection: DBConnection = mockk(relaxed = true)
        every { mockedDBConnection.getConnection() } returns mockedConnection

        // when -> test action
        val dbInterface = DBInterface(mockedDBConnection)
        val status = dbInterface.isConnected()

        // then -> verify results
        assertFalse { status }
    }

    @Test
    fun testGetAllGames_Multiple() {
        // given -> setup
        // *** Setup desired game values
        val games = mutableListOf(
            Game("1", "Chess", "Classic Chess"),
            Game("2", "Uno", "Classic Uno")
        )
        val helper = DBInterfaceTestHelpers()
        helper.parseGames(games)
        val ids = helper.getGameIds()
        val names = helper.getGameNames()
        val descriptions = helper.getGameDescriptions()
        val iterations = helper.getGameIterations()
        // *** Mock fetched games
        val mockedResultSet: ResultSet = mockk(relaxed = true)
        every { mockedResultSet.getString("id") } returnsMany ids
        every { mockedResultSet.getString("name") } returnsMany names
        every { mockedResultSet.getString("description") } returnsMany descriptions
        every { mockedResultSet.next() } returnsMany iterations
        // *** Mock query process
        val mockedStatement: PreparedStatement = mockk(relaxed = true)
        every { mockedStatement.executeQuery() } returns mockedResultSet
        // *** Mock DB Connection
        val mockedConnection: Connection = mockk(relaxed = true)
        every { mockedConnection.prepareStatement("SELECT * FROM goodboards.games;") } returns mockedStatement
        val mockedDBConnection: DBConnection = mockk(relaxed = true)
        every { mockedDBConnection.getConnection() } returns mockedConnection

        // when -> test action
        val dbInterface = DBInterface(mockedDBConnection)
        val result = dbInterface.getAllGames()

        // then -> verify results
        assertEquals(games, result)
    }

    /*  Things to Test
    * - Creating DB Connection --> DBConnectionTest.kt
    * - getAllGames() -- multiple games, 1 game, 0 games
    * - getGameById() -- when game is there, when game is not
    * - addGame() -- add 1 game, add 2 games, try to add a game already added?
    * - getGameByName() -- when game name exists, when game doesn't exist
    * - deleteGameById() -- when successful delete, when incorrect delete
    * */

}