package com.cydeo.day9.bookit.pages;

import com.cydeo.day9.bookit.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MySelfPage extends TopNavigationBar {
    @FindBy(xpath = "(//*[@class='title is-6'])[3]")
    public WebElement teamName;

    public MySelfPage() {
        PageFactory.initElements(Driver.get(), this);
    }

}
