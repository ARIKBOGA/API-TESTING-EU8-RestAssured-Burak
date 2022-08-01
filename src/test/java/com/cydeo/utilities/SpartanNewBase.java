package com.cydeo.utilities;

import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;

public class SpartanNewBase {

    protected static ResponseSpecification responseSpec;
    protected static RequestSpecification requestSpec;
    protected static RequestSpecification userSpec;

    @BeforeAll
    public static void init() {

        baseURI = "http://" + ConfigurationReader.getProperty("EC2_IP");
        port = Integer.parseInt(ConfigurationReader.getProperty("spartan_auth_port"));
        basePath = ConfigurationReader.getProperty("spartan_basePath");

        requestSpec = given()
                .auth().basic("admin", "admin")
                .log().all();
        userSpec = given()
                .auth().basic("user", "user")
                .log().all();
        responseSpec = given().expect()
                .statusCode(200)
                .logDetail(LogDetail.ALL)
                .contentType(ContentType.JSON);
    }

    @AfterAll
    public static void close() {

        // reset the info we set above
        reset();
    }
}
