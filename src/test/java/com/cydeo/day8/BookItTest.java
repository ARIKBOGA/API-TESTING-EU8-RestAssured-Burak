package com.cydeo.day8;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class BookItTest {

    private final String accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxNDAiLCJhdWQiOiJzdHVkZW50LXRlYW0tbGVhZGVyIn0.xNvdQRyrYMb3Ec5QByHwXowBo3zPK2jQQS1eJ2RYIto";

    @BeforeAll
    public static void init() {
        baseURI = "https://cybertek-reservation-api-qa.herokuapp.com";
    }

    @DisplayName("GET all campuses")
    @Test
    public void test1() {

        given().header("Authorization", accessToken)
                .accept(ContentType.JSON)
                .get("/api/campuses")
                .then()
                .statusCode(200)
                .log().all();

    }
}
