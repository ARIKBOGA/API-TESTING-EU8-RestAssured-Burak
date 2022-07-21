package com.cydeo.pojo;

import com.cydeo.utilities.POJOUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(value = "studentId", allowSetters = true)
public class Student {
    private int studentId;
    private String admissionNo;
    private int batch;
    private String birthDate;
    private Company company;
    private Contact contact;
    private String firstName;
    private String gender;
    private String joinDate;
    private String lastName;
    private String major;
    private String password;
    private String section;
    private String subject;

    public Student() {
        setAdmissionNo(POJOUtils.getAdmissionNo());
        setBatch(POJOUtils.getBatchNo());
        setBirthDate(POJOUtils.getDateAsString());
        setCompany(new Company());
        setContact(new Contact());
        setFirstName(POJOUtils.getName());
        setGender(POJOUtils.getGender());
        setJoinDate(POJOUtils.getDateAsString());
        setLastName(POJOUtils.getLastName());
        setMajor(POJOUtils.getMajor());
        setPassword(POJOUtils.getPassword());
        setSection(POJOUtils.getSection());
        setSubject(POJOUtils.getSubject());
    }
}

@Getter
@Setter
@ToString
@JsonIgnoreProperties(value = "companyId", allowSetters = true)
class Company {
    private int companyId;
    private String companyName;
    private String startDate;
    private String title;
    private Address address;

    public Company() {
        setAddress(new Address());
        setCompanyName(POJOUtils.getCompanyName());
        setStartDate(POJOUtils.getDateAsString());
        setTitle(POJOUtils.getCompanyTitle());
    }
}

@Getter
@Setter
@ToString
@JsonIgnoreProperties(value = "addressId", allowSetters = true)
class Address {
    private int addressId;
    private String city;
    private String state;
    private String street;
    private int zipCode;

    public Address() {
        setCity(POJOUtils.getCity());
        setState(POJOUtils.getState());
        setStreet(POJOUtils.getStreetName());
        setZipCode(Integer.parseInt(POJOUtils.getZipCode()));
    }
}

@Getter
@Setter
@ToString
@JsonIgnoreProperties(value = "contactId", allowSetters = true)
class Contact {
    private int contactId;
    private String emailAddress;
    private String phone;
    private String premanentAddress;

    public Contact() {
        setEmailAddress(POJOUtils.getEmail());
        setPhone(String.valueOf(POJOUtils.getPhoneNumber()));
        setPremanentAddress(POJOUtils.getStreetAddress());
    }
}