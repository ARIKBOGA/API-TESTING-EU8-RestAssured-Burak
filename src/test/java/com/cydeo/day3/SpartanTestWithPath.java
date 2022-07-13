package com.cydeo.day3;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpartanTestWithPath extends SpartanTestBase {

    private static Response response;


    /*
       Given accept type is json
       And path param id is 10
       When user sends a get request to "api/spartans/{id}"
       Then status code is 200
       And content-type is "application/json"
       And response payload values match the following:
            id is 10,
            name is "Lorenza",
            gender is "Female",
            phone is 3312820936
    */

    @DisplayName("Get one Spartan with Path method")
    @Test
    public void test1() throws FileNotFoundException {

        response = given()
                .accept(ContentType.JSON)
                .pathParam("id", 10)
                .when()
                .get("api/spartans/{id}");

        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());


        assertEquals(10, (Integer) response.path("id"));
        assertEquals("Lorenza", response.path("name"));
        assertEquals("Female", response.path("gender"));
        assertEquals(3312820936L, (Long) response.path("phone"));

    }


    @Test
    public void test2() {

        response = given()
                .accept(ContentType.JSON)
                .when()
                .get("api/spartans");

        //response.prettyPrint();

        System.out.println("response.path(\"id[-1]\") = " + response.path("id[-1]"));

        System.out.println(response.path("name[-2]").toString());


        // save all names in a list after getting them from the api
        List<String> names = response.path("name");
        // print all names (distinct)
        System.out.println("names = " + names
                .stream()
                .distinct()
                .collect(Collectors.toList()));
    }
}
