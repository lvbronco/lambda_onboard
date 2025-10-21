package com.lambdatest.private.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import java.time.Duration;
import java.util.List;

/**
 * Private utility class for LambdaTest testing
 * This is a private Maven dependency that will be used in Hyperexecute
 */
public class TestUtils {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    public TestUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    /**
     * Wait for element to be visible
     */
    public WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Wait for element to be clickable
     */
    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    /**
     * Wait for multiple elements
     */
    public List<WebElement> waitForElements(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }
    
    /**
     * Safe click with retry
     */
    public boolean safeClick(By locator, int maxRetries) {
        for (int i = 0; i < maxRetries; i++) {
            try {
                WebElement element = waitForClickable(locator);
                element.click();
                return true;
            } catch (Exception e) {
                if (i == maxRetries - 1) {
                    System.err.println("Failed to click element after " + maxRetries + " retries: " + e.getMessage());
                    return false;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
        }
        return false;
    }
    
    /**
     * Safe send keys with retry
     */
    public boolean safeSendKeys(By locator, String text, int maxRetries) {
        for (int i = 0; i < maxRetries; i++) {
            try {
                WebElement element = waitForElement(locator);
                element.clear();
                element.sendKeys(text);
                return true;
            } catch (Exception e) {
                if (i == maxRetries - 1) {
                    System.err.println("Failed to send keys after " + maxRetries + " retries: " + e.getMessage());
                    return false;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
        }
        return false;
    }
    
    /**
     * Check if element exists
     */
    public boolean elementExists(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get element text safely
     */
    public String getElementText(By locator) {
        try {
            WebElement element = waitForElement(locator);
            return element.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * Wait for page to load
     */
    public void waitForPageLoad() {
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"));
    }
    
    /**
     * Take screenshot with custom name
     */
    public void takeScreenshot(String name) {
        try {
            // This would be implemented based on your screenshot utility
            System.out.println("Screenshot taken: " + name);
        } catch (Exception e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
        }
    }
}
