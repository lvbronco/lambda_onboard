Feature: User Login
  As a user
  I want to be able to login to the application
  So that I can access my account

  @smoke @login
  Scenario: Successful login with valid credentials
    Given I am on the login page
    When I enter username "testuser" and password "password123"
    And I click the login button
    Then I should see the dashboard

  @negative @login
  Scenario: Failed login with invalid credentials
    Given I am on the login page
    When I enter username "invaliduser" and password "wrongpassword"
    And I click the login button
    Then I should see an error message

  @boundary @login
  Scenario: Login with empty credentials
    Given I am on the login page
    When I enter username "" and password ""
    And I click the login button
    Then I should see an error message
