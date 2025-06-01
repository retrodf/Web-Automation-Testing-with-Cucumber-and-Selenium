package io.cucumber.zaidan.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.qameta.allure.Step;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import io.cucumber.zaidan.config.AllureManager;
import io.cucumber.zaidan.config.WebDriverManager;
import io.cucumber.zaidan.pages.DashboardPage;

/**
 * Step definitions for dashboard functionality
 */
public class DashboardSteps {
    private WebDriver driver;
    private DashboardPage dashboardPage;

    public DashboardSteps() {
        this.driver = WebDriverManager.getDriver();
        this.dashboardPage = new DashboardPage(driver);
    }

    @Then("User is navigated to the dashboard page")
    @Step("Verifying navigation to dashboard")
    @Description("Validates that user has been successfully redirected to the dashboard page after login")
    public void userIsNavigatedToDashboardPage() {
        Assertions.assertTrue(dashboardPage.isDashboardDisplayed(), "Dashboard page is not displayed");
    }
    
    @And("User should be able to see navigation bar for bendahara")
    @Step("Verifying bendahara navigation bar")
    @Description("Validates that the navigation bar is visible for bendahara role")
    public void userShouldBeAbleToSeeNavigationBarForBendahara() {
        Assertions.assertTrue(dashboardPage.isNavigationDisplayed(), "Navigation bar is not displayed");
        AllureManager.addTestData("User Role", "bendahara");
        AllureManager.takeScreenshot(driver, "Bendahara Dashboard Navigation");
    }

    @And("User has opened administrator dashboard page")
    @Step("Verifying administrator dashboard")
    public void userHasOpenedAdminDashboard() {
        // Verify we're on the dashboard
        Assertions.assertTrue(dashboardPage.isDashboardDisplayed(), "Administrator dashboard is not displayed");
        
        AllureManager.takeScreenshot(driver, "Admin Dashboard Page");
        AllureManager.addTestData("Dashboard URL", driver.getCurrentUrl());
        AllureManager.addTestData("Dashboard Title", driver.getTitle());
        AllureManager.attachBrowserInfo(driver);
    }
}
