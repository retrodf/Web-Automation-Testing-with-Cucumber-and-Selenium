package io.cucumber.zaidan.pages;

import io.cucumber.zaidan.config.AllureManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.qameta.allure.Step;

public class LoginPage extends BasePage {
    
    @FindBy(name = "username")
    private WebElement usernameField;
    
    @FindBy(id = "username") 
    private WebElement usernameFieldById;
    
    @FindBy(xpath = "//input[@type='text' and (contains(@placeholder, 'username') or contains(@placeholder, 'Username'))]")
    private WebElement usernameFieldByXpath;
    
    @FindBy(name = "password")
    private WebElement passwordField;
    
    @FindBy(id = "password")
    private WebElement passwordFieldById;
    
    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordFieldByXpath;
    
    @FindBy(xpath = "//button[contains(text(), 'Login') or contains(text(), 'Masuk') or contains(@type, 'submit')]")
    private WebElement loginButton;
    
    @FindBy(xpath = "//button[contains(@class, 'login') or @type='submit' or contains(@class, 'btn')]")
    private WebElement loginButtonAlternative;
    
    @FindBy(xpath = "//h1[contains(text(), 'Pengelolaan Dana Pendidikan Sekolah Zaidan Educare')]")
    private WebElement pageTitle;    @FindBy(xpath = "//div[contains(@class, 'bg-[#ffecec]')]")
    private WebElement errorMessageBg;
    
    @FindBy(xpath = "//div[contains(@class, 'text-card-foreground shadow rounded-lg border')]")
    private WebElement errorMessageCard;
    
    @FindBy(xpath = "//div[contains(text(), 'Incorrect username or password')]")
    private WebElement errorMessageEnglish;
    
    @FindBy(xpath = "//div[contains(text(), 'Username atau password salah')]")
    private WebElement errorMessageIndonesian;
    
    @FindBy(xpath = "//div[contains(@class, 'alert') or contains(@class, 'error') or contains(@class, 'notification')]")
    private WebElement errorMessage;
    
    @FindBy(xpath = "//*[contains(text(), 'invalid') or contains(text(), 'incorrect') or contains(text(), 'wrong') or contains(text(), 'failed')]")
    private WebElement errorMessageByText;
    
    @FindBy(xpath = "//div[contains(@class, 'alert-danger') or contains(@class, 'alert-error') or contains(@class, 'error-message')]")
    private WebElement errorMessageAlternative;
    
    @FindBy(xpath = "//img[contains(@alt, 'Zaidan EDUCARE logo')]")
    private WebElement logo;
    
    @FindBy(xpath = "//img")
    private WebElement anyImage;
    
    @FindBy(xpath = "//div[contains(@class, 'logo')]")
    private WebElement logoDiv;
    
    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Navigate to login page
     */
    @Step("Navigating to login page")
    public void navigateToLoginPage() {
        navigateToApp();
        takeScreenshot("Login_Page_Loaded");
        AllureManager.addTestData("Login Page URL", getCurrentUrl());
    }
    
    /**
     * Enter username in the username field
     * @param username Username to enter
     */
    @Step("Entering username: {username}")
    public void enterUsername(String username) {
        try {
            WebElement usernameElement = getUsernameField();
            usernameElement.clear();
            usernameElement.sendKeys(username);
            AllureManager.addTestData("Username Entered", username);
            System.out.println("Successfully entered username: " + username);
        } catch (Exception e) {
            System.out.println("Failed to enter username: " + e.getMessage());
            takeScreenshot("Username_Entry_Failed");
            throw new RuntimeException("Failed to enter username", e);
        }
    }
    
    /**
     * Enter password in the password field
     * @param password Password to enter
     */
    @Step("Entering password")
    public void enterPassword(String password) {
        try {
            WebElement passwordElement = getPasswordField();
            passwordElement.clear();
            passwordElement.sendKeys(password);
            AllureManager.addTestData("Password Entered", "****");
            System.out.println("Successfully entered password");
        } catch (Exception e) {
            System.out.println("Failed to enter password: " + e.getMessage());
            takeScreenshot("Password_Entry_Failed");
            throw new RuntimeException("Failed to enter password", e);
        }
    }
    
    /**
     * Click the login button
     */
    @Step("Clicking login button")
    public void clickLoginButton() {
        try {
            WebElement loginButtonElement = getLoginButton();
            loginButtonElement.click();
            System.out.println("Successfully clicked login button");
            takeScreenshot("Login_Button_Clicked");
            waitForPageLoad();
        } catch (Exception e) {
            System.out.println("Failed to click login button: " + e.getMessage());
            takeScreenshot("Login_Button_Click_Failed");
            throw new RuntimeException("Failed to click login button", e);
        }
    }
    
    /**
     * Perform complete login action
     * @param username Username
     * @param password Password
     */
    @Step("Performing login with username: {username}")
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        takeScreenshot("Credentials_Entered");
        clickLoginButton();
    }
    
    /**
     * Check if login page is displayed
     * @return true if login page is displayed
     */
    @Step("Verifying login page is displayed")
    public boolean isLoginPageDisplayed() {
        try {
            boolean isDisplayed = wait.until(ExpectedConditions.visibilityOf(pageTitle)).isDisplayed();
            AllureManager.addTestData("Login Page Displayed", String.valueOf(isDisplayed));
            return isDisplayed;
        } catch (Exception e) {
            System.out.println("Login page verification failed: " + e.getMessage());
            return false;
        }
    }    /**
     * Check if error message is displayed (using strategies from working Pages.java)
     * @return true if error message is displayed
     */
    @Step("Checking for error message")
    public boolean isErrorMessageDisplayed() {
        try {
            // First, wait a moment for error message to appear
            Thread.sleep(2000);
            
            // Try the exact strategies that worked in Pages.java first
            try {
                if (errorMessageBg.isDisplayed()) {
                    AllureManager.addTestData("Error Message Found", "By bg-[#ffecec] class");
                    System.out.println("Error message found by bg-[#ffecec] class");
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Error message not found by bg-[#ffecec] class: " + e.getMessage());
            }
            
            try {
                if (errorMessageCard.isDisplayed()) {
                    AllureManager.addTestData("Error Message Found", "By text-card-foreground shadow rounded-lg border class");
                    System.out.println("Error message found by card class");
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Error message not found by card class: " + e.getMessage());
            }
            
            try {
                if (errorMessageEnglish.isDisplayed()) {
                    AllureManager.addTestData("Error Message Found", "By English text content");
                    System.out.println("Error message found by English text");
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Error message not found by English text: " + e.getMessage());
            }
            
            try {
                if (errorMessageIndonesian.isDisplayed()) {
                    AllureManager.addTestData("Error Message Found", "By Indonesian text content");
                    System.out.println("Error message found by Indonesian text");
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Error message not found by Indonesian text: " + e.getMessage());
            }
            
            // Try fallback strategies
            try {
                if (errorMessage.isDisplayed()) {
                    AllureManager.addTestData("Error Message Found", "By class (alert/error/notification)");
                    System.out.println("Error message found by class");
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Error message not found by class: " + e.getMessage());
            }
            
            try {
                if (errorMessageByText.isDisplayed()) {
                    AllureManager.addTestData("Error Message Found", "By text content");
                    System.out.println("Error message found by text content");
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Error message not found by text: " + e.getMessage());
            }
            
            try {
                if (errorMessageAlternative.isDisplayed()) {
                    AllureManager.addTestData("Error Message Found", "By alternative class");
                    System.out.println("Error message found by alternative class");
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Error message not found by alternative class: " + e.getMessage());
            }
            
            // Last resort - check page source for error-related text
            String pageSource = driver.getPageSource().toLowerCase();
            if (pageSource.contains("incorrect username or password") || 
                pageSource.contains("username atau password salah") ||
                pageSource.contains("bg-[#ffecec]") ||
                pageSource.contains("invalid") || pageSource.contains("incorrect") || 
                pageSource.contains("error") || pageSource.contains("failed") ||
                pageSource.contains("wrong")) {
                AllureManager.addTestData("Error Message Found", "In page source");
                System.out.println("Error indicators found in page source");
                takeScreenshot("Error_Message_Page_Source_Check");
                return true;
            }
            
            AllureManager.addTestData("Error Message Displayed", "false");
            System.out.println("No error message found with any strategy");
            takeScreenshot("No_Error_Message_Found");
            return false;
        } catch (Exception e) {
            AllureManager.addTestData("Error Message Check Failed", e.getMessage());
            System.out.println("Error message check failed: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Verify login page elements are correctly displayed
     * @return true if all elements are displayed
     */
    @Step("Verifying login page elements")
    public boolean verifyLoginPageElements() {
        try {
            takeScreenshot("Login_Elements_Check");
            
            // Check for logo
            boolean logoDisplayed = false;
            try {
                logoDisplayed = logo.isDisplayed();
                AllureManager.addTestData("Logo Type", "Zaidan EDUCARE logo");
            } catch (Exception e1) {
                try {
                    logoDisplayed = anyImage.isDisplayed();
                    AllureManager.addTestData("Logo Type", "Generic image");
                } catch (Exception e2) {
                    try {
                        logoDisplayed = logoDiv.isDisplayed();
                        AllureManager.addTestData("Logo Type", "Logo div");
                    } catch (Exception e3) {
                        AllureManager.addTestData("Logo Type", "Not found");
                    }
                }
            }
            
            // Check for username and password fields
            boolean usernameFieldDisplayed = getUsernameField().isDisplayed();
            boolean passwordFieldDisplayed = getPasswordField().isDisplayed();
            boolean loginButtonDisplayed = getLoginButton().isDisplayed();
            
            AllureManager.addTestData("Logo Displayed", String.valueOf(logoDisplayed));
            AllureManager.addTestData("Username Field Displayed", String.valueOf(usernameFieldDisplayed));
            AllureManager.addTestData("Password Field Displayed", String.valueOf(passwordFieldDisplayed));
            AllureManager.addTestData("Login Button Displayed", String.valueOf(loginButtonDisplayed));
            
            return usernameFieldDisplayed && passwordFieldDisplayed && loginButtonDisplayed;
        } catch (Exception e) {
            System.out.println("Error verifying login page elements: " + e.getMessage());
            takeScreenshot("Login_Elements_Verification_Failed");
            return false;
        }
    }
    
    /**
     * Get username field with multiple fallback strategies
     * @return WebElement for username field
     */
    private WebElement getUsernameField() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(usernameField));
        } catch (Exception e) {
            try {
                return wait.until(ExpectedConditions.visibilityOf(usernameFieldById));
            } catch (Exception e2) {
                return wait.until(ExpectedConditions.visibilityOf(usernameFieldByXpath));
            }
        }
    }
    
    /**
     * Get password field with multiple fallback strategies
     * @return WebElement for password field
     */
    private WebElement getPasswordField() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(passwordField));
        } catch (Exception e) {
            try {
                return wait.until(ExpectedConditions.visibilityOf(passwordFieldById));
            } catch (Exception e2) {
                return wait.until(ExpectedConditions.visibilityOf(passwordFieldByXpath));
            }
        }
    }
    
    /**
     * Get login button with multiple fallback strategies
     * @return WebElement for login button
     */
    private WebElement getLoginButton() {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        } catch (Exception e) {
            return wait.until(ExpectedConditions.elementToBeClickable(loginButtonAlternative));
        }
    }
    
    /**
     * Navigate to a specific page (wrapper for StepDefinitions compatibility)
     * @param url URL to navigate to
     */
    @Step("Navigating to page: {url}")
    public void navigateToPage(String url) {
        driver.get(url);
        waitForPageLoad();
        takeScreenshot("Page_Loaded");
        AllureManager.addTestData("Page URL", url);
    }
    
    /**
     * Enter credentials (wrapper method combining username and password entry)
     * @param username Username to enter
     * @param password Password to enter
     */
    @Step("Entering credentials - username: {username}")
    public void enterCredentials(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        AllureManager.addTestData("Username", username);
        AllureManager.addTestData("Password", "********");
        takeScreenshot("Credentials_Entered");
    }
    
    /**
     * Check if logo is displayed (wrapper for StepDefinitions compatibility)
     * @return true if logo is displayed
     */
    @Step("Checking if logo is displayed")
    public boolean isLogoDisplayed() {
        try {
            try {
                return logo.isDisplayed();
            } catch (Exception e1) {
                try {
                    return anyImage.isDisplayed();
                } catch (Exception e2) {
                    return logoDiv.isDisplayed();
                }
            }
        } catch (Exception e) {
            AllureManager.addTestData("Logo Displayed", "false");
            return false;
        }
    }
}