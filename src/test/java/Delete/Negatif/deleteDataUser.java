package Delete.Negatif;

import groovy.json.JsonSlurper;
import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.UUID;

public class deleteDataUser {

    Dotenv env = Dotenv.load();
    private String generatedEmail; // Simpan email di variabel global
    private String userId; // Simpan ID user setelah POST

    @BeforeTest
    public void setup(){

        RestAssured.baseURI = env.get("BASE_URL");
        generatedEmail = "test" + UUID.randomUUID().toString().substring(0, 8) + "@google.com";

    }

    @AfterTest
    public void tearDown() {

        RestAssured.reset();

    }

    @Test
    public void deleteDataUserWithInValidData() {

        String tokenAuth = env.get("ACCESS_TOKEN");
        RestAssured.given()
                .header("Authorization","Bearer " + tokenAuth)
                .when().delete("/00001")
                .then()
                .log().all()
                .assertThat().statusCode(404);

    }

    @Test
    public void deleteDataUserWithInValidToken() {

        String tokenAuth = env.get("ACCESS_TOKEN");
        String name = "testing lutfi";
        String gender = "male";
        String status = "active";

        JSONObject bodyJson = new JSONObject();
        bodyJson.put("name", name);
        bodyJson.put("email", generatedEmail);
        bodyJson.put("gender", gender);
        bodyJson.put("status", status);

        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + tokenAuth)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(bodyJson.toString())
                .when().post()
                .then()
                .log().all()
                .extract().response();

        String responseBody = response.getBody().asString();
        JsonSlurper jsonSlurper = new JsonSlurper();
        Map<String, Object> jsonResponse = (Map<String, Object>) jsonSlurper.parseText(responseBody);
        String id = jsonResponse.get("id").toString();

        RestAssured.given()
                .header("Authorization","Bearer 1231")
                .when().delete(id)
                .then()
                .log().all()
                .assertThat().statusCode(401);

    }

}
