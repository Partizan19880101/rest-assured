package config;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.Matchers.lessThan;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.BeforeClass;

public class VideoGameConfig {

    @BeforeClass
    public static void setup() {
        baseURI = "https://videogamedb.uk/";
        basePath = "api/v2/";
        //RestAssured.port = 443; this is default port

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setBasePath(basePath)
                .setContentType("application/xml")
                .addHeader("Accept", "application/xml")
                .addFilter(new RequestLoggingFilter()) //always log everything
                .addFilter(new ResponseLoggingFilter()) //always log everything
                .build();

        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectResponseTime(lessThan(2000L))
                .build();

    }
}
