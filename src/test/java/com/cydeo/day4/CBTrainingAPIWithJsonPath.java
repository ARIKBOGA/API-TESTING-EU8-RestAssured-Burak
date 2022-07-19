package com.cydeo.day4;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;

public class CBTrainingAPIWithJsonPath {

    @BeforeAll
    public static void init() {
        baseURI = "http://api.cybertektraining.com";
    }

    private static Response response;
    private static List<String> stringList;
    private static JsonPath jsonPath;

    @DisplayName("Get request to individual student")
    @Test
    public void test1() {

        // send a request to student id 32801 as a parameter
        //verify status code=200 /content type=application/json;charset=UTF-8 /Content-Encoding = gzip
        //verify Date header exists
        //assert that
            /*
                firstName Vera
                batch 14
                section 12
                emailAddress aaa@gmail.com
                companyName Cybertek
                state IL
                zipCode 60606
                using JsonPath
             */

        Map<String,Object> pathMap = new HashMap<>();
        pathMap.put("id",32801);

        response = RestAssured
                .given()
                .log().all()
                .pathParams(pathMap)
                .when()
                .get("/student/{id}");

        System.out.println("response.headers().get(\"Content-Encoding\") = " + response.headers().get("Content-Encoding"));

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("application/json;charset=UTF-8", response.contentType());
        Assertions.assertTrue( response.headers().get("Content-Encoding").toString().endsWith("gzip"));
        Assertions.assertTrue(response.headers().exist());

        Assertions.assertEquals("Vera", response.path("students[0].firstName"));
        Assertions.assertEquals("14", response.path("students[0].batch").toString());
        Assertions.assertEquals("12", response.path("students[0].section"));
        Assertions.assertEquals("aaa@gmail.com", response.path("students[0].contact.emailAddress"));
        Assertions.assertEquals("Cybertek", response.path("students[0].company.companyName"));
        Assertions.assertEquals("IL", response.path("students[0].company.address.state"));
        Assertions.assertEquals(60606, (Integer) response.path("students[0].company.address.zipCode"));

    }
}

/*
/Library/Java/JavaVirtualMachines/jdk-11.0.12.jdk/Contents/Home/bin/java -ea -Didea.test.cyclic.buffer.size=1048576 -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=49503:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/burakarikboga/.m2/repository/org/junit/platform/junit-platform-launcher/1.8.2/junit-platform-launcher-1.8.2.jar:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/junit/lib/junit5-rt.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/junit/lib/junit-rt.jar:/Users/burakarikboga/Desktop/SDET_EU8/6. API Testing/Projects/EU8-RestAssured-Burak/target/test-classes:/Users/burakarikboga/.m2/repository/org/junit/jupiter/junit-jupiter/5.8.2/junit-jupiter-5.8.2.jar:/Users/burakarikboga/.m2/repository/org/junit/jupiter/junit-jupiter-api/5.8.2/junit-jupiter-api-5.8.2.jar:/Users/burakarikboga/.m2/repository/org/opentest4j/opentest4j/1.2.0/opentest4j-1.2.0.jar:/Users/burakarikboga/.m2/repository/org/junit/platform/junit-platform-commons/1.8.2/junit-platform-commons-1.8.2.jar:/Users/burakarikboga/.m2/repository/org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2.jar:/Users/burakarikboga/.m2/repository/org/junit/jupiter/junit-jupiter-params/5.8.2/junit-jupiter-params-5.8.2.jar:/Users/burakarikboga/.m2/repository/org/junit/jupiter/junit-jupiter-engine/5.8.2/junit-jupiter-engine-5.8.2.jar:/Users/burakarikboga/.m2/repository/org/junit/platform/junit-platform-engine/1.8.2/junit-platform-engine-1.8.2.jar:/Users/burakarikboga/.m2/repository/io/rest-assured/rest-assured/5.1.1/rest-assured-5.1.1.jar:/Users/burakarikboga/.m2/repository/org/apache/groovy/groovy/4.0.1/groovy-4.0.1.jar:/Users/burakarikboga/.m2/repository/org/apache/groovy/groovy-xml/4.0.1/groovy-xml-4.0.1.jar:/Users/burakarikboga/.m2/repository/org/apache/httpcomponents/httpclient/4.5.13/httpclient-4.5.13.jar:/Users/burakarikboga/.m2/repository/org/apache/httpcomponents/httpcore/4.4.13/httpcore-4.4.13.jar:/Users/burakarikboga/.m2/repository/commons-logging/commons-logging/1.2/commons-logging-1.2.jar:/Users/burakarikboga/.m2/repository/commons-codec/commons-codec/1.11/commons-codec-1.11.jar:/Users/burakarikboga/.m2/repository/org/apache/httpcomponents/httpmime/4.5.13/httpmime-4.5.13.jar:/Users/burakarikboga/.m2/repository/org/hamcrest/hamcrest/2.1/hamcrest-2.1.jar:/Users/burakarikboga/.m2/repository/org/ccil/cowan/tagsoup/tagsoup/1.2.1/tagsoup-1.2.1.jar:/Users/burakarikboga/.m2/repository/io/rest-assured/json-path/5.1.1/json-path-5.1.1.jar:/Users/burakarikboga/.m2/repository/org/apache/groovy/groovy-json/4.0.1/groovy-json-4.0.1.jar:/Users/burakarikboga/.m2/repository/io/rest-assured/rest-assured-common/5.1.1/rest-assured-common-5.1.1.jar:/Users/burakarikboga/.m2/repository/io/rest-assured/xml-path/5.1.1/xml-path-5.1.1.jar:/Users/burakarikboga/.m2/repository/org/apache/commons/commons-lang3/3.11/commons-lang3-3.11.jar:/Users/burakarikboga/.m2/repository/com/oracle/database/jdbc/ojdbc11/21.5.0.0/ojdbc11-21.5.0.0.jar com.intellij.rt.junit.JUnitStarter -ideVersion5 -junit5 com.cydeo.day4.CBTrainingAPIWithJsonPath,test1
{
    "students": [
        {
            "studentId": 32801,
            "firstName": "Vera",
            "lastName": "Jakson",
            "batch": 14,
            "joinDate": "02/02/2020",
            "birthDate": "02/02/1985",
            "password": "123",
            "subject": "string",
            "gender": "F",
            "admissionNo": "string",
            "major": "abc",
            "section": "12",
            "contact": {
                "contactId": 32721,
                "phone": "7738557985",
                "emailAddress": "aaa@gmail.com",
                "premanentAddress": "123 main str"
            },
            "company": {
                "companyId": 32721,
                "companyName": "Cybertek",
                "title": "string",
                "startDate": "string",
                "address": {
                    "addressId": 32641,
                    "street": " 100 S Clark str",
                    "city": "Chicago",
                    "state": "IL",
                    "zipCode": 60606
                }
            }
        }
    ]
}

Process finished with exit code 0

 */