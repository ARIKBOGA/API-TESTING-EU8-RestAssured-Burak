package com.review;

import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;

public abstract class ZipBase {
    @BeforeAll
    public static void beforeAll() {
        baseURI = "https://www.zippopotam.us";
        //basePath = "/us";
    }
}
