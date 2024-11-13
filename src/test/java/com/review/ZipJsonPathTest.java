package com.review;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ZipJsonPathTest extends ZipBase {

    private final static Map<String, Object> pathParams = new HashMap<>();

    @Test
    public void test1() {

        pathParams.clear();
        pathParams.put("country", "us");
        pathParams.put("state", "ma");
        pathParams.put("city", "belmont");
        Response response = given().accept(ContentType.JSON)
                .pathParams(pathParams)
                .get("/{country}/{state}/{city}")
                .then()
                .log().all()
                .contentType("application/json")
                .headers("Charset", "UTF-8", "Transfer-Encoding", "chunked")
                .header("X-Cache", equalTo("hit"))
                .header("CF-Cache-Status", "DYNAMIC")
                .statusCode(200)
                .extract().response();

        //response.prettyPrint();

        assertEquals("US", response.path("\'country abbreviation\'"));
        assertEquals("Belmont", response.path("places[0].\'place name\'"));
        assertEquals("-71.4594", response.path("places[0].longitude"));
        assertEquals("02178", response.path("places[0].\'post code\'"));
        assertEquals("42.4464", response.path("places[0].latitude"));
        assertEquals("Belmont", response.path("\'place name\'"));
        assertEquals("United States", response.path("country"));
        assertEquals("Massachusetts", response.path("state"));
        assertEquals("MA", response.path("\'state abbreviation\'"));

        JsonPath jsonPath = response.jsonPath();

        // GPATH syntax "it" statement
        // get the latitude that belongs to a place that have 02178 zipcode
        String actual = jsonPath.getString("places.findAll {it.\'post code\'==\'02178\'}.latitude");

        System.out.println("actual = " + actual);

    }
}

/*
{
    "country abbreviation": "US",
    "places": [
        {
            "place name": "Belmont",
            "longitude": "-71.4594",
            "post code": "02178",
            "latitude": "42.4464"
        },
        {
            "place name": "Belmont",
            "longitude": "-71.2044",
            "post code": "02478",
            "latitude": "42.4128"
        }
    ],
    "country": "United States",
    "place name": "Belmont",
    "state": "Massachusetts",
    "state abbreviation": "MA"
}
 */