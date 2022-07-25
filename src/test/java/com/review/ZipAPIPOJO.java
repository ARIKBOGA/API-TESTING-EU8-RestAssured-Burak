package com.review;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class ZipAPIPOJO {
}
 class Place{
    @JsonProperty("place name")
    public String placeName;
    public String longitude;
    public String state;
    @JsonProperty("state abbreviation")
    public String stateAbbreviation;
    public String latitude;
}

 class Root{
    @JsonProperty("post code")
    public String postCode;
    public String country;
    @JsonProperty("country abbreviation")
    public String countryAbbreviation;
    public ArrayList<Place> places;
}


