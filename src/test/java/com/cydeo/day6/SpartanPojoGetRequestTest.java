package com.cydeo.day6;

import com.cydeo.pojo.Search;
import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class SpartanPojoGetRequestTest extends SpartanTestBase {

    private static Response response;

    @DisplayName("Get one spartan and convert it to Java object")
    @Test
    public void test1() {

        // converting with RestAssured.as method
        Spartan spartan15 = given().pathParam("id", 15)
                .get("/api/spartans/{id}")
                .as(Spartan.class);
        System.out.println("spartan15 = " + spartan15);

        response = get("/api/spartans")
                .then()
                .extract().response();
        List<Map<String, Object>> spartanList = response.as(List.class);
        spartanList.forEach(System.out::println);


        // converting with jsonPath.getObject method
        response = given().pathParam("id", 15)
                .get("/api/spartans/{id}");
        JsonPath jsonPath = response.jsonPath();
        Spartan s15 = jsonPath.getObject("", Spartan.class);
        System.out.println("s15 = " + s15);
        System.out.println("s15.getGender() = " + s15.getGender());
    }

    @DisplayName("Get one spartan from search endpoint result and use POJO")
    @Test
    public void spartanSearchWithPojo() {
        // spartans/search?nameContains=a&gender=Male
        // send get request to above endpoint and save first object with type Spartan POJO

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("nameContains", "a");
        queryMap.put("gender", "Male");

        response = given()
                .queryParams(queryMap)
                .get("/api/spartans/search")
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();

        Spartan firstSpartan = response.jsonPath().getObject("content[0]", Spartan.class);
        System.out.println("firstSpartan = " + firstSpartan);
    }


    @DisplayName("Get one spartan from search endpoint result and use POJO")
    @Test
    public void spartanSearchWithPojo_Array() {
        // spartans/search?nameContains=a&gender=Male
        // send get request to above endpoint and save first object with type Spartan POJO

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("nameContains", "a");
        queryMap.put("gender", "Male");

        response = given()
                .queryParams(queryMap)
                .get("/api/spartans/search")
                .then()
                .statusCode(200)
                //.log().all()
                .extract().response();

        Search search = response.as(Search.class);
        System.out.println("search.getContent()[0].getName() = " + search.getContent()[0].getName());


    }


    @DisplayName("Get one spartan from search endpoint result and use POJO")
    @Test
    public void test4() {

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("nameContains", "a");
        queryMap.put("gender", "Male");

        List<Spartan> spartanList = given().queryParams(queryMap).get("/api/spartans/search")
                .then()
                .statusCode(200)
                .extract().jsonPath()
                .getList("content", Spartan.class);

        System.out.println("spartanList.get(0) = " + spartanList.get(0));

    }


}
