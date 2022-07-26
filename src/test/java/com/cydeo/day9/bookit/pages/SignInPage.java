package com.cydeo.day9.bookit.pages;

import com.cydeo.day9.bookit.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignInPage {

    @FindBy(name = "email")
    public WebElement email;
    @FindBy(name = "password")
    public WebElement password;
    @FindBy(xpath = "//button[.='sign in']")
    public WebElement signInButton;

    public SignInPage() {
        PageFactory.initElements(Driver.get(), this);
    }

}
