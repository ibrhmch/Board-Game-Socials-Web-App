# Design

## Applications
Both due to the requirement for the course and it's compatibility to the goal we have three major components.
* Web Application
* Data Analyzer
* Data Collector

## Home Page
![image](https://user-images.githubusercontent.com/47374005/233217257-43380782-dc3b-491c-a40a-65c0dd1301de.png)

## Individual Game Page
![image](images/game.png)

## Routes
- /games: Lists game information and news
- /contact: Lists Contact information about developers
- /game/{id}: Display the individual game description and news. 

## API
- calling NewsAPI for news using https://newsapi.org/v2/everything?q=board_games&apiKey=

## News Retrieval
`Collector` will use news service APIs to retrieve news information and submit this into a `queue`.

`Analyzer` will collate the news instances from said queue, and persist it in the database with the required relational constraints.

## Database
We are using a Postgres database. Some key design decisions:
- Our database is named `goodboards`, and all tables are defined under a single schema `goodboards`.
- Tables:
  - `games` - contains all game data. Tracks UUID, name, and description of the game
  - `news` - contains all news data per game retrieved from the database. Tracks the ID, title, and description of each news article.
- Only one connection to the database exists at any point. The connection is defined as a Singleton.

Read more [here](database.md).
