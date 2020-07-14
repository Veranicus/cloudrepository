package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginControllerPageTest {

    @FindBy(css = "#inputUsername")
    private WebElement inputUsername;

    @FindBy(css = "#inputPassword")
    private WebElement inputPassword;

    @FindBy(css = "#submit-button")
    private WebElement submitButton;

    @FindBy(css = "#errorUser")
    private WebElement errorUserMessage;


    public LoginControllerPageTest(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public String getErrorUserMessageText() {
        return errorUserMessage.getText();
    }

    public void login(String username, String password) {
        this.inputUsername.sendKeys(username);
        this.inputPassword.sendKeys(password);
        this.submitButton.click();
    }


}