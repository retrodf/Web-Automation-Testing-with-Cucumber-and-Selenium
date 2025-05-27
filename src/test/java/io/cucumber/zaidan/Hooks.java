package io.cucumber.zaidan;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

/**
 * Hooks for Cucumber test setup and teardown
 */
public class Hooks {
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = WebDriverManager.getDriver();
    }    @After
    public void tearDown(Scenario scenario) {
        try {
            // Take screenshot if scenario fails
            if (scenario.isFailed()) {
                takeScreenshot("Failure_" + scenario.getName());
                System.out.println("Scenario failed: " + scenario.getName());
                
                // Add a delay to see the failed state
                Thread.sleep(2000);
            }
            
            // Always take a final screenshot
            takeScreenshot("Final_" + scenario.getName());
            System.out.println("Test completed: " + scenario.getName());
            
            // Add a small delay before quitting to see the final state
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Error in tearDown: " + e.getMessage());
        } finally {
            // Always quit the driver
            WebDriverManager.quitDriver();
        }
    }
    
    private void takeScreenshot(String name) {
        if (driver instanceof TakesScreenshot) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
        }
    }
}
