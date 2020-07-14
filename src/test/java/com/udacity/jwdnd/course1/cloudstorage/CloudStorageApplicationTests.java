package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.controllers.HomeControllerPageTest;
import com.udacity.jwdnd.course1.cloudstorage.controllers.LoginControllerPageTest;
import com.udacity.jwdnd.course1.cloudstorage.controllers.SignupControllerPageTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

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
        signupControllerPageTest.signup(username,password,firstName,lastName);

        driver.get("http://localhost:" + this.port + "/login");
        LoginControllerPageTest loginControllerPageTest = new LoginControllerPageTest(driver);
        loginControllerPageTest.login(username, password);

        Assertions.assertEquals("Home",driver.getTitle());

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
        signupControllerPageTest.signup(username,password,firstName,lastName);

        driver.get("http://localhost:" + this.port + "/login");
        LoginControllerPageTest loginControllerPageTest = new LoginControllerPageTest(driver);
        loginControllerPageTest.login(username, password);


        driver.get("http://localhost:" + this.port + "/home");
        HomeControllerPageTest homeControllerPageTest = new HomeControllerPageTest(driver);
        homeControllerPageTest.saveNote(noteTitle,noteDescription);

        Assertions.assertEquals(noteTitle,homeControllerPageTest.getNoteTitle());
        Assertions.assertEquals(noteDescription,homeControllerPageTest.getNoteDescription());



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
