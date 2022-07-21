package com.cydeo.day7.homework;

import com.cydeo.pojo.Student;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CybertekStudentPOJO {
    @BeforeAll
    public static void init() {
        baseURI = "http://api.cybertektraining.com";
    }

    @DisplayName("Get request to CybertekAPI to practice POJO")
    @Test
    public void getAllStudentsToList() {

        List<Student> students = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .get("/student/all")
                .then()
                .log().all()
                .statusCode(200)
                .extract().jsonPath()
                .getList("students", Student.class);

        System.out.println("students.size() = " + students.size());
        students.forEach(System.out::println);
    }


    @DisplayName("POST request to CybertekAPI/GET request with Post's ID")
    @Test
    public void postStudent() {

        Student posted = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(new Student())
                .post("/student/create")
                .then()
                .statusCode(200)
                .extract().as(Student.class);

        List<Student> gotten = given().accept(ContentType.JSON)
                .and().pathParam("id", posted.getStudentId())
                .get("/student/{id}")
                .then()
                .log().all()
                .extract().jsonPath().getList("students", Student.class);

        System.out.println("posted = " + posted);
        System.out.println("\n---------------------------\n");
        System.out.println("gotten = " + gotten);

        assertThat(posted.getStudentId(), is(gotten.get(0).getStudentId()));
        assertThat(posted.getBatch(), is(gotten.get(0).getBatch()));
        assertThat(posted.getLastName(), is(gotten.get(0).getLastName()));
        assertThat(posted.getMajor(), is(gotten.get(0).getMajor()));


    }
}