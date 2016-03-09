package twitterpeople

import grails.test.mixin.TestFor
import grails.test.mixin.integration.Integration
import spock.lang.Specification

/**
 * Created by jorge on 9/03/16.
 */
@Integration
@TestFor(ApplicationController)
class ApplicationIntegrationSpec extends Specification{

    void "test something"() {

        when:
        controller.index()

        then:
        true
    }
}
