package com.goodboards.db

data class PlayerSession(val uuid: String, val playerId: String, val gameId: String, val sessionId: String, val wins: Int, val losses: Int)
