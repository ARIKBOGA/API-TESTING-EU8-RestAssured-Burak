package com.cydeo.day9.bookit.pages;

import com.cydeo.day9.bookit.utilities.BrowserUtils;
import com.cydeo.day9.bookit.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class TopNavigationBar {
    @FindBy(linkText = "map")
    public WebElement map;
    @FindBy(linkText = "schedule")
    public WebElement schedule;
    @FindBy(linkText = "hunt")
    public WebElement hunt;
    @FindBy(xpath = "(//a[contains(text(),'my')])[2]/..")
    public WebElement my;
    @FindBy(linkText = "self")
    public WebElement self;
    @FindBy(linkText = "team")
    public WebElement team;
    @FindBy(linkText = "sign out")
    public WebElement signOut;

    public TopNavigationBar() {
        PageFactory.initElements(Driver.get(), this);
    }

    public void goToSelf() {
        Actions actions = new Actions(Driver.get());
        actions.moveToElement(my).perform();
        BrowserUtils.waitForVisibility(self, 10);
        self.click();
    }

    public void goToTeam() {
        Actions actions = new Actions(Driver.get());
        actions.moveToElement(my).perform();
        team.click();
    }

    public void signOut() {
        Actions actions = new Actions(Driver.get());
        actions.moveToElement(my).perform();
        signOut.click();
    }
}
