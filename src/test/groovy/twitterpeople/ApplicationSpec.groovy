package twitterpeople

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * Created by jorge on 9/03/16.
 */
@TestFor(ApplicationController)
class ApplicationSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {

        when:
        controller.index()

        then:
        true
    }
}
