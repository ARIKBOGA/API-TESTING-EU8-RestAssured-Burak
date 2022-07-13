package com.cydeo.day2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class SpartanGetRequests {

    private static final String BASE_URL = "http://34.238.126.28:8000";


    //    Given Accept type application/json
    //    When user send GET request to api/spartans end point
    //    Then status code must 200
    //    And response Content Type must be application/json
    //    And response body should include spartan result

    @DisplayName("API Test-1")
    @Test
    public void test1() {

        Response response = RestAssured.
                given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/api/spartans");

        assertEquals(200, response.statusCode(), "Failed");
        assertEquals("application/json", response.contentType(), "Failed");

        System.out.println(response.prettyPrint());

    }

     /*
        Given accept header is application/json
        When users sends a get request to /api/spartans/3
        Then status code should be 200
        And content type should be application/json
        and json body should contain Fidole
     */

    @DisplayName("Get One Spartan By ID")
    @Test
    public void test2() {

        Response response = RestAssured.
                given().
                accept(ContentType.JSON).
                when().
                get(BASE_URL + "/api/spartans/3");

        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());
        assertTrue(response.body().asString().contains("Fidole"), "Failed");
    }


    /*
        Given no headers provided
        When Users sends GET request to /api/hello
        Then response status code should be 200
        And Content type header should be “text/plain;charset=UTF-8”
        And header should contain date
        And Content-Length should be 17
        And body should be “Hello from Sparta"
        */

    @DisplayName("Get Request to api/hello")
    @Test
    public void test3() {
        Response response = RestAssured
                .when()
                .get(BASE_URL + "/api/hello");

        assertEquals(200, response.statusCode());
        assertEquals("text/plain;charset=UTF-8", response.contentType()); // we can use "response.header("Content-Type")" also
        assertTrue(response.headers().hasHeaderWithName("Date"));
        assertEquals("17", response.header("Content-Length"));
        assertEquals("Hello from Sparta", response.body().asString());
    }

}