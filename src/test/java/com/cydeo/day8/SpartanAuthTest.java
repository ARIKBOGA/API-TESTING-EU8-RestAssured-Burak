package com.cydeo.day8;

import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class SpartanAuthTest extends SpartanAuthTestBase {

    @DisplayName("GET api/spartans as a public user(guest) -> expect 401")
    @Test
    public void test1() {
        given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .get("/api/spartans")
                .then()
                .statusCode(401)
                .log().all();
    }

    @DisplayName("GET api/spartans as admin -> expect 200")
    @Test
    public void test2() {
        given().auth()
                .basic("admin", "admin")
                .when().get("/api/spartans")
                .then().statusCode(200)
                .log().all();
    }


    @DisplayName("DELETE request as editor user -> expect 403")
    @Test
    public void test3() {

        given().auth().basic("user", "user")
                .accept(ContentType.JSON)
                .pathParam("id", 5)
                .delete("/api/spartans/{id}")
                .then()
                .statusCode(403)
                .log().all()
                .extract().response().prettyPrint();
    }
}
