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
class NotFoundSpec  extends BaseSpec{

    void "test person not found"() {
        expect:
        givenRequest( documentBase("people/notfound") )
                .when()
                .port(8080)
                .get("/people/usuario_no_existente_en_twitter")
                .then()
                .assertThat().statusCode(is(404))
    }

}

