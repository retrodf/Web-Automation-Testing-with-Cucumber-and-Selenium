package io.cucumber.zaidan.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import io.cucumber.zaidan.config.WebDriverManager;
import io.cucumber.zaidan.config.AllureManager;

/**
 * Common steps and setup/teardown methods shared across all step definition classes
 */
public class CommonSteps {
    private static WebDriver driver;

    @Before
    public void setUp() {
        // WebDriverManager handles the driver setup automatically
        driver = WebDriverManager.getDriver();
    }

    @After
    public void tearDown() {
        try {
            // Capture screenshot for Allure report if driver is still active
            if (driver != null) {
                try {
                    // Check if driver is still working by getting current URL
                    driver.getCurrentUrl();
                    AllureManager.takeScreenshot(driver, "Final State");
                } catch (Exception e) {
                    System.out.println("WebDriver session already terminated, skipping screenshot");
                }
            }
        } finally {
            // Always try to quit the driver
            WebDriverManager.quitDriver();
            driver = null;
        }
    }

    /**
     * Get the current WebDriver instance
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        return driver;
    }
}
