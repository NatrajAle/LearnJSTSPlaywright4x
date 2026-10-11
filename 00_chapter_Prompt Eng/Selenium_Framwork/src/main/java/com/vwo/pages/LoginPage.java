package com.vwo.pages;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(xpath = "//input[@type='email' and @name='username' and contains(@placeholder,'Enter email ID') and not(@id='forgot-password-username') and not(@id='sso-email') and not(@id='js-login-confirm-email')]")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@type='password' and @placeholder='Enter password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[.//span[normalize-space()='Sign in'] or normalize-space(.)='Sign in']")
    private WebElement signInButton;

    @FindBy(xpath = "//label[contains(.,'Remember me')]")
    private WebElement rememberMeLabel;

    @FindBy(xpath = "//*[contains(normalize-space(.),'Your email or password is invalid') or contains(normalize-space(.),'Invalid email or password') or contains(normalize-space(.),'Email or password is invalid') or contains(normalize-space(.),'Please enter a valid email address')]")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    public void open(String url) {
        driver.get(url);
    }

    public void enterEmail(String email) {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(emailInput));
        element.clear();
        element.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(passwordInput));
        element.clear();
        element.sendKeys(password);
    }

    public void clickRememberMe() {
        wait.until(ExpectedConditions.elementToBeClickable(rememberMeLabel)).click();
    }

    public void clickSignIn() {
        wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
    }

    public boolean isLoginPageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(emailInput)).isDisplayed() && wait.until(ExpectedConditions.visibilityOf(passwordInput)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(errorMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoggedIn() {
        try {
            String url = driver.getCurrentUrl();
            return !url.contains("#/login") && !url.contains("/login");
        } catch (Exception e) {
            return false;
        }
    }
}
