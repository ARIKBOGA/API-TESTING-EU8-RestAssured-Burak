package com.cydeo.day12;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class MockAPI {

    @BeforeAll
    public static void init() {
        RestAssured.baseURI = "https://5f14d995-6895-48c8-9621-c23dd3eec498.mock.pstmn.io";
    }

    @Test
    public void getCustomer() {
        given()
                .get("/customer")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void getEmployee() {
        given()
                .get("/employee")
                .then()
                .statusCode(401)
                .log().body();
    }
}
