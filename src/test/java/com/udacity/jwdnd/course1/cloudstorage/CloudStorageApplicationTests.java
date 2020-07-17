package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.controllers.HomeControllerPageTest;
import com.udacity.jwdnd.course1.cloudstorage.controllers.LoginControllerPageTest;
import com.udacity.jwdnd.course1.cloudstorage.controllers.SignupControllerPageTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

//I'm using specific port just for testing editing credentials. I receive password after clicking on edit
//With ajax call to localhost:8080.  If I test with random port, this test always fails.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
class CloudStorageApplicationTests {


    private int port = 8080;

    public static WebDriver driver;

    public String baseURL;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        baseURL = "http://localhost:" + port;
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @AfterAll
    static void afterAll() {
        driver.quit();
        driver = null;
    }

    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void getSignupPage() {
        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());
    }

    @Test
    void testUnauthorizedUserAccessRestrictionsHome() {
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertNotEquals("Home", driver.getTitle());
        Assertions.assertEquals("Login", driver.getTitle());

    }

    @Test
    void testUnauthorizedUserAccessRestrictionsResult() {
        driver.get("http://localhost:" + this.port + "/result");
        Assertions.assertNotEquals("Result", driver.getTitle());
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    void testLoginAndAccessHomePageAndUnauthorizedAccessHomePage() {
        String username = "test";
        String password = "test";
        String firstName = "test";
        String lastName = "test";

        driver.get("http://localhost:" + this.port + "/signup");

        SignupControllerPageTest signupControllerPageTest = new SignupControllerPageTest(driver);
        signupControllerPageTest.signup(username, password, firstName, lastName);

        driver.get("http://localhost:" + this.port + "/login");
        LoginControllerPageTest loginControllerPageTest = new LoginControllerPageTest(driver);
        loginControllerPageTest.login(username, password);

        Assertions.assertEquals("Home", driver.getTitle());

        HomeControllerPageTest homeControllerPageTest = new HomeControllerPageTest(driver);

        homeControllerPageTest.logout();

        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertNotEquals("Home", driver.getTitle());
        Assertions.assertEquals("Login", driver.getTitle());


    }

    @Test
    void testNoteCreation() {

        String username = "test";
        String password = "test";
        String firstName = "test";
        String lastName = "test";

        String noteTitle = "test";
        String noteDescription = "test";

        driver.get("http://localhost:" + this.port + "/signup");

        SignupControllerPageTest signupControllerPageTest = new SignupControllerPageTest(driver);
        signupControllerPageTest.signup(username, password, firstName, lastName);

        driver.get("http://localhost:" + this.port + "/login");
        LoginControllerPageTest loginControllerPageTest = new LoginControllerPageTest(driver);
        loginControllerPageTest.login(username, password);

        driver.get("http://localhost:" + this.port + "/home");

        driver.findElement(By.id("nav-notes-tab")).click();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("addNewNote"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description"))).sendKeys(noteDescription);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("noteSaveSubmit"))).click();

        driver.get("http://localhost:" + this.port + "/home");

        driver.findElement(By.id("nav-notes-tab")).click();
        HomeControllerPageTest homeControllerPageTest = new HomeControllerPageTest(driver);

        String resultNoteTitle = wait.until(ExpectedConditions.elementToBeClickable(By.id("noteTitleAfterSubmit"))).getText();
        String resultNoteDescription = wait.until(ExpectedConditions.elementToBeClickable(By.id("noteDescriptionAfterSubmit"))).getText();

        Assertions.assertEquals(noteTitle, resultNoteTitle);
        Assertions.assertEquals(noteDescription, resultNoteDescription);
//        driver.navigate().to(HOME_URL);
//        webDriver.findElement(By.id("nav-notes-tab")).click();
    }

    @Test
    void testNoteEditing() {
        String username = "test";
        String password = "test";
        String firstName = "test";
        String lastName = "test";

        String noteTitle = "test";
        String noteDescription = "test";

        driver.get("http://localhost:" + this.port + "/signup");

        SignupControllerPageTest signupControllerPageTest = new SignupControllerPageTest(driver);
        signupControllerPageTest.signup(username, password, firstName, lastName);

        driver.get("http://localhost:" + this.port + "/login");
        LoginControllerPageTest loginControllerPageTest = new LoginControllerPageTest(driver);
        loginControllerPageTest.login(username, password);

        driver.get("http://localhost:" + this.port + "/home");

        driver.findElement(By.id("nav-notes-tab")).click();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("addNewNote"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description"))).sendKeys(noteDescription);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("noteSaveSubmit"))).click();

        driver.get("http://localhost:" + this.port + "/home");

        driver.findElement(By.id("nav-notes-tab")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("editNoteButton"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("editNote-title")))
                .clear();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("editNote-title")))
                .sendKeys("testTitle2");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("editNote-description")))
                .clear();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("editNote-description")))
                .sendKeys("testDescription2");

        wait.until(ExpectedConditions.elementToBeClickable(By.id("editNoteSaveButton"))).click();

        driver.get("http://localhost:" + this.port + "/home");

        driver.findElement(By.id("nav-notes-tab")).click();

        String resultNoteTitle =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("noteTitleAfterSubmit"))).getText();
        String resultNoteDescription =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("noteDescriptionAfterSubmit"))).getText();

        Assertions.assertEquals("testTitle2", resultNoteTitle);
        Assertions.assertEquals("testDescription2", resultNoteDescription);


    }

    @Test
    void testNoteDeletion() {
        String username = "test";
        String password = "test";
        String firstName = "test";
        String lastName = "test";

        String noteTitle = "test";
        String noteDescription = "test";

        driver.get("http://localhost:" + this.port + "/signup");

        SignupControllerPageTest signupControllerPageTest = new SignupControllerPageTest(driver);
        signupControllerPageTest.signup(username, password, firstName, lastName);

        driver.get("http://localhost:" + this.port + "/login");
        LoginControllerPageTest loginControllerPageTest = new LoginControllerPageTest(driver);
        loginControllerPageTest.login(username, password);

        driver.get("http://localhost:" + this.port + "/home");

        driver.findElement(By.id("nav-notes-tab")).click();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("addNewNote"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description"))).sendKeys(noteDescription);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("noteSaveSubmit"))).click();

        driver.get("http://localhost:" + this.port + "/home");

        driver.findElement(By.id("nav-notes-tab")).click();

        driver.get("http://localhost:" + this.port + "/home");

        driver.findElement(By.id("nav-notes-tab")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("deleteNoteButton"))).click();

        driver.get("http://localhost:" + this.port + "/home");

        driver.findElement(By.id("nav-notes-tab")).click();

        HomeControllerPageTest homeControllerPageTest = new HomeControllerPageTest(driver);

        Assertions.assertThrows(NoSuchElementException.class, homeControllerPageTest::getNoteDescription);
        Assertions.assertThrows(NoSuchElementException.class, homeControllerPageTest::getNoteTitle);

    }

    @Test
    void testCredentialCreation() {

        String username = "test";
        String password = "test";
        String firstName = "test";
        String lastName = "test";

        String credentialURL = "testCredentialUrl";
        String credentialUsername = "testCredentialUsername";
        String credentialPassword = "testCredentialPassword";

        driver.get("http://localhost:" + this.port + "/signup");

        SignupControllerPageTest signupControllerPageTest = new SignupControllerPageTest(driver);
        signupControllerPageTest.signup(username, password, firstName, lastName);

        driver.get("http://localhost:" + this.port + "/login");
        LoginControllerPageTest loginControllerPageTest = new LoginControllerPageTest(driver);
        loginControllerPageTest.login(username, password);

        driver.get("http://localhost:" + this.port + "/home");

        driver.findElement(By.id("nav-credentials-tab")).click();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("newCredentialButton"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(credentialURL);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username"))).sendKeys(credentialUsername);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password"))).sendKeys(credentialPassword);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("saveCredentialButton"))).click();

        driver.get("http://localhost:" + this.port + "/home");

        driver.findElement(By.id("nav-credentials-tab")).click();
        HomeControllerPageTest homeControllerPageTest = new HomeControllerPageTest(driver);

        String resultCredentialUrl = wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialUrlAfterSubmit"))).getText();
        String resultCredentialUsername = wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialUsernameAfterSubmit"))).getText();
        String resultCredentialPassword =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialPasswordAfterSubmit"))).getText();

        Assertions.assertEquals(credentialURL, resultCredentialUrl);
        Assertions.assertEquals(credentialUsername, resultCredentialUsername);
        Assertions.assertNotEquals(credentialPassword, resultCredentialPassword);
    }


    @Test
    void testCredentialEditing() {

        String username = "test";
        String password = "test";
        String firstName = "test";
        String lastName = "test";

        String credentialURL = "testCredentialUrl";
        String credentialUsername = "testCredentialUsername";
        String credentialPassword = "testCredentialPassword";

        String credentialURL2 = "testCredentialUrl2";
        String credentialUsername2 = "testCredentialUsername2";
        String credentialPassword2 = "testCredentialPassword2";

        driver.get("http://localhost:" + this.port + "/signup");

        SignupControllerPageTest signupControllerPageTest = new SignupControllerPageTest(driver);
        signupControllerPageTest.signup(username, password, firstName, lastName);

        driver.get("http://localhost:" + this.port + "/login");
        LoginControllerPageTest loginControllerPageTest = new LoginControllerPageTest(driver);
        loginControllerPageTest.login(username, password);

        driver.get("http://localhost:" + this.port + "/home");

        driver.findElement(By.id("nav-credentials-tab")).click();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("newCredentialButton"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(credentialURL);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username"))).sendKeys(credentialUsername);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password"))).sendKeys(credentialPassword);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("saveCredentialButton"))).click();

        driver.get("http://localhost:" + this.port + "/home");
        driver.findElement(By.id("nav-credentials-tab")).click();

        String resultCredentialUrl =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialUrlAfterSubmit"))).getText();
        String resultCredentialUsername =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialUsernameAfterSubmit"))).getText();
        String resultCredentialPassword =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialPasswordAfterSubmit"))).getText();

        Assertions.assertEquals(credentialURL, resultCredentialUrl);
        Assertions.assertEquals(credentialUsername, resultCredentialUsername);
        Assertions.assertNotEquals(credentialPassword, resultCredentialPassword);

        driver.get("http://localhost:" + this.port + "/home");

        driver.findElement(By.id("nav-credentials-tab")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("editCredentialButton"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("editCredential-url"))).clear();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("editCredential-url"))).sendKeys(credentialURL2);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("editCredential-username"))).clear();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("editCredential-username"))).sendKeys(credentialUsername2);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("editCredential-password"))).clear();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("editCredential-password"))).sendKeys(credentialPassword2);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("editCredentialSaveButton"))).click();
        driver.get("http://localhost:" + this.port + "/home");
        driver.findElement(By.id("nav-credentials-tab")).click();

        String resultCredentialUrl2 =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialUrlAfterSubmit"))).getText();
        String resultCredentialUsername2 =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialUsernameAfterSubmit"))).getText();
        String resultCredentialPassword2 =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialPasswordAfterSubmit"))).getText();

        Assertions.assertEquals(credentialURL2, resultCredentialUrl2);
        Assertions.assertEquals(credentialUsername2, resultCredentialUsername2);
        Assertions.assertNotEquals(credentialPassword2, resultCredentialPassword2);
    }

    @Test
    void testCredentialDeletion() {
        String username = "test";
        String password = "test";
        String firstName = "test";
        String lastName = "test";

        String credentialURL = "testCredentialUrl";
        String credentialUsername = "testCredentialUsername";
        String credentialPassword = "testCredentialPassword";

        driver.get("http://localhost:" + this.port + "/signup");

        SignupControllerPageTest signupControllerPageTest = new SignupControllerPageTest(driver);
        signupControllerPageTest.signup(username, password, firstName, lastName);

        driver.get("http://localhost:" + this.port + "/login");
        LoginControllerPageTest loginControllerPageTest = new LoginControllerPageTest(driver);
        loginControllerPageTest.login(username, password);

        driver.get("http://localhost:" + this.port + "/home");

        driver.findElement(By.id("nav-credentials-tab")).click();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("newCredentialButton"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(credentialURL);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username"))).sendKeys(credentialUsername);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password"))).sendKeys(credentialPassword);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("saveCredentialButton"))).click();

        driver.get("http://localhost:" + this.port + "/home");

        driver.findElement(By.id("nav-credentials-tab")).click();

        String resultCredentialUrl = wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialUrlAfterSubmit"))).getText();
        String resultCredentialUsername = wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialUsernameAfterSubmit"))).getText();
        String resultCredentialPassword =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialPasswordAfterSubmit"))).getText();

        Assertions.assertEquals(credentialURL, resultCredentialUrl);
        Assertions.assertEquals(credentialUsername, resultCredentialUsername);
        Assertions.assertNotEquals(credentialPassword, resultCredentialPassword);

        driver.get("http://localhost:" + this.port + "/home");

        driver.findElement(By.id("nav-credentials-tab")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("deleteCredentialButton"))).click();

        driver.get("http://localhost:" + this.port + "/home");

        driver.findElement(By.id("nav-credentials-tab")).click();


        HomeControllerPageTest homeControllerPageTest = new HomeControllerPageTest(driver);

        Assertions.assertThrows(NoSuchElementException.class, homeControllerPageTest::getCredentialUrlSubmitted);
        Assertions.assertThrows(NoSuchElementException.class, homeControllerPageTest::getCredentialUsernameSubmitted);
        Assertions.assertThrows(NoSuchElementException.class, homeControllerPageTest::getCredentialPasswordSubmitted);

    }

    @Test
    void testUserWrongLogin() {

        String username = "test";
        String password = "test";

        driver.get("http://localhost:" + this.port + "/login");

        LoginControllerPageTest loginControllerPageTest = new LoginControllerPageTest(driver);
        loginControllerPageTest.login(username, password);
        Assertions.assertEquals("Invalid username or password",
                loginControllerPageTest.getErrorUserMessageText());
    }


}
