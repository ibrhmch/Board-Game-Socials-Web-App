package test.goodboards.db

import com.goodboards.db.Game

class DBInterfaceTestHelpers() {

    val ids = mutableListOf<String>()
    val names = mutableListOf<String>()
    val descriptions = mutableListOf<String>()
    val iterations = mutableListOf<Boolean>()

    fun parseGames (games: List<Game>) {
        for(game in games) {
            ids.add(game.uuid)
            names.add(game.name)
            descriptions.add(game.description)
            iterations.add(true)
        }
        iterations.add(false)
    }

    fun getGameIds() : List<String> {
        return ids
    }

    fun getGameNames() : List<String> {
        return names
    }

    fun getGameDescriptions() : List<String> {
        return descriptions
    }

    fun getGameIterations() : List<Boolean> {
        return iterations
    }

}