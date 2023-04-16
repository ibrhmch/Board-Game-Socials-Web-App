package com.goodboards.db

import java.sql.DriverManager

object DBInterface {

    private val jdbcUrl = "jdbc:" + System.getenv("DATABASE_URL")
    private val username = System.getenv("DATABASE_USERNAME")
    private val password = System.getenv("DATABASE_PASSWORD")
    private val connection = DriverManager
        .getConnection(jdbcUrl, username, password)

    fun isConnected() : Boolean {
        return connection.isValid(0)
    }

    fun getAllGames() : List<Game> {
        val query = connection.prepareStatement("SELECT * FROM goodboards.games")
        val result = query.executeQuery()
        val games = mutableListOf<Game>()
        while(result.next()){
            val id = result.getString("id")
            val name = result.getString("name")
            val description = result.getString("description")
            games.add(Game(id, name, description))
        }
        return games
    }

    fun getGame(id: String) : Game {
        val statement = "SELECT * FROM goodboards.games WHERE id=$id;"
        val query = connection.prepareStatement(statement)
        val result = query.executeQuery()
        val games = mutableListOf<Game>()
        if(result.next()) {
            val id = result.getString("id")
            val name = result.getString("name")
            val description = result.getString("description")
            games.add(Game(id, name, description))
        }
        if(games.size != 1) {
            throw Exception("Incorrect number of games with ID $id")
        }
        return games[0]
    }

    // TODO: in progress
    fun addGame(id: String, name: String, description: String) {
        val statement = "INSERT INTO goodboards.games(id, name, description) VALUES(?,?,?);"

    }

}