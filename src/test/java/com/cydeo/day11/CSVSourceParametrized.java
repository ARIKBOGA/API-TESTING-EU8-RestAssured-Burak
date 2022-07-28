package com.cydeo.day11;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.Matchers.*;

public class CSVSourceParametrized {

    // Write a parameterized test for this request
    // GET https://api.zippopotam.us/us/{state}/{city}
    /*
        "NY, New York",
        "CO, Denver",
        "VA, Fairfax",
        "VA, Arlington",
        "MA, Boston",
        "NY, New York",
        "MD, Annapolis"
     */
    //verify place name contains your city name
    //print number of places for each request

    @ParameterizedTest
    @CsvSource({"NY, New York",
            "CO, Denver",
            "VA, Fairfax",
            "VA, Arlington",
            "MA, Boston",
            "MD, Annapolis"})
    public void zipCodeMultiInputTest(String state, String city) {

        List<String> cities = given()
                .pathParam("state", state)
                .pathParam("city", city)
                .get("https://api.zippopotam.us/us/{state}/{city}")
                .then().statusCode(200)
                .extract().jsonPath().getList("places.'place name'");

        cities.forEach(each -> {
            assertTrue(each.toLowerCase().contains(city.toLowerCase()));
        });

        System.out.println("numberOfPlaces of " + city + " = " + cities.size());
    }
}