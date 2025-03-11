package Put.Positif;

import groovy.json.JsonSlurper;
import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.UUID;

public class editDataUser {

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
    public void editDataWithValidData() {

        String tokenAuth = env.get("ACCESS_TOKEN");
        String name = "testing lutfi";
        String gender = "male";
        String status = "active";
        String editname = "test edit";

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
        JSONObject bodyJsonEdit = new JSONObject();
        bodyJsonEdit.put("name", editname);

        RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization","Bearer " + tokenAuth)
                .body(bodyJsonEdit.toString())
                .when().put(id)
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("name", Matchers.equalTo(editname));

    }

}
