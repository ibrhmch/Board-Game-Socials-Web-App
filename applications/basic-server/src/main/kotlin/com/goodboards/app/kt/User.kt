package com.goodboards.app.kt
import java.util.concurrent.atomic.AtomicInteger

class User(val id: Int, var title: String) {
    companion object {
        private val idCounter = AtomicInteger()
        val articles = mutableListOf(User.newEntry(
            "The drive to develop!",
        ))
        fun newEntry(title: String) = User(idCounter.getAndIncrement(), title)
    }
}