package Post.Negatif;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import org.json.JSONObject;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.UUID;

public class createNewDataUser {

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
    public void creatNewDataUserWithInvalidData(){

        String tokenAuth = env.get("ACCESS_TOKEN");
        System.out.println("Token: " + tokenAuth);
        String gender = "male";
        String status = "active";

        JSONObject bodyJson = new JSONObject();
        bodyJson.put("email", generatedEmail);
        bodyJson.put("gender", gender);
        bodyJson.put("status", status);

        RestAssured.given()
                .header("Authorization", "Bearer " + tokenAuth)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(bodyJson.toString())
                .when().post()
                .then().log().all()
                .assertThat().statusCode(422);

    }

    @Test
    public void creatNewDataUserWithMaxCaracter(){

        String tokenAuth = env.get("ACCESS_TOKEN");
        System.out.println("Token: " + tokenAuth);
        String name = "lutfiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii";
        String gender = "male";
        String status = "active";

        JSONObject bodyJson = new JSONObject();
        bodyJson.put("name", name);
        bodyJson.put("email", generatedEmail);
        bodyJson.put("gender", gender);
        bodyJson.put("status", status);

        RestAssured.given()
                .header("Authorization", "Bearer " + tokenAuth)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(bodyJson.toString())
                .when().post()
                .then().log().all()
                .assertThat().statusCode(422);

    }

    @Test
    public void requestValidDataWithInvalidToken(){

        String tokenAuth = env.get("ACCESS_TOKEN");
        System.out.println("Token: " + tokenAuth);
        String name = "testing lutfi";
        String gender = "male";
        String status = "active";

        JSONObject bodyJson = new JSONObject();
        bodyJson.put("name", name);
        bodyJson.put("email", generatedEmail);
        bodyJson.put("gender", gender);
        bodyJson.put("status", status);

        RestAssured.given()
                .header("Authorization", "Bearer aaa")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(bodyJson.toString())
                .when().post()
                .then().log().all()
                .assertThat().statusCode(401);
    }

}
