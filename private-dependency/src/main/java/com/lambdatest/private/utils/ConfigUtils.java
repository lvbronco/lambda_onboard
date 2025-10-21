package com.lambdatest.private.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration utility for private LambdaTest testing
 */
public class ConfigUtils {
    
    private static Properties properties;
    
    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/test/resources/config.properties"));
        } catch (IOException e) {
            System.err.println("Failed to load config.properties: " + e.getMessage());
        }
    }
    
    /**
     * Get property value
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * Get property value with default
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Get LambdaTest username
     */
    public static String getLTUsername() {
        return System.getenv("LT_USERNAME");
    }
    
    /**
     * Get LambdaTest access key
     */
    public static String getLTAccessKey() {
        return System.getenv("LT_ACCESS_KEY");
    }
    
    /**
     * Get browser name
     */
    public static String getBrowserName() {
        return getProperty("browser.name", "Chrome");
    }
    
    /**
     * Get browser version
     */
    public static String getBrowserVersion() {
        return getProperty("browser.version", "latest");
    }
    
    /**
     * Get platform
     */
    public static String getPlatform() {
        return getProperty("platform", "Windows 10");
    }
    
    /**
     * Get base URL
     */
    public static String getBaseUrl() {
        return getProperty("base.url", "https://example.com");
    }
    
    /**
     * Get timeout
     */
    public static int getTimeout() {
        return Integer.parseInt(getProperty("timeout", "30"));
    }
}
