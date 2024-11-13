package com.cydeo.day12;

import com.cydeo.utilities.SpartanNewBase;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class OldRestAssuredTest extends SpartanNewBase {

    @DisplayName("GET req. to spartan API to get ALL")
    @Test
    public void test1() {
        given().contentType(ContentType.JSON)
                .auth().basic("admin", "admin")
                .log().all()
                .get("/spartans")
                .then().statusCode(200)
                .body("id[0]", is(1))
                .log().all();
    }


        /*
            in previous version of RestAssured, the given when then style
            was actually written in given expect when formatted.
            it will assert all the result and give one answer and does not fail whole thing
            if first one fail unlike new structure.

         */


    @DisplayName("OLD structure GET req. to spartan API to get ALL")
    @Test
    public void test2() {
        given().contentType(ContentType.JSON)
                .auth().basic("admin", "admin")
                .expect()
                .statusCode(200)
                .body("id[0]", is(10))
                .body("id[4]", is(5))
                .body("id[7]", is(180))
                .contentType(ContentType.XML)
                .logDetail(LogDetail.ALL) // log way with using expect method
                .when()
                .get("/spartans");
    }
}
