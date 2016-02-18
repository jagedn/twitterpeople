package twitterpeople

import twitter4j.Twitter
import twitter4j.User

/**
 * Created by jorge on 18/02/16.
 */
class PersonInterceptor {

    TwitterProxyService twitterProxyService

    boolean before(){

        switch( params.action ){
            case 'show':
                return twitterProxyService.createPerson(params.id)
        }

        true
    }

}
