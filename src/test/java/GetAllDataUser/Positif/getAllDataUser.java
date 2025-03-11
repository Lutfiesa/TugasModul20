package GetAllDataUser.Positif;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import org.json.JSONObject;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class getAllDataUser {

    Dotenv env = Dotenv.load();

    @BeforeTest
    public void setup(){

        RestAssured.baseURI = env.get("BASE_URL");

    }

    @AfterTest
    public void tearDown() {

        RestAssured.reset();

    }

    @Test
    public void requestWithinValidData(){

        String tokenAuth = env.get("ACCESS_TOKEN");
        RestAssured.given()
                .header("Authorization","Bearer" + tokenAuth)
                .when().get()
                .then()
                .log().all()
                .assertThat().statusCode(200);

    }

    @Test
    public void requestValidDataWithParameter(){

        String tokenAuth = env.get("ACCESS_TOKEN");
        String gender = "male";
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("gender", gender);

        RestAssured.given()
                .header("Authorization","Bearer" + tokenAuth)
                .body(jsonBody.toString())
                .when().get()
                .then()
                .log().all()
                .assertThat().statusCode(200);

    }

}

