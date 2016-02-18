package twitterpeople

import grails.transaction.Transactional
import twitter4j.Twitter
import twitter4j.User

@Transactional
class TwitterProxyService {

    Twitter twitter

    boolean createPerson( String id) {
        if( !Person.get(id) ){
            User user = twitter.showUser("@${id}")
            if( user ){
                Person add = new Person(user.properties)
                add.id = id
                if( add.validate() )
                    add.save(flush:true)

                else{
                    println add.errors
                    return false
                }
            }
        }
        return true
    }
}
