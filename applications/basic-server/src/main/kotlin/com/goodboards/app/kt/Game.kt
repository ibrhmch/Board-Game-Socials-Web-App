package com.goodboards.app.kt
import java.util.concurrent.atomic.AtomicInteger

class Game(var name: String, var description: String) {
    val id = getNextId()
    companion object {
        private val idCounter = AtomicInteger()
        fun getNextId(): Int{
            return idCounter.getAndIncrement()
        }
    }
}