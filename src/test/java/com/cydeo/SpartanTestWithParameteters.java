package com.cydeo;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class SpartanTestWithParameteters {

    @BeforeAll
    public static void init() {
        RestAssured.baseURI = "http://34.238.126.28:8000";
    }
}
