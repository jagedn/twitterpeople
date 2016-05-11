package twitterpeople

/**
 * Created by jorge on 30/04/16.
 */
import grails.test.mixin.integration.Integration

import static com.jayway.restassured.RestAssured.given
import static org.hamcrest.CoreMatchers.is
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document
import static org.springframework.restdocs.restassured.operation.preprocess.RestAssuredPreprocessors.modifyUris

@Integration
class IndexSpec extends BaseSpec {

        void "test index"() {

            expect:
            givenRequest( documentBase("people") )
                    .when()
                    .port(8080)
                    .get("/people")
                    .then()
                    .assertThat().statusCode(is(200))

        }

}
