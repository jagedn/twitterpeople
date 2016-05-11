package twitterpeople

import com.jayway.restassured.RestAssured
import com.jayway.restassured.builder.RequestSpecBuilder
import com.jayway.restassured.specification.RequestSpecification
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.restassured.RestDocumentationFilter
import org.springframework.restdocs.snippet.Snippet
import spock.lang.Specification

import static com.jayway.restassured.RestAssured.given
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration
import static org.springframework.restdocs.restassured.operation.preprocess.RestAssuredPreprocessors.modifyUris

/**
 * Created by jorge on 30/04/16.
 */
class BaseSpec extends Specification{

    protected final String HOST = 'www.twitterpeople.com'

    protected final ManualRestDocumentation restDocumentation = new ManualRestDocumentation('build/generated-snippets')

    protected RequestSpecification documentationSpec

    void setup() {
        this.documentationSpec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(restDocumentation)).build()
        this.restDocumentation.beforeTest(getClass(), specificationContext.currentSpec.name)
    }

    void cleanup() {
        this.restDocumentation.afterTest()
    }

    RestDocumentationFilter documentBase(String documentName, Snippet... snippets=null){
        // podemos sustituir el host en la documentacion o el puerto
        // y usamos prettyPrint porque queremos que nuestra docu sea legible
        if( snippets )
            document(documentName,
                    preprocessRequest(prettyPrint(),modifyUris().scheme("http").host(HOST).removePort()),
                    preprocessResponse(prettyPrint()),
                    snippets
            )
        else
            document(documentName,
                    preprocessRequest(prettyPrint(),modifyUris().scheme("http").host(HOST).removePort()),
                    preprocessResponse(prettyPrint()),
            )
    }

    RequestSpecification givenRequest(RestDocumentationFilter filter){
        given(documentationSpec)
                .accept("application/json")
                .filter(filter)
    }

}
