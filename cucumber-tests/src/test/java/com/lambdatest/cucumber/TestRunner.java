package com.lambdatest.cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.lambdatest.cucumber.utils.DriverManager;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.lambdatest.cucumber.stepdefs",
    plugin = {
        "pretty",
        "html:target/cucumber-reports",
        "json:target/cucumber-reports/Cucumber.json",
        "junit:target/cucumber-reports/Cucumber.xml",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    },
    tags = "@smoke or @negative or @boundary"
)
public class TestRunner extends AbstractTestNGCucumberTests {
    
    @BeforeSuite
    public void setUp() {
        System.out.println("Setting up Cucumber test suite...");
    }
    
    @AfterSuite
    public void tearDown() {
        System.out.println("Tearing down Cucumber test suite...");
        DriverManager.quitDriver();
    }
}
