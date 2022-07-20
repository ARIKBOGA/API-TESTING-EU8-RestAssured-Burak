package com.cydeo.day7;

import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import com.cydeo.utilities.SpartanUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SpartanPostRequestDemo extends SpartanTestBase {

    private static Response response;
    Map<String, Object> queryMap;

    /*
 Given accept type and Content type is JSON
 And request json body is:
 {
   "gender":"Male",
   "name":"Severus",
   "phone":8877445596
}
 When user sends POST request to '/api/spartans'
 Then status code 201
 And content type should be application/json
 And json payload/response/body should contain:
 "A Spartan is Born!" message
 and same data what is posted
*/
    @Test
    public void postMethod1() {
        response = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(" {\n" +
                        "   \"gender\":\"Male\",\n" +
                        "   \"name\":\"Severus\",\n" +
                        "   \"phone\":8877445596\n" +
                        "}")
                .post("/api/spartans")
                .then()
                .statusCode(201)
                .extract().response();

        assertThat(response.contentType(), is("application/json"));
        assertThat(response.path("success"), is("A Spartan is Born!"));
        assertThat(response.path("data.name"), is(equalTo("Severus")));

        System.out.println(response.path("data.id").toString());
    }

    @Test
    public void postMethod2() {
        Spartan spartan = SpartanUtils.getNewSpartan();
        System.out.println(spartan);
        response = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(spartan)
                .post("/api/spartans")
                .then()
                .statusCode(201)
                .extract().response();

        assertThat(response.contentType(), is("application/json"));
        assertThat(response.path("success"), is("A Spartan is Born!"));

        System.out.println(response.path("data.id").toString());
    }

    @DisplayName("Post and get request to spartan API")
    @Test
    public void postMethod3() {

        Spartan spartanPost = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(SpartanUtils.getNewSpartan())
                .post("/api/spartans")
                .then()
                .statusCode(201)
                .extract().jsonPath().getObject("data", Spartan.class);

        Spartan spartanGet = given().accept(ContentType.JSON)
                .and().pathParam("id", spartanPost.getId())
                .when().get("/api/spartans/{id}")
                .then().statusCode(200).extract()
                .as(Spartan.class);

        assertThat(spartanPost.getId(), is(spartanGet.getId()));
        assertThat(spartanPost.getGender(), is(spartanGet.getGender()));
        assertThat(spartanPost.getPhone(), is(spartanGet.getPhone()));
        assertThat(spartanPost.getName(), is(spartanGet.getName()));

        System.out.println("spartanPost = " + spartanPost);
        System.out.println("spartanGet = " + spartanGet);
    }
}
