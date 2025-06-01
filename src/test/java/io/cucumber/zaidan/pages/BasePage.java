package io.cucumber.zaidan.pages;

import io.cucumber.zaidan.config.AllureManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Base Page class implementing Page Factory pattern
 * All page objects should extend this class
 */
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final String APP_URL = "http://ptbsp.ddns.net:6882";
    
    /**
     * Constructor to initialize WebDriver and PageFactory elements
     * @param driver WebDriver instance
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Initialize page elements using PageFactory
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Navigate to the application base URL
     */
    public void navigateToApp() {
        driver.get(APP_URL);
        waitForPageLoad();
        AllureManager.addTestData("Navigation URL", APP_URL);
    }
    
    /**
     * Wait for page to load completely
     */
    protected void waitForPageLoad() {
        try {
            Thread.sleep(2000); // Basic wait for page stability
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Page load wait interrupted", e);
        }
    }
    
    /**
     * Get current page title
     * @return Page title
     */
    public String getPageTitle() {
        String title = driver.getTitle();
        AllureManager.addTestData("Page Title", title);
        return title;
    }
    
    /**
     * Get current page URL
     * @return Current URL
     */
    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        AllureManager.addTestData("Current URL", url);
        return url;
    }
    
    /**
     * Take screenshot and attach to Allure report
     * @param name Screenshot name
     */
    protected void takeScreenshot(String name) {
        AllureManager.takeScreenshot(driver, name);
    }
}
