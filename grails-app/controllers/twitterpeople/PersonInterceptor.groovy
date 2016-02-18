package twitterpeople

import twitter4j.Twitter
import twitter4j.User

/**
 * Created by jorge on 18/02/16.
 */
class PersonInterceptor {

    Twitter twitter

    boolean before(){

        switch( params.action ){
            case 'show':
                if( !Person.get(params.id) ){
                    User user = twitter.showUser("@${params.id}")
                    if( user ){
                        Person add = new Person(user.properties)
                        add.id = params.id
                        if( add.validate() )
                            add.save(flush:true)
                        else{
                            println add.errors
                            return false
                        }
                    }
                }
                break

        }

        true
    }

}
