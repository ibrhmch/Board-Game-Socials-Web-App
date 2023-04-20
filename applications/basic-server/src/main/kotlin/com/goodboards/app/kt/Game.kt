package com.goodboards.app.kt
import java.util.concurrent.atomic.AtomicInteger

class Game(val id: Int, var name: String, var description: String) {
    companion object {
        private val idCounter = AtomicInteger()
        fun newEntry(name: String, description: String) = Game(idCounter.getAndIncrement(), name, description)
    }
}