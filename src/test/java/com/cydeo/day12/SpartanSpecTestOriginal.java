package com.cydeo.day12;

import com.cydeo.utilities.SpartanNewBase;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class SpartanSpecTestOriginal extends SpartanNewBase {





    @Test
    public void test3(){


        given()
                .spec(userSpec)
                .queryParams("nameContains","j",
                        "gender","Female")
        .when()
                .get("/spartans/search")
        .then()
                .spec(responseSpec)
                .body("numberOfElements",is(6));

    }

}
