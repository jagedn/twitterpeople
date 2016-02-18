import twitter4j.*
import twitter4j.api.*
import twitter4j.auth.AccessToken
import twitterpeople.TwitterProxyService


class BootStrap {

    TwitterProxyService twitterProxyService

    def init = { servletContext ->

        twitterProxyService.createPerson("jagedn")

    }

    def destroy = {
    }
}
