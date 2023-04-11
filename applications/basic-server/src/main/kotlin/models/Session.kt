package models
import java.util.concurrent.atomic.AtomicInteger

class Session
private constructor(val id: Int, var sessionName: String, var game: String, var players: Int) {
    companion object {
        private val idCounter = AtomicInteger()
        fun newEntry(sessionName: String, game: String, players: Int) = Session(idCounter.getAndIncrement(), sessionName, game, players)
    }
}