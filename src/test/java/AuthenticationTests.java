import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

public class AuthenticationTests {

    @BeforeClass
    public static void setup() {
        RestAssured.proxy("localhost", 8888);
    }

    @Test
    public void basicPreemptiveAuthTest() {
        given()
                .auth().preemptive().basic("username", "password").
        when().get("http://localhost:8080/someEndpoint");
    }

    @Test
    public void basicChallengedTest() {
        given()
                .auth().basic("username", "password").
                when().get("http://localhost:8080/someEndpoint");
    }
}
