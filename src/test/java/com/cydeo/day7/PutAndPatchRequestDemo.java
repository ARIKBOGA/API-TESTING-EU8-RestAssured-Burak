package com.cydeo.day7;

import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PutAndPatchRequestDemo extends SpartanTestBase {

    private static final Map<String, Object> putMap = new HashMap<>();

    @DisplayName("PUT request to one spartan for update with Map")
    @Test
    public void PUTRequest() {

        putMap.clear();
        putMap.put("name", "IntelliJ");
        putMap.put("gender", "Male");
        putMap.put("phone", 1234567890L);

        given().contentType(ContentType.JSON)
                .and().body(putMap)
                .and().pathParam("id", 139)
                .when().put("/api/spartans/{id}")
                .then().statusCode(204);

        Spartan spartan = get("/api/spartans/139").as(Spartan.class);
        System.out.println("spartan = " + spartan);

    }


    @DisplayName("PATCH request to one spartan for partial update with Map")
    @Test
    public void PATCHRequest() {

        putMap.clear();
        putMap.put("name", "PATCHED");


        given().contentType(ContentType.JSON)
                .and().body(putMap)
                .and().pathParam("id", 122)
                .when().patch("/api/spartans/{id}")
                .then().statusCode(204);

        Spartan spartan = get("/api/spartans/122").as(Spartan.class);
        System.out.println("spartan = " + spartan);

        assertThat("PATCHED", is(spartan.getName()));

    }

    @DisplayName("DELETE request to one spartan")
    @Test
    public void DELETERequest() {

        given().pathParam("id", 122)
                .when().delete("/api/spartans/{id}")
                .then().statusCode(204);

        get("/api/spartans/122")
                .then().statusCode(404);


    }
}
