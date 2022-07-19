package com.cydeo.day6;

import com.cydeo.pojo.Employee;
import com.cydeo.pojo.Region;
import com.cydeo.pojo.Regions;
import com.cydeo.utilities.HRTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ORDSPojoGetRequestTest extends HRTestBase {

    private static Response response;

    @DisplayName("Get request to ORDS- POJO")
    @Test
    public void test1() {
        Region region = given().accept(ContentType.JSON)
                .get("/regions")
                .jsonPath()
                .getObject("items[0]", Region.class);

        System.out.println("region.getLinks() = " + region.getLinks());

        System.out.println("region.getLinks().get(0).getRel() = " + region.getLinks().get(0).getRel());

        System.out.println("region.getID() = " + region.getID());
    }

    @DisplayName("GET request to /employees and only get couple of values as a Pojo class")
    @Test
    public void employeeGet() {

        List<Employee> employeeList = get("/employees")
                .then()
                .statusCode(200)
                .extract().jsonPath()
                .getList("items", Employee.class);
        employeeList.stream().limit(4L).forEach(System.out::println);
    }

    /*
       send a get request to regions
        verify that region ids are 1,2,3,4
        verify that regions names Europe ,Americas , Asia, Middle East and Africa
        verify that count is 4
        try to use pojo as much as possible
        ignore non-used fields
    */

    @DisplayName("Get request to regions")
    @Test
    public void getRegions() {
        Regions regions = get("/regions")
                .then()
                .statusCode(200)
                .extract()
                .as(Regions.class);

        // compare IDs by getting with stream operations from regions list
        assertThat(List.of(1, 2, 3, 4), is(regions.getItems()
                .stream()
                .map(Region::getID)
                .collect(Collectors.toList())));

        assertEquals(4, regions.getCount());

        // compare region names by getting with stream operations from regions list
        assertThat(List.of("Europe", "Americas", "Asia", "Middle East and Africa"), is(equalTo(regions.getItems()
                .stream()
                .map(Region::getRegionName)
                .collect(Collectors.toList()))));
    }
}