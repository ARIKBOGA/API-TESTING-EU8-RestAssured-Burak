package com.cydeo.pojo;

import java.util.Arrays;

public class Search {
    private Spartan[] content;
    private short totalElement;

    public Spartan[] getContent() {
        return content;
    }

    public void setContent(Spartan[] content) {
        this.content = content;
    }

    public short getTotalElement() {
        return totalElement;
    }

    public void setTotalElement(short totalElement) {
        this.totalElement = totalElement;
    }

    @Override
    public String toString() {
        return "Search{" +
                "content=" + Arrays.toString(content) +
                ", totalElement=" + totalElement +
                '}';
    }
}
