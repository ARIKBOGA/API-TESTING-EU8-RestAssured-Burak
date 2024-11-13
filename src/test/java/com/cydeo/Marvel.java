package com.cydeo;

import com.cydeo.day9.bookit.utilities.Driver;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class Marvel {

    Set<String> properties = new HashSet<>(Set.of("id", "name", "description", "resourceURI", "thumbnail", "comics", "stories", "events", "urls"));

    String localeTime = LocalDateTime.now().toString();
    String apiPrivateKey = "353c44eaf28d0c0aa2dca51ebe6fdf945798f20f";
    String apiPublicKey = "c7eff628f75ad29dfe1b8140c0514ab4";

    String ending = "?ts=" + localeTime
            + "&apikey=" + apiPublicKey
            + "&hash=" + getHashValue(localeTime, apiPrivateKey, apiPublicKey);

    public String getHashValue(String @NotNull ... arr) {
        String str = "";
        for (String s : arr) {
            str += s;
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.reset();
        md.update(str.getBytes());

        String hash = new BigInteger(1, md.digest()).toString(16);
        while (hash.length() < 32) {
            hash = "0" + hash;
        }
        return hash;
    }

    @Test
    public void test1() {

        List<HashMap> list = given()
                .get("http://gateway.marvel.com/v1/public/characters" + ending)
                .then()
                .extract()
                .response()
                .jsonPath()
                .getList("data.results", HashMap.class);

        for (int i = 0; i < list.size(); i++) {
            //System.out.println(i+1 + list.get(i).keySet().toString());
            Assertions.assertTrue(list.get(i).keySet().containsAll(properties), "Error on the index: " + i);
        }
    }

    @Test
    void test2() {

        String num = given()
                .get("http://gateway.marvel.com/v1/public/characters/1010699" + ending)
                .then()
                //.log().all()
                .extract()
                .jsonPath().getString("data.results[0].comics.available");

        Driver.get().get("https://www.marvel.com/comics/characters/1010699/aaron_stack");

        String[] arr = Driver.get().findElement(By.xpath("//span[contains(text(),'Showing')]")).getText().split(" ");

        assertThat(num, is(equalTo(arr[3])));

        Driver.closeDriver();
    }
}