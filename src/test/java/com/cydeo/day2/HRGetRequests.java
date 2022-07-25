package com.cydeo.day2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class HRGetRequests {

    @BeforeAll
    public static void init() {

        RestAssured.baseURI = "http://54.234.226.200:1000/ords/hr";

    }

    @DisplayName("Get request to/region")
    @Test
    public void test1() {

        Response response = get("/regions");

        System.out.println(response.getStatusCode());
    }


      /*
        Given accept type is application/json
        When user sends get request to /regions/2
        Then response status code must be 200
        and content type equals to application/json
        and response body contains   Americas
     */

    @DisplayName("Get request to/region/2")
    @Test
    public void test2() {

        Response response = given()
                        .accept(ContentType.JSON)
                        .when()
                        .get("/regions/2");

        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());
        assertTrue(response.body().asString().contains("Americas"));
    }
}