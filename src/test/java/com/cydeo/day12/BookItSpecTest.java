package com.cydeo.day12;

import com.cydeo.utilities.BookItTestBase;
import com.cydeo.utilities.Role;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class BookItSpecTest extends BookItTestBase {

    private ValidatableResponse checkMeWithMyReqSpec(RequestSpecification spec, String firstName, String lastName) {
        return given()
                .spec(spec)
                .when()
                .get("/api/users/me")
                .then()
                .spec(getDynamicResSpec(200, ContentType.JSON))
                .spec(userCheck(firstName, lastName));
    }

    @Test
    public void test1() {

        // send a request to /api/users/me as a teacher
        // verify status code and contentType
        checkMeWithMyReqSpec(userReqSpec(Role.TEACHER), "Janette", "Baskett")
                .body("id", is(170)); // if we use ValidatableResponse as return type
        // then we can make additional query after a response spec

    }

    @Test
    public void test2() {

        // send a request to /api/users/me as a student-member
        // verify status code and contentType
        checkMeWithMyReqSpec(userReqSpec(Role.TEAM_MEMBER), "Marius", "Forker");

    }

    @Test
    public void test3() {

        // send a request to /api/users/me as a team leader
        // verify status code and contentType
        checkMeWithMyReqSpec(userReqSpec(Role.TEAM_LEADER), "Alastair", "Potteril");

    }
}
