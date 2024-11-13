package com.cydeo.day10;

import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.is;

public class SpartanWithXML extends SpartanAuthTestBase {

    @DisplayName("GET request to api/spartans and verify XML")
    @Test
    public void test1() {
        RestAssured.given().accept(ContentType.XML)
                .auth().basic("admin", "admin")
                .get("/api/spartans")
                .then()
                .statusCode(200)
                .contentType("application/xml;charset=UTF-8")
                .body("List.item[0].name", is("Meade"))
                .body("List.item[0].gender", is("Male"))
                .log().all();

    }

    @DisplayName("GET request to api/spartans with XML path")
    @Test
    public void test2() {
        XmlPath xmlPath = RestAssured.given().accept(ContentType.XML)
                .auth().basic("admin", "admin")
                .get("/api/spartans")
                .then()
                .extract().xmlPath();

        // get second spartan name
        String secondName = xmlPath.getString("List.item[1].name");
        System.out.println("secondName = " + secondName);

        // get fifth phone number
        long phone = xmlPath.getLong("List.item[4].phone");
        System.out.println("phone = " + phone);

        // get all names
        List<Object> nameList = xmlPath.getList("List.item.name");
        System.out.println("nameList = " + nameList);

    }
}
