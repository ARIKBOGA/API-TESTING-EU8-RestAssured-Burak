package com.cydeo.day10;

import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class FormulaOneXMLTest {

    @BeforeAll
    public static void setup() {
        baseURI = "http://ergast.com";
        basePath = "/api/f1";
    }

    @Test
    public void test1() {

        XmlPath xmlPath = given().accept(ContentType.XML)
                .pathParam("driver", "alonso")
                .get("/drivers/{driver}")
                .then().statusCode(200)
                .log().all()
                .extract().response().xmlPath();

        String givenName = xmlPath.getString("MRData.DriverTable.Driver.GivenName");
        String familyName = xmlPath.getString("MRData.DriverTable.Driver.FamilyName");
        String dateOfBirth = xmlPath.getString("MRData.DriverTable.Driver.DateOfBirth");
        String nationality = xmlPath.getString("MRData.DriverTable.Driver.Nationality");
        System.out.println("givenName = " + givenName);
        System.out.println("familyName = " + familyName);
        System.out.println("dateOfBirth = " + dateOfBirth);
        System.out.println("nationality = " + nationality);

        // get attributes  **** Use "@" sign ****
        String driverId = xmlPath.getString("MRData.DriverTable.Driver.@driverId");
        String code = xmlPath.getString("MRData.DriverTable.Driver.@code");
        String url = xmlPath.getString("MRData.DriverTable.Driver.@url");
        System.out.println("driverId = " + driverId);
        System.out.println("code = " + code);
        System.out.println("url = " + url);
    }
}
