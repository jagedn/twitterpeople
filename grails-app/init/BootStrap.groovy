import twitter4j.*
import twitter4j.api.*
import twitter4j.auth.AccessToken
import twitterpeople.Person
import twitterpeople.TwitterProxyService


class BootStrap {

    TwitterProxyService twitterProxyService

    def init = { servletContext ->
        Person add = twitterProxyService.createPerson('jagedn')
        if (add && add.validate()) {
            add.save(flush: true)
        }
    }

    def destroy = {
    }
}
