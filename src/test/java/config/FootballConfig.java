package config;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.BeforeClass;

public class FootballConfig {
    @BeforeClass
    public static void setup() {
        baseURI = "https://api.football-data.org";
        basePath = "/v4";

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setBasePath(basePath)
                .addHeader("X-Auth-Token", "0ccd4a3dd5e243ff8e37b64d8a345864")
                .addHeader("X-Response-Control", "minified")
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }
}
