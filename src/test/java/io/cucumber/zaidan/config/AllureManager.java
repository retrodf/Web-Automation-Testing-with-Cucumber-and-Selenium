package io.cucumber.zaidan.config;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Helper class for Allure reporting
 */
public class AllureManager {
    private static final ThreadLocal<Map<String, String>> testData = new ThreadLocal<>();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    /**
     * Initialize test data for the current thread
     */
    public static void initTestData() {
        testData.set(new HashMap<>());
    }

    /**
     * Add test data that will be displayed in the report
     * @param key The name of the data
     * @param value The value of the data
     */
    public static void addTestData(String key, String value) {
        if (testData.get() == null) {
            initTestData();
        }
        testData.get().put(key, value);
    }

    /**
     * Takes a screenshot and attaches it to the Allure report
     * @param driver The WebDriver instance
     * @param name Name of the screenshot in the report
     */
    public static void takeScreenshot(WebDriver driver, String name) {
        if (driver instanceof TakesScreenshot) {
            try {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                attachScreenshot(name, screenshot);
            } catch (Exception e) {
                System.out.println("Failed to take screenshot: " + e.getMessage());
            }
        }
    }

    /**
     * Attach a screenshot to the Allure report
     * @param name Name of the screenshot in the report
     * @param screenshot The screenshot as bytes
     */
    public static void attachScreenshot(String name, byte[] screenshot) {
        String timestamp = LocalDateTime.now().format(formatter);
        Allure.addAttachment(name + "_" + timestamp, "image/png", new ByteArrayInputStream(screenshot), "png");
    }

    /**
     * Start an Allure step
     * @param name Name of the step
     * @return UUID of the step
     */
    public static String startStep(String name) {
        String uuid = UUID.randomUUID().toString();
        Allure.getLifecycle().startStep(uuid, new StepResult().setName(name).setStatus(Status.PASSED));
        return uuid;
    }

    /**
     * End an Allure step
     * @param uuid UUID of the step to end
     */
    public static void endStep(String uuid) {
        Allure.getLifecycle().stopStep(uuid);
    }

    /**
     * Add all test data as an attachment to the report
     */
    public static void attachTestData() {
        if (testData.get() != null && !testData.get().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Test Data:\n");
            
            testData.get().forEach((key, value) -> sb.append(key).append(": ").append(value).append("\n"));
            
            String timestamp = LocalDateTime.now().format(formatter);
            Allure.addAttachment("Test Data_" + timestamp, "text/plain", sb.toString());
        }
    }

    /**
     * Attach browser information to the report
     * @param driver WebDriver instance
     */
    public static void attachBrowserInfo(WebDriver driver) {
        if (driver != null) {
            try {
                String browserInfo = "Browser Information:\n" +
                        "Current URL: " + driver.getCurrentUrl() + "\n" +
                        "Title: " + driver.getTitle() + "\n" +
                        "Browser: Microsoft Edge\n" +
                        "Timestamp: " + LocalDateTime.now().format(formatter);
                
                Allure.addAttachment("Browser Info", "text/plain", browserInfo);
            } catch (Exception e) {
                System.out.println("Failed to attach browser info: " + e.getMessage());
            }
        }
    }
}
