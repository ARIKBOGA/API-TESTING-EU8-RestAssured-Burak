package com.cydeo.day10;

import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class JSONSchemaValidationTest extends SpartanAuthTestBase {

    @DisplayName("GET request to verify one spartan against to schema")
    @Test
    public void test1() {

        given().contentType(ContentType.JSON)
                .auth().basic("admin", "admin")
                .pathParam("id", 10)
                .get("/api/spartans/{id}")
                .then().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SingleSpartanSchema.json"))
                .log().all();

    }

    @DisplayName("GET request to verify ALL spartan against to schema")
    @Test
    public void test2() {

        given().contentType(ContentType.JSON)
                .auth().basic("admin", "admin")
                .get("/api/spartans")
                .then().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/java/com/cydeo/day10/allSpartansSchema.json")))
                .log().all();

    }

    // HOMEWORK
    @DisplayName("POST request to verify one spartan against to schema")
    @Test
    public void test3() {

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("name", "UnderfedBull");
        postMap.put("gender", "Male");
        postMap.put("phone", 1234987650L);

        given().contentType(ContentType.JSON)
                .auth().basic("admin", "admin")
                .body(postMap)
                .post("/api/spartans")
                .then().statusCode(201)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/java/com/cydeo/day10/spartanPostJsonSchema.json")))
                .log().all();
    }
}