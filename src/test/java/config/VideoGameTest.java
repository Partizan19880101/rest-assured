package config;

import static config.VideoGameEndpoints.ALL_VIDEO_GAMES;
import static config.VideoGameEndpoints.SINGLE_VIDEO_GAMES;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.lessThan;

import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.Test;
import pojo.VideoGame;

public class VideoGameTest extends VideoGameConfig {
    String gameBodyJson = "{\n"
            + "  \"category\": \"Platform\",\n"
            + "  \"name\": \"Mario\",\n"
            + "  \"rating\": \"Mature\",\n"
            + "  \"releaseDate\": \"2012-05-04\",\n"
            + "  \"reviewScore\": 85\n"
            + "}";

    @Test
    public void getAllGames() {
        given()
        .when()
                .get(ALL_VIDEO_GAMES)
        .then();
    }

    @Test
    public void createNewGameByJson() {
        given()
                .body(gameBodyJson)
        .when()
                .post(ALL_VIDEO_GAMES)
        .then();
    }

    @Test
    public void createNewGameByXML() {
        String gameBodyXML = "<VideoGameRequest>\n"
                + "\t<category>Platform</category>\n"
                + "\t<name>Mario</name>\n"
                + "\t<rating>Mature</rating>\n"
                + "\t<releaseDate>2012-05-04</releaseDate>\n"
                + "\t<reviewScore>85</reviewScore>\n"
                + "</VideoGameRequest>";
        given()
                .body(gameBodyXML)
                .contentType("application/xml")
                .accept("application/xml")
        .when()
                .post(ALL_VIDEO_GAMES)
        .then();
    }

    @Test
    public void updateGame() {
        given()
                .pathParam("videoGameId", 3)
                .body(gameBodyJson)
        .when()
                .put(SINGLE_VIDEO_GAMES)
        .then();
    }

    @Test
    public void deleteGame() {
        given()
                .pathParam("videoGameId", 3)
                .accept("text/plain")
        .when()
                .delete(SINGLE_VIDEO_GAMES)
        .then();
    }

    @Test
    public void getSingleGame() {
        given()
                .pathParam("videoGameId", 3)
        .when()
                .get(SINGLE_VIDEO_GAMES)
        .then();
    }

    @Test
    public void testVideoGameSerializationByJson() {
        VideoGame videoGame = new VideoGame("Shgoter", "MyAwesomeGame", "Mature", "2018-04-04", 99);

        given()
                .body(videoGame)
        .when()
                .post(ALL_VIDEO_GAMES)
        .then();
    }

    @Test
    public void convertJsonToPojo() {
        Response response =
                given()
                        .pathParam("videoGameId", 3)
                        .when()
                        .get(SINGLE_VIDEO_GAMES);

        VideoGame videoGame = response.getBody().as(VideoGame.class);
        System.out.println(videoGame.toString());
    }

    @Test
    public void testVideoGameSchemaXML() {
        given()
                .pathParam("videoGameId", 5)
                .accept("application/xml")
        .when()
                .get(SINGLE_VIDEO_GAMES)
        .then()
                .body(RestAssuredMatchers.matchesXsdInClasspath("VideoGameXSD.xsd"));
    }

    @Test
    public void testVideoGameSchemaJson() {
        given()
                .pathParam("videoGameId", 5)
                .accept("application/json")
        .when()
                .get(SINGLE_VIDEO_GAMES)
        .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("VideoGameJsonSchema.json"));
    }

    @Test
    public void captureResponseTime() {
        long response = get(ALL_VIDEO_GAMES).time();
        System.out.println("Response time in MS: " + response);
    }

    @Test
    public void assertOnResponseTime() {
        get(ALL_VIDEO_GAMES)
                .then().time(lessThan(1000L));
    }
}
