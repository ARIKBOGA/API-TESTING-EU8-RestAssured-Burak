package com.cydeo.pojo;

import com.cydeo.utilities.POJOUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(value = "id", allowSetters = true)
public class Spartan {
    private int id;
    private String name;
    private String gender;
    private long phone;

    public Spartan() {
        setName(POJOUtils.getName());
        setGender(POJOUtils.getGender());
        setPhone(POJOUtils.getPhoneNumber());
    }
}