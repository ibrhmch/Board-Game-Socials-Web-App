package com.goodboards.app

import com.goodboards.app.database.DatabaseInit
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
import com.goodboards.app.game.GameNews
import com.goodboards.app.game.GamesHelper
import com.goodboards.app.news.NewsHelper
import com.goodboards.app.player.PlayerGameOptions
import com.goodboards.app.player.PlayerHelper
import com.goodboards.app.sessions.PlayerSession
import com.goodboards.app.sessions.SessionData
import com.goodboards.app.sessions.SessionHelper
import com.goodboards.db.Player
import java.util.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.request.*

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
            val games = GamesHelper.getAllGames()
            call.respond(FreeMarkerContent("games/games.ftl", mapOf("games" to games)))
        }
        get("/contact") {
            call.respond(FreeMarkerContent("contact.ftl", mapOf("games" to GamesHelper.getAllGames())))
        }

        get("/game/{id}") {
            val id = call.parameters.getOrFail<String>("id")
            val game = GamesHelper.getGameById(id)
            val news = NewsHelper.getNewsForGame(game.id)
            val gameNewsData = GameNews(id, game.name, game.description, news)
            call.respond(FreeMarkerContent("games/game.ftl", mapOf("gameNewsData" to gameNewsData)))
        }
        get("/game/{id}/new") {
            val id = call.parameters.getOrFail<String>("id")
            call.respond(FreeMarkerContent("games/newGame.ftl", mapOf("game" to GamesHelper.getAllGames().find { it.id == id })))
        }

        get("/games") {
            val games = GamesHelper.getAllGames()
            call.respond(FreeMarkerContent("games/games.ftl", mapOf("games" to games)))
        }

        get("/sessions") {
            call.respond(FreeMarkerContent("sessions/sessions.ftl", mapOf("sessions" to SessionHelper.getAllSessions())))
        }

        get("/createSession") {

            val games = GamesHelper.getAllGames()
            val players = PlayerHelper.getAllPlayers()
            val playerGameOptions = PlayerGameOptions(players, games)


            call.respond(FreeMarkerContent("sessions/createSession.ftl", mapOf("playerGameOptions" to playerGameOptions)))
        }

        post ("/sessions"){
            val formParameters = call.receiveParameters()
            val sessionId = (0..10).random().toString()
            val sessionName = formParameters.getOrFail("sessionName")
            val gameId = formParameters.getOrFail("game")
            SessionHelper.addSession(sessionName)
            call.respondRedirect("/sessions")
        }

        get("/sessions/{id}") {
            val sessionId = call.parameters.getOrFail<String>("id")
            val playerSessions = SessionHelper.getSessionInfo(sessionId)
            val sessionData = mutableListOf<SessionData>()
            for (session in playerSessions) {
                val sessionId = session.sessionId
                val gameName = GamesHelper.getGameById(session.gameId).name
                val playerName = PlayerHelper.getPlayerById(session.playerId).name
                val wins = session.wins
                val losses = session.losses

                sessionData.add(SessionData(sessionId, playerName, gameName, wins, losses))
            }

            call.respond(FreeMarkerContent("sessions/session.ftl", mapOf("sessionData" to sessionData)))
        }
//
//        post("/sessions/{sessionId}") {
//            val formParameters = call.receiveParameters()
//            val sessionId = call.parameters.getOrFail<String>("sessionId")
//            val wins = formParameters.getOrFail("wins")
//            val losses = formParameters.getOrFail("losses")
//
//            var playerName = ""
//            var gameName = ""
//            val defaultPlayerData = mutableListOf<PlayerData>()
//
//
//            var sessionData = SessionData("1", "Khaleds sesh", "Uno", defaultPlayerData)
//
//
//            val players = mutableListOf<PlayerData>()
//
//            // Create Player Data
//            for (record in winLossRecords) {
//                if (record.sessionId == sessionId) {
//                    // Update win loss records
//                    wLr[sessionId] = Record(sessionId, wins.toInt(), losses.toInt(), record.game_id, record.playerId)
//                    // Use playerId to get playerName
//                    for (player in allPlayers) {
//                        if (record.playerId == player.id.toString()) {
//                            playerName = player.name
//                        }
//                    }
//                    val player = PlayerData(playerName, wins.toInt(), losses.toInt())
//                    players.add(player);
//                }
//            }
//
//            // Create Session Data
//            for (session in sessions) {
//                if (session.sessionId == sessionId) {
//                    // use gameId to get game name
//                    for (game in games) {
//                        if (game.id.toString() == session.game_id) {
//                            gameName = game.name;
//                        }
//                    }
//                    sessionData = SessionData(session.sessionId , session.sessionName, gameName, players);
//                }
//            }
//
//
//
//            call.respond(FreeMarkerContent("session.ftl", mapOf("sessionData" to sessionData)))
//        }



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