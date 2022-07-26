package com.cydeo.day9.bookit.step_definitions;

import com.cydeo.day9.bookit.pages.SelfPage;
import com.cydeo.day9.bookit.utilities.BookItApiUtil;
import com.cydeo.day9.bookit.utilities.ConfigurationReader;
import com.cydeo.day9.bookit.utilities.DBUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiStepDefs {

    private static Response response;
    private static String studentEmail;
    private static String studentPassword;
    private static String token;
    private static String emailGlobal;
    private static final String QUERY = "select * from users where id=1202;";
    private static JsonPath jsonPath;
    private static Map<String, Object> queryMap;

    @Given("I logged BookIt api using {string} and {string}")
    public void i_logged_book_it_api_using_and(String email, String password) {
        emailGlobal = email;
        token = BookItApiUtil.generateToken(email, password);
    }

    @When("I get the current user information from api")
    public void i_get_the_current_user_information_from_api() {
        response = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get(ConfigurationReader.get("base_url") + "/api/users/me")
                .then().extract().response();

    }

    @Then("status code should be {int}")
    public void status_code_should_be(int expectedSC) {
        Assert.assertEquals(expectedSC, response.statusCode());
    }

    @Then("the information about current user from api and database should match")
    public void theInformationAboutCurrentUserFromApiAndDatabaseShouldMatch() {

        // get data from DB
        queryMap = DBUtils.getRowMap(QUERY);

        // get data from API
        jsonPath = response.jsonPath();


        // compare DB vs API
        compareDBvsAPI();
    }

    @Then("UI,API and Database user information must be match")
    public void uiAPIAndDatabaseUserInformationMustBeMatch() {

        // get data from DB
        queryMap = DBUtils.getRowMap(QUERY);

        // get data from API
        jsonPath = response.jsonPath();

        // get data from UI
        SelfPage selfPage = new SelfPage();

        // compare UI vs DB
        Assert.assertEquals(selfPage.name.getText(), (queryMap.get("firstname") + " " + queryMap.get("lastname")));
        Assert.assertEquals(selfPage.role.getText(), queryMap.get("role"));

        // compare UI vs API
        Assert.assertEquals(selfPage.name.getText(), jsonPath.getString("firstName") + " " + jsonPath.getString("lastName"));
        Assert.assertEquals(selfPage.role.getText(), jsonPath.getString("role"));

        // compare DB vs API
        compareDBvsAPI();

    }

    @When("I send POST request to {string} endpoint with following information")
    public void i_send_POST_request_to_endpoint_with_following_information(String path, Map<String, String> studentInfo) {
        //why we prefer to get information as a map from feature file ?
        //bc we have queryParams method that takes map and pass to url as query key&value structure
        System.out.println("studentInfo = " + studentInfo);

        //assign email and password value to these variables so that we can use them later for deleting
        studentEmail = studentInfo.get("email");
        studentPassword = studentInfo.get("password");

        response = given().accept(ContentType.JSON)
                .queryParams(studentInfo)
                .and().header("Authorization", token)
                .log().all()
                .when()
                .post(ConfigurationReader.get("base_url") + path)
                .then().log().all().extract().response();



    }

    @Then("I delete previously added student")
    public void i_delete_previously_added_student() {
        BookItApiUtil.deleteStudent(studentEmail, studentPassword);
    }

    private void compareDBvsAPI() {
        Assert.assertEquals(emailGlobal, queryMap.get("email"));
        Assert.assertEquals(queryMap.get("id"), (long) jsonPath.getInt("id"));
        Assert.assertEquals(queryMap.get("firstname"), jsonPath.getString("firstName"));
        Assert.assertEquals(queryMap.get("lastname"), jsonPath.getString("lastName"));
        Assert.assertEquals(queryMap.get("role"), jsonPath.getString("role"));
    }

    @Given("I logged BookIt api as {string}")
    public void iLoggedBookItApiAs(String teacher) {


    }
}
