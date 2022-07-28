package com.cydeo.day10;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class SOAPTest {

    @Test
    public void test1() {

        System.out.println("numberToWords(3403) = " + numberToWords(3403));
        System.out.println("numberToWords(5367408) = " + numberToWords(5367408));

    }

    public static String numberToWords(long number) {

        return given().accept(ContentType.XML)
                .contentType("text/xml; charset=utf-8")
                .body("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                        "  <soap:Body>\n" +
                        "    <NumberToWords xmlns=\"http://www.dataaccess.com/webservicesserver/\">\n" +
                        "      <ubiNum>" + number + "</ubiNum>\n" +
                        "    </NumberToWords>\n" +
                        "  </soap:Body>\n" +
                        "</soap:Envelope>")
                .post("https://www.dataaccess.com/webservicesserver/NumberConversion.wso")
                .then()
                //.log().all()
                .extract().xmlPath().getString("soap:Envelope@xmlns:soap");
    }
}