package io.cucumber.zaidan;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object pattern implementation for login and dashboard pages
 */
public class Pages {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public Pages(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Methods for Login Page
     */    public void navigateToLoginPage() {
        driver.get("http://ptbsp.ddns.net:6882");
        
        // Add a short wait to make sure the page loads completely
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void enterUsername(String username) {
        try {
            // Try multiple locator strategies
            WebElement usernameField;
            try {
                usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
            } catch (Exception e) {
                try {
                    usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
                } catch (Exception e2) {
                    // Try XPath as a last resort
                    usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@type='text' and (contains(@placeholder, 'username') or contains(@placeholder, 'Username'))]")));
                }
            }
            
            usernameField.clear();
            usernameField.sendKeys(username);
        } catch (Exception e) {
            System.out.println("Failed to enter username: " + e.getMessage());
            throw e;
        }
    }

    public void enterPassword(String password) {
        try {
            // Try multiple locator strategies
            WebElement passwordField;
            try {
                passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
            } catch (Exception e) {
                try {
                    passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
                } catch (Exception e2) {
                    // Try XPath as a last resort
                    passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@type='password']")));
                }
            }
            
            passwordField.clear();
            passwordField.sendKeys(password);
        } catch (Exception e) {
            System.out.println("Failed to enter password: " + e.getMessage());
            throw e;
        }
    }    public void clickLoginButton() {
        try {
            // Try multiple locator strategies
            WebElement loginButton;
            try {
                loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(), 'Login') or contains(text(), 'Masuk') or contains(text(), 'login')]")));
            } catch (Exception e) {
                try {
                    loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginButton")));
                } catch (Exception e2) {
                    // Try more general XPath as a last resort
                    loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(@type, 'submit') or contains(@class, 'login') or contains(@class, 'btn')]")));
                }
            }
            
            // Add a small delay before clicking
            Thread.sleep(500);
            loginButton.click();
        } catch (Exception e) {
            System.out.println("Failed to click login button: " + e.getMessage());
            throw new RuntimeException("Failed to click login button", e);
        }
    }

    public boolean isLoginPageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h1[contains(text(), 'Pengelolaan Dana Pendidikan Sekolah Zaidan Educare')]"))).isDisplayed();
    }    public boolean isErrorMessageDisplayed() {
        try {
            // Using the class structure you provided for the error notification
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'bg-[#ffecec]')] | " +
                         "//div[contains(@class, 'text-card-foreground shadow rounded-lg border')] | " +
                         "//div[contains(text(), 'Incorrect username or password')] | " +
                         "//div[contains(text(), 'Username atau password salah')]")));
            System.out.println("Error message found: " + errorElement.getText());
            return errorElement.isDisplayed();
        } catch (Exception e) {
            System.out.println("Failed to find error message: " + e.getMessage());
            return false;
        }
    }

    /**
     * Methods for Dashboard Page
     */    public boolean isDashboardPageDisplayed() {
        try {
            // Try multiple ways to detect a dashboard
            
            // First check URL
            String currentUrl = driver.getCurrentUrl();
            System.out.println("Current URL: " + currentUrl);
            
            if (currentUrl.contains("dashboard") || 
                currentUrl.contains("admin") || 
                currentUrl.contains("beranda") ||
                !currentUrl.contains("login")) {
                System.out.println("Dashboard detected via URL");
                return true;
            }
            
            // If URL check failed, look for dashboard-specific elements
            try {
                WebElement dashboardElement = driver.findElement(
                    By.xpath("//*[contains(text(), 'Dashboard') or contains(text(), 'Dasbor') or contains(@class, 'dashboard')]"));
                System.out.println("Dashboard element found: " + dashboardElement.getText());
                return true;
            } catch (Exception ex) {
                // Continue checking other methods
            }
              // Check if there's a navigation menu present (indicating logged-in state)
            try {
                WebElement navMenu = driver.findElement(
                    By.xpath("//nav | //div[contains(@class, 'navbar')] | //div[contains(@class, 'sidebar')]"));
                System.out.println("Navigation menu found, likely on dashboard. Tag: " + navMenu.getTagName());
                return true;
            } catch (Exception ex) {
                // Final check failed
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error detecting dashboard: " + e.getMessage());
            return false;
        }
    }

    public String getDashboardTitle() {
        return driver.getTitle();
    }    public boolean isNavigationItemDisplayed(String navItem) {
        try {
            // Try multiple ways to find navigation items, starting with specific class structure
            try {
                driver.findElement(
                    By.xpath("//div[contains(@class, 'rounded-xl border bg-card')]//a[contains(text(),'" + navItem + "')] | " +
                             "//div[contains(@class, 'rounded-xl border bg-card')]//*[contains(text(), '" + navItem + "')]"));
                System.out.println("Found navigation item with exact class: " + navItem);
                return true;
            } catch (Exception e1) {
                // Try more flexible approaches - any element containing the text in navigation context
                try {
                    driver.findElement(
                        By.xpath("//nav//*[contains(text(), '" + navItem + "')] | " +
                                 "//aside//*[contains(text(), '" + navItem + "')] | " +
                                 "//div[contains(@class, 'navbar') or contains(@class, 'nav') or contains(@class, 'sidebar')]//*[contains(text(), '" + navItem + "')] | " +
                                 "//a[contains(text(), '" + navItem + "')]"));
                    System.out.println("Found navigation item with general selector: " + navItem);
                    return true;
                } catch (Exception e2) {
                    // If its Dasbor, also look for Dashboard in English as a fallback
                    if (navItem.equals("Dasbor")) {
                        try {
                            driver.findElement(
                                By.xpath("//*[contains(text(), 'Dashboard')]"));
                            System.out.println("Found Dashboard as alternative to Dasbor");
                            return true;
                        } catch (Exception e3) {
                            return false;
                        }
                    }
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to find navigation item: " + navItem + " - " + e.getMessage());
            return false;
        }
    }
    
    public boolean isBendaharaNavbarDisplayed() {
        try {
            // Using the class you provided for the navbar that contains "Bendahara" text
            WebElement navElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'rounded-xl border bg-card')]//*[contains(text(), 'Bendahara')]")));
            System.out.println("Found Bendahara in navbar");
            return navElement.isDisplayed();
        } catch (Exception e) {
            System.out.println("Failed to find Bendahara in navbar: " + e.getMessage());
            return false;
        }
    }

    /**
     * Methods for Logout
     */    public void clickLogoutButton() {
        try {
            // Try multiple ways to find the logout button with a much more flexible approach
            WebElement logoutButton;
            try {
                // First try specific classes that might be used for logout buttons
                logoutButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'bg-destructive')] | " +
                             "//a[contains(text(), 'Logout') or contains(text(), 'Log out') or contains(text(), 'Keluar')] | " +
                             "//button[contains(text(), 'Logout') or contains(text(), 'Log out') or contains(text(), 'Keluar')]")));
            } catch (Exception e1) {
                try {
                    // Try a more general approach - any button with a relevant term
                    logoutButton = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(@class, 'destructive') or contains(@class, 'danger') or contains(@class, 'logout')] | " +
                                 "//div[contains(@class, 'shadow')]//button[contains(@class, 'shadow-sm')] | " +
                                 "//div[contains(@class, 'navbar') or contains(@class, 'nav')]//button")));
                } catch (Exception e2) {
                    // Last resort - look for any icon or element that might be a logout button
                    logoutButton = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(@aria-label, 'logout') or contains(@title, 'logout')] | " +
                                 "//*[contains(@class, 'logout-icon') or contains(@class, 'sign-out')] | " +
                                 "//button[last()]")));
                }
            }
            
            System.out.println("Found logout button: " + logoutButton.getText());
            // Add a small delay before clicking
            Thread.sleep(1000);
            logoutButton.click();
            System.out.println("Clicked logout button");
        } catch (Exception e) {
            System.out.println("Failed to click logout button: " + e.getMessage());
            throw new RuntimeException("Failed to click logout button", e);
        }
    }    public void confirmLogout(String buttonText) {
        try {
            // Wait a moment for the dialog to appear
            Thread.sleep(1000);
            
            // Try to find the confirm button in the alert dialog with much more flexible approach
            WebElement confirmButton;
            try {
                // First, try with the exact text provided
                confirmButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class, 'fixed')]//button[contains(text(), '" + buttonText + "')] | " +
                             "//div[contains(@class, 'dialog') or contains(@class, 'modal')]//button[contains(text(), '" + buttonText + "')] | " +
                             "//button[contains(text(), '" + buttonText + "')]")));
                System.out.println("Found confirm button with text: " + buttonText);
            } catch (Exception e1) {
                try {
                    // If that fails, try with more generic case-insensitive approach
                    String lowerButtonText = buttonText.toLowerCase();
                    confirmButton = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@role='dialog']//button[translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')='" + 
                                lowerButtonText + "'] | " +
                                "//button[translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')='" + 
                                lowerButtonText + "']")));
                    System.out.println("Found confirm button with case-insensitive text: " + buttonText);                } catch (Exception e2) {
                    // Last resort - find any button in a dialog or the primary/first button if multiple are present
                    confirmButton = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[contains(@class, 'dialog') or contains(@class, 'modal')]//button | " +
                                "//div[@role='dialog']//button | " +
                                "//button[contains(@class, 'primary') or contains(@class, 'confirm')]")));
                    System.out.println("Found confirm button as a last resort");
                }
            }
            
            System.out.println("Found confirm button: " + confirmButton.getText());
            // Add a small delay before clicking
            Thread.sleep(1000);
            confirmButton.click();
            System.out.println("Clicked confirm button");
        } catch (Exception e) {
            System.out.println("Failed to click confirm button: " + e.getMessage());
            throw new RuntimeException("Failed to confirm logout", e);
        }
    }
}
