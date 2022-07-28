package com.cydeo.day11;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

public class CSVFileSourceParametrizedTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/postalcode.csv", numLinesToSkip = 1)
    public void test1(String state, String city, int zipcode) {

        given()
                .pathParam("state", state)
                .pathParam("city", city)
                .get("https://api.zippopotam.us/us/{state}/{city}")
                .then().statusCode(200)
                .body("places.'place name'", hasSize(zipcode));
    }
}
