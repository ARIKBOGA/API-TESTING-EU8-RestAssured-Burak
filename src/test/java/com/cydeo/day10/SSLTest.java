package com.cydeo.day10;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class SSLTest {
    @Test
    public void relaxedHTTPS() {

        given()
                .relaxedHTTPSValidation() // this method allows us to send a request to an API which is doesn't have an SSL cert.
                .get("https://untrusted-root.badssl.com")
                .then().log().all();

    }


    @Test
    public void keyStore() {

        given()
                .keyStore("pathtofile", "password")
                .when().get("apiurl");

    }


    // for postman preferences -> certificate tab
}

/*

    HTTP vs HTTPS
HTTPS--> secure connection,secure HTTP
Anytime you visit a website which is https, secured an ecnrypted connection will be setup between you and the server.
it means when you type card nummbers, data cannot be stolen on the way.
sometimes api also require certificates.

given().relaxedHTTPSValidation() --> is to trust the api without providing certificate
given().keyStore("pathtofile","password")
            .when().get("apiurl")

for postman --> preferences --> certificate tab

 */