package com.cydeo.day8;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class BookItTest {

    private final String email = "wcanadinea@ihg.com";
    private final String password = "waverleycanadine";

    @BeforeAll
    public static void init() {
        baseURI = "https://cybertek-reservation-api-qa2.herokuapp.com";
    }

    @DisplayName("GET all campuses")
    @Test
    public void test1() {

        given().header("Authorization", getToken(email, password))
                .accept(ContentType.JSON)
                .get("/api/campuses")
                .then()
                .statusCode(200)
                .log().all();

    }


    private static String getToken(String email, String password) {
        return "Bearer " + given().accept(ContentType.JSON)
                .queryParams("email", email, "password", password)
                .get("/sign")
                .jsonPath().getString("accessToken");
    }
}
