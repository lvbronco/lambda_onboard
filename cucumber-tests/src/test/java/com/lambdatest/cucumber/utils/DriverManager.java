package com.lambdatest.cucumber.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DriverManager {
    
    private static WebDriver driver;
    private static final String LT_USERNAME = System.getenv("LT_USERNAME");
    private static final String LT_ACCESS_KEY = System.getenv("LT_ACCESS_KEY");
    
    public static WebDriver getDriver() {
        if (driver == null) {
            driver = createDriver();
        }
        return driver;
    }
    
    private static WebDriver createDriver() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("browserName", "Chrome");
            capabilities.setCapability("version", "latest");
            capabilities.setCapability("platform", "Windows 10");
            capabilities.setCapability("build", "Cucumber Hyperexecute Build");
            capabilities.setCapability("name", "Cucumber Test");
            capabilities.setCapability("network", true);
            capabilities.setCapability("visual", true);
            capabilities.setCapability("video", true);
            capabilities.setCapability("console", true);
            
            // LambdaTest credentials
            capabilities.setCapability("user", LT_USERNAME);
            capabilities.setCapability("accessKey", LT_ACCESS_KEY);
            
            // Chrome options
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--window-size=1920,1080");
            
            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
            
            String gridURL = "https://" + LT_USERNAME + ":" + LT_ACCESS_KEY + "@hub.lambdatest.com/wd/hub";
            return new RemoteWebDriver(new URL(gridURL), capabilities);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to create WebDriver", e);
        }
    }
    
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
