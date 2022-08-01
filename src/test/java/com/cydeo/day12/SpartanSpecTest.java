package com.cydeo.day12;

import com.cydeo.utilities.SpartanNewBase;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class SpartanSpecTest extends SpartanNewBase {

    //all tests in this class will use admin credentials
    //all test in this class will expect json result from response

    //all test in this class response status code is 200
    //all test in this class response content type header is json

    @Test
    public void test1() {

        given()
                .spec(requestSpec)
                .get("/spartans")
        .then()
                .spec(responseSpec);

    }


    @Test
    public void test2() {

        given()
                .spec(requestSpec)
                .pathParam("id", 12)
        .when()
                .get("/spartans/{id}")
        .then()
                .spec(responseSpec);

    }
}
