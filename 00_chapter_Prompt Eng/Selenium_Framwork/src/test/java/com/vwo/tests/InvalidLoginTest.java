package com.vwo.tests;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.vwo.pages.LoginPage;

public class InvalidLoginTest {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        String chromeDriverPath = System.getProperty("webdriver.chrome.driver", "C:\\Users\\Natraj\\.cache\\selenium\\chromedriver\\win64\\157.0.8096.0\\chromedriver.exe");
        String chromeBinaryPath = System.getProperty("webdriver.chrome.binary", "C:\\$Recycle.Bin\\S-1-5-21-3806250824-1531740365-1751313502-1001\\$RPO7C7P\\chrome-for-testing\\chrome-win64\\chrome.exe");
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.setBinary(chromeBinaryPath);
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        loginPage = new LoginPage(driver);
        loginPage.open("https://app.vwo.com/#/login");
    }

    @Test
    public void loginWithInvalidCredentials() {
        String email = System.getProperty("invalid.vwo.email", "invalid@vwo.com");
        String password = System.getProperty("invalid.vwo.password", "WrongPassword123");
        try {
            loginPage.login(email, password);
            Assert.assertTrue(loginPage.isErrorDisplayed(), "Invalid login should display an error message");
            Assert.assertTrue(loginPage.isLoginPageDisplayed(), "User should remain on the login page when credentials are invalid");
        } catch (Exception e) {
            Assert.fail("Invalid login validation failed with exception: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
