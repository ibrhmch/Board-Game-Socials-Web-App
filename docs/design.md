# Design

## Applications
Both due to the requirement for the course and it's compatibility to the goal we have three major components.
* Web Application
* Data Analyzer
* Data Collector

## User pages
## Home Page
![image](https://user-images.githubusercontent.com/47374005/233217257-43380782-dc3b-491c-a40a-65c0dd1301de.png)

## Individual Game Page
![image](https://github.com/CSCI-5828-Foundations-Sftware-Engr/slackers/blob/main/docs/images/game.png)

# Routes
- /games: Lists game information and news
- /contact: Lists Contact information about developers
- /game/{game_id}: Display the individual game description and news. 

# API
- calling NewsAPI for news using https://newsapi.org/v2/everything?q=board_games&apiKey=18af37ae1b52421d808c96babcf7db7b

## News Retrieval
`Collector` will use news service APIs to retrieve news information and submit this into a `queue`.

`Analyzer` will collate the news instances from said queue, and persist it in the database with the required relational constraints.
