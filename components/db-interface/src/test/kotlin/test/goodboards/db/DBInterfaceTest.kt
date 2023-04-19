package test.goodboards.db

import com.goodboards.db.DBInterface
import com.goodboards.db.DBConnection
import org.junit.Test
import kotlin.test.*
import io.mockk.*
import java.sql.Connection

class DBInterfaceTest {

    @Test
    fun testIsConnected_HappyPath() {
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

    /*  Things to Test
    * - Creating DB Connection --> DBConnectionTest.kt
    * - getAllGames() -- multiple games, 1 game, 0 games
    * - getGameById() -- when game is there, when game is not
    * - addGame() -- add 1 game, add 2 games, try to add a game already added?
    * - getGameByName() -- when game name exists, when game doesn't exist
    * - deleteGameById() -- when successful delete, when incorrect delete
    * */

}