package twitterpeople

import com.jayway.restassured.builder.RequestSpecBuilder
import grails.test.mixin.integration.Integration
import org.junit.ClassRule
import org.springframework.restdocs.JUnitRestDocumentation
import spock.lang.Shared
import spock.lang.Specification

import static com.jayway.restassured.RestAssured.given
import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.is
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration
import static org.springframework.restdocs.restassured.operation.preprocess.RestAssuredPreprocessors.modifyUris

@Integration
class PeopleSpec extends Specification {

    @ClassRule
    @Shared
    JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/generated-snippets");

    @Shared
    def documentationSpec = new RequestSpecBuilder()
            .addFilter(documentationConfiguration(restDocumentation)).build();


    def setup() {
    }

    def cleanup() {
    }

    void "test index"() {

        given:
        def request = given(documentationSpec)
                .accept("application/json")
                .filter(document("people",
                preprocessRequest(modifyUris()
                        .scheme("http")
                        .host("localhost")
                        .removePort())))
        when:
        def then = request
                .when()
                .port(8080)
                .get("/people")
                .then()

        then:
        then.assertThat().statusCode(is(200));

    }

    void "test person #username"() {

        given:
        def request = given(documentationSpec)
                .accept("application/json")
                .filter(document("people/${document}",
                preprocessRequest(modifyUris()
                        .scheme("http")
                        .host("localhost")
                        .removePort())))
        when:
        def then = request
                .when()
                .port(8080)
                .get("/people/${username}")
                .then()

        then:
        then.assertThat().statusCode(is(200));
        then.assertThat().content('id', equalTo(username));

        where:
        username | document
        'jagedn' | "person1"
    }

}
