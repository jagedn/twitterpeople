package twitterpeople

/**
 * Created by jorge on 30/04/16.
 */
import grails.test.mixin.integration.Integration
import spock.lang.Specification

import static com.jayway.restassured.RestAssured.given
import static org.hamcrest.CoreMatchers.is
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document
import static org.springframework.restdocs.restassured.operation.preprocess.RestAssuredPreprocessors.modifyUris

@Integration
class HateoasSpec extends BaseSpec{

    void "test with HATEOAS a person #username"() {

        given:
        def request = given(documentationSpec)
                .accept("application/hal+json")
                .filter(document("people-hateoas",
                preprocessRequest(modifyUris()
                        .scheme("http")
                        .host(HOST)
                        .removePort()),
                preprocessResponse(prettyPrint()))
        )
        when:
        def then = request
                .when()
                .port(8080)
                .get("/people/jagedn")
                .then()

        then:
        then.assertThat().statusCode(is(200));
        then.assertThat().content('_links.self.href', is('http://localhost:8080/people/jagedn'))
    }

}
