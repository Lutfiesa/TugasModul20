package GetDataUser.Positif;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;

public class GetDataUser {

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
    public void requestWithValidData(){

        String tokenAuth = env.get("ACCESS_TOKEN");
        RestAssured.given()
                .header("Authorization","Bearer " + tokenAuth)
                .when().get("/7759083")
                .then()
                .log().all()
                .assertThat().statusCode(200);

    }

    @Test
    public void testValidateJsonSchemaGetSingelUser(){

        String tokenAuth = env.get("ACCESS_TOKEN");
        File file = new File("src/test/resources/GetSingleUserSchema.json");
        RestAssured.given()
                .header("Authorization","Bearer " + tokenAuth)
                .when().get("/7759083")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body(JsonSchemaValidator.matchesJsonSchema(file));

    }

}
