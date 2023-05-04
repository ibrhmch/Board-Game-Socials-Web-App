package com.goodboards.app.player

import com.goodboards.app.database.DBHelper

class PlayerHelper {
    companion object {
        fun getAllPlayers(): List<Player> {
            val dbPlayers = DBHelper.getDBInterface().getAllPlayers()
            return dbPlayers.map { dbplayer-> Player(dbplayer.uuid, dbplayer.name) }
        }

        fun getPlayerById(id: String): Player {
            val dbPlayer = DBHelper.getDBInterface().getPlayerById(id)
            return Player(dbPlayer.uuid, dbPlayer.name)
        }

    }
}