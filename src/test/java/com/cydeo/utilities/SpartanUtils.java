package com.cydeo.utilities;

import com.cydeo.pojo.Spartan;
import com.github.javafaker.Faker;

import java.util.Random;

public abstract class SpartanUtils {

    private static final Faker faker = new Faker();

    public static Spartan getNewSpartan() {

        Spartan spartan = new Spartan();

        spartan.setName(getName());
        spartan.setGender(getGender());
        spartan.setPhone(getPhoneNumber());

        return spartan;
    }

    private static String getName() {
        return faker.gameOfThrones().character();
    }

    private static long getPhoneNumber(){
        String phoneNumber = faker.phoneNumber().cellPhone()
                .replace("-", "")
                .replace(".", "")
                .replace(" ", "")
                .replace("(", "")
                .replace(")", "");
        if (phoneNumber.startsWith("0"))
            phoneNumber = new Random().nextInt(10) + phoneNumber.substring(1);
        return Long.parseLong(phoneNumber);
    }

    private static String getGender() {
        return new Random().nextInt(2) == 0 ? "Male" : "Female";
    }
}