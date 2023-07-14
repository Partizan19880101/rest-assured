import config.VideoGameConfig;
import org.junit.Test;

import static config.VideoGameEndpoints.ALL_VIDEO_GAMES;
import static io.restassured.RestAssured.*;

public class MyFirstTest extends VideoGameConfig {

    @Test
    public void myFirstTest() {
        given()
                .log().all()
        .when()
                .get("/videogame")
        .then()
                .log().all();

    }
    @Test
    public void myFirstTestWithEndpoint() {
        get(ALL_VIDEO_GAMES)
                .then().log().all();
    }
}
