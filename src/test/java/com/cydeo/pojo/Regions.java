package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Regions {
    private List<Region> items;
    //private boolean hasMore;
    //private int limit;
    //private int offset;
    private int count;
    private List<Link> links;
}
