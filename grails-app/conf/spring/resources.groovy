import twitter4j.TwitterFactory
import grails.rest.render.hal.*

// Place your Spring DSL code here
beans = {

    twitter(TwitterFactory) { bean ->
        bean.factoryMethod = "getSingleton"
        bean.singleton = true
    }

    halPeopleRenderer(HalJsonRenderer, twitterpeople.Person)

    halPeopleCollectionRenderer(HalJsonCollectionRenderer, twitterpeople.Person)
}
