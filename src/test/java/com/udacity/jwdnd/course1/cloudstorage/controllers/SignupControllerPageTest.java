package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupControllerPageTest {

    @FindBy(css = "#inputUsername")
    private WebElement inputUsername;

    @FindBy(css = "#inputPassword")
    private WebElement inputPassword;

    @FindBy(css = "#inputFirstName")
    private WebElement inputFirstName;

    @FindBy(css = "#inputLastName")
    private WebElement inputLastName;

    @FindBy(css = "#submit-button")
    private WebElement submitButton;

    @FindBy(css = "#signupError")
    private WebElement errorUserMessage;




    public SignupControllerPageTest(WebDriver webDriver) {
        PageFactory.initElements(webDriver,this);
    }

    public void signup(String username, String password, String firstName, String lastName){
        this.inputUsername.sendKeys(username);
        this.inputPassword.sendKeys(password);
        this.inputFirstName.sendKeys(firstName);
        this.inputLastName.sendKeys(lastName);
        this.submitButton.click();
    }

}
