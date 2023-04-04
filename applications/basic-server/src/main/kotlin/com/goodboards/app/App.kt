package com.goodboards.app

import freemarker.cache.ClassTemplateLoader
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import models.User
import java.util.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import models.NewsResponse

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
        get {
            val news: NewsResponse = client.get("https://newsapi.org/v2/everything?q=board_games&apiKey=")
            call.respond(FreeMarkerContent("index.ftl", mapOf("news" to news.articles)))
        }
        get("/form") {
            call.respond(FreeMarkerContent("form.ftl", mapOf("users" to users)))
        }
        post("/form") {
            val formParameters = call.receiveParameters()
            val title = formParameters.getOrFail("title")
            val newEntry = User.newEntry(title)
            println(newEntry)
            users.add(newEntry)
            call.respond(FreeMarkerContent("show.ftl", mapOf("user" to users.find { it.id == newEntry.id })))
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
    embeddedServer(Netty, port, watchPaths = listOf("basic-server"), module = { module() }).start()
}
