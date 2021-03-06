package com.cydeo.day1;

import com.cydeo.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class SimpleGetRequest {
    private static final String URL = "http://" + ConfigurationReader.getProperty("EC2_IP") + ":8000/api/spartans";

    @Test
    public void test1() {

        Response response = RestAssured.get(URL);

        System.out.println("response.statusCode() = " + response.statusCode());

        //System.out.println(response.prettyPrint());
        //response.as(Map.class);


        response.headers().asList().forEach(System.out::println);
    }
}
