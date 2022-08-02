package com.cydeo.utilities;

import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;



public class BookItTestBase {

    protected static RequestSpecification teacherReqSpec;
    protected static RequestSpecification teamMemberReqSpec;
    protected static RequestSpecification teamLeaderReqSpec;
    protected static ResponseSpecification responseSpec;


    @BeforeAll
    public static void init() {
        baseURI = ConfigurationReader.getProperty("base_url");


        responseSpec = expect().
                statusCode(200)
                .contentType(ContentType.JSON).log().all();

    }

    @AfterAll
    public static void tearDown() {
        reset();
    }


    private static String getTokenByRole(Role role) {

        String email = "", password = "";
        switch (role) {
            case TEACHER:
                email = ConfigurationReader.getProperty("teacher_email");
                password = ConfigurationReader.getProperty("teacher_password");
                break;
            case TEAM_MEMBER:
                email = ConfigurationReader.getProperty("team_member_email");
                password = ConfigurationReader.getProperty("team_member_password");
                break;
            case TEAM_LEADER:
                email = ConfigurationReader.getProperty("team_leader_email");
                password = ConfigurationReader.getProperty("team_leader_password");
                break;
        }

        return given()
                .queryParams("email", email, "password", password)
                .get("/sign")
                .then()
                .extract().jsonPath().getString("accessToken");
    }

    public static ResponseSpecification getDynamicResSpec(int statusCode, ContentType contentType) {
        return expect()
                .statusCode(statusCode)
                .contentType(contentType)
                .logDetail(LogDetail.ALL);
    }

    public static ResponseSpecification userCheck(String firstName, String lastName) {
        return expect()
                .body("firstName", is(firstName))
                .body("lastName", is(lastName))
                .logDetail(LogDetail.ALL);
    }

    public static RequestSpecification userReqSpec(Role role) {
        return (given().accept(ContentType.JSON).header("Authorization", getTokenByRole(role)));
    }

}