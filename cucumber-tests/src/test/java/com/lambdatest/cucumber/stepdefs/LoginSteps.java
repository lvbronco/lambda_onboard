package com.lambdatest.cucumber.stepdefs;

import com.lambdatest.cucumber.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps {
    
    private LoginPage loginPage;
    
    public LoginSteps() {
        this.loginPage = new LoginPage();
    }
    
    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        loginPage.navigateToLoginPage();
    }
    
    @When("I enter username {string} and password {string}")
    public void i_enter_username_and_password(String username, String password) {
        loginPage.enterCredentials(username, password);
    }
    
    @When("I click the login button")
    public void i_click_the_login_button() {
        loginPage.clickLoginButton();
    }
    
    @Then("I should see the dashboard")
    public void i_should_see_the_dashboard() {
        Assert.assertTrue(loginPage.isDashboardVisible(), "Dashboard should be visible after login");
    }
    
    @Then("I should see an error message")
    public void i_should_see_an_error_message() {
        Assert.assertTrue(loginPage.isErrorMessageVisible(), "Error message should be visible");
    }
}
