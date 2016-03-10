package twitterpeople

import com.jayway.restassured.builder.RequestSpecBuilder
import com.jayway.restassured.specification.RequestSpecification
import grails.test.mixin.integration.Integration
import org.junit.ClassRule
import org.springframework.restdocs.JUnitRestDocumentation
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.payload.ResponseFieldsSnippet
import spock.lang.Shared
import spock.lang.Specification

import static com.jayway.restassured.RestAssured.get
import static com.jayway.restassured.RestAssured.given
import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.is
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration
import static org.springframework.restdocs.restassured.operation.preprocess.RestAssuredPreprocessors.modifyUris

@Integration
class PeopleSpec extends Specification {

    final ManualRestDocumentation restDocumentation = new ManualRestDocumentation('build/generated-snippets')

    private RequestSpecification documentationSpec

    void setup() {
        this.documentationSpec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(restDocumentation)).build()
        this.restDocumentation.beforeTest(getClass(), specificationContext.currentSpec.name)
    }

    void cleanup() {
        this.restDocumentation.afterTest()
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
        def fields = [
                fieldWithPath('id').description('user name'),
                fieldWithPath('description').description(''),
                fieldWithPath('followersCount').description('how many followers'),
                fieldWithPath('friendsCount').description('how many friends'),
                fieldWithPath('location').description(''),
                fieldWithPath('someFriends').description('some friends'),
        ]

        def request = given(documentationSpec)
                .accept("application/json")
                .filter(document("people/${document}",
                preprocessRequest(modifyUris()
                        .scheme("http")
                        .host("localhost")
                        .removePort()),
                preprocessResponse(prettyPrint()),
                new ResponseFieldsSnippet(fields))
        )
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

    void "test with HATEOAS a person #username"() {

        given:
        def request = given(documentationSpec)
                .accept("application/hal+json")
                .filter(document("people-hateoas",
                preprocessRequest(modifyUris()
                        .scheme("http")
                        .host("localhost")
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
