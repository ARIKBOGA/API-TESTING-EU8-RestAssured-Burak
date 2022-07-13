package com.cydeo.day3;

import com.cydeo.utilities.DBUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class ORDSApiTestsWithParameters {

    private static Response response;

    @BeforeAll
    public static void init() {
        RestAssured.baseURI = "http://34.238.126.28:1000/ords/hr";
        DBUtils.createConnection();
    }

    @AfterAll
    public static void tearDown(){
        DBUtils.destroy();
    }

    /*
        Given accept type is Json
        And parameters: q = {"region_id":2}
        When users sends a GET request to "/countries"
        Then status code is 200
        And Content type is application/json
        And Payload should contain "United States of America"
     */

    @DisplayName("Get request to /countries with Param-QueryMap")
    @Test
    public void test1() {

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q", "{\"region_id\":2}");

        response = given().log().all()
                .accept(ContentType.JSON)
                .queryParams(queryMap)
                .when()
                .get("/countries");

        assertEquals(200, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.header("Content-Type"));
        assertTrue(response.body().asString().contains("United States of America"));
    }

    /*
        Send a GET request to employees and get only employees who works as a IT_PROG {"job_id":"IT_PROG"}

     */

    @DisplayName("Get request to /employees for who works as a IT_PROG")
    @Test
    public void test2() {

        response = given()
                .accept(ContentType.JSON)
                .queryParam("q", "{\"job_id\":\"IT_PROG\"}")
                .when()
                .get("/employees");

        assertEquals(200, response.statusCode());

        List<Object> resultSet = DBUtils.getColumnData("SELECT  DISTINCT JOB_ID FROM EMPLOYEES", "JOB_ID");
        //System.out.println(resultSet);


        // assert the response doesn't contain the other JOB_IDs (except "IT_PROG")
        resultSet.forEach(jobId -> {
            if (!jobId.equals("IT_PROG"))
                assertFalse(response.body().asString().contains(jobId.toString()));
        });

        assertTrue(response.body().asString().contains("IT_PROG"));

    }
}