package com.cydeo.day3;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SpartanTestWithParameteters {

    private static Response response;

    @BeforeAll
    public static void init() {

        RestAssured.baseURI = "http://34.238.126.28:8000";

    }

    /*   Given accept type is Json
          And Id parameter value is 5
          When user sends GET request to /api/spartans/{id}
          Then response status code should be 200
          And response content-type: application/json
          And "Blythe" should be in response payload
       */

    @DisplayName("Test with ID parameter")
    @Test
    public void test1() {

        response = given()
                .accept(ContentType.JSON)
                .pathParam("id", 5)
                .when()
                .get("/api/spartans/{id}");


        assertEquals(200, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());
        assertTrue(response.body().asString().contains("Blythe"));

    }


       /*
        TASK
        Given accept type is Json
        And Id parameter value is 500
        When user sends GET request to /api/spartans/{id}
        Then response status code should be 404
        And response content-type: application/json
        And "Not Found" message should be in response payload
     */

    @DisplayName("Test with ID parameter-Negative")
    @Test
    public void test2() {

        response = given()
                .accept(ContentType.JSON)
                .pathParam("id", 500)
                .and() // this is just for making easy to read the method thread. It is "Syntactic sugar, e.g."
                .when()
                .get("/api/spartans/{id}");

        assertEquals(404, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());
        assertTrue(response.body().asString().contains("Not Found"));
    }
}
