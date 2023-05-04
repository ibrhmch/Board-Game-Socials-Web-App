package com.goodboards.db

class DBInterface(dbConnection: DBConnection) {

    private val connection = dbConnection.getConnection()

    fun isConnected() : Boolean {
        return connection.isValid(0)
    }

    fun getAllGames() : List<Game> {
        val query = connection.prepareStatement("SELECT * FROM goodboards.games;")
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

    fun getGameById(id: String) : Game {
        val statement = "SELECT * FROM goodboards.games WHERE id='$id';"
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

    fun addGame(name: String, description: String) {
        val statement = "INSERT INTO goodboards.games(name, description) VALUES('$name','$description');"
        connection.prepareStatement(statement).execute()
    }

    fun getGameByName(name: String) : Game {
        val statement = "SELECT * FROM goodboards.games WHERE name='$name';"
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
            throw Exception("Incorrect number of games with name $name")
        }
        return games[0]
    }

    fun deleteGameById(uuid: String): Boolean {
        val statement = "DELETE FROM goodboards.games WHERE id = CAST(? AS UUID);"
        val query = connection.prepareStatement(statement)
        query.setString(1, uuid)
        val rowsDeleted = query.executeUpdate()
        return rowsDeleted != 0
    }
    fun getNewsForGame(gameId: String) : List<News> {
        val query = connection.prepareStatement("SELECT * FROM goodboards.news WHERE gameid = '${gameId}';")
        val result = query.executeQuery()
        val news = mutableListOf<News>()
        while(result.next()){
            val id = result.getString("id")
            val gameId = result.getString("gameid")
            val name = result.getString("title")
            val description = result.getString("description")
            val url = result.getString("url")
            news.add(News(id, gameId, name, description, url))
        }
        return news
    }

    fun getNewsBasedOnTitle(title: String) : List<News>{
        val query = connection.prepareStatement("SELECT * FROM goodboards.news WHERE title = '${title}';")
        val result = query.executeQuery()
        val news = mutableListOf<News>()
        while (result.next()){
            val id = result.getString("id")
            val gameId = result.getString("gameid")
            val name = result.getString("title")
            val description = result.getString("description")
            val url = result.getString("url")
            news.add(News(id, gameId, name, description, url))
        }
        return news
    }

    fun addNews(title: String, description: String, link: String, gameId: String, id: String) {
        val statement = "INSERT INTO goodboards.news(id, gameid, title, description, url) VALUES('$id','$gameId', '$title','$description','$link');"
        connection.prepareStatement(statement).execute()
    }

    fun getAllSessions() : List<Session> {
        val query = connection.prepareStatement("SELECT * FROM goodboards.sessions;")
        val result = query.executeQuery()
        val sessions = mutableListOf<Session>()
        while (result.next()) {
            val id = result.getString("id")
            val sessionName = result.getString("sessionName")
            sessions.add(Session(id, sessionName))
        }

        return sessions;
    }

    fun getAllPlayers() : List<Player> {
        val query = connection.prepareStatement("SELECT * FROM goodboards.players;")
        val result = query.executeQuery()
        val players = mutableListOf<Player>()
        while (result.next()) {
            val id = result.getString("id")
            val playerName = result.getString("name")
            players.add(Player(id, playerName))
        }

        return players;
    }

    fun getPlayerById(id: String): Player {
        val query = connection.prepareStatement("SELECT * FROM goodboards.players  WHERE id='$id';");
        val result = query.executeQuery()
        val players = mutableListOf<Player>()
        while (result.next()) {
            val id = result.getString("id")
            val playerName = result.getString("name")
            players.add(Player(id, playerName))
        }
        return players[0]
    }

    fun addSession(sessionName: String) {
        val statement = "INSERT INTO goodboards.sessions(sessionName) VALUES('$sessionName');"
        connection.prepareStatement(statement).execute()
    }

    fun getSessionInfo(sessionId: String): List<PlayerSession> {
        val statement = "SELECT * FROM goodboards.playerSession WHERE sessionId='$sessionId';"
        val query = connection.prepareStatement(statement)
        val result = query.executeQuery()
        val playerSessions = mutableListOf<PlayerSession>()
        if(result.next()) {
            val uuid = result.getString("id")
            val playerId = result.getString("playerId")
            val sessionId = result.getString("sessionId")
            val wins = result.getInt("wins")
            val losses = result.getInt("losses")
            val gameId = result.getString("gameId")
            playerSessions.add(PlayerSession(uuid, playerId, gameId, sessionId, wins, losses))
        }
        return playerSessions
    }


}