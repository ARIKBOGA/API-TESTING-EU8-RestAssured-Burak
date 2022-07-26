package com.cydeo.day9.homework;

import com.cydeo.day9.bookit.pages.SelfPage;
import com.cydeo.day9.bookit.pages.SignInPage;
import com.cydeo.day9.bookit.utilities.BrowserUtils;
import com.cydeo.day9.bookit.utilities.DBUtils;
import com.cydeo.day9.bookit.utilities.Driver;
import com.cydeo.day9.bookit.utilities.Environment;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Homework {

    private static final String QUERY = "select firstname,\n" +
            "       lastname,\n" +
            "       role,\n" +
            "       batch_number,\n" +
            "       users.campus_id,\n" +
            "       t.name as team_name,\n" +
            "       c.location,\n" +
            "       users.id\n" +
            "from users\n" +
            "         join campus c on c.id = users.campus_id\n" +
            "         join team t on c.id = t.campus_id\n" +
            "         join batch b on b.number = t.batch_number\n" +
            "where email = 'teacherilsamnickel@gmail.com';";

    @BeforeAll
    public static void init() {
        DBUtils.createConnection();
        baseURI = "https://cybertek-reservation-api-qa2.herokuapp.com";
    }

    @AfterAll
    public static void teardown() {
        DBUtils.destroyConnection();
        Driver.closeDriver();
    }

    @Test
    public void test() {

        // create a token for the other requests to API
        String token = given().contentType(ContentType.JSON)
                .queryParam("email", "teacherilsamnickel@gmail.com")
                .queryParam("password", "samnickel")
                .get("/sign")
                .then().statusCode(200)
                .extract().response().jsonPath().getString("accessToken");
        System.out.println("token = " + token);

        // get name and role of the user
        JsonPath userInfoPath = given().contentType(ContentType.JSON)
                .header("Authorization", token)
                .get("/api/students/me")
                .then().statusCode(200)
                .extract().response().jsonPath();

        String fullName = userInfoPath.getString("firstName") + " " + userInfoPath.getString("lastName");
        String role = userInfoPath.getString("role");
        int userID = userInfoPath.getInt("id");

        System.out.println("fullName = " + fullName);
        System.out.println("role = " + role);
        System.out.println("userID = " + userID);


        // get batch name of mine
        JsonPath batchInfoPath = given().contentType(ContentType.JSON)
                .header("Authorization", token)
                .get("/api/batches/my")
                .then().statusCode(200)
                .extract().response().jsonPath();

        int batchNumber = batchInfoPath.getInt("number");
        String fullNameInBatch = batchInfoPath.getString("teams[9].members[2].firstName") + " " + batchInfoPath.getString("teams[9].members[2].lastName");
        String teamNameInBatch = batchInfoPath.getString("teams[9].name");

        System.out.println("batchNumber = " + batchNumber);
        System.out.println("fullNameInBatch = " + fullNameInBatch);
        System.out.println("teamNameInBatch = " + teamNameInBatch);


        // get campus info of mine
        JsonPath campusInfoPath = given().contentType(ContentType.JSON)
                .header("Authorization", token)
                .get("/api/campuses/my")
                .then().statusCode(200)
                .extract().response().jsonPath();

        String campusLocation = campusInfoPath.getString("location");
        int campusID = campusInfoPath.getInt("id");
        System.out.println("campusLocation = " + campusLocation);
        System.out.println("campusID = " + campusID);

        // get teamName of mine
        JsonPath teamInfoPath = given().contentType(ContentType.JSON)
                .header("Authorization", token)
                .get("/api/teams/my")
                .then().statusCode(200)
                .extract().response().jsonPath();

        String teamName = teamInfoPath.getString("name");
        System.out.println("teamName = " + teamName);


        // Get related data from DB
        String dbFullName = "", dbRole = "", dbCampusLocation = "", dbTeamName = "";
        int dbBatchNumber = 0, dbCampusId = 0;
        long dbUserID = 0L;
        List<Map<String, Object>> resultList = DBUtils.getQueryResultMap(QUERY);
        for (Map<String, Object> row : resultList) {
            if (row.get("team_name").equals(teamNameInBatch)) {
                dbFullName = row.get("firstname") + " " + row.get("lastname");
                dbRole = (String) row.get("role");
                dbBatchNumber = (int) row.get("batch_number");
                dbCampusId = (int) row.get("campus_id");
                dbCampusLocation = (String) row.get("location");
                dbTeamName = (String) row.get("team_name");
                dbUserID = (long) row.get("id");
            }
        }

        System.out.println("dbUserID = " + dbUserID);
        System.out.println("dbFullName = " + dbFullName);
        System.out.println("dbRole = " + dbRole);
        System.out.println("dbBatchNumber = " + dbBatchNumber);
        System.out.println("dbCampusId = " + dbCampusId);
        System.out.println("dbCampusLocation = " + dbCampusLocation);
        System.out.println("dbTeamName = " + dbTeamName);


        // GET data from my SELF page on UI side
        Driver.get().get(Environment.URL);
        Driver.get().manage().window().maximize();
        SignInPage signInPage = new SignInPage();
        signInPage.email.sendKeys("teacherilsamnickel@gmail.com");
        signInPage.password.sendKeys("samnickel");
        signInPage.signInButton.click();

        BrowserUtils.waitFor(3);
        SelfPage selfPage = new SelfPage();
        selfPage.goToSelf();


        BrowserUtils.waitFor(3);
        String UI_fullName = selfPage.name.getText();
        String UI_role = selfPage.role.getText();
        String UI_batch = selfPage.batch.getText();
        String UI_campus = selfPage.campus.getText();
        String UI_teamName = selfPage.team.getAttribute("innerText");


        System.out.println("UI_fullName = " + UI_fullName);
        System.out.println("UI_role = " + UI_role);
        System.out.println("UI_batch = " + UI_batch);
        System.out.println("UI_campus = " + UI_campus);
        System.out.println("UI_teamName = " + UI_teamName);


        // assertions API vs UI
        assertEquals(fullName, UI_fullName);
        assertEquals(role, UI_role);
        assertEquals(campusLocation, UI_batch);
        assertEquals(teamName, UI_teamName);

        // assertions DB vs API
        assertEquals(dbUserID, userID);
        assertEquals(dbBatchNumber, batchNumber);
        assertEquals(dbFullName, fullNameInBatch);
        assertEquals(dbRole, role);
        assertEquals(dbCampusId, campusID);
        assertEquals(dbTeamName, teamNameInBatch);


        // assertions UI vs DB
        assertEquals(UI_fullName, dbFullName);
        assertEquals(UI_role, dbRole);
        assertEquals(UI_batch, dbCampusLocation);
        assertEquals(UI_teamName, dbTeamName); // there is bug I think

    }

}
// try to get name,role,batch number, campus, team name from api for one student
// it will be multiple api request
// responses returns batch name team name with students information
// first make sure your student is inside the respones then get those info

// prepare one list of information about student and compare with ui

//UI vs API


//verify same information vs
//connect db and get the same information which requires joins multiple tables
