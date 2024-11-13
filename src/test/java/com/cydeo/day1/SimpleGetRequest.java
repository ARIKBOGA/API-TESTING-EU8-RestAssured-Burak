package com.cydeo.day1;

import com.cydeo.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

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

    @Test
    public void test2() throws IOException, URISyntaxException {
        StringBuilder result = new StringBuilder();
        URL url = new URL("https://jsonmock.hackerrank.com/api/moviesdata/search/?Title=" + "harry");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        System.out.println(result);
        String[] split = result.toString().split(",");
        String str = split[2].substring(split[2].indexOf(":"));
        System.out.println(str);


    }
}
