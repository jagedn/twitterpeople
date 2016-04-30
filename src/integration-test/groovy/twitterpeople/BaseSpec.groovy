package twitterpeople

import com.jayway.restassured.builder.RequestSpecBuilder
import com.jayway.restassured.specification.RequestSpecification
import org.springframework.restdocs.ManualRestDocumentation
import spock.lang.Specification

import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration

/**
 * Created by jorge on 30/04/16.
 */
class BaseSpec extends Specification{

    protected final String HOST = 'www.twitterpeope.com'

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

}
