package io.cucumber.zaidan.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.zaidan.config.WebDriverManager;
import io.cucumber.zaidan.config.AllureManager;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Hooks for Cucumber test setup and teardown
 */
public class Hooks {
    private WebDriver driver;
    
    @Before
    public void setUp(Scenario scenario) {
        // Initialize test data storage
        AllureManager.initTestData();
        
        // Set up WebDriver
        driver = WebDriverManager.getDriver();
        
        // Add basic environment info
        AllureManager.addTestData("Test Name", scenario.getName());
        AllureManager.addTestData("Browser", "Microsoft Edge");
        AllureManager.addTestData("Test Started", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        // Create environment properties file for Allure (only once)
        createEnvironmentPropertiesFile();
    }
    
    /**
     * Creates the environment.properties file for Allure reporting
     */
    private void createEnvironmentPropertiesFile() {
        try {
            Path allureResultsDir = Paths.get(System.getProperty("user.dir"), "target", "allure-results");
            if (!Files.exists(allureResultsDir)) {
                Files.createDirectories(allureResultsDir);
            }
            
            Path envPropertiesPath = allureResultsDir.resolve("environment.properties");
            if (!Files.exists(envPropertiesPath)) {
                Properties envProperties = new Properties();
                
                // Get browser details
                Capabilities caps = null;
                if (driver instanceof RemoteWebDriver) {
                    caps = ((RemoteWebDriver) driver).getCapabilities();
                } else if (driver instanceof EdgeDriver) {
                    caps = ((EdgeDriver) driver).getCapabilities();
                }
                
                // Add environment details
                envProperties.setProperty("Browser", "Microsoft Edge");
                if (caps != null) {
                    envProperties.setProperty("Browser.Version", caps.getBrowserVersion());
                }
                envProperties.setProperty("OS", System.getProperty("os.name"));
                envProperties.setProperty("OS.Version", System.getProperty("os.version"));
                envProperties.setProperty("Java.Version", System.getProperty("java.version"));
                envProperties.setProperty("Application", "Education Fund Payment Management System for Zaidan Educare School");
                
                // Write the properties file
                try (OutputStream output = new FileOutputStream(envPropertiesPath.toFile())) {
                    envProperties.store(output, "Allure Environment Properties");
                    System.out.println("Created Allure environment properties file");                }
            }
        } catch (IOException e) {
            System.out.println("Error creating environment properties: " + e.getMessage());
        }
    }
    
    @After
    public void tearDown(Scenario scenario) {
        try {
            // Get current timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            
            // Take screenshot if scenario fails
            if (scenario.isFailed()) {
                // Log browser and error information to Allure
                AllureManager.addTestData("Status", "FAILED");
                AllureManager.addTestData("Failure Time", timestamp);
                
                Allure.getLifecycle().startStep(
                    scenario.getId() + "-error", 
                    new StepResult().setStatus(Status.FAILED).setName("Failure Information")
                );
                
                // Take screenshot for failed scenario
                AllureManager.takeScreenshot(driver, "Failure_" + scenario.getName() + "_" + timestamp);
                System.out.println("Scenario failed: " + scenario.getName());
                
                // Get browser logs if available
                try {
                    // Capture page source and URL
                    String pageSource = driver.getPageSource();
                    Allure.addAttachment("Page HTML at failure", "text/html", pageSource);
                    String currentUrl = driver.getCurrentUrl();
                    AllureManager.addTestData("Failed URL", currentUrl);
                    
                    // Add browser information to the report
                    AllureManager.attachBrowserInfo(driver);
                } catch (Exception e) {
                    Allure.addAttachment("Error collecting browser information", "text/plain", e.getMessage());
                }
                
                Allure.getLifecycle().stopStep();
                
                // Add a delay to see the failed state
                Thread.sleep(2000);
            } else {
                AllureManager.addTestData("Status", "PASSED");
            }
            
            // Always take a final screenshot
            AllureManager.takeScreenshot(driver, "Final_" + scenario.getName() + "_" + timestamp);
            System.out.println("Test completed: " + scenario.getName());
            
            // Add test completion time
            AllureManager.addTestData("Test Completed", timestamp);
            
            // Attach all test data
            AllureManager.attachTestData();
            
            // Add a small delay before quitting to see the final state
            Thread.sleep(1000);        } catch (Exception e) {
            System.out.println("Error in tearDown: " + e.getMessage());
            Allure.addAttachment("Error in tearDown", "text/plain", e.getMessage() + "\n" + e.getStackTrace().toString());
        } finally {
            // Always quit the driver
            WebDriverManager.quitDriver();
        }
    }
}
