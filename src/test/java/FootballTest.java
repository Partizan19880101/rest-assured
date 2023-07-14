import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

import config.FootballConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.List;
import org.junit.Test;

public class FootballTest extends FootballConfig {

    @Test
    public void getDetailOfOneArea() {
        given()
                .queryParam("areas", 2044)
        .when()
                .get("/areas");
    }

    @Test
    public void getDetailsOfMultipleAreas() {
        String areasId = "2036, 2045, 2012";

        given()
                .urlEncodingEnabled(true)
                .queryParam("areas", areasId)
        .when()
                .get("/areas");
    }

    @Test
    public void getDataFounded() {
        given()
        .when()
                .get("teams/57")
        .then()
                .body("founded", equalTo(1886));
    }

    @Test
    public void getFirstTeamName() {
        given()
        .when()
                .get("competitions/2021/teams")
        .then()
                .body("teams.name[0]", equalTo("Arsenal FC"));
    }

    @Test
    public void allTeamData() {
        String responseBody = get("teams/57").asString();
        System.out.println(responseBody);
    }

    @Test
    public void allTeamData_DoCheckFirst() {
        Response response =
                given()
                .when()
                        .get("teams/57")
                .then()
                        .contentType(ContentType.JSON)
                        .extract().response();
        String jsonResponseAsString = response.asString();
        System.out.println(jsonResponseAsString);
    }

    @Test
    public void extractHeaders() {
        Response response =
                get("teams/57")
        .then().extract().response();

        String contentTypeHeader = response.getContentType();
        System.out.println(contentTypeHeader);

        String apiVersionHeader = response.getHeader("X-API-Version");
        System.out.println(apiVersionHeader);
    }

    @Test
    public void extractFirstTeamName() {
        String firstTeamName =
                get("competitions/2021/teams").jsonPath().getString("teams.name[0]");
        System.out.println(firstTeamName);
    }

    @Test
    public void extractAllTeamNames() {
        Response response = get("competitions/2021/teams")
                .then().extract().response();

        List<String> teamNames = response.path("teams.name");
        for (String teamName : teamNames) {
            System.out.println(teamName);
        }
    }
}
