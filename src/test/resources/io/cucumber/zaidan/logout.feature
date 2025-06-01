Feature: Logout Functionality
  As an administrator of the Education Fund Payment Management System
  I want to be able to logout from the system
  So that I can securely end my session

  @logout @TC3
  Scenario: Successful logout from administrator dashboard
    Given User has opened the browser
    And User has been logged in as an Administrator
    And User has opened administrator dashboard page
    When User clicks on logout button
    And User clicks "Ya" button
    Then User is navigated to login page
    And System displays login page elements correctly
