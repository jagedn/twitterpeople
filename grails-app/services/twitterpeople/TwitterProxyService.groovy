package twitterpeople

import grails.transaction.Transactional
import twitter4j.Twitter
import twitter4j.User

@Transactional
class TwitterProxyService {

    Twitter twitter

    Person createPerson( id ) {
        Person add
        User user
        if( "$id".isNumber() == false ){
            add=Person.get(id)
            if(add){
               return add
            }
            user = twitter.showUser("@${id}")
        }else{
            user = twitter.showUser( id as long)
        }

        if( user ){
            add = new Person(user.properties)
            add.id = id
            if( add.validate() ) {
                long [] friendsIds = twitter.friendsFollowers().getFriendsIDs(user.id,-1,2).IDs;
                friendsIds.each{ friendId->
                    Person friend = createPerson(friendId)
                    if( friend ){
                        add.addToSomeFriends(friend)
                    }
                }
                add.save(flush: true)
            }
            else{
                println add.errors
            }
        }
        add
    }
}
