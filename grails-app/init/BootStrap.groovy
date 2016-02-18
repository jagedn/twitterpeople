import twitter4j.*
import twitter4j.api.*
import twitter4j.auth.AccessToken


class BootStrap {

    Twitter twitter

    def init = { servletContext ->
        User jagedn = twitter.showUser("@jagedn")
        println jagedn.description
    }

    def destroy = {
    }
}
