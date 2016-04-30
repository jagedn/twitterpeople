package twitterpeople

import grails.transaction.Transactional
import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.User

@Transactional
class TwitterProxyService {

    Twitter twitter

    Person createPerson( id ) {
        try {
            Person add
            User user
            if ("$id".isNumber() == false) {
                add = Person.get(id)
                if (add) {
                    return add
                }
                user = twitter.showUser("@${id}")
            } else {
                user = twitter.showUser(id as long)
            }

            if (user) {
                add = new Person(user.properties)
                add.id = id
            }
            add
        }catch( TwitterException te){
            te.printStackTrace()
            null
        }
    }
}
