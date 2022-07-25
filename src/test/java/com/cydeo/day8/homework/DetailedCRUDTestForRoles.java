package com.cydeo.day8.homework;

import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class DetailedCRUDTestForRoles extends SpartanAuthTestBase {

    private static final Map<String, String> credentials = new LinkedHashMap<>();
    private static final Map<String, Integer> expectedStatusCode = new HashMap<>();

    static {
        credentials.put("guest", "guest");
        credentials.put("editor", "editor");
        credentials.put("admin", "admin");
    }

    @DisplayName("Role test")
    @Test
    public void testTheStatusCodesForAllRole() {

        credentials.forEach((key, value) -> {

            setExpectedStatusCodes(key);

            // get request
            int getSC = given().auth()
                    .basic(key, value)
                    .when().get("/api/spartans")
                    .then()
                    .statusCode(expectedStatusCode.get("get"))
                    .extract().response().statusCode();
            System.out.println("GET req. for: " + key + " and the Status code is: " + getSC);

            // post request
            int postSC = given().accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .auth().basic(key, value)
                    .body("{\n" +
                            "  \"gender\": \"Male\",\n" +
                            "  \"name\": \"Selim\",\n" +
                            "  \"phone\": 1234567890\n" +
                            "}")
                    .post("/api/spartans")
                    .then()
                    .statusCode(expectedStatusCode.get("post"))
                    .extract().response().statusCode();
            System.out.println("POST req. for: " + key + " and the Status code is: " + postSC);

            // put request
            int putSC = given().accept(ContentType.JSON).contentType(ContentType.JSON)
                    .auth().basic(key, value)
                    .pathParam("id", 15)
                    .body("{\n" +
                            "  \"gender\": \"Male\",\n" +
                            "  \"name\": \"Burak\",\n" +
                            "  \"phone\": 1987654321\n" +
                            "}")
                    .put("/api/spartans/{id}")
                    .then().statusCode(expectedStatusCode.get("put"))
                    .extract().response().statusCode();
            System.out.println("PUT req. for: " + key + " and the Status code is: " + putSC);

            // patch request
            int patchSC = given().contentType(ContentType.JSON)
                    .auth().basic(key, value)
                    .pathParam("id", 25)
                    .body("{\"name\":\"Java\"}")
                    .patch("/api/spartans/{id}")
                    .then().statusCode(expectedStatusCode.get("patch"))
                    .extract().response().statusCode();
            System.out.println("PATCH req. for: " + key + " and the Status code is: " + patchSC);

            // delete request
            int deleteSC = given().contentType(ContentType.JSON)
                    .auth().basic(key, value)
                    .pathParam("id", 40) // the id number should be changed for every request because the admin role has deleted in previous req.
                    .delete("/api/spartans/{id}")
                    .then().statusCode(expectedStatusCode.get("delete"))
                    .extract().response().statusCode();
            System.out.println("DELETE req. for: " + key + " and the Status code is: " + deleteSC);

        });
    }

    private void setExpectedStatusCodes(String key) {
        switch (key) {
            case "admin":
                expectedStatusCode.put("get", 200);
                expectedStatusCode.put("post", 201);
                expectedStatusCode.put("put", 204);
                expectedStatusCode.put("patch", 204);
                expectedStatusCode.put("delete", 204);
                break;
            case "editor":
                expectedStatusCode.put("get", 200);
                expectedStatusCode.put("post", 201);
                expectedStatusCode.put("put", 204);
                expectedStatusCode.put("patch", 204);
                expectedStatusCode.put("delete", 403);
                break;
            case "guest":
                expectedStatusCode.put("get", 401);
                expectedStatusCode.put("post", 401);
                expectedStatusCode.put("put", 401);
                expectedStatusCode.put("patch", 401);
                expectedStatusCode.put("delete", 401);
                break;
        }
    }
}