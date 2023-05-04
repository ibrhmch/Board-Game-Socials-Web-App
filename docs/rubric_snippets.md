### Github URL: [Goodboards](https://github.com/CSCI-5828-Foundations-Sftware-Engr/slackers)

- [x] [Web application, basic form, reporting.](..%2F..%2Fapplications%2Fbasic-server%2Fsrc%2Fmain%2Fkotlin%2Fcom%2Fgoodboards%2Fapp%2FApp.kt)
![img.png](img.png)
- [x] [Data collection](..%2Fcomponents%2Fdata-collector)
```kotlin
    override fun execute(task: RetrieveNewsTask) {
        runBlocking {
            logger.info("starting data collection.")

            // Query news for all games in the database
            val games = dbInterface.getAllGames()
            val newsUnits = mutableListOf<String>()
            for(game in games) {

                // Format game name for API call
                val encodedName = URLEncoder.encode(game.name, "UTF-8")
                val url = "https://newsapi.org/v2/everything?q=$encodedName&language=en&pageSize=10&apiKey=$apiKey"

                // Get data and deserialize
                try {
                    // TODO: use a builder instead
                    val newsRaw: HttpResponse = client.get(url)
                    val newsResponse = format.decodeFromString<NewsResponse>(newsRaw.readText())

                    // Package response into units of work for redis
                    for(article in newsResponse.articles) {
                        if(article.title.isNotEmpty() && article.description.isNotEmpty() && article.url.isNotEmpty()) {
                            val unit = NewsUnit(game.uuid, article.title, article.description, article.url)
                            newsUnits.add(format.encodeToString(unit))
                        }
                    }
                } catch (e: Exception) {
                    logger.error("Failed to fetch news with error $e")
                }
            }

            // Put news in Redis if there is any
            logger.info(newsUnits.size.toString())
            if(newsUnits.isNotEmpty())
            {
                redisInterface.pushToList(name = "news:collect-analyze", newsUnits)
            }

            logger.info("completed data collection.")
        }
    }
```
- [x] [Data analyzer](..%2Fapplications%2Fdata-analyzer-server)
```kotlin
    fun scheduleAnalyzerNewsTask(){
        val finder = Wrapper.getAnalyzerWorkFinder()
        val worker = Wrapper.getAnalyzerWorker()
        val scheduler = WorkScheduler<AnalyzerTask>(finder, mutableListOf(worker), 30)
        scheduler.start()
    }
```
- [x] Unit tests 
  - [Server](..%2Fapplications%2Fbasic-server%2Fsrc%2Ftest)
  - [Analyzer](..%2Fapplications%2Fdata-analyzer-server%2Fsrc%2Ftest)
  - [Collector](..%2Fcomponents%2Fdata-collector%2Fsrc%2Ftest)
  - [Db Interface](..%2Fcomponents%2Fdb-interface%2Fsrc%2Ftest)
  - [Redis Interface](..%2Fcomponents%2Fredis-interface%2Fsrc%2Ftest)

- [x] [Data persistence](..%2Fcomponents%2Fdb-interface)
- [x] [Rest collaboration](..%2Fapplications%2Fbasic-server%2Fsrc%2Fmain%2Fkotlin%2Fcom%2Fgoodboards%2Fapp%2FApp.kt)
- [x] [Product environment](..%2F.github%2Fworkflows%2Fbuild.yml)
- [x] [Integration tests](..%2Fintegration)
- [x] [Using mock objects or any test doubles](..%2Fcomponents%2Fdb-interface%2Fsrc%2Ftest%2Fkotlin%2Ftest%2Fgoodboards%2Fdb%2Futil%2FDBMockUtil.kt)
- [x] [Continuous integration](..%2F.github%2Fworkflows%2Fbuild.yml)
- [x] Production monitoring![prd1.jpg](images%2Fprd1.jpg)![prod.jpg](images%2Fprod.jpg)
- [x] [Acceptance tests](..%2F.github%2Fworkflows%2Fintegration-test-staging.yml)
- [x] [Event collaboration messaging](..%2Fcomponents%2Fredis-interface%2Fsrc%2Fmain%2Fkotlin%2Fcom%2Fgoodboards%2Fredis%2FRedisInterface.kt)
- [x] [Continuous delivery](..%2F.github)
