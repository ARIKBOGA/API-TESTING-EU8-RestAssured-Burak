package com.cydeo.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@AllArgsConstructor
public class Search {
    private Spartan[] content;
    private short totalElement;
}