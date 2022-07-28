package com.cydeo.day10;

import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ResponseTimeTest extends SpartanAuthTestBase {

    @Test
    public void lessThan() {

        Response response = given().auth().basic("admin", "admin")
                .accept(ContentType.JSON)
                .get("/api/spartans")
                .then()
                .time(lessThanOrEqualTo(1000L))
                .extract().response();

        System.out.println("response.getTime() = " + response.getTime());
    }


    @Test
    public void bothGreaterAndLess() {

        Response response = given().auth().basic("admin", "admin")
                .accept(ContentType.JSON)
                .get("/api/spartans")
                .then()
                .time(both(greaterThan(500L)).and(lessThanOrEqualTo(2000L)))
                .extract().response();

        System.out.println("response.getTime() = " + response.getTime());
    }

}

/*
    How to verify response time with Restassured library ?

once we sent the request after then(). method there is time(Matchers) method which takes the response time and based on the matcher we provide it will do assertion.
we can use greaterThan(), lessThan(), or both at the same time.
	  given()
      .auth().basic("admin", "admin")
                .accept(ContentType.JSON)
                .when()
                .get("/api/spartans")
                .then()
                .time(both(greaterThan(500L)).and(lessThanOrEqualTo(1100L)))
                .extract().response();
 */