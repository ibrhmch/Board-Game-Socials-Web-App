package com.goodboards.app.sessions

import com.goodboards.app.database.DBHelper
import com.goodboards.app.game.Game
import com.goodboards.db.PlayerSession
import com.goodboards.db.Session


class SessionHelper {
    companion object {
        fun getAllSessions(): List<Session> {
            val dbSessions = DBHelper.getDBInterface().getAllSessions()
            return dbSessions.map { dbsession ->
                Session(
                    dbsession.uuid,
                    dbsession.sessionName
                )
            }

        }

        fun addSession(name: String) {
            DBHelper.getDBInterface().addSession(name)
        }

        fun getSessionInfo(sessionId: String): List<PlayerSession> {
            return DBHelper.getDBInterface().getSessionInfo(sessionId)
        }

//        fun getSessionById(id: String): Game {
//            val dbgame = DBHelper.getDBInterface().getGameById(id);
//            return Game(dbgame.uuid, dbgame.name, dbgame.description)
//        }
    }
}