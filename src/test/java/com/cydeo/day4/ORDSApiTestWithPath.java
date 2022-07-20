package com.cydeo.day4;

import com.cydeo.utilities.HRTestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ORDSApiTestWithPath extends HRTestBase {

    private static Response response;

    @DisplayName("Get Request to Countries with path method")
    @Test
    public void test1() {

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q", "{\"region_id\":2}");

        response = RestAssured.given().accept(ContentType.JSON).and().queryParams(queryMap).log().all().when().get("/countries");

        //response.prettyPrint();

        System.out.println(response.path("items[0].country_id").toString());
        System.out.println(response.path("items[1].country_name").toString());
        System.out.println(response.path("items[2].links[0].href").toString());


        List<String> countryNames = response.path("items.country_name");
        System.out.println("countryNames = " + countryNames);

        List<Integer> idList = response.path("items.region_id");
        System.out.println("idList = " + idList);
        Assertions.assertEquals(0, idList.stream().filter(p -> p != 2).count());
    }


    @DisplayName("Get request to countries")
    @Test
    public void test2() {

        response = RestAssured.get("/countries");

        // first way to get the second country name with jsonpath
        JsonPath jsonPath = response.jsonPath();
        String secondCountry = jsonPath.getString("items[1].country_name");
        System.out.println("secondCountry = " + secondCountry);

        // second way to get the second country name with jsonpath
        List<String> countryNames = jsonPath.getList("items.country_name");
        System.out.println("secondCountry = " + countryNames.get(1));

        System.out.println("----------------------------\n");

        // get all country names where their region_id is equal to 2
        List<String> countryNamesWithRegionID_2 = jsonPath.getList("items.findAll {it.region_id==2}.country_name");
        System.out.println("countryNamesWithRegionID_2 = " + countryNamesWithRegionID_2);
    }


    @DisplayName("Get request to employees with query param")
    @Test
    public void test3() {

        response = RestAssured.given().queryParam("limit", 107).when().get("/employees");

        // Get all email of employees who is working as "IT_PROG"
        JsonPath jsonPath = response.jsonPath();
        List<String> list = jsonPath.getList("items.findAll {it.job_id==\"IT_PROG\"}.email");
        System.out.println("list = " + list);


        // Get first name of employees who is making more than 10_000
        list = jsonPath.getList("items.findAll {it.salary>10000}.first_name");
        System.out.println("list = " + list);

        // get the max salary person name
        String maxSalaryPerson = jsonPath.getString("items.max {it.salary}.first_name");
        System.out.println("maxSalaryPerson = " + maxSalaryPerson);

    }
}