package io.cucumber.zaidan.pages;

import io.cucumber.zaidan.config.AllureManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.qameta.allure.Step;
import java.util.List;

/**
 * Dashboard Page class using Page Factory pattern
 * Contains all elements and actions related to dashboard functionality
 */
public class DashboardPage extends BasePage {
    
    // Page Elements using @FindBy annotations
    @FindBy(xpath = "//*[contains(text(), 'Dashboard') or contains(text(), 'Dasbor') or contains(@class, 'dashboard')]")
    private WebElement dashboardElement;
    
    @FindBy(xpath = "//nav | //div[contains(@class, 'navbar')] | //div[contains(@class, 'sidebar')]")
    private WebElement navigationElement;
    
    @FindBy(xpath = "//div[contains(@class, 'navbar') or contains(@class, 'nav') or contains(@class, 'sidebar')]//*[contains(text(), 'Dasbor')] | //a[contains(text(), 'Dasbor')]")
    private WebElement dasborNavigation;
    
    @FindBy(xpath = "//*[contains(text(), 'Dashboard')]")
    private WebElement dashboardEnglish;    @FindBy(xpath = "//button[contains(@class, 'bg-destructive')]")
    private WebElement logoutButtonByClass;
    
    @FindBy(xpath = "//a[contains(text(), 'Logout') or contains(text(), 'Log out') or contains(text(), 'Keluar')]")
    private WebElement logoutLink;
    
    @FindBy(xpath = "//button[contains(text(), 'Logout') or contains(text(), 'Log out') or contains(text(), 'Keluar')]")
    private WebElement logoutButton;
    
    @FindBy(xpath = "//button[contains(@class, 'destructive') or contains(@class, 'danger') or contains(@class, 'logout')]")
    private WebElement logoutButtonAlternative;
    
    @FindBy(xpath = "//div[contains(@class, 'shadow')]//button[contains(@class, 'shadow-sm')]")
    private WebElement logoutButtonShadow;
    
    @FindBy(xpath = "//div[contains(@class, 'navbar') or contains(@class, 'nav')]//button")
    private WebElement logoutButtonNavbar;
    
    @FindBy(xpath = "//button[contains(text(), 'Ya') or contains(text(), 'Yes') or contains(text(), 'OK')]")
    private WebElement confirmButton;
    
    @FindBy(xpath = "//div[contains(@class, 'navbar') or contains(@class, 'nav')]//li | //div[contains(@class, 'sidebar')]//li")
    private List<WebElement> navigationItems;
    
    @FindBy(xpath = "//div[contains(@class, 'content') or contains(@class, 'main') or @id='content']")
    private WebElement mainContent;
    
    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public DashboardPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Check if dashboard page is displayed
     * @return true if dashboard page is displayed
     */
    @Step("Verifying dashboard page is displayed")
    public boolean isDashboardDisplayed() {
        try {
            // Wait for page to load and check URL
            waitForPageLoad();
            String currentUrl = getCurrentUrl();
            
            // Check if we're not on the login page anymore
            boolean notOnLoginPage = !currentUrl.contains("login") && !currentUrl.equals(APP_URL);
            
            // Try to find dashboard elements
            boolean dashboardElementFound = false;
            try {
                dashboardElementFound = dashboardElement.isDisplayed();
                AllureManager.addTestData("Dashboard Element Found", "Main dashboard element");
            } catch (Exception e) {
                try {
                    dashboardElementFound = navigationElement.isDisplayed();
                    AllureManager.addTestData("Dashboard Element Found", "Navigation element");
                } catch (Exception e2) {
                    try {
                        dashboardElementFound = mainContent.isDisplayed();
                        AllureManager.addTestData("Dashboard Element Found", "Main content");
                    } catch (Exception e3) {
                        AllureManager.addTestData("Dashboard Element Found", "None found");
                    }
                }
            }
            
            boolean isDashboard = notOnLoginPage && dashboardElementFound;
            AllureManager.addTestData("Dashboard Displayed", String.valueOf(isDashboard));
            AllureManager.addTestData("Current URL", currentUrl);
            
            if (isDashboard) {
                takeScreenshot("Dashboard_Page_Verified");
            }
            
            return isDashboard;
        } catch (Exception e) {
            System.out.println("Dashboard verification failed: " + e.getMessage());
            takeScreenshot("Dashboard_Verification_Failed");
            return false;
        }
    }
    
    /**
     * Check if specific navigation item is displayed
     * @param navItem Navigation item text to check
     * @return true if navigation item is displayed
     */
    @Step("Checking navigation item: {navItem}")
    public boolean isNavigationItemDisplayed(String navItem) {
        try {
            // Check for specific navigation item
            WebElement navElement = driver.findElement(
                org.openqa.selenium.By.xpath("//div[contains(@class, 'navbar') or contains(@class, 'nav') or contains(@class, 'sidebar')]//*[contains(text(), '" + navItem + "')] | //a[contains(text(), '" + navItem + "')]")
            );
            
            boolean isDisplayed = navElement.isDisplayed();
            AllureManager.addTestData("Navigation Item '" + navItem + "' Displayed", String.valueOf(isDisplayed));
            return isDisplayed;
            
        } catch (Exception e) {
            if (navItem.equals("Dasbor")) {
                try {
                    boolean isDisplayed = dashboardEnglish.isDisplayed();
                    AllureManager.addTestData("Dashboard (English) Found", String.valueOf(isDisplayed));
                    return isDisplayed;
                } catch (Exception e2) {
                    AllureManager.addTestData("Navigation Item '" + navItem + "' Displayed", "false");
                    return false;
                }
            }
            AllureManager.addTestData("Navigation Item '" + navItem + "' Displayed", "false");
            return false;
        }
    }
    
    /**
     * Check if bendahara navigation bar is displayed
     * @return true if bendahara navigation is displayed
     */
    @Step("Verifying bendahara navigation bar")
    public boolean isBendaharaNavbarDisplayed() {
        try {
            // Check for "Dasbor" navigation item specific to bendahara role
            boolean dasborDisplayed = isNavigationItemDisplayed("Dasbor");
            
            // Additional checks for bendahara-specific elements
            boolean navigationExists = false;
            try {
                navigationExists = navigationElement.isDisplayed();
            } catch (Exception e) {
                // Navigation element not found
            }
            
            boolean bendaharaNavbar = dasborDisplayed && navigationExists;
            AllureManager.addTestData("Bendahara Navbar Displayed", String.valueOf(bendaharaNavbar));
            
            if (bendaharaNavbar) {
                takeScreenshot("Bendahara_Navigation_Verified");
            }
            
            return bendaharaNavbar;
        } catch (Exception e) {
            System.out.println("Bendahara navbar verification failed: " + e.getMessage());
            takeScreenshot("Bendahara_Navbar_Verification_Failed");
            return false;
        }
    }
    
    /**
     * Click logout button
     */
    @Step("Clicking logout button")
    public void clickLogout() {
        try {
            WebElement logoutElement = getLogoutElement();
            logoutElement.click();
            System.out.println("Successfully clicked logout button");
            AllureManager.addTestData("Logout Action", "Logout button clicked");
            takeScreenshot("Logout_Button_Clicked");
            waitForPageLoad();
        } catch (Exception e) {
            System.out.println("Failed to click logout button: " + e.getMessage());
            takeScreenshot("Logout_Button_Click_Failed");
            throw new RuntimeException("Failed to click logout button", e);
        }
    }
    
    /**
     * Click confirmation button (usually "Ya" for logout confirmation)
     * @param buttonText Text of the button to click
     */
    @Step("Clicking confirmation button: {buttonText}")
    public void clickConfirmation(String buttonText) {
        try {
            // Wait a bit for confirmation dialog to appear
            Thread.sleep(1000);
            
            WebElement confirmationButton;
            if (buttonText.equals("Ya")) {
                confirmationButton = confirmButton;
            } else {
                confirmationButton = driver.findElement(
                    org.openqa.selenium.By.xpath("//button[contains(text(), '" + buttonText + "')]")
                );
            }
            
            wait.until(ExpectedConditions.elementToBeClickable(confirmationButton));
            confirmationButton.click();
            
            System.out.println("Successfully clicked confirmation button: " + buttonText);
            AllureManager.addTestData("Confirmation Action", "Clicked " + buttonText);
            takeScreenshot("Confirmation_Button_Clicked");
            waitForPageLoad();
            
        } catch (Exception e) {
            System.out.println("Failed to click confirmation button: " + e.getMessage());
            takeScreenshot("Confirmation_Button_Click_Failed");
            throw new RuntimeException("Failed to click confirmation button", e);
        }
    }    /**
     * Get logout element with multiple fallback strategies (based on working Pages.java implementation)
     * @return WebElement for logout button/link
     */
    private WebElement getLogoutElement() {
        System.out.println("Attempting to find logout element...");
        
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(logoutButtonByClass));
            System.out.println("Found logout button by bg-destructive class");
            return element;
        } catch (Exception e) {
            System.out.println("Logout button by bg-destructive class failed: " + e.getMessage());
        }
        
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
            System.out.println("Found logout link by text");
            return element;
        } catch (Exception e) {
            System.out.println("Logout link by text failed: " + e.getMessage());
        }
        
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
            System.out.println("Found logout button by text");
            return element;
        } catch (Exception e) {
            System.out.println("Logout button by text failed: " + e.getMessage());
        }
        
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(logoutButtonAlternative));
            System.out.println("Found logout button by alternative classes");
            return element;
        } catch (Exception e) {
            System.out.println("Logout button alternative classes failed: " + e.getMessage());
        }
        
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(logoutButtonShadow));
            System.out.println("Found logout button by shadow class");
            return element;
        } catch (Exception e) {
            System.out.println("Logout button shadow class failed: " + e.getMessage());
        }
        
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(logoutButtonNavbar));
            System.out.println("Found logout button in navbar");
            return element;
        } catch (Exception e) {
            System.out.println("Logout button navbar failed: " + e.getMessage());
        }
        
        // Final attempt - use the exact strategy from Pages.java as last resort
        try {
            System.out.println("Trying exact Pages.java strategy");
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(
                org.openqa.selenium.By.xpath("//button[contains(@aria-label, 'logout') or contains(@title, 'logout')] | " +
                                           "//*[contains(@class, 'logout-icon') or contains(@class, 'sign-out')] | " +
                                           "//button[last()]")
            ));
            System.out.println("Found logout element with Pages.java last resort strategy");
            return element;
        } catch (Exception e) {
            System.out.println("Pages.java last resort strategy failed: " + e.getMessage());
        }
        
        // Debug - print page source snippet
        String pageSource = driver.getPageSource();
        System.out.println("Page source contains 'destructive': " + pageSource.toLowerCase().contains("destructive"));
        System.out.println("Page source contains 'bg-destructive': " + pageSource.toLowerCase().contains("bg-destructive"));
        takeScreenshot("Logout_Element_Not_Found_Debug");
        
        throw new RuntimeException("Could not find logout element with any strategy including Pages.java working strategies");
    }
    
    /**
     * Get all navigation items
     * @return List of navigation items
     */
    @Step("Getting all navigation items")
    public List<WebElement> getNavigationItems() {
        try {
            List<WebElement> items = navigationItems;
            AllureManager.addTestData("Navigation Items Count", String.valueOf(items.size()));
            return items;
        } catch (Exception e) {
            System.out.println("Failed to get navigation items: " + e.getMessage());
            return java.util.Collections.emptyList();
        }
    }
    
    /**
     * Verify dashboard page elements
     * @return true if all expected elements are present
     */
    @Step("Verifying dashboard page elements")
    public boolean verifyDashboardElements() {
        try {
            takeScreenshot("Dashboard_Elements_Check");
            
            boolean dashboardDisplayed = isDashboardDisplayed();
            boolean navigationDisplayed = false;
            
            try {
                navigationDisplayed = navigationElement.isDisplayed();
            } catch (Exception e) {
                // Navigation not found
            }
            
            AllureManager.addTestData("Dashboard Elements Verified", String.valueOf(dashboardDisplayed && navigationDisplayed));
            
            return dashboardDisplayed && navigationDisplayed;
        } catch (Exception e) {
            System.out.println("Dashboard elements verification failed: " + e.getMessage());
            takeScreenshot("Dashboard_Elements_Verification_Failed");
            return false;
        }
    }
    
    /**
     * Check if navigation is displayed (wrapper for StepDefinitions compatibility)
     * @return true if navigation is displayed
     */
    @Step("Checking if navigation is displayed")
    public boolean isNavigationDisplayed() {
        try {
            return navigationElement.isDisplayed();
        } catch (Exception e) {
            try {
                return !navigationItems.isEmpty() && navigationItems.get(0).isDisplayed();
            } catch (Exception e2) {
                AllureManager.addTestData("Navigation Displayed", "false");
                return false;
            }
        }
    }
    
    /**
     * Click logout button (wrapper for StepDefinitions compatibility)
     */
    @Step("Clicking logout button")
    public void clickLogoutButton() {
        clickLogout();
    }
    
    /**
     * Confirm logout action (wrapper for StepDefinitions compatibility)
     * @param buttonText Text of the button to click
     */
    @Step("Confirming logout with button: {buttonText}")
    public void confirmLogout(String buttonText) {
        clickConfirmation(buttonText);
    }
}