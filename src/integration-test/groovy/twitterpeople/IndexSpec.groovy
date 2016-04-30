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

            given:
            def request = given(documentationSpec)
                    .accept("application/json")
                    .filter(document("people",
                        // podemos sustituir el host en la documentacion o el puerto
                        preprocessRequest(prettyPrint(),modifyUris().scheme("http").host(HOST).removePort())                     ,
                        // queremos que nuestra docu sea legible
                        preprocessResponse(prettyPrint())
            ))
            when:
            def then = request
                    .when()
                    .port(8080)
                    .get("/people")
                    .then()

            then:
            then.assertThat().statusCode(is(200));

        }

}
