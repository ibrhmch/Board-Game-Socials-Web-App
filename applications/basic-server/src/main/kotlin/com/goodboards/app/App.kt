package com.goodboards.app

import com.goodboards.app.database.DatabaseInit
import com.goodboards.app.game.Game
import com.goodboards.app.game.GamesHelper
import com.goodboards.app.gameNews.GameNews
import com.goodboards.app.kt.*
import com.goodboards.app.news.NewsHelper
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import org.slf4j.LoggerFactory
import java.util.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.request.*

val allPlayers = mutableListOf(
    Player(1, "khaled"),
    Player(2, "Charles")
)


val playersForSessionOne = mutableListOf(
    Player(1, "Khaled"),
    Player(2, "Charles")
)

val playersForSessionTwo = mutableListOf(
    Player(2, "Charles"),
)

val games = mutableListOf(
    Game("1","Uno", "typical friendship destroying game"),
    Game("2","Chess", "Chess game description"),
)

val playerGameOptions = PlayerGameOptions(allPlayers, games);


val sessions = mutableListOf(
    Session("1", "Khaled's sesh", playersForSessionOne, "1"),
    Session("2", "Charles sesh", playersForSessionTwo, "1")
)

val winLossRecords = mutableListOf(
    Record("1", 1, 1, "2", "1"),
    Record("1", 2, 0, "2", "2"),
    Record("2", 3, 0, "1", "2"),
)

var wLr : HashMap<String, Record> = HashMap<String, Record>()



private val logger = LoggerFactory.getLogger("App.kt")
val client = HttpClient(CIO) {
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.HEADERS
    }
    install(JsonFeature) {
        serializer = KotlinxSerializer()
    }
}

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
    install(Routing) {
        get {
            call.respond(FreeMarkerContent("games/games.ftl", mapOf("games" to GamesHelper.getAllGames())))
        }
        get("/contact") {
            call.respond(FreeMarkerContent("contact.ftl", mapOf("games" to GamesHelper.getAllGames())))
        }

        get("/game/{id}") {
            val id = call.parameters.getOrFail<String>("id")
            val game = GamesHelper.getAllGames().find { it.id == id }
            val mockNewsData = game?.let { it1 -> NewsHelper.getNewsForGame(it1.name) }
            val gameNewsData = game?.let { it1 -> GameNews(id, game.name, it1.description, mockNewsData!!) }
            call.respond(FreeMarkerContent("games/game.ftl", mapOf("gameNewsData" to gameNewsData)))
        }
        get("/game/{id}/new") {
            val id = call.parameters.getOrFail<String>("id")
            call.respond(FreeMarkerContent("games/newGame.ftl", mapOf("game" to GamesHelper.getAllGames().find { it.id == id })))
        }
        get("/contact") {
            call.respond(FreeMarkerContent("contact.ftl", mapOf("games" to GamesHelper.getAllGames())))
        }


        get("/sessions") {
            call.respond(FreeMarkerContent("sessions/sessions.ftl", mapOf("sessions" to sessions)))
        }

        get("/createSession") {

            call.respond(FreeMarkerContent("sessions/createSession.ftl", mapOf("playerGameOptions" to playerGameOptions)))
        }

        post ("/sessions"){
            val formParameters = call.receiveParameters()
            val sessionId = (0..10).random().toString()
            val sessionName = formParameters.getOrFail("sessionName")
            val game = formParameters.getOrFail("game")
            val players = mutableListOf<Player>()
            wLr.put("1", Record("1", 1, 1, "2", "1"))
            wLr.put("1", Record("1", 2, 0, "2", "2"))
            wLr.put("2", Record("2", 3, 0, "1", "2"))

            for (player in allPlayers) {
                val playerName = formParameters[player.name]
                if (playerName == "on") {
                    players.add(player)
                }
            }

            val newSession = Session.newEntry(sessionId, sessionName, players, game)

            // Add new Session
            sessions.add(newSession)

            // Initialize win loss records
            for (player in players) {
                winLossRecords.add(Record(sessionId, 0, 0, game, player.id.toString()))
            }
            call.respondRedirect("sessions/sessions")
        }

        get("/sessions/{sessionId}") {
            val sessionId = call.parameters.getOrFail<String>("sessionId")
            var playerName = ""
            var gameName = ""
            val defaultPlayerData = mutableListOf<PlayerData>()


            var sessionData = SessionData("1", "Khaleds sesh", "Uno", defaultPlayerData)


            val players = mutableListOf<PlayerData>()

            // Create Player Data
            for (record in winLossRecords) {
                if (record.sessionId == sessionId) {
                    // Use playerId to get playerName
                    for (player in allPlayers) {
                        if (record.playerId == player.id.toString()) {
                            playerName = player.name
                        }
                    }
                    val player = PlayerData(playerName, record.wins, record.loss)
                    players.add(player);
                }
            }

            // Create Session Data
            for (session in sessions) {
                if (session.sessionId == sessionId) {
                    // use gameId to get game name
                    for (game in games) {
                        if (game.id.toString() == session.game_id) {
                            gameName = game.name;
                        }
                    }
                    sessionData = SessionData(session.sessionId , session.sessionName, gameName, players);
                }
            }



            call.respond(FreeMarkerContent("sessions/session.ftl", mapOf("sessionData" to sessionData)))
        }

        post("/sessions/{sessionId}") {
            val formParameters = call.receiveParameters()
            val sessionId = call.parameters.getOrFail<String>("sessionId")
            val wins = formParameters.getOrFail("wins")
            val losses = formParameters.getOrFail("losses")

            var playerName = ""
            var gameName = ""
            val defaultPlayerData = mutableListOf<PlayerData>()


            var sessionData = SessionData("1", "Khaleds sesh", "Uno", defaultPlayerData)


            val players = mutableListOf<PlayerData>()

            // Create Player Data
            for (record in winLossRecords) {
                if (record.sessionId == sessionId) {
                    // Update win loss records
                    wLr[sessionId] = Record(sessionId, wins.toInt(), losses.toInt(), record.game_id, record.playerId)
                    // Use playerId to get playerName
                    for (player in allPlayers) {
                        if (record.playerId == player.id.toString()) {
                            playerName = player.name
                        }
                    }
                    val player = PlayerData(playerName, wins.toInt(), losses.toInt())
                    players.add(player);
                }
            }

            // Create Session Data
            for (session in sessions) {
                if (session.sessionId == sessionId) {
                    // use gameId to get game name
                    for (game in games) {
                        if (game.id.toString() == session.game_id) {
                            gameName = game.name;
                        }
                    }
                    sessionData = SessionData(session.sessionId , session.sessionName, gameName, players);
                }
            }



            call.respond(FreeMarkerContent("sessions/session.ftl", mapOf("sessionData" to sessionData)))
        }

        get("/games") {
            call.respond(FreeMarkerContent("games/games.ftl", mapOf("games" to GamesHelper.getAllGames())))
        }

        static("images") { resources("images") }
        static("style") { resources("style") }
    }
}

private fun PipelineContext<Unit, ApplicationCall>.headers(): MutableMap<String, String> {
    val headers = mutableMapOf<String, String>()
    call.request.headers.entries().forEach { entry ->
        headers[entry.key] = entry.value.joinToString()
    }
    return headers
}

fun main() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    val port = System.getenv("PORT")?.toInt() ?: 8888
    try {
        DatabaseInit.readGameJsonIntoDB("game_info.json");
    } catch (e: Exception) {
        logger.error("Error reading game JSON, $e")
    }
    embeddedServer(Netty, port, watchPaths = listOf("basic-server"), module = { module() }).start()
}