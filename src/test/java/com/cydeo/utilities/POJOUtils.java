package com.cydeo.utilities;

import com.github.javafaker.Faker;

import java.util.Random;

public abstract class POJOUtils {

    private static final Faker faker = new Faker();

    public static String getName() {
        return faker.name().firstName();
    }

    public static long getPhoneNumber() {
        return Long.parseLong(faker.numerify("5#########"));
    }

    public static String getGender() {
        return new Random().nextInt(2) == 0 ? "Male" : "Female";
    }

    public static String getEmail() {
        return faker.internet().emailAddress();
    }

    public static String getStreetAddress() {
        return faker.address().streetAddress();
    }

    public static String getCity() {
        return faker.address().city();
    }

    public static String getState() {
        return faker.address().state();
    }

    public static String getStreetName() {
        return faker.address().streetName();
    }

    public static String getZipCode() {
        return faker.numerify("4####");
    }

    public static String getCompanyName() {
        return faker.company().name();
    }

    public static String getDateAsString() {
        return faker.numerify("0#/0#/19##");
    }

    public static String getCompanyTitle() {
        return faker.company().profession();
    }

    public static String getAdmissionNo() {
        return faker.numerify("#####");
    }

    public static int getBatchNo() {
        return Integer.parseInt(faker.numerify("7#"));
    }

    public static String getLastName() {
        return faker.name().lastName();
    }

    public static String getMajor() {
        return faker.letterify("???");
    }

    public static String getPassword() {
        return faker.numerify("###");
    }

    public static String getSection() {
        return faker.numerify("##");
    }

    public static String getSubject() {
        return faker.aviation().aircraft();
    }
}
