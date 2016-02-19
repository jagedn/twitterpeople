package twitterpeople

import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import grails.test.mixin.integration.Integration
import spock.lang.Specification

/**
 * Created by jorge on 19/02/16.
 */
@Integration
class SimpleSpec extends Specification{

    void "test index"(){

        given:
        RestBuilder rest = new RestBuilder()

        when:
        RestResponse response = rest.get("http://localhost:8080/people")

        then:
        response.status == 200

    }

    void "test user #username"(){

        given:
        RestBuilder rest = new RestBuilder()

        when:
        RestResponse response = rest.get("http://localhost:8080/people/${username}")

        then:
        response.status == 200
        response.json.id == username

        where:
        username | description
        'jagedn' | ""
    }

}
