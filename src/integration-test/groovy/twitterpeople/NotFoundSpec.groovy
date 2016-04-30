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

        given:

        def request = given(documentationSpec)
                .accept("application/json")
                .filter(document("people/notfound",
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
                .get("/people/usuario_no_existente_en_twitter")
                .then()

        then:
        then.assertThat().statusCode(is(404));
    }

}
