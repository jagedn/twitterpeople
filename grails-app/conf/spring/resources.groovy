import twitter4j.TwitterFactory

// Place your Spring DSL code here
beans = {

    twitter(TwitterFactory) { bean ->
        bean.factoryMethod = "getSingleton"
        bean.singleton = true
    }

}
