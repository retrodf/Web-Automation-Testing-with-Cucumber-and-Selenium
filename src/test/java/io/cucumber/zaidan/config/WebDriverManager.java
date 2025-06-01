package io.cucumber.zaidan.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import java.time.Duration;

/**
 * Helper class for WebDriver setup
 */
public class WebDriverManager {
    private static WebDriver driver;

    /**
     * Creates and returns an Edge WebDriver instance
     * @return Edge WebDriver instance
     */   
        
    public static WebDriver getDriver() {
        if (driver == null) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--remote-allow-origins=*");
            
            driver = new EdgeDriver(options);
            
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));  
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));   
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(45));
            
            driver.manage().window().maximize();
        }
        return driver;
    }

    /**
     * Quits the WebDriver instance and sets it to null
     */    public static void quitDriver() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.out.println("Exception while quitting driver: " + e.getMessage());
                // Suppress exception since we're cleaning up
            } finally {
                driver = null;
            }
        }
    }
}
