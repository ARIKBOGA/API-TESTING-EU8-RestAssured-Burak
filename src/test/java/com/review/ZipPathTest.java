package com.review;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ZipPathTest extends ZipBase {

    @Test
    public void test1() {

        Response response = given().accept(ContentType.JSON)
                .pathParam("zip", 22031)
                .get("/{zip}");

        response.prettyPrint();
    }
}
