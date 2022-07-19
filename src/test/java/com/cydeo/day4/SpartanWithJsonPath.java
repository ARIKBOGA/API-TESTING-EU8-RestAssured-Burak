package com.cydeo.day4;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class SpartanWithJsonPath extends SpartanTestBase {

    private static Response response;

    @DisplayName("Get one spartan with JsonPath")
    @Test
    public void test1(){

        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("gender","Male");
        queryMap.put("nameContains","Fidole");

        response = RestAssured.given().accept(ContentType.JSON)
                .and().queryParams(queryMap)
                .log().all()
                .when()
                .get("/api/spartans/search");

        //response.prettyPrint();

        JsonPath jsonPath = response.jsonPath();
        String name = jsonPath.getString("content[0].name");
        System.out.println("name = " + name);
        System.out.println("id = " + jsonPath.getInt("content[0].id"));


    }
}
