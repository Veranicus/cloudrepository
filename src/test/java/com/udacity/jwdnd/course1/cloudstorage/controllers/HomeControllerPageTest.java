package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomeControllerPageTest {

    @FindBy(css = "#logout-button")
    private WebElement logoutButton;

    @FindBy(css = "#note-title")
    private WebElement noteTitle;

    @FindBy(css = "#note-description")
    private WebElement noteDescription;

    @FindBy(css = "#nav-notes-tab")
    private WebElement noteTab;

    @FindBy(css = "#noteSubmit")
    private WebElement noteSubmit;

    @FindBy(css = "#noteTitle")
    private WebElement noteTitleSubmited;

    @FindBy(css = "#noteDescription")
    private WebElement noteDescriptionsubmited;

    @FindBy(css = "#addNewNote")
    private WebElement addNewNoteButton;


    public HomeControllerPageTest(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }


    public void saveNote(String noteTitle, String noteDescription) {
        noteTab.click();
//        try {
//            wait(50);
//        }catch (InterruptedException i){
//            i.printStackTrace();
//        }
        addNewNoteButton.sendKeys(Keys.ENTER);
        this.noteTitle.sendKeys(noteTitle);
        this.noteDescription.sendKeys(noteDescription);
        noteSubmit.click();
    }

    public void logout() {
        this.logoutButton.click();
    }

    public String getNoteTitle() {
        return noteTitleSubmited.getText();
    }

    public String getNoteDescription() {
        return noteDescriptionsubmited.getText();
    }
}
