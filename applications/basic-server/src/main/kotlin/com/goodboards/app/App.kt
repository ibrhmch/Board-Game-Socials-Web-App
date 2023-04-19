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
import models.Game
import models.Game
import models.Session
import java.util.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import models.NewsResponse

val games = mutableListOf(
    Game.newEntry("Uno", "typical friendship destroying game"),
    Game.newEntry("Uno", "typical friendship destroying game"),
    Game.newEntry("Uno", "typical friendship destroying game"),
    Game.newEntry("Uno", "typical friendship destroying game"),
    Game.newEntry("Uno", "typical friendship destroying game"),
    Game.newEntry("Uno", "typical friendship destroying game"),
)

val sessions = mutableListOf(
    Session.newEntry("Test1", "gameTest", 5),
)
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
val users = mutableListOf(
    User.newEntry(
        "Abhishek Purushothama",
    ),
    User.newEntry(
        "Khaled Hossain",
    ),
    User.newEntry(
        "Ch Mohammad Ibrahim",
    ),
    User.newEntry(
        "Michelle Tran",
    ),
    User.newEntry(
        "Tuan Tran",
    ),
    User.newEntry(
        "Lin Shi",
    ))

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
    install(Routing) {
        get ("/"){
            call.respond(FreeMarkerContent("games.ftl", mapOf("games" to games)))
        }
        get("/sessions") {
            call.respond(FreeMarkerContent("sessions.ftl", mapOf("sessions" to sessions)))
        }
        get("/sessions/{id}") {
            // Get session info from db
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(FreeMarkerContent("session.ftl", mapOf("session" to sessions.find { it.id == id })))
        }
        get("/contact") {
            call.respond(FreeMarkerContent("contact.ftl", mapOf("games" to games)))
        }
        get("/games") {
            call.respond(FreeMarkerContent("games.ftl", mapOf("games" to games)))
        }
        get("/game/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(FreeMarkerContent("game.ftl", mapOf("game" to games.find { it.id == id })))
        }
        get("/game/{id}/new") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(FreeMarkerContent("newGame.ftl", mapOf("game" to games.find { it.id == id })))
        get("/contact") {
            call.respond(FreeMarkerContent("contact.ftl", mapOf("games" to games)))
        }
        get("/games") {
            call.respond(FreeMarkerContent("games.ftl", mapOf("games" to games)))
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
    DatabaseInit.readGameJsonIntoDB("game_info.json");
    embeddedServer(Netty, port, watchPaths = listOf("basic-server"), module = { module() }).start()
}
