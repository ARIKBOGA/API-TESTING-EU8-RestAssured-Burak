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
/*
        Json Schema Validation

Json Schema is description about Json Data.

{
"id": 10,
"name": "Lorenza",
"gender": "Female",
"phone": 3312820936
}

id--> required, integer , ip to 6 digits
name --> required, string, up to 30char
gender --> optional ,Male or Female
phone --> optional long, default 0



to create json schema in case you need it --> https://www.jsonschema.net/home

to manually test json schema --> https://www.jsonschemavalidator.net/

STEPS for VALIDATION
1.You will get JSON schema for each endpoint you have that is returning JSON BODY/Payload
2.Save those .json files under resources folder
3.Add Json schema validator depedenceny to your pom.xml
	   <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>json-schema-validator</artifactId>
            <version>4.4.0</version>
        </dependency>
4.Then perform required api request to test specific schema example:
	you are getting one spartan, you will test with singleSpartanSchema.json file

.then()
        .statusCode(200)
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SingleSpartanSchema.json"));
 */

/*
    JsonSchemaValidator --> class that we use for schema validation

if the file under resources we use matchesJsonSchemaInClasspath("filename") method.
file name will be enough.

if the file is not under resouserces then we use matchesJsonSchema(new File("file path under project(starts with src/")) to provide file to JsonSchemaValidator class.
 */