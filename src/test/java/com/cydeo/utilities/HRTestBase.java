package com.cydeo.utilities;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class HRTestBase {

    @BeforeAll
    public static void init() {
        RestAssured.baseURI = "http://" + ConfigurationReader.getProperty("EC2_IP") + ":1000/ords/hr";
        DBUtils.createConnection();
    }

    @AfterAll
    public static void tearDown() {
        DBUtils.destroy();
    }
}
