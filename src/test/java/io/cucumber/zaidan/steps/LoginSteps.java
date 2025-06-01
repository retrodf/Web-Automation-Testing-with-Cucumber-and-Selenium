package io.cucumber.zaidan.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.qameta.allure.Step;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import io.cucumber.zaidan.config.AllureManager;
import io.cucumber.zaidan.config.WebDriverManager;
import io.cucumber.zaidan.pages.LoginPage;

/**
 * Step definitions for login functionality
 */
public class LoginSteps {
    private WebDriver driver;
    private LoginPage loginPage;
    private final String APP_URL = "http://ptbsp.ddns.net:6882";

    public LoginSteps() {
        this.driver = WebDriverManager.getDriver();
        this.loginPage = new LoginPage(driver);
    }

    @Given("User has opened the browser")
    @Step("Opening browser")
    public void userHasOpenedTheBrowser() {
        AllureManager.addTestData("Browser", "Microsoft Edge");
    }
    
    @And("User has navigated on the login page Education Fund Payment Management System for Zaidan Educare School app")
    @Step("Navigating to login page")
    public void userHasNavigatedOnTheLoginPage() {
        loginPage.navigateToPage(APP_URL);
        AllureManager.addTestData("URL", APP_URL);
        
        // Verify we're on the login page
        Assertions.assertTrue(loginPage.isLoginPageDisplayed(), "Login page is not displayed");
    }
    
    @When("User enters username {string} and password {string}")
    @Step("Entering credentials - username: {0}")
    @Description("Enter username and password in the login form")
    public void userEntersUsernameAndPassword(String username, String password) {
        loginPage.enterCredentials(username, password);
    }
    
    @And("User clicks on login button")
    @Step("Clicking login button")
    @Description("Finds and clicks the login button to submit credentials")
    public void userClicksOnLoginButton() {
        loginPage.clickLoginButton();
    }

    @Then("User should be able to see {string} notification message")
    @Step("Verifying error notification: {0}")
    @Description("Validates that the appropriate error message is displayed for invalid login attempts")
    public void userShouldBeAbleToSeeNotificationMessage(String messageType) {
        if (messageType.equals("un-successful login")) {
            Assertions.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed");
            AllureManager.addTestData("Error Type", "Invalid Login");
            AllureManager.takeScreenshot(driver, "Login Error Message");
        }
    }

    @And("System displays login page elements correctly")
    @Step("Verifying login page elements")
    @Description("Checks that all expected elements are displayed on the login page")
    public void systemDisplaysLoginPageElementsCorrectly() {
        Assertions.assertTrue(loginPage.isLoginPageDisplayed(), "Login page elements are not displayed correctly");
        Assertions.assertTrue(loginPage.isLogoDisplayed(), "Logo is not displayed on login page");
        
        AllureManager.takeScreenshot(driver, "Login Page Elements");
        AllureManager.attachBrowserInfo(driver);
    }
    
    @Then("User is navigated to login page")
    @Step("Verifying navigation to login page")
    @Description("Validates redirection to login page after logout")
    public void user_is_navigated_to_login_page() {
        Assertions.assertTrue(loginPage.isLoginPageDisplayed(), "User is not redirected to login page");
        
        AllureManager.takeScreenshot(driver, "After_Logout_Login_Page");
        AllureManager.addTestData("Login Page URL", driver.getCurrentUrl());
        AllureManager.attachBrowserInfo(driver);
    }
}
