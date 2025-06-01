package io.cucumber.zaidan.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.qameta.allure.Step;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import io.cucumber.zaidan.config.AllureManager;
import io.cucumber.zaidan.config.WebDriverManager;
import io.cucumber.zaidan.pages.LoginPage;
import io.cucumber.zaidan.pages.DashboardPage;

/**
 * Step definitions for logout functionality
 */
public class LogoutSteps {
    private WebDriver driver;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private final String APP_URL = "http://ptbsp.ddns.net:6882";

    public LogoutSteps() {
        this.driver = WebDriverManager.getDriver();
        this.loginPage = new LoginPage(driver);
        this.dashboardPage = new DashboardPage(driver);
    }

    @Given("User has been logged in as an Administrator")
    @Step("Logging in as Administrator")
    @Description("Logs in as an admin user to prepare for logout test")
    public void userHasBeenLoggedInAsAdmin() {
        // Navigate to login page
        loginPage.navigateToPage(APP_URL);
        
        // Verify login page is displayed
        Assertions.assertTrue(loginPage.isLoginPageDisplayed(), "Login page is not displayed");
        
        // Login with admin credentials
        loginPage.enterCredentials("admin", "admin123");
        loginPage.clickLoginButton();
        
        // Verify dashboard is displayed
        Assertions.assertTrue(dashboardPage.isDashboardDisplayed(), "Dashboard is not displayed after admin login");
        
        AllureManager.addTestData("Username", "admin");
        AllureManager.addTestData("Role", "Administrator");
    }

    @When("User clicks on logout button")
    @Step("Clicking logout button")
    @Description("Finds and clicks the logout button from the administrator dashboard")
    public void userClicksOnLogoutButton() {
        dashboardPage.clickLogoutButton();
    }

    @And("User clicks {string} button")
    @Step("Clicking {0} button")
    @Description("Confirms the logout action by clicking the confirmation button")
    public void userClicksButton(String buttonText) {
        dashboardPage.confirmLogout(buttonText);
        AllureManager.addTestData("Button Text", buttonText);
    }
}
