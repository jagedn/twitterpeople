package twitterpeople

import com.jayway.restassured.builder.RequestSpecBuilder
import com.jayway.restassured.specification.RequestSpecification
import grails.test.mixin.integration.Integration
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.constraints.ConstraintDescriptions
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.RequestFieldsSnippet
import org.springframework.restdocs.payload.ResponseFieldsSnippet
import spock.lang.Specification

import static com.jayway.restassured.RestAssured.given
import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.is
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedRequestFields
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration
import static org.springframework.restdocs.restassured.operation.preprocess.RestAssuredPreprocessors.modifyUris
import static org.springframework.restdocs.snippet.Attributes.key;

@Integration
class PeopleSpec extends BaseSpec {

    void "test person #username with #description"() {

        given:
        FieldDescriptor[] fields = [
                fieldWithPath('id').description('user name'),
                fieldWithPath('description').description(''),
                fieldWithPath('followersCount').description('how many followers'),
                fieldWithPath('friendsCount').description('how many friends'),
                fieldWithPath('location').description(''),
                //fieldWithPath('dateCreated').description('when was created in our system'),
        ]

        def request = given(documentationSpec)
                .accept("application/json")
                .filter(document("people/${document}",
                preprocessRequest(modifyUris().scheme("http").host(HOST).removePort()),
                preprocessResponse(prettyPrint()),
                responseFields(fields))
        )
        when:
        def then = request
                .when()
                .port(8080)
                .get("/people/${username}")
                .then()

        then:
        then.assertThat().statusCode(is(200))
                .and().content('id', equalTo(username))
                .and().content('description', equalTo(description));

        where:
        document    | username | description
        "example1"  | 'jagedn' | 'Dev As Service'
    }


    void "test person #username with relaxed response fields"() {

        given:
        FieldDescriptor[] responseFields = [
                fieldWithPath('description').description('').attributes(
                        key('constraints').value('la descripcion sera menor de 30 caracteres')
                )
        ]

        def request = given(documentationSpec)
                .accept("application/json")
                .filter(document("people/relaxed",
                        preprocessRequest(modifyUris().scheme("http").host(HOST).removePort()),
                        preprocessResponse(prettyPrint()),
                        relaxedResponseFields(responseFields)))
        when:
        def then = request
                .when().port(8080).get("/people/${username}").then()

        then:
        then.assertThat().statusCode(is(200));

        where:
        username | description
        'jagedn' | 'Dev As Service'
    }
}
