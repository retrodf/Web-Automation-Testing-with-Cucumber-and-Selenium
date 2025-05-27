package io.cucumber.zaidan;

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
     */    public static WebDriver getDriver() {
        if (driver == null) {
            // Let WebDriverManager handle the driver setup automatically
            io.github.bonigarcia.wdm.WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            // Add additional options for stability
            options.addArguments("--start-maximized");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-popup-blocking");
            
            driver = new EdgeDriver(options);
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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
