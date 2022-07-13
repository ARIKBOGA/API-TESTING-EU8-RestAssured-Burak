package com.cydeo.day3;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ORDSApiTestsWithParameters {

    private static Response response;

    @BeforeAll
    public static void init() {
        RestAssured.baseURI = "http://44.202.119.26:1000/ords/hr";
    }

    /*
        Given accept type is Json
        And parameters: q = {"region_id":2}
        When users sends a GET request to "/countries"
        Then status code is 200
        And Content type is application/json
        And Payload should contain "United States of America"
        {"job_id":"IT_PROG"}
     */

    @DisplayName("Get request to /countries with Param-QueryMap")
    @Test
    public void test1() {

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q", "{\"region_id\":2}");

        response = given().log().all()
                .accept(ContentType.JSON)
                .queryParams(queryMap)
                .when()
                .get("/countries");

        assertEquals(200, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());
        assertTrue(response.body().asString().contains("United States of America"));
    }
}
